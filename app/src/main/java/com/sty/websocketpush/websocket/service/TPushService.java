package com.sty.websocketpush.websocket.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.sty.websocketpush.websocket.WebSocketManager;

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
        Log.d(TAG, "onCreate");
        mWebSocketManager = WebSocketManager.getInstance();
        mWebSocketManager.init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if(mWebSocketManager != null) {
            mWebSocketManager.disconnect();
        }
        //发送广播重启服务
        Intent intent = new Intent("com.sty.tpush.destroy");
        sendBroadcast(intent);
    }
}
