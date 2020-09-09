package com.sty.websocketpush.websocket.noticelogic;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.AnnounceTakeOutOrderNotify;
import com.sty.websocketpush.websocket.bean.ConfirmMessage;
import com.sty.websocketpush.websocket.interfaces.ICallback;
import com.sty.websocketpush.websocket.interfaces.INotifyListener;
import com.sty.websocketpush.websocket.interfaces.NotifyClass;
import com.sty.websocketpush.websocket.utils.Logger;

/**
 * 具体逻辑对应的处理子类
 * action = newMessage，新增订单 or 取消订单 触发推送。
 * 格式：{"action":"newMessage","resp_event":20,"resp":{"code":0,"msg":"api new message","data":{"message_id":375176,"title":"【美团】新的订单！1752","content":"【美团】您有新的订单，请及时处理。1752","priority":2,"type":5,"group":"meituan_order","group_oid":"37363700162950193","receive_type":0}}}
 * 注解类对应data
 * @Author: tian
 * @UpdateDate: 2020/9/7 11:16 AM
 */
@NotifyClass(AnnounceTakeOutOrderNotify.class)
public class NewTakeoutOrderListener implements INotifyListener<AnnounceTakeOutOrderNotify> {
    private static final String TAG = NewTakeoutOrderListener.class.getSimpleName();

    @Override
    public void fire(AnnounceTakeOutOrderNotify announceMsgNotify) {
        Logger.d(TAG, announceMsgNotify.toString());
        if(announceMsgNotify != null) {
            ConfirmMessage confirmMessage = new ConfirmMessage(announceMsgNotify.getMessageId());
//            WebSocketManager.getInstance().sendReq(Action.GOT_MESSAGE, confirmMessage, new ICallback() {
//                @Override
//                public void onSuccess(Object o) {
//                }
//
//                @Override
//                public void onFail(String msg) {
//                }
//            });
            //todo 这里发收到新订单的EventBus
        }
    }
}
