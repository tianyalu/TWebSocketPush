package com.sty.websocketpush.websocket.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 11:16 AM
 */
public class AnnounceMsgNotify {
    @SerializedName("msg_version")
    private String msgVersion;

    public String getMsgVersion() {
        return msgVersion;
    }

    public void setMsgVersion(String msgVersion) {
        this.msgVersion = msgVersion;
    }
}
