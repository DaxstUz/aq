package com.aqsystem.aqsystem.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLCommonUtils;

import java.io.File;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.constants.account.BLAccountErrCode;
import cn.com.broadlink.sdk.param.account.BLRegistParam;
import cn.com.broadlink.sdk.result.account.BLBaseResult;
import cn.com.broadlink.sdk.result.account.BLLoginResult;

/**
 * Created by Administrator on 2017/1/22 0022.
 */

public class FindPsdActivity extends BaseActivity {

    private EditText mCountryCodeView, mPhoneView, mVCodeView;
    private EditText mPasswordView;

    private Button mGetVCodeBtn, mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpsd_layout);

        findView();

        setListener();
    }

    private void findView(){
        mCountryCodeView = (EditText) findViewById(R.id.country_code_view);
        mPhoneView = (EditText) findViewById(R.id.phone_view);
        mVCodeView = (EditText) findViewById(R.id.v_code_view);
        mPasswordView = (EditText) findViewById(R.id.password_view);

        mGetVCodeBtn = (Button) findViewById(R.id.btn_get_v_code);
        mLoginBtn = (Button) findViewById(R.id.btn_register);
    }
    String phone;
    private void setListener(){
        mGetVCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryCode = mCountryCodeView.getText().toString();
                phone = mPhoneView.getText().toString();
                if(TextUtils.isEmpty(countryCode) || TextUtils.isEmpty(phone)){
                    BLCommonUtils.toastShow(FindPsdActivity.this, "请检查手机区号和手机号");
                }else{
                    new FindPsdActivity.GetVCodeTask().execute(phone, countryCode);
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryCode = mCountryCodeView.getText().toString();
                String phone = mPhoneView.getText().toString();
                String vCode = mVCodeView.getText().toString();
                String password = mPasswordView.getText().toString();

                if(TextUtils.isEmpty(countryCode) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(vCode) || TextUtils.isEmpty(password)
                        ){
                    BLCommonUtils.toastShow(FindPsdActivity.this, "请检查信息是否填写完整");
                }else{
                    new FindPsdActivity.RegistCodeTask().execute(countryCode, phone, vCode, password);
                }
            }
        });
    }

    //获取验证码
    private class RegistCodeTask extends AsyncTask<String, Void, BLLoginResult> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FindPsdActivity.this);
            progressDialog.setMessage("提交中...");
            progressDialog.show();
        }

        @Override
        protected BLLoginResult doInBackground(String... params) {
            String countryCode = params[0];
            String phone = params[1];
            String vCode = params[2];
            String password = params[3];

//            BLRegistParam registParam = new BLRegistParam();
//            registParam.setPhoneOrEmail(phone);
//            registParam.setPassword(password);
//            registParam.setNickname(mickName);
//            registParam.setCountrycode(countryCode);
//            registParam.setCode(vCode);
//
//            /**图标地址*/
//            registParam.setIconpath("http://musicdata.baidu.com/data2/pic/115360807/115360807.jpg");
//
//            File iconFile = null;
            /**本地图标使用**/
//            iconFile = new File("本地图标路径");
            return  BLLet.Account.retrievePassword(phone,vCode,password);
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
                        loginResult.getLogintime(), loginResult.getSex(), null,phone);

                BLCommonUtils.toastShow(FindPsdActivity.this, "重置成功");
//                Intent intent = new Intent();
//                intent.setClass(FindPsdActivity.this, DevListActivity.class);
//                startActivity(intent);
                FindPsdActivity.this.finish();
            }else if(loginResult != null){
                BLCommonUtils.toastShow(FindPsdActivity.this, loginResult.getMsg());
            }
        }
    }

    //获取验证码
    private class GetVCodeTask extends AsyncTask<String, Void, BLBaseResult>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FindPsdActivity.this);
            progressDialog.setMessage("获取验证码...");
            progressDialog.show();
        }

        @Override
        protected BLBaseResult doInBackground(String... params) {
            return BLLet.Account.sendRetrieveVCode (params[0]);
        }

        @Override
        protected void onPostExecute(BLBaseResult baseResult) {
            super.onPostExecute(baseResult);
            progressDialog.dismiss();
            if(baseResult != null && baseResult.getError() == BLAccountErrCode.SUCCESS){
                BLCommonUtils.toastShow(FindPsdActivity.this, "获取验证码成功");
            }else if(baseResult != null){
                BLCommonUtils.toastShow(FindPsdActivity.this, baseResult.getMsg());
            }
        }
    }
}
