package com.sty.websocketpush.websocket.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sty.websocketpush.websocket.application.MyApplication;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/4 2:22 PM
 */
public class NetworkUtils {

    /**
     * 判断网络是否连接
     * @return
     */
    public static boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected()) {
                //当前网络是连接的
                if(info.getState() == NetworkInfo.State.CONNECTED) {
                    //当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
