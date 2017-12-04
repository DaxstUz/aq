package com.aqsystem.aqsystem.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.MainActivity;
import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLCommonUtils;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.http.engine.OnRegister;
import com.youzan.sdk.http.engine.QueryError;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.constants.account.BLAccountErrCode;
import cn.com.broadlink.sdk.result.account.BLLoginResult;

/**
 * 登录
 * Created by YeJin on 2016/5/9.
 */
public class LoginActivity extends BaseActivity{

    private EditText mUserNameView, mPasswordView;

    private Button mRegsiterBtn, mLoginBtn,findpsd;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        findView();

        setListener();
    }

    private void findView(){
        mUserNameView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mRegsiterBtn = (Button) findViewById(R.id.register);
        mLoginBtn = (Button) findViewById(R.id.btn_login);

        findpsd=findView(R.id.findpsd);
    }

    private void setListener(){
        mRegsiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterByPhoneActivity.class);
                startActivity(intent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = mUserNameView.getText().toString();
                String password = mPasswordView.getText().toString();
                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
                    BLCommonUtils.toastShow(LoginActivity.this, "请输入账号和密码！");
                }else{
                    new LoginTask().execute(userName, password);
                }
            }
        });

        findpsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, FindPsdActivity.class);
                startActivity(intent);
            }
        });
    }

    //登录
    private class LoginTask extends AsyncTask<String, Void, BLLoginResult>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("登录中...");
            progressDialog.show();
        }

        @Override
        protected BLLoginResult doInBackground(String... params) {
            return BLLet.Account.login(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(BLLoginResult loginResult) {
            super.onPostExecute(loginResult);
            progressDialog.dismiss();
            if(loginResult != null && loginResult.getError() == BLAccountErrCode.SUCCESS){
                //保存登录信息
                BLApplcation.mBLUserInfoUnits.login(loginResult.getUserid(),
                        loginResult.getLoginsession(), loginResult.getNickname(),
                        loginResult.getIconpath(), loginResult.getLoginip(),
                        loginResult.getLogintime(), loginResult.getSex(), null,userName);


                YouzanUser youzanUser=new YouzanUser();
                youzanUser.setUserId(loginResult.getUserid());
                youzanUser.setGender(loginResult.getSex());
                youzanUser.setTelephone(loginResult.getUserid());
                youzanUser.setUserName(loginResult.getNickname());

                YouzanSDK.asyncRegisterUser(youzanUser, new OnRegister() {
                    //注册失败, 请参考错误信息修改注册参数
                    //如报非法请求, 请检查UA, AppID和AppSecret是否正确
                    @Override
                    public void onFailed(QueryError error) {
                        Toast.makeText(LoginActivity.this, error.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    //注册成功, 打开有赞入口网页
                    @Override
                    public void onSuccess() {
//                        Intent intent = new Intent(LoginActivity.this, BaseWebActivity.class);
                        //打开页面...
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                });


            }else{
                BLCommonUtils.toastShow(LoginActivity.this, loginResult.getMsg());
            }
        }

    }

}
