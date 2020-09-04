package com.sty.websocketpush.websocket;

import com.sty.websocketpush.websocket.bean.Request;

/**
 * 超时任务的回调
 * @Author: tian
 * @UpdateDate: 2020/9/3 2:27 PM
 */
public interface RequestCallBack {
    /**
     * 请求成功
     */
    void requestSuccess();

    /**
     * 请求失败
     * @param message 请求体
     * @param request 请求失败的消息
     */
    void requestFailed(String message, Request request);

    /**
     * 请求超时
     * @param request 请求体
     */
    void timeOut(Request request);
}
