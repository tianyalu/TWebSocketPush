package com.sty.websocketpush.websocket.application;

import android.text.TextUtils;

import com.sty.websocketpush.websocket.utils.Constants;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/8 2:15 PM
 */
public class AppConfig {
    public static String getToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEyNjAsImlzcyI6Imh0dHA6Ly8xMjAuNzYuMjIwLjEwNTo1MDUvYXBpL0F1dGgvbG9naW4iLCJpYXQiOjE1OTk1NDkwNzYsImV4cCI6MTY1MTM4OTA3NiwibmJmIjoxNTk5NTQ5MDc2LCJqdGkiOiIySlFFSm1oSG9CNkxJYmlYIn0.4xjYV2JDlfqGJQ1v1Ou-65b91IYJgAo6k1TCnCMh_g0";
    }

    public static String getDeviceId() {
        return "dc188d05-2eac-3ef2-8af1-736f29944520";
    }

    public static String getSocketUrl() {
        String socketUrl = Constants.WEB_SOCKET_URL;
        String token = AppConfig.getToken();
        if(!TextUtils.isEmpty(token)) {
            token = token.replaceAll(" ", "");
            if(token.startsWith("Bearer")) {
                token = token.substring(6);
            }
            socketUrl += "?token=" + token;
            socketUrl += "&device_id=" + getDeviceId();
        }
        return socketUrl;
    }
}
