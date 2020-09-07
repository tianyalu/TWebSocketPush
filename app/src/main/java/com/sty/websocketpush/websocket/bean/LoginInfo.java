package com.sty.websocketpush.websocket.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 登录需要传递给服务器做验证的类（客户端信息类）
 * @Author: tian
 * @UpdateDate: 2020/9/7 3:30 PM
 */
public class LoginInfo {
    @SerializedName("store_gid")
    private String storeGid;

    @SerializedName("staff_gid")
    private String staffGid;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("staff_number")
    private String staffNumber;

    @SerializedName("pwd")
    private String pwd;

    public LoginInfo(String storeGid, String staffGid, String deviceId, String userName,
                     String staffNumber, String pwd) {
        this.storeGid = storeGid;
        this.staffGid = staffGid;
        this.deviceId = deviceId;
        this.userName = userName;
        this.staffNumber = staffNumber;
        this.pwd = pwd;
    }

    public static class Builder {
        private String storeGid;
        private String staffGid;
        private String deviceId;
        private String userName;
        private String staffNumber;
        private String pwd;

        public Builder setStoreGid(String storeGid) {
            this.storeGid = storeGid;
            return this;
        }

        public Builder setStaffGid(String staffGid) {
            this.staffGid = staffGid;
            return this;
        }

        public Builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setStaffNumber(String staffNumber) {
            this.staffNumber = staffNumber;
            return this;
        }

        public Builder setPwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public LoginInfo build() {
            return new LoginInfo(storeGid, staffGid, deviceId, userName, staffNumber, pwd);
        }
    }

    public String getStoreId() {
        return storeGid;
    }

    public String getStaffGid() {
        return staffGid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "storeId='" + storeGid + '\'' +
                ", staffGid='" + staffGid + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userName='" + userName + '\'' +
                ", staffNumber='" + staffNumber + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
