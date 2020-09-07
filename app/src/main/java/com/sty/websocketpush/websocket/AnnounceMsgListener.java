package com.sty.websocketpush.websocket;

import com.sty.websocketpush.websocket.bean.AnnounceMsgNotify;
import com.sty.websocketpush.websocket.interfaces.INotifyListener;
import com.sty.websocketpush.websocket.interfaces.NotifyClass;

/**
 * 具体逻辑对应的处理子类
 * @Author: tian
 * @UpdateDate: 2020/9/7 11:16 AM
 */
@NotifyClass(AnnounceMsgNotify.class)
public class AnnounceMsgListener implements INotifyListener<AnnounceMsgNotify> {

    @Override
    public void fire(AnnounceMsgNotify announceMsgNotify) {
        //todo 这里发EventBus
    }
}
