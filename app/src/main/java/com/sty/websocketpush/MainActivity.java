package com.sty.websocketpush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sty.websocketpush.websocket.RequestListen;
import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.bean.Request;
import com.sty.websocketpush.websocket.bean.RequestChild;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnConnect;
    private Button btnDisconnect;
    private Button btnSendMessage;
    private Button btnKeepAlive;
    private Button btnKeepAliveOri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        addListeners();
    }

    private void initView() {
        btnConnect = findViewById(R.id.btn_connect);
        btnDisconnect = findViewById(R.id.btn_disconnect);
        btnSendMessage = findViewById(R.id.btn_send_message);
        btnKeepAlive = findViewById(R.id.btn_keep_alive);
        btnKeepAliveOri = findViewById(R.id.btn_keep_alive_ori);
    }

    private void addListeners() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnConnectClicked();
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.getInstance().disconnect();
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSendMessageClicked();
            }
        });
        btnKeepAlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.getInstance().startKeepAlive();
            }
        });
        btnKeepAliveOri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void onBtnConnectClicked() {
//        WebSocketManager.getInstance().connect();
    }

    private void onBtnSendMessageClicked() {
        RequestChild requestChild = new RequestChild.Builder()
                .setId("12345")
                .setClientType("你好， hello! ")
                .build();
        Request request = new Request.Builder()
                .setAction(WebSocketManager.ACTION_REQ_MESSAGE)
                .setTimeout(10 * 1000)
                .setReqCount(0)
//                .setRequestChild(requestChild)
                .build();

//        WebSocketManager.getInstance().sendRequest(request, request.getReqCount() + 1, new RequestListen() {
//            @Override
//            public void requestSuccess() {
//                Log.d(TAG, "requestSuccess: 发送消息成功");
//            }
//
//            @Override
//            public void requestFailed(String message) {
//                Log.d(TAG, "requestFailed: 发送消息失败： " + message);
//            }
//        });
    }
}