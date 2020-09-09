package com.sty.websocketpush.websocket.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器推送的外卖订单消息体
 * @Author: tian
 * @UpdateDate: 2020/9/7 11:16 AM
 */
public class AnnounceTakeOutOrderNotify {
    @SerializedName("message_id")
    private String messageId;
    private String title;
    private String content;
    private int priority;
    private int type;
    /**
     * 外卖订单的事件类型:
     * 1. meituan_order  美团订单
     * 2. elemebai_order  饿百订单
     * 3. meituan_order@cancel  美团订单取消
     * 4. elemebai_order@cancel  饿了么订单取消
     * 5. oto_order@presale  线上商城订单预售
     * 6. oto_order@nowsale  线上商城订单现售
     * 7. oto_order@presale@cancel  线上商城订单预售取消
     * 8. oto_order@nowsale@cancel  线上商城订单现售取消
     */
    private String group;
    @SerializedName("group_oid")
    /**
     * 外卖订单号
     */
    private String groupOid;
    @SerializedName("receive_type")
    /**
     * 线上商城参数：收货方式：1自提，2配送
     */
    private int receiveType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupOid() {
        return groupOid;
    }

    public void setGroupOid(String groupOid) {
        this.groupOid = groupOid;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    @Override
    public String toString() {
        return "AnnounceTakeOutOrderNotify{" +
                "messageId='" + messageId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", priority=" + priority +
                ", type=" + type +
                ", group='" + group + '\'' +
                ", groupOid='" + groupOid + '\'' +
                ", receiveType=" + receiveType +
                '}';
    }
}
