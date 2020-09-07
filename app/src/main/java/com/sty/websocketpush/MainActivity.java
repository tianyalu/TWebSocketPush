package com.sty.websocketpush;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.LoginInfo;
import com.sty.websocketpush.websocket.bean.Request;
import com.sty.websocketpush.websocket.bean.RequestChild;
import com.sty.websocketpush.websocket.receiver.ReceiverManager;
import com.sty.websocketpush.websocket.service.TPushService;
import com.sty.websocketpush.websocket.utils.AppUtils;
import com.sty.websocketpush.websocket.utils.PermissionUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String[] needPermissions = {Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE};
    private Button btnConnect;
    private Button btnDisconnect;
    private Button btnSendMessage;
    private Button btnKeepAlive;
    private Button btnStartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        addListeners();
        ReceiverManager.registerScreenStatusReceiver(this);

        if (!PermissionUtils.checkPermissions(this, needPermissions)) {
            PermissionUtils.requestPermissions(this, needPermissions);
        }
    }

    private void initView() {
        btnConnect = findViewById(R.id.btn_connect);
        btnDisconnect = findViewById(R.id.btn_disconnect);
        btnSendMessage = findViewById(R.id.btn_send_message);
        btnKeepAlive = findViewById(R.id.btn_keep_alive);
        btnStartService = findViewById(R.id.btn_start_service);
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
                WebSocketManager.getInstance().startHeartbeat();
            }
        });
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TPushService.class);
                startService(intent);
            }
        });
    }

    private void onBtnConnectClicked() {

    }

    private void onBtnSendMessageClicked() {
        LoginInfo loginInfo = new LoginInfo.Builder()
                .setDeviceId(AppUtils.getUniqueDeviceId(this))
                .setStoreGid("1062")
                .setStaffGid("106200000000000249")
                .setUserName("三藏")
                .setUserName("106449")
                .setPwd("e410cc5446c6b0f624746dee5bb816cc")
                .build();
        Log.d(TAG, loginInfo.toString());

        WebSocketManager.getInstance().sendReq(Action.LOGIN, loginInfo, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReceiverManager.unRegisterScreenStatusReceiver(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.REQUEST_PERMISSIONS_CODE) {
            if (!PermissionUtils.verifyPermissions(grantResults)) {
                PermissionUtils.showMissingPermissionDialog(this);
            } else {

            }
        }
    }
}