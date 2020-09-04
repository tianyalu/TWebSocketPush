package com.sty.websocketpush.websocket.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.sty.websocketpush.websocket.ForegroundCallbacks;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/4 2:13 PM
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        //因为打算用服务做，所以不用这种方式
        //initAppStatusListener();
    }

    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                Log.d(TAG, "应用回到前台调用重连方法");
            }

            @Override
            public void onBecameBackground() {

            }
        });
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
