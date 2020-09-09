package com.sty.websocketpush.websocket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.utils.Logger;

/**
 * 静态注册该广播无效，需要动态注册
 * @Author: tian
 * @UpdateDate: 2020/9/7 3:45 PM
 */
public class ScreenStatusReceiver extends BroadcastReceiver {
    private static final String TAG = ScreenStatusReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            if("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                Logger.d(TAG, "Detect screen on : " + WebSocketManager.getInstance().isConnected());
                if(!WebSocketManager.getInstance().isConnected()) {
                    WebSocketManager.getInstance().reconnect();
                }
            }else if("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                Logger.d(TAG, "Detect screen off");
            }
        }
    }
}
