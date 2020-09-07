package com.sty.websocketpush.websocket.interfaces;

import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.Request;

/**
 * 中间层回调接口
 * @Author: tian
 * @UpdateDate: 2020/9/4 4:13 PM
 */
public interface IWsCallback<T> {
    void onSuccess(T t);

    void onError(String msg, Request request, Action action);

    void onTimeout(Request request, Action action);
}
