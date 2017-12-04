package com.aqsystem.aqsystem.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLCommonUtils;
import com.aqsystem.aqsystem.intferfacer.DevConfigModel;
import com.aqsystem.aqsystem.presenter.DevConfigIModeImpl;
import com.aqsystem.aqsystem.presenter.DevConfigListener;

import cn.com.broadlink.sdk.param.controller.BLDeviceConfigParam;


/**
 * 设备配置页面
 * Created by YeJin on 2016/5/10.
 */
public class DevConfigActivity extends BaseActivity implements DevConfigListener {

    private TextView mSSIDView;

    private EditText mPasswordView;

    private SharedPreferences mWiFiPreferences;

    private DevConfigModel mDevConfigModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_config_layout);

        mWiFiPreferences = getSharedPreferences("SHARED_PRE_WIFI_FILE", Context.MODE_PRIVATE);

        setTitle("配置设备");

        findView();

        mDevConfigModel = new DevConfigIModeImpl();

        initWiFiSSIDView();
    }

    private void findView(){
        mSSIDView = (TextView) findViewById(R.id.ssid_view);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    public void devConfig(View view){
        if(BLCommonUtils.isWifiConnect(DevConfigActivity.this)){
            String ssid = mSSIDView.getText().toString();
            String password = mPasswordView.getText().toString();
            BLDeviceConfigParam configParam = new BLDeviceConfigParam();
            configParam.setSsid(ssid);
            configParam.setPassword(password);
            configParam.setGatewayaddr(getGateWay());
            configParam.setVersion(3);

            SharedPreferences.Editor editor = mWiFiPreferences.edit();
            editor.putString(ssid, password);
            editor.commit();
            mDevConfigModel.startConfig(configParam, this);
        }else{
            BLCommonUtils.toastShow(DevConfigActivity.this, "请连接WIFI");
        }
    }

    private ProgressDialog progressDialog;

    @Override
    public void configStart() {
        progressDialog = new ProgressDialog(DevConfigActivity.this);
        progressDialog.setMessage("配置中...");
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mDevConfigModel.cancleConfig();
            }
        });
        progressDialog.show();
    }

    @Override
    public void configend() {
        if(progressDialog != null){
            progressDialog.dismiss();
            BLCommonUtils.toastShow(DevConfigActivity.this, "配置结束");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerBroadcastReceiver();

        if(BLCommonUtils.isWifiConnect(DevConfigActivity.this)){
            initWiFiSSIDView();
        }
    }

    private WifiBroadcastReceiver mWifiBroadcastReceiver;

    public void registerBroadcastReceiver(){
        if(mWifiBroadcastReceiver == null){
            mWifiBroadcastReceiver = new WifiBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(mWifiBroadcastReceiver, intentFilter);
        }
    }

    public void unregisterReceiver(){
        if(mWifiBroadcastReceiver != null){
            unregisterReceiver(mWifiBroadcastReceiver);
            mWifiBroadcastReceiver = null;
        }
    }

    class WifiBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BLCommonUtils.isWifiConnect(DevConfigActivity.this)) {
                initWiFiSSIDView();
            }
        }
    }

    //显示当前手机所连接的SSID
    public void initWiFiSSIDView() {
        String ssid = "";
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        try {
            WifiInfo info = wifi.getConnectionInfo();
            String CurInfoStr = info.toString() + "";
            String CurSsidStr = info.getSSID().toString() + "";
            if (CurInfoStr.contains(CurSsidStr)) {
                ssid = CurSsidStr;
            } else if(CurSsidStr.startsWith("\"") && CurSsidStr.endsWith("\"")){
                ssid = CurSsidStr.substring(1, CurSsidStr.length() - 1);
            } else {
                ssid = CurSsidStr;
            }
        } catch (Exception e) {
        }

        if (BLCommonUtils.isWifiConnect(DevConfigActivity.this) && !TextUtils.isEmpty(ssid)) {
            mSSIDView.setText(ssid);
            mPasswordView.setText(mWiFiPreferences.getString(ssid, null));
        }
    }


    // 获取网关
    private String getGateWay() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        // dhcpInfo获取的是最后一次成功的相关信息，包括网关、ip等
        return Formatter.formatIpAddress(dhcpInfo.gateway);
    }
}
