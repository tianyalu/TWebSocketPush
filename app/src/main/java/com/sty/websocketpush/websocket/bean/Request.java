package com.sty.websocketpush.websocket.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/2 6:03 PM
 */
public class Request<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 行为
     */
    @SerializedName("action")
    private String action;

    /**
     * 和Action一起组装URL
     */
    @SerializedName("req_event")
    private int reqEvent;

    /**
     * 用来做每次请求的唯一标识
     */
    @SerializedName("seq_id")
    private long seqId;

    /**
     * 请求体
     */
    @SerializedName("req")
    private T req;

    /**
     * 请求次数
     */
    private transient int reqCount;

    /**
     * 超时的时间
     */
    private transient long timeout;

    public Request() {

    }

    public Request(String action, int reqEvent, long seqId, T req, int reqCount, long timeout) {
        this.action = action;
        this.reqEvent = reqEvent;
        this.seqId = seqId;
        this.req = req;
        this.reqCount = reqCount;
        this.timeout = timeout;
    }

    public static class Builder<T> {
        //action 请求类型
        private String action;
        private int reqEvent;
        private long seqId;
        //请求子类数据，按照具体业务划分
        private T req;
        //请求次数 便于重试
        private int reqCount;
        //超时时间
        private long timeout;

        public Builder<T> setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder<T> setReqEvent(int reqEvent) {
            this.reqEvent = reqEvent;
            return this;
        }

        public Builder<T> setSeqId(long seqId) {
            this.seqId = seqId;
            return this;
        }

        public Builder<T> setReq(T req) {
            this.req = req;
            return this;
        }

        public Builder<T> setReqCount(int reqCount) {
            this.reqCount = reqCount;
            return this;
        }

        public Builder<T> setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Request<T> build() {
            return new Request<T>(action, reqEvent, seqId, req, reqCount, timeout);
        }
    }

    public String getAction() {
        return action;
    }

    public int getReqEvent() {
        return reqEvent;
    }

    public long getSeqId() {
        return seqId;
    }

    public T getReq() {
        return req;
    }

    public int getReqCount() {
        return reqCount;
    }

    public long getTimeout() {
        return timeout;
    }
}
