package com.aqsystem.aqsystem.presenter;

import android.os.Handler;

import com.aqsystem.aqsystem.intferfacer.DevConfigModel;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.param.controller.BLDeviceConfigParam;


/**
 * Created by YeJin on 2016/5/10.
 */
public class DevConfigIModeImpl implements DevConfigModel {

    Handler handler = new Handler();

    @Override
    public void startConfig(final BLDeviceConfigParam deviceConfigParam, final DevConfigListener devConfigListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        devConfigListener.configStart();
                    }
                });
                BLLet.Controller.deviceConfig(deviceConfigParam);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        devConfigListener.configend();
                    }
                });
            }
        }).start();
    }

    @Override
    public void cancleConfig() {
        BLLet.Controller.deviceConfigCancel();
    }
}
