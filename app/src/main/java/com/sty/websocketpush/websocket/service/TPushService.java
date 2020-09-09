package com.sty.websocketpush.websocket.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.utils.Logger;

import androidx.annotation.Nullable;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 12:00 PM
 */
public class TPushService extends Service {
    private static final String TAG = TPushService.class.getSimpleName();
    private WebSocketManager mWebSocketManager;

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
        return null;
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
