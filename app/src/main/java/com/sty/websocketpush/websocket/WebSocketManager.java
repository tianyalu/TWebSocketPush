package com.sty.websocketpush.websocket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.sty.websocketpush.BuildConfig;
import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.Request;
import com.sty.websocketpush.websocket.utils.Constants;
import com.sty.websocketpush.websocket.utils.NetworkUtils;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import androidx.annotation.NonNull;


/**
 * @Author: tian
 * @UpdateDate: 2020/9/2 5:28 PM
 * 参考：https://cloud.tencent.com/developer/article/1610958
 * https://blog.csdn.net/zly921112/article/details/76758424
 */
public class WebSocketManager {
    private static final String TAG = WebSocketManager.class.getSimpleName();
    public static final String ACTION_KEEP_ALIVE = "action_keep_alive";
    public static final String ACTION_REQ_MESSAGE = "action_req_message";

    private static final int SUCCESS_HANDLE = 0x01;
    private static final int ERROR_HANDLE = 0x02;

    private static final int FRAME_QUEUE_SIZE = 5; //帧队列最大值
    private static final int CONNECT_TIMEOUT = 5 * 1000; //连接超时时间：10S
    private static final int REQUEST_TIMEOUT = 10 * 1000; //10S
    private static final int HEART_BEAT_RATE = 30 * 1000; //30S
    private int reconnectCount = 0; //重连次数
    private long minInterval = 3000; //重连最小时间间隔
    private long maxInterval = 60000; //重连最大时间间隔
    private AtomicLong atomicLong = new AtomicLong(SystemClock.uptimeMillis()); //每个请求的唯一标识

    private String url;

    private WebSocket mWebSocket;
    private WsStatus mStatus;
    private WsListener mListener;
    private ScheduledExecutorService singleExecutor;
    private ScheduledExecutorService heartBeatExecutor;
    private ConcurrentHashMap<Long, CallbackWrapper> callbacks = new ConcurrentHashMap<>(); //每次的任务以seqId为键值存放在hashMap中
    private Handler mHandler;
    private boolean isKeepHeartBeat = false;

    private WebSocketManager() {
        singleExecutor = Executors.newSingleThreadScheduledExecutor();
        mHandler = new MyHandler(Looper.getMainLooper());
        isKeepHeartBeat = true;
    }

    private static final class SingletonHolder {
        private static WebSocketManager instance = new WebSocketManager();
    }

    public static WebSocketManager getInstance() {
        return SingletonHolder.instance;
    }

    public enum WsStatus {
        /**
         * 连接成功
         */
        CONNECT_SUCCESS,
        /**
         * 连接失败
         */
        CONNECT_FAIL,
        /**
         * 正在连接
         */
        CONNECTING
    }

    /**
     * 初始化，建立连接（登录后建立连接）
     */
    public void init() {
        url = BuildConfig.DEBUG ? Constants.API_DEBUG_URL : Constants.API_RELEASE_URL;
        createAndConnectSocket();
        setStatus(WsStatus.CONNECTING);
    }

