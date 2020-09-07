package com.sty.websocketpush.websocket.bean;

/**
 * Socket请求时定义的动作参数
 * @Author: tian
 * @UpdateDate: 2020/9/4 3:16 PM
 */
public enum Action {
    LOGIN("login", 1, null),
    HEARTBEAT("heartbeat", 2, null),
    SYNC("sync", 3, null);

    private String action;
    private int reqEvent;
    private Class respClazz;

    Action(String action, int reqEvent, Class respClazz) {
        this.action = action;
        this.reqEvent = reqEvent;
        this.respClazz = respClazz;
    }

    public String getAction() {
        return action;
    }

    public int getReqEvent() {
        return reqEvent;
    }

    public Class getRespClazz() {
        return respClazz;
    }
}
