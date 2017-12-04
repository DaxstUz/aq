/*
 * author: lachang@youzan.com
 * Copyright (C) 2016 Youzan, Inc. All Rights Reserved.
 */
package com.aqsystem.aqsystem.syncregister;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.aqsystem.aqsystem.R;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.ShareDataEvent;
import com.youzan.sdk.web.event.UserInfoEvent;
import com.youzan.sdk.web.plugin.YouzanBrowser;

/**
 * 同步调用.
 * 可直接打开网页, 但需订阅用户信息同步事件{@link UserInfoEvent}.
 */
public class SyncWebActivity extends Activity {
    public static final int REQUEST_LOGIN = 0x10;
    public static final String SIGN_URL = "sign_url";
    private YouzanBrowser mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initWebView();

        Intent intent = getIntent();
        String url = intent.getStringExtra(SIGN_URL);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        } else {
            //打开店铺链接等
            //TODO-WARNING: 请修改成你们店铺的链接
//            mWebView.loadUrl("https://h5.youzan.com/v2/showcase/homepage?alias=ujuvv9y9");
            mWebView.loadUrl("https://h5.koudaitong.com/v2/goods/1y8wyvqgdlonp");
        }
    }

    private void initWebView() {
        mWebView = (YouzanBrowser) findViewById(R.id.webview);
//        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mWebView.sharePage();//触发分享
//            }
//        });

        //订阅分享回调
        mWebView.subscribe(new ShareDataEvent() {
            @Override
            public void call(IBridgeEnv iBridgeEnv, GoodsShareModel data) {
                String content = data.getDesc() + data.getLink();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, data.getTitle());
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        //订阅用户信息同步事件
        mWebView.subscribe(new UserInfoEvent() {
            @Override
            public void call(IBridgeEnv iBridgeEnv) {
                //判断应用中用户是否登录
                if (User.getInstance().isLogin()) {
                    syncYzUser();
                } else {
                    //没有登录, 则跳转登录界面
                    Intent intent = new Intent(SyncWebActivity.this, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
            }
        });

        //上传文件的回调
        mWebView.setOnChooseFileCallback(new YouzanBrowser.OnChooseFile() {
            @Override
            public void onWebViewChooseFile(Intent intent, int i) throws ActivityNotFoundException {
                startActivityForResult(intent, i);
            }
        });
    }

    private void syncYzUser() {
        YouzanUser user = new YouzanUser();
        user.setUserId("XXXX");//用户唯一性ID, 你可以使用用户的ID等标识
        user.setGender(1);// "1"表示男性, "0"表示女性
        user.setNickName("小明的昵称");//昵称, 会显示在有赞商家版后台
        user.setTelephone("12345678901");//手机号
        user.setUserName("小明");//用户名
        YouzanSDK.syncRegisterUser(mWebView, user);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                //登录成功, 同步信息
                syncYzUser();
            } else {
                //登录失败, 回退
                onBackPressed();
            }
        } else {
            mWebView.isReceiveFileForWebView(requestCode, data);
            //处理WebView上传文件, 就上面一句就行了
        }
    }

    /**
     * 页面回退
     * YouzanBrowser.pageGoBack()返回True表示处理的是网页的回退
     */
    @Override
    public void onBackPressed() {
        if (!mWebView.pageGoBack()) {
            super.onBackPressed();
        }
    }

}
