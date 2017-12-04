package com.aqsystem.aqsystem.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.syncregister.*;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.ShareDataEvent;
import com.youzan.sdk.web.event.UserInfoEvent;
import com.youzan.sdk.web.plugin.YouzanBrowser;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.result.account.BLGetUserPhoneAndEmailResult;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class WebActivity extends BaseActivity {

//    YouzanBrowser webView = new YouzanBrowser(this);
//webView.loadUrl("店铺链接");

    public static final int REQUEST_LOGIN = 0x10;
    private YouzanBrowser webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView() {
        webview = findView(R.id.webview);

//        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webview.sharePage();//触发分享
//            }
//        });

        String url = getIntent().getStringExtra("url");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        //订阅用户信息同步事件
        webview.subscribe(new UserInfoEvent() {
            @Override
            public void call(IBridgeEnv iBridgeEnv) {
                //判断应用中用户是否登录
//                if (User.getInstance().isLogin()) {
                    syncYzUser();
//                } else {
//                    //没有登录, 则跳转登录界面
//                    Intent intent = new Intent(WebActivity.this, com.aqsystem.aqsystem.syncregister.LoginActivity.class);
//                    startActivityForResult(intent, REQUEST_LOGIN);
//                }
            }
        });


        //订阅分享回调
        webview.subscribe(new ShareDataEvent() {
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


        //上传文件的回调
        webview.setOnChooseFileCallback(new YouzanBrowser.OnChooseFile() {
            @Override
            public void onWebViewChooseFile(Intent intent, int i) throws ActivityNotFoundException {
                startActivityForResult(intent, i);
            }
        });
    }


    private void syncYzUser() {

        YouzanUser user = new YouzanUser();
        user.setUserId(BLApplcation.mBLUserInfoUnits.getTel());//用户唯一性ID, 你可以使用用户的ID等标识
        user.setGender("1");// "1"表示男性, "0"表示女性
        user.setNickName(BLApplcation.mBLUserInfoUnits.getNickname());//昵称, 会显示在有赞商家版后台
        user.setTelephone(BLApplcation.mBLUserInfoUnits.getTel());//手机号
        user.setUserName(BLApplcation.mBLUserInfoUnits.getNickname());//用户名

//        user.setUserId(BLApplcation.mBLUserInfoUnits.getTel());//用户唯一性ID, 你可以使用用户的ID等标识
//        user.setGender(BLApplcation.mBLUserInfoUnits.getSex());// "1"表示男性, "0"表示女性
//        user.setNickName(BLApplcation.mBLUserInfoUnits.getNickname());//昵称, 会显示在有赞商家版后台
//        user.setTelephone(BLApplcation.mBLUserInfoUnits.getTel());//手机号
//        user.setUserName(BLApplcation.mBLUserInfoUnits.getNickname());//用户名
        YouzanSDK.syncRegisterUser(webview, user);

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
            webview.isReceiveFileForWebView(requestCode, data);
            //处理WebView上传文件, 就上面一句就行了
        }
    }

    /**
     * 页面回退
     * YouzanBrowser.pageGoBack()返回True表示处理的是网页的回退
     */
    @Override
    public void onBackPressed() {
        if (!webview.pageGoBack()) {
            super.onBackPressed();
        }
    }
}
