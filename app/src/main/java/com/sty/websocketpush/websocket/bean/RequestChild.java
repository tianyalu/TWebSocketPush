package com.sty.websocketpush.websocket.bean;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/2 6:04 PM
 */
public class RequestChild {
    /**
     * 设备类型
     */
    private String clientType;

    /**
     * 用于用户注册的id
     */
    private String id;

    public RequestChild() {
    }

    public String getClientType() {
        return clientType;
    }

    public String getId() {
        return id;
    }

    public RequestChild(String clientType, String id) {
        this.clientType = clientType;
        this.id = id;
    }

    public static class Builder {
        private String clientType;
        private String id;

        public RequestChild.Builder setClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public RequestChild.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public RequestChild build() {
            return new RequestChild(clientType, id);
        }
    }
}
