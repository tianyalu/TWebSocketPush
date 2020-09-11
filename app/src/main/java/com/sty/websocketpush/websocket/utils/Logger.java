package com.sty.websocketpush.websocket.utils;

import android.content.Intent;
import android.util.Log;

import com.sty.websocketpush.BuildConfig;
import com.sty.websocketpush.websocket.application.MyApplication;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/8 9:58 AM
 */
public class Logger {
    private static boolean showLog = BuildConfig.DEBUG;
    private static boolean displayLogOnView = true;
    private static String defaultTag = "logger";

    public static void switchDisplayLogOnView(boolean isEnable) {
        displayLogOnView = isEnable;
    }

    public static boolean getDisplayLogOnViewStatus() {
        return displayLogOnView;
    }

    public static void v(String tag, String msg) {
        if(showLog) {
            Log.v(tag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(tag, msg);
        }
    }
    public static void v(String msg) {
        if(showLog) {
            Log.v(defaultTag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(defaultTag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if(showLog) {
            Log.i(tag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(tag, msg);
        }
    }
    public static void i(String msg) {
        if(showLog) {
            Log.i(defaultTag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(defaultTag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if(showLog) {
            Log.d(tag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(tag, msg);
        }
    }
    public static void d(String msg) {
        if(showLog) {
            Log.d(defaultTag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(defaultTag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if(showLog) {
            Log.w(tag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(tag, msg);
        }
    }
    public static void w(String msg) {
        if(showLog) {
            Log.w(defaultTag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(defaultTag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if(showLog) {
            Log.e(tag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(tag, msg);
        }
    }
    public static void e(String msg) {
        if(showLog) {
            Log.e(defaultTag, msg);
        }
        if(displayLogOnView) {
            sendLogBroadcast(defaultTag, msg);
        }
    }

    private static void sendLogBroadcast(String tag, String msg) {
        String newMsg = DateUtils.normalDateNow() + "：[" + tag + "] " + msg;
        Intent intent = new Intent();
        intent.setAction("com.sty.tpush.logger");
        intent.putExtra("LOG_MSG", newMsg);
        MyApplication.getAppContext().sendBroadcast(intent);
    }

}