    /**
     * 创建并Socket并连接
     */
    private void createAndConnectSocket() {
        try {
            mWebSocket = new WebSocketFactory()
                    .createSocket(Constants.WEB_SOCKET_URL, CONNECT_TIMEOUT)
                    //设置帧队列最大值为5
                    .setFrameQueueSize(FRAME_QUEUE_SIZE)
                    //设置不允许服务端关闭连接却未发送关闭帧
                    .setMissingCloseFrameAllowed(false)
                    //添加回调监听
                    .addListener(mListener = new WsListener())
                    //异步连接
                    .connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reconnect() {
        if(!NetworkUtils.isNetConnect()) {
            reconnectCount = 0;
            Log.d(TAG, "重连时网络不可用");
            return;
        }

        //todo 用户登录状态的判断
        //socket不为空，并且当前连接已断开，并且不处于连接状态
        if(mWebSocket != null && !mWebSocket.isOpen() && getStatus() != WsStatus.CONNECTING) {
            reconnectCount++;
            setStatus(WsStatus.CONNECTING);

            //重连次数的策略
            long reconnectTime = minInterval;
            if(reconnectCount > 3) {
                //url 可以用默认的
                url = BuildConfig.DEBUG ? Constants.API_DEBUG_URL : Constants.API_RELEASE_URL;
                long temp = minInterval * (reconnectCount - 2);
                reconnectTime = temp > maxInterval ? maxInterval : temp;
            }
            Log.d(TAG, "准备开始第" + reconnectCount + "次重连，重连间隔: " + reconnectTime + "  -- url: " + url);
            mHandler.postDelayed(mReconnectTask, reconnectTime);
        }
    }

    public void disconnect() {
        if(mWebSocket != null) {
            mWebSocket.disconnect();
        }
    }

    private void cancelReconnect() {
        reconnectCount = 0;
        mHandler.removeCallbacks(mReconnectTask);
    }

    private Runnable mReconnectTask = new Runnable() {
        @Override
        public void run() {
            createAndConnectSocket();
        }
    };

    /**
     * WebSock回调事件监听器
     */
    private class WsListener extends WebSocketAdapter {

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
            setStatus(WsStatus.CONNECT_SUCCESS);
            Log.d(TAG, "onConnected: 连接成功");
        }

        @Override
        public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
            setStatus(WsStatus.CONNECT_FAIL);
            Log.d(TAG, "onConnectError: 连接错误");
            cause.printStackTrace();
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            setStatus(WsStatus.CONNECT_FAIL);
            //连接断开后自动重连
            reconnect();
            Log.d(TAG, "onDisconnected: 连接断开");
        }

        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            Log.d(TAG, "onTextMessage: 收到消息 --> "  + text);
        }
    }

    public void sendReq(Action action, Object req, ICallback callback) {
        sendReq(action, req, callback, REQUEST_TIMEOUT);
    }

    public void sendReq(Action action, Object req, ICallback callback, long timeout) {
        sendReq(action, req, callback, timeout, 1);
    }

    /**
     *
     * @param action Action
     * @param req 请求参数
     * @param callback 回调
     * @param timeout 超时时间
     * @param reqCount 请求次数
     */
    public <T> void sendReq(Action action, T req, final ICallback callback, final long timeout, int reqCount) {
        if(!NetworkUtils.isNetConnect()) {
            callback.onFail("网络不可用");
            Log.d(TAG, "sendReq: 网络不可用");
            return;
        }
        Request<T> request = new Request.Builder<T>()
                .setAction(action.getAction())
                .setReqEvent(action.getReqEvent())
                .setSeqId(atomicLong.getAndIncrement())
                .setReqCount(reqCount)
                .setReq(req)
                .setTimeout(timeout)
                .build();

        ScheduledFuture timeoutTask = enqueueTimeout(request, timeout); //添加超时任务
        IWsCallback tempCallback = new IWsCallback() {
            @Override
            public void onSuccess(Object o) {
                mHandler.obtainMessage(SUCCESS_HANDLE, new CallbackDataWrapper<>(callback, o))
                        .sendToTarget();
            }

            @Override
            public void onError(String msg, Request request, Action action) {
                mHandler.obtainMessage(ERROR_HANDLE, new CallbackDataWrapper<>(callback, msg))
                        .sendToTarget();
            }

            @Override
            public void onTimeout(Request request, Action action) {
                timeoutHandle(request, action, callback, timeout);
            }
        };
        callbacks.put(request.getSeqId(), new CallbackWrapper(tempCallback, timeoutTask, action, request));

        String requestMessage = new Gson().toJson(request);
        Log.d(TAG, "send text: " + requestMessage);
        mWebSocket.sendText(requestMessage);
    }

    /**
     * 添加超时任务
     * @param request
     * @param timeoutMillis
     * @return
     */
    private ScheduledFuture enqueueTimeout(final Request request, final long timeoutMillis) {
        Log.d(TAG, "  enqueueTimeout: 添加超时任务类型为： " + request.getAction());
        return singleExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                CallbackWrapper wrapper = callbacks.remove(request.getSeqId());
                if(wrapper != null) {
                    wrapper.getTempCallback().onTimeout(wrapper.getRequest(), wrapper.getAction());
                }
            }
        }, timeoutMillis, TimeUnit.MILLISECONDS); //延迟timeoutMillis毫秒执行
    }

    /**
     * 任务超时的处理
     * @param request
     */
    private void timeoutHandle(Request request, Action action, ICallback callback, long timeoutMillis) {
        if(request.getReqCount() > 3) {
            Log.d(TAG, "timeoutHandle: action(" + action.getAction() + ")连续3次请求超时，需要执行http请求");
            //TODO 走http请求
        } else {
            sendReq(action, request.getReq(), callback, timeoutMillis, request.getReqCount() + 1);
            Log.d(TAG, "timeoutHandle: action(" + action.getAction() + ")发起第 " +
                    request.getReqCount() + " 次请求");
        }
    }

    /**
     * 心跳
     */
