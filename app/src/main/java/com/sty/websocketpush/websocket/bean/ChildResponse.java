package com.sty.websocketpush.websocket.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/7 9:41 AM
 */
public class ChildResponse implements Parcelable {
    private int code; //code == 0 表示成功
    private String msg;
    private String data;

    public ChildResponse() {

    }

    protected ChildResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.readString();
    }

    public static final Creator<ChildResponse> CREATOR = new Creator<ChildResponse>() {
        @Override
        public ChildResponse createFromParcel(Parcel in) {
            return new ChildResponse(in);
        }

        @Override
        public ChildResponse[] newArray(int size) {
            return new ChildResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeString(data);
    }

    public boolean isOK() {
        return code == 0;
    }
}
