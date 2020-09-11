package com.sty.websocketpush.websocket.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.bean.LoggerController;
import com.sty.websocketpush.websocket.utils.Constants;
import com.sty.websocketpush.websocket.utils.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.sty.websocketpush.websocket.utils.Constants.SET_DISPLAY_LOG_ON_VIEW;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 12:00 PM
 */
public class TPushService extends Service {
    private static final String TAG = TPushService.class.getSimpleName();
    private WebSocketManager mWebSocketManager;
    //将Handler实例传递给Messenger
    //适用于跨进程通讯
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    //仅适用于同一个进程通讯
    private class MyBinder extends Binder implements IService{
        @Override
        public void switchDisplayLogOnViewStatus() {
            Logger.switchDisplayLogOnView(!Logger.getDisplayLogOnViewStatus());
        }
    }

    /**android  service跨进程通讯
     * 用来处理消息的Handler
     */
    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SET_DISPLAY_LOG_ON_VIEW:
//                    Bundle bundle = msg.getData();
//                    if(bundle != null) {
//                        boolean enable = bundle.getBoolean("log_enable");
//                        Log.d(TAG, "logger status: " + enable + " --> " + Thread.currentThread().getName());
//                        Logger.switchDisplayLogOnView(enable);
//
//                    }

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate");
        mWebSocketManager = WebSocketManager.getInstance();
        mWebSocketManager.init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(TAG, "onBind");
        return new MyBinder();
//        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy");
        if(mWebSocketManager != null) {
            mWebSocketManager.disconnect();
        }
        //发送广播重启服务
        Intent intent = new Intent("com.sty.tpush.destroy");
        sendBroadcast(intent);
    }
}
