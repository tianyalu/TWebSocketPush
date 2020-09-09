package com.sty.websocketpush.websocket.bean;

/**
 * Socket请求时定义的动作参数
 * @Author: tian
 * @UpdateDate: 2020/9/4 3:16 PM
 */
public enum Action {
    //暂未使用
    LOGIN("login", 1, null),
    //心跳
    HEARTBEAT("heartbeat", 2, null),
    //暂未使用
    SYNC("sync", 3, null),
    //收到推送消息后的回复
    GOT_MESSAGE("gotMessage", 4, null);

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
