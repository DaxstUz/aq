package com.aqsystem.aqsystem.presenter;

import com.aqsystem.aqsystem.data.BLControlActConstans;
import com.aqsystem.aqsystem.intferfacer.SPControlModel;

import java.util.ArrayList;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.constants.controller.BLControllerErrCode;
import cn.com.broadlink.sdk.data.controller.BLStdData;
import cn.com.broadlink.sdk.param.controller.BLStdControlParam;
import cn.com.broadlink.sdk.result.controller.BLStdControlResult;

/**
 * Created by YeJin on 2016/5/10.
 */
public class SPControlModelImpl implements SPControlModel {

    private SPControlListener mSPControlListener;

    private SPControlModelImpl(){}

    private android.os.Handler mHandler = new android.os.Handler();

    public SPControlModelImpl(SPControlListener spControlListener){
        mSPControlListener = spControlListener;
    }

    @Override
    public void queryDevStatus(String did) {
        /**查询开关状态命令**/
        BLStdControlParam queryParam = new BLStdControlParam();
        queryParam.setAct(BLControlActConstans.ACT_GET);
        queryParam.getParams().add("pwr");
        final BLStdControlResult stdControlResult = BLLet.Controller.dnaControl(did, null, queryParam);
        if(mSPControlListener != null && stdControlResult != null && stdControlResult.getStatus() == BLControllerErrCode.SUCCESS){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSPControlListener.deviceStatusShow((Integer) stdControlResult.getData().getVals().get(0).get(0).getVal());
                }
            });
        }
    }

    @Override
    public void controlDevPwr(final String did, final int pwr) {
        /**设置开关状态**/
        if(mSPControlListener != null){
            mSPControlListener.controlStart();
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                BLStdData.Value value = new BLStdData.Value();
                value.setVal(pwr);

                ArrayList<BLStdData.Value> pwrVals = new ArrayList<>();
                pwrVals.add(value);

                BLStdControlParam ctrlParam = new BLStdControlParam();
                ctrlParam.setAct(BLControlActConstans.ACT_SET);
                ctrlParam.getParams().add("pwr");
                ctrlParam.getVals().add(pwrVals);

                final BLStdControlResult stdControlResult = BLLet.Controller.dnaControl(did, null, ctrlParam);
                if(mSPControlListener != null){
                    mSPControlListener.controlEnd();
                }

                if(stdControlResult != null && stdControlResult.getStatus() == BLControllerErrCode.SUCCESS){
                    if(mSPControlListener != null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mSPControlListener.controlSuccess((Integer) stdControlResult.getData().getVals().get(0).get(0).getVal());
                            }
                        });
                    }
                }else{
                    if(mSPControlListener != null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mSPControlListener.controlFail(stdControlResult);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Override
    public void taskDevSet(final String did, final String taskData) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                BLStdControlParam getParam = new BLStdControlParam();
                getParam.setAct(BLControlActConstans.ACT_GET);
                getParam.getParams().add("pertsk");

                final BLStdControlResult getControlResult = BLLet.Controller.dnaControl(did, null, getParam);
                if (getControlResult != null && getControlResult.getStatus() == BLControllerErrCode.SUCCESS) {

                    BLStdData.Value value = new BLStdData.Value();
                    value.setVal(taskData);
                    ArrayList<BLStdData.Value> vals = new ArrayList<>();
                    vals.add(value);

                    BLStdControlParam setParam = new BLStdControlParam();
                    setParam.setAct(BLControlActConstans.ACT_SET);
                    setParam.getParams().add("pertsk");
                    setParam.getVals().add(vals);

                    final BLStdControlResult setControlResult = BLLet.Controller.dnaControl(did, null, setParam);
                    if (setControlResult != null && setControlResult.getStatus() == BLControllerErrCode.SUCCESS) {
                        if(mSPControlListener != null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mSPControlListener.taskSuccess();
                                }
                            });
                        }
                    } else {
                        if(mSPControlListener != null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mSPControlListener.taskFaile(setControlResult.getMsg());
                                }
                            });
                        }
                    }
                } else {
                    if(mSPControlListener != null){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mSPControlListener.taskFaile(getControlResult.getMsg());
                            }
                        });
                    }
                }
            }
        }).start();
    }
}
