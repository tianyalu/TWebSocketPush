package com.sty.websocketpush.websocket.event;

import com.sty.websocketpush.websocket.bean.AnnounceTakeOutOrderNotify;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/9 2:50 PM
 */
public class WsTakeoutOrderEvent {
    private AnnounceTakeOutOrderNotify orderNotify;

    public WsTakeoutOrderEvent(AnnounceTakeOutOrderNotify orderNotify) {
        this.orderNotify = orderNotify;
    }

    public AnnounceTakeOutOrderNotify getOrderNotify() {
        return orderNotify;
    }

    public void setOrderNotify(AnnounceTakeOutOrderNotify orderNotify) {
        this.orderNotify = orderNotify;
    }
}
