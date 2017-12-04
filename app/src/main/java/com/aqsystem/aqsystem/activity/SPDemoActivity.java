package com.aqsystem.aqsystem.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLCommonUtils;
import com.aqsystem.aqsystem.intferfacer.SPControlModel;
import com.aqsystem.aqsystem.presenter.SPControlListener;
import com.aqsystem.aqsystem.presenter.SPControlModelImpl;

import cn.com.broadlink.sdk.data.controller.BLDNADevice;
import cn.com.broadlink.sdk.result.BLBaseResult;

/**
 * SP系列Demo
 * Created by YeJin on 2016/5/10.
 */
public class SPDemoActivity extends Activity implements SPControlListener {
    private static final int PWR_ON = 1;

    private static final int PWR_OFF = 0;

    private BLDNADevice mDNADevice;

    private TextView mSpStatusView;

    private SPControlModel mSPControlModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_demo_layout);

        mDNADevice = getIntent().getParcelableExtra("INTENT_DEV_ID");

        mSpStatusView = (TextView) findViewById(R.id.sp_status_view);

        mSPControlModel = new SPControlModelImpl(this);
    }

    public void spOpen(View view){
        mSPControlModel.controlDevPwr(mDNADevice.getDid(), PWR_ON);
    }

    public void spClose(View view){
        mSPControlModel.controlDevPwr(mDNADevice.getDid(), PWR_OFF);
    }

    public void spTaskSet(View view) {
        mSPControlModel.taskDevSet(mDNADevice.getDid(), "1|+0800-150000@150100|1234567|1|1");
    }

    @Override
    public void deviceStatusShow(int pwr) {
        if(pwr == PWR_ON){
            mSpStatusView.setText("开");
        }else if(pwr == PWR_OFF){
            mSpStatusView.setText("关");
        }
    }

    @Override
    public void taskSuccess() {
        BLCommonUtils.toastShow(SPDemoActivity.this, "任务设置成功");
    }

    @Override
    public void taskFaile(String msg) {
        BLCommonUtils.toastShow(SPDemoActivity.this, "任务设置失败 :" + msg);
    }

    private ProgressDialog mProgressDialog;

    @Override
    public void controlStart() {
        mProgressDialog = new ProgressDialog(SPDemoActivity.this);
        mProgressDialog.setMessage("控制中...");
        mProgressDialog.show();
    }

    @Override
    public void controlEnd() {
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void controlSuccess(int pwr) {
        deviceStatusShow(pwr);

        BLCommonUtils.toastShow(SPDemoActivity.this, "控制成功");
    }

    @Override
    public void controlFail(BLBaseResult result) {
        BLCommonUtils.toastShow(SPDemoActivity.this, "控制失败");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startQuerySPStatusTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopQuerySPStatusTimer();
    }

    /**查询SP设备状态定时器**/
    private Timer mQuerySPStatusTimer;

    private void startQuerySPStatusTimer(){
        if(mQuerySPStatusTimer == null){
            mQuerySPStatusTimer = new Timer();
            mQuerySPStatusTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSPControlModel.queryDevStatus(mDNADevice.getDid());
                }
            }, 0 , 30000);
        }
    }

    private void stopQuerySPStatusTimer(){
        if(mQuerySPStatusTimer != null){
            mQuerySPStatusTimer.cancel();
            mQuerySPStatusTimer = null;
        }
    }
}
