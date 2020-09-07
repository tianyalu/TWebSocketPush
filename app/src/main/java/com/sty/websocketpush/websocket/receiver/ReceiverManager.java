package com.sty.websocketpush.websocket.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 3:53 PM
 */
public class ReceiverManager {
    private static ScreenStatusReceiver mScreenStatusReceiver;

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
}
