package com.sty.websocketpush.websocket.interfaces;

/**
 * 通知的回调接口
 * @Author: tian
 * @UpdateDate: 2020/9/7 11:10 AM
 */
public interface INotifyListener<T> {
    void fire(T t);
}
