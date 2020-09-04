package com.sty.websocketpush.websocket;

/**
 * UI层回调接口
 * @Author: tian
 * @UpdateDate: 2020/9/4 3:19 PM
 */
public interface ICallback<T> {
    void onSuccess(T t);

    void onFail(String msg);
}
