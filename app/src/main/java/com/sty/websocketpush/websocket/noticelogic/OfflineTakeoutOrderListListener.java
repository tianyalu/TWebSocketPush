package com.sty.websocketpush.websocket.noticelogic;

import com.google.gson.Gson;
import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.AnnounceTakeOutOrderNotify;
import com.sty.websocketpush.websocket.bean.ConfirmMessage;
import com.sty.websocketpush.websocket.event.WsTakeoutOrderEvent;
import com.sty.websocketpush.websocket.interfaces.ICallback;
import com.sty.websocketpush.websocket.interfaces.INotifyListener;
import com.sty.websocketpush.websocket.interfaces.NotifyClass;
import com.sty.websocketpush.websocket.utils.Logger;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 具体逻辑对应的处理子类
 * action = offLineMessage，离线消息， 目标设备成功连接 socket 触发推送。
 * 格式：{"action":"offLineMessage","resp_event":20,"resp":{"code":0,"msg":"离线消息","data_type":1,"data":[{"message_id":375176,"title":"【美团】新的订单！1752","content":"【美团】您有新的订单，请及时处理。1752","priority":2,"type":5,"group":"meituan_order","group_oid":"37363700162950193","receive_type":0}]}}
 * 注解类对应data
 * @Author: tian
 * @UpdateDate: 2020/9/8 4:31 PM
 */
@NotifyClass(AnnounceTakeOutOrderNotify.class)
public class OfflineTakeoutOrderListListener implements INotifyListener<List<AnnounceTakeOutOrderNotify>> {
    private static final String TAG = OfflineTakeoutOrderListListener.class.getSimpleName();

    @Override
    public void fire(List<AnnounceTakeOutOrderNotify> announceTakeOutOrderNotifyList) {
        Logger.d(TAG, announceTakeOutOrderNotifyList.toString());
        if(announceTakeOutOrderNotifyList != null && announceTakeOutOrderNotifyList.size() > 0) {
            try {
                for (AnnounceTakeOutOrderNotify orderNotify : announceTakeOutOrderNotifyList) {
                    Logger.d(TAG, "orderNotify: " + orderNotify.toString());
                    ConfirmMessage confirmMessage = new ConfirmMessage(orderNotify.getMessageId());
                    WebSocketManager.getInstance().sendReq(Action.GOT_MESSAGE, confirmMessage, new ICallback() {
                        @Override
                        public void onSuccess(Object o) {
                            Logger.d(TAG, "success");
                        }

                        @Override
                        public void onFail(String msg) {
                            Logger.d(TAG, "fail");
                        }
                    });
                    EventBus.getDefault().post(new WsTakeoutOrderEvent(orderNotify));
                    //todo 在需要的地方接收
                }
            }catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG, e.getMessage());
            }
        }
    }
}
