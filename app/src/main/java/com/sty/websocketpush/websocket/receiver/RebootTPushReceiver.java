package com.sty.websocketpush.websocket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sty.websocketpush.websocket.service.TPushService;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 3:03 PM
 */
public class RebootTPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && "com.sty.tpush.destroy".equals(intent.getAction())) {
            Intent tPushIntent = new Intent(context, TPushService.class);
            context.startService(tPushIntent);
        }
    }
}
