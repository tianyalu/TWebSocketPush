package com.sty.websocketpush.websocket;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/3 2:24 PM
 */
public interface RequestListen {
    /**
     * 请求成功
     */
    void requestSuccess();

    /**
     * 请求失败
     * @param message
     */
    void requestFailed(String message);
}
