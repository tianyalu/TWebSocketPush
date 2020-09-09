package com.sty.websocketpush;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sty.websocketpush.websocket.WebSocketManager;
import com.sty.websocketpush.websocket.adapter.RcvLogAdapter;
import com.sty.websocketpush.websocket.bean.Action;
import com.sty.websocketpush.websocket.bean.LoginInfo;
import com.sty.websocketpush.websocket.receiver.ReceiverManager;
import com.sty.websocketpush.websocket.service.TPushService;
import com.sty.websocketpush.websocket.utils.PermissionUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WebSocketPushActivity extends AppCompatActivity {
    private static final String TAG = WebSocketPushActivity.class.getSimpleName();
    private String[] needPermissions = {Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_STATE};
    private Button btnConnect;
    private Button btnStartService;
    private Button btnDisconnect;
    private Button btnLogin;
    private Button btnKeepAlive;
    private Button btnSendMessage;
    private Button btnClearLog;
    private RecyclerView rcvLog;
    private RcvLogAdapter rcvAdapter;
    private LogDisplayReceiver logDisplayReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket_push);

        initView();
        addListeners();
        initReceiver();
        requestPermissions();
    }

    private void initView() {
        btnConnect = findViewById(R.id.btn_connect);
        btnStartService = findViewById(R.id.btn_start_service);
        btnDisconnect = findViewById(R.id.btn_disconnect);
        btnLogin = findViewById(R.id.btn_login);
        btnKeepAlive = findViewById(R.id.btn_keep_alive);
        btnSendMessage = findViewById(R.id.btn_send_message);
        btnClearLog = findViewById(R.id.btn_clear_log);

        rcvLog = findViewById(R.id.rcv_log);
        rcvAdapter = new RcvLogAdapter(this);
        rcvLog.setLayoutManager(new LinearLayoutManager(this));
        rcvLog.setAdapter(rcvAdapter);
    }

    private void addListeners() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnConnectClicked();
            }
        });
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebSocketPushActivity.this, TPushService.class);
                startService(intent);
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.getInstance().disconnect();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLoginClicked();
            }
        });
        btnKeepAlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketManager.getInstance().startHeartbeat();
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSendMessageClicked();
            }
        });
        btnClearLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rcvAdapter != null) {
                    rcvAdapter.clearLog();
                }
            }
        });

    }

    private void requestPermissions() {
        if (!PermissionUtils.checkPermissions(this, needPermissions)) {
            PermissionUtils.requestPermissions(this, needPermissions);
        }
    }

    private void initReceiver() {
        ReceiverManager.registerScreenStatusReceiver(this);

        logDisplayReceiver = new LogDisplayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sty.tpush.logger");
        registerReceiver(logDisplayReceiver, intentFilter);
    }
    private void onBtnConnectClicked() {
        WebSocketManager.getInstance().init();
    }

    private void onBtnLoginClicked() {
        LoginInfo loginInfo = new LoginInfo.Builder()
                .setDeviceId("dc188d05-2eac-3ef2-8af1-736f29944520")
                .setStoreGid("1062")
                .setStaffGid("106200000000000249")
                .setUserName("三藏")
                .setStaffNumber("106449")
                .setPwd("8e6a3e2c77fdf2b21c6d777962e1d857600cd0f1")
                .build();
        Log.d(TAG, loginInfo.toString());

        WebSocketManager.getInstance().sendReq(Action.LOGIN, loginInfo, null);
    }

    private void onBtnSendMessageClicked() {
        LoginInfo loginInfo = new LoginInfo.Builder()
                .setDeviceId("dc188d05-2eac-3ef2-8af1-736f29944520")
                .setStoreGid("1062")
                .setStaffGid("106200000000000249")
                .setUserName("三藏")
                .setStaffNumber("106449")
                .setPwd("8e6a3e2c77fdf2b21c6d777962e1d857600cd0f1")
                .build();
        Log.d(TAG, loginInfo.toString());

        WebSocketManager.getInstance().sendReq(Action.LOGIN, loginInfo, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReceiverManager.unRegisterScreenStatusReceiver(this);
        unregisterReceiver(logDisplayReceiver);
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

    public class LogDisplayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null) {
                String msg = intent.getStringExtra("LOG_MSG");
//                Log.d(TAG, "currentThread: " + Thread.currentThread().getName() + " /msg: " + msg);
                rcvAdapter.addData(msg);
                rcvLog.scrollToPosition(0);
            }
        }
    }
}