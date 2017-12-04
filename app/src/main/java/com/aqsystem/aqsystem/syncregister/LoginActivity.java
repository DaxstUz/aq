/*
 * author: lachang@youzan.com
 * Copyright (C) 2016 Youzan, Inc. All Rights Reserved.
 */
package com.aqsystem.aqsystem.syncregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aqsystem.aqsystem.R;


/**
 * 演示 - APP中登录界面
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟登录, 标识登录成功
                User.getInstance().setIsLogin(true);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.login_failed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
