package com.sty.websocketpush.websocket.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/10 11:56 AM
 */
public class LoggerController implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean enabled;

    public LoggerController(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
