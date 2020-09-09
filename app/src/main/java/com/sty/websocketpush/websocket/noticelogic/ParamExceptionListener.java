package com.sty.websocketpush.websocket.noticelogic;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.interfaces.INotifyListener;
import com.sty.websocketpush.websocket.utils.Logger;

/**
 * 具体逻辑对应的处理子类
 * action = paramException，参数异常消息， 连接socket时，缺少 token 或 device_id 参数 触发推送，推送后服务器会主动关闭 socket。
 * 格式：{"action":"paramException","resp_event":20,"resp":{"code":-1,"msg":"参数缺失，关闭连接！","data":{}}}
 * 没有使用参数类
 * @Author: tian
 * @UpdateDate: 2020/9/9 9:12 AM
 */
public class ParamExceptionListener implements INotifyListener {
    private static final String TAG = ParamExceptionListener.class.getSimpleName();
    @Override
    public void fire(Object o) {
        Logger.d(TAG, "参数异常消息， 连接socket时，缺少 token 或 device_id 参数");

        //暂时停止重连，
        WebSocketManager.getInstance().stopReconnectTemporarily();
        //请求登录获取token，
        //这里省略
        // 无论成功失败都继续重连
        WebSocketManager.getInstance().cancelStopReconnectTemporarily();
        WebSocketManager.getInstance().reconnect();
    }
}
