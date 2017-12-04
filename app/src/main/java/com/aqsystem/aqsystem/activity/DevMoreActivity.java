package com.aqsystem.aqsystem.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.io.File;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLCommonUtils;
import com.aqsystem.aqsystem.data.BLDevProfileInfo;
import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.constants.controller.BLControllerErrCode;
import cn.com.broadlink.sdk.data.controller.BLDNADevice;
import cn.com.broadlink.sdk.result.controller.BLDownloadScriptResult;
import cn.com.broadlink.sdk.result.controller.BLProfileStringResult;
import cn.com.broadlink.sdk.result.controller.BLQueryResoureVersionResult;
import cn.com.broadlink.sdk.result.controller.ResourceVersion;

/**
 * 设备更多操作
 * Created by YeJin on 2016/5/10.
 */
public class DevMoreActivity extends Activity{

    /**RM 分类**/
    private static final String CATEGORY_RM = "1.1.5";

    /**SP 分类**/
    private static final String CATEGORY_SP = "4.1.50";

    private BLDNADevice mDNADevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_more_layout);

        mDNADevice = getIntent().getParcelableExtra("INTENT_DEV_ID");
    }

    //脚本版本查询
    public void scriptVersion(View view){
        new QueryScriptVersionTask().execute();
    }

    //脚本下载
    public void scriptDown(View view){
        new DownLoadScriptTask().execute();
    }

    //SP控制
    public void spControl(View view){
        if(scriptFileExist()){
            BLDevProfileInfo profileInfo = queryDevProfile();
            if(profileInfo.getSrvs().get(0).equals(CATEGORY_SP)){
                Intent intent = new Intent();
                intent.putExtra("INTENT_DEV_ID", mDNADevice);
                intent.setClass(DevMoreActivity.this, SPDemoActivity.class);
                startActivity(intent);
            }else{
                BLCommonUtils.toastShow(DevMoreActivity.this, "该设备不属于SP系列");
            }
        }else{
            BLCommonUtils.toastShow(DevMoreActivity.this, "脚本不存在");
        }
    }

    //RM控制
    public void rmControl(View view){
        if(scriptFileExist()){
            BLDevProfileInfo profileInfo = queryDevProfile();
            if(profileInfo.getSrvs().get(0).equals(CATEGORY_RM)){
                Intent intent = new Intent();
                intent.putExtra("INTENT_DEV_ID", mDNADevice);
                intent.setClass(DevMoreActivity.this, RMDemoActivity.class);
                startActivity(intent);
            }else{
                BLCommonUtils.toastShow(DevMoreActivity.this, "该设备不属于RM系列");
            }
        }else{
            BLCommonUtils.toastShow(DevMoreActivity.this, "脚本不存在");
        }
    }

    /**查询设备profile**/
    private BLDevProfileInfo queryDevProfile(){
        BLProfileStringResult devProfileResult = BLLet.Controller.queryProfile(mDNADevice.getDid());
        if(devProfileResult != null && devProfileResult.getStatus() == BLControllerErrCode.SUCCESS){
            String profileStr = devProfileResult.getProfile();
            Log.i("dev profile", profileStr + "");

            return JSON.parseObject(profileStr, BLDevProfileInfo.class);
        }

        return null;
    }

    private boolean scriptFileExist(){
        /***获取产品脚本本地保存的路径***/
        String scriptFilePath = BLLet.Controller.queryScriptPath(mDNADevice.getPid());
        Log.e("FileExist" , scriptFilePath);
        File file = new File(scriptFilePath);
        return  file.exists();
    }

    //脚本版本查询
    class QueryScriptVersionTask extends AsyncTask<Void, Void, BLQueryResoureVersionResult>{
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DevMoreActivity.this);
            progressDialog.setMessage("脚本版本查询...");
            progressDialog.show();
        }

        @Override
        protected BLQueryResoureVersionResult doInBackground(Void... params) {
            return BLLet.Controller.queryScriptVersion(mDNADevice.getPid());
        }

        @Override
        protected void onPostExecute(BLQueryResoureVersionResult result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(result != null && result.getStatus() == BLControllerErrCode.SUCCESS){
                ResourceVersion version = result.getVersions().get(0);
                BLCommonUtils.toastShow(DevMoreActivity.this, "Script Version ：" + version.getVersion());
            }
        }
    }

    //脚本版本查询
    class DownLoadScriptTask extends AsyncTask<Void, Void, BLDownloadScriptResult>{
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DevMoreActivity.this);
            progressDialog.setMessage("脚本下载中...");
            progressDialog.show();
        }

        @Override
        protected BLDownloadScriptResult doInBackground(Void... params) {
            return BLLet.Controller.downloadScript(mDNADevice.getPid());
        }

        @Override
        protected void onPostExecute(BLDownloadScriptResult result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(result != null && result.getStatus() == BLControllerErrCode.SUCCESS){
                Log.e("DownLoad" , result.getSavePath());
                BLCommonUtils.toastShow(DevMoreActivity.this, "Script Path ：" + result.getSavePath());
            }
        }
    }
}
