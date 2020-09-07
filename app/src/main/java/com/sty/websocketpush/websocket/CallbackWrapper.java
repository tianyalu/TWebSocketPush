package com.sty.websocketpush.websocket;

import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.Request;
import com.sty.websocketpush.websocket.interfaces.IWsCallback;

import java.util.concurrent.ScheduledFuture;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/4 5:06 PM
 */
public class CallbackWrapper {
    private final IWsCallback tempCallback;
    private final ScheduledFuture timeoutTask;
    private final Action action;
    private final Request request;

    public CallbackWrapper(IWsCallback tempCallback, ScheduledFuture timeoutTask, Action action, Request request) {
        this.tempCallback = tempCallback;
        this.timeoutTask = timeoutTask;
        this.action = action;
        this.request = request;
    }

    public IWsCallback getTempCallback() {
        return tempCallback;
    }

    public ScheduledFuture getTimeoutTask() {
        return timeoutTask;
    }

    public Action getAction() {
        return action;
    }

    public Request getRequest() {
        return request;
    }
}
