package com.sty.websocketpush.websocket;

import android.graphics.Paint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sty.websocketpush.websocket.bean.Response;
import com.sty.websocketpush.websocket.interfaces.INotifyListener;
import com.sty.websocketpush.websocket.interfaces.NotifyClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 11:02 AM
 */
public class NotifyListenerManager {
    private static final String TAG = NotifyListenerManager.class.getSimpleName();
    private Map<String, INotifyListener> map = new HashMap<>();
    private static final class SingletonHolder {
        private static NotifyListenerManager instance = new NotifyListenerManager();
    }

    private NotifyListenerManager() {
        register();
    }

    private void register() {
        map.put("notifyAnnounceMsg", new AnnounceMsgListener());
    }

    public static NotifyListenerManager getInstance() {
        return SingletonHolder.instance;
    }

    public void fire(Response response) {
        String action = response.getAction();
        String resp = response.getResp();
        INotifyListener listener = map.get(action);
        if(listener == null) {
            Log.d(TAG, "not found notify listener");
            return;
        }

        NotifyClass notifyClass = listener.getClass().getAnnotation(NotifyClass.class);
        Class<?> clazz = notifyClass.value();
        Object result = null;
        try {
            result = new Gson().fromJson(resp, clazz);
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        Log.d(TAG, result.toString());
        listener.fire(result);
    }
}