//    private void keepAlive() {
//        final Request request = new Request.Builder()
//                .setReqCount(0)
//                .setTimeout(REQUEST_TIMEOUT)
//                .setAction(ACTION_KEEP_ALIVE)
//                .build();
//        WebSocketManager.getInstance().sendRequest(request, request.getReqCount() + 1, new RequestListen() {
//            @Override
//            public void requestSuccess() {
//                Log.d(TAG, "requestSuccess: 心跳发送成功了");
//                dispatchCallback(request.getAction(), true);
//            }
//
//            @Override
//            public void requestFailed(String message) {
//                Log.d(TAG, "requestFailed: 心跳发送失败了");
//                dispatchCallback(request.getAction(), false);
//            }
//        });
//    }

    /**
     * 开始心跳
     */
    public void startKeepAlive() {
        mHandler.postDelayed(mKeepAliveTask, HEART_BEAT_RATE);
    }

    /**
     * 心跳任务
     */
    private Runnable mKeepAliveTask = new Runnable() {
        @Override
        public void run() {
//            keepAlive();
            mHandler.removeCallbacks(mKeepAliveTask);
            mHandler.postDelayed(mKeepAliveTask, HEART_BEAT_RATE);
        }
    };


    /**
     * 任务回调
     * @param action
     * @param isSuccess
     */
    private void dispatchCallback(String action, boolean isSuccess) {
//        TimeoutTask outTask = callbacks.remove(action);
//        if(outTask == null) {
//            Log.d(TAG, "dispatchCallback: 任务队列为空");
//        }else {
//            outTask.getScheduledFuture().cancel(true);
//            if(isSuccess) {
//                outTask.getRequestCallBack().requestSuccess();
//            }else {
//                outTask.getRequestCallBack().requestFailed("dispatchCallback: 请求失败", new Request());
//            }
//        }
    }

    public WsStatus getStatus() {
        return mStatus;
    }

    public void setStatus(WsStatus wsStatus) {
        this.mStatus = wsStatus;
    }

    private boolean isNetConnect() {
        return WsStatus.CONNECT_SUCCESS == mStatus;
    }

    class MyHandler extends Handler {
        public MyHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SUCCESS_HANDLE:
                    CallbackDataWrapper successWrapper = (CallbackDataWrapper) msg.obj;
                    successWrapper.getCallback().onSuccess(successWrapper.getData());
                    break;
                case ERROR_HANDLE:
                    CallbackDataWrapper errorWrapper = (CallbackDataWrapper) msg.obj;
                    errorWrapper.getCallback().onFail((String) errorWrapper.getData());
                    break;
                default:
                    break;
            }
        }
    }
}
