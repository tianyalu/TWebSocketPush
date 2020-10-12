package com.sty.websocketpush.websocket;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sty.websocketpush.websocket.bean.ChildResponse;
import com.sty.websocketpush.websocket.bean.Codec;
import com.sty.websocketpush.websocket.bean.Response;
import com.sty.websocketpush.websocket.interfaces.INotifyListener;
import com.sty.websocketpush.websocket.interfaces.NotifyClass;
import com.sty.websocketpush.websocket.noticelogic.AuthExceptionListener;
import com.sty.websocketpush.websocket.noticelogic.NewTakeoutOrderListener;
import com.sty.websocketpush.websocket.noticelogic.OfflineTakeoutOrderListListener;
import com.sty.websocketpush.websocket.noticelogic.ParamExceptionListener;
import com.sty.websocketpush.websocket.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        map.put("newMessage", new NewTakeoutOrderListener());
        map.put("offLineMessage", new OfflineTakeoutOrderListListener());
        map.put("paramException", new ParamExceptionListener());
        map.put("authException", new AuthExceptionListener());
    }

    public static NotifyListenerManager getInstance() {
        return SingletonHolder.instance;
    }

    public void fire(Response response) {
        String action = response.getAction();
        String resp = response.getResp();
        ChildResponse childResponse = Codec.decoderChildResp(resp); //解析第二层Bean
        String dataStr = childResponse.getData();
        Logger.d(TAG, "dataStr: " + dataStr);
        INotifyListener listener = map.get(action);
        if(listener == null) {
            Logger.d(TAG, "not found notify listener");
            return;
        }

        Object result = null;
        if(!TextUtils.isEmpty(dataStr) && !"{}".equals(dataStr)) {
            NotifyClass notifyClass = listener.getClass().getAnnotation(NotifyClass.class);
            final Class<?> clazz = notifyClass.value();

            try {
                int dataType = childResponse.getDataType();
                if (dataType == 0) { //对象
                    result = new Gson().fromJson(dataStr, clazz);
                } else if (dataType == 1) { //数组
                    List<?> lists;
                    List resultList = new ArrayList();
                    lists = new Gson().fromJson(dataStr, new TypeToken<List<?>>() {
                    }.getType());
                    if(lists != null && lists.size() > 0) {
                        for (Object list : lists) {
                            Object item = new Gson().fromJson(list.toString(), clazz);
                            resultList.add(item);
                        }
                    }
                    result = resultList;
                    Logger.d(TAG, "result: " + result.toString());
                }

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Logger.d(TAG, "error: " + e.getMessage());
            }
        }

        listener.fire(result);
    }
}
