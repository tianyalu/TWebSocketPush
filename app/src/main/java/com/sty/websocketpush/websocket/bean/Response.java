package com.sty.websocketpush.websocket.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器响应
 * @Author: tian
 * @UpdateDate: 2020/9/7 9:27 AM
 */
public class Response{
    /**
     * 10: 代表客户端请求的响应
     * 20：代表服务器端的主动推送
     */
    @SerializedName("resp_event")
    private int respEvent;

    @SerializedName("seq_id")
    private String seqId;

    private String action;

    private String resp;

    @SerializedName("child_response")
    private ChildResponse childResponse;

    public Response(){}

    public int getRespEvent() {
        return respEvent;
    }

    public void setRespEvent(int respEvent) {
        this.respEvent = respEvent;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public ChildResponse getChildResponse() {
        return childResponse;
    }

    public void setChildResponse(ChildResponse childResponse) {
        this.childResponse = childResponse;
    }
}
