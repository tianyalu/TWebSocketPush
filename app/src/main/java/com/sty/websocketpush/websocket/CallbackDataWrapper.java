package com.sty.websocketpush.websocket;

import com.sty.websocketpush.websocket.interfaces.ICallback;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/4 4:21 PM
 */
public class CallbackDataWrapper<T> {
    private ICallback<T> callback;
    private Object data;

    public CallbackDataWrapper(ICallback<T> callback, Object data) {
        this.callback = callback;
        this.data = data;
    }

    public ICallback<T> getCallback() {
        return callback;
    }

    public void setCallback(ICallback<T> callback) {
        this.callback = callback;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
