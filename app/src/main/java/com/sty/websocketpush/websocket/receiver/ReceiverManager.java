package com.sty.websocketpush.websocket.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.sty.websocketpush.WebSocketPushActivity;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 3:53 PM
 */
public class ReceiverManager {
    private static ScreenStatusReceiver mScreenStatusReceiver;
    private static NetStatusReceiver mNetStatusReceiver;

    public static void registerScreenStatusReceiver(Context context) {
        mScreenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter screenStatusIF = new IntentFilter();
        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(mScreenStatusReceiver, screenStatusIF);
    }
    public static void unRegisterScreenStatusReceiver(Context context) {
        context.unregisterReceiver(mScreenStatusReceiver);
    }

    public static void registerNetStatusReceiver(Context context) {
        mNetStatusReceiver = new NetStatusReceiver();
        IntentFilter netStatusIF = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mNetStatusReceiver, netStatusIF);
    }

    public static void unRegisterNetStatusReceiver(Context context) {
        context.unregisterReceiver(mNetStatusReceiver);
    }

}
