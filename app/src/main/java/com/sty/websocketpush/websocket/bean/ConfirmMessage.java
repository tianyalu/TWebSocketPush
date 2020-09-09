package com.sty.websocketpush.websocket.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/9 10:59 AM
 */
public class ConfirmMessage {
    @SerializedName("message_id")
    private String messageId;

    public ConfirmMessage(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
