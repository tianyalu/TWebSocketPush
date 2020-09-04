package com.sty.websocketpush.websocket;

import com.sty.websocketpush.websocket.bean.Request;

import java.util.concurrent.ScheduledFuture;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/3 2:26 PM
 */
public class TimeoutTask {
    /**
     * 请求主体
     */
    private Request request;

    /**
     * 通用返回
     */
    private RequestCallBack requestCallBack;

    /**
     * r 任务
     */
    private ScheduledFuture scheduledFuture;

    public TimeoutTask(Request request, RequestCallBack requestCallBack, ScheduledFuture scheduledFuture) {
        this.request = request;
        this.requestCallBack = requestCallBack;
        this.scheduledFuture = scheduledFuture;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestCallBack getRequestCallBack() {
        return requestCallBack;
    }

    public void setRequestCallBack(RequestCallBack requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
