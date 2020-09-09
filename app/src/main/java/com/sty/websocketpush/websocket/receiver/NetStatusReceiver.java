package com.sty.websocketpush.websocket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.utils.Logger;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/4 2:40 PM
 */
public class NetStatusReceiver extends BroadcastReceiver {
    private static final String TAG = NetStatusReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            String action = intent.getAction();
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                //获取网络连接管理器
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取当前网络状态信息
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if(info != null && info.isAvailable()) {
                    Logger.d(TAG, "监听到可用网络切换，调用重连方法");
                    WebSocketManager.getInstance().reconnect();
                }
            }
        }
    }
}
