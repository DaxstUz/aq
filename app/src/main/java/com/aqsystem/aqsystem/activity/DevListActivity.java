package com.aqsystem.aqsystem.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLCommonUtils;
import com.aqsystem.aqsystem.intferfacer.SPControlModel;
import com.aqsystem.aqsystem.presenter.SPControlListener;
import com.aqsystem.aqsystem.presenter.SPControlModelImpl;
import com.aqsystem.aqsystem.util.DeviceUtil;
import com.aqsystem.aqsystem.util.NetUtil;
import com.aqsystem.aqsystem.util.ParcelableUtil;
import com.aqsystem.aqsystem.util.SysUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.constants.controller.BLControllerErrCode;
import cn.com.broadlink.sdk.data.controller.BLDNADevice;
import cn.com.broadlink.sdk.interfaces.controller.BLDeviceScanListener;
import cn.com.broadlink.sdk.result.BLBaseResult;
import cn.com.broadlink.sdk.result.controller.BLDownloadScriptResult;

import static com.aqsystem.aqsystem.BLApplcation.mDevList;

/**
 * 设备列表
 * Created by YeJin on 2016/5/9.
 */
public class DevListActivity extends Activity {

//    http://182.92.157.16:8080/anqun/manager/getByTel

    private SharedPreferences parcelable;

    private ListView mDevListView;

    private DeviceAdapter mDeviceAdapter;

    private static final int PWR_ON = 1;

    private static final int PWR_OFF = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parcelable = getSharedPreferences("parcelable", 0);
        setContentView(R.layout.dev_list_layout);

        getdata();
        findView();

        setListener();

        initDevice();

        mDeviceAdapter = new DeviceAdapter(DevListActivity.this, mDevList);
        mDevListView.setAdapter(mDeviceAdapter);


    }

    private void initDevice() {
        Set<String> infoset = new HashSet<>();

        try {
            infoset = parcelable.getStringSet("parcelable", new HashSet<String>());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (infoset != null) {

            Iterator<String> iterator = infoset.iterator();

            while (iterator.hasNext()) {
                Parcel parcel = ParcelableUtil.unmarshall(Base64.decode(iterator.next(), 0));
                BLDNADevice device = BLDNADevice.CREATOR.createFromParcel(parcel);
                BLApplcation.mDevList.add(device);
                BLLet.Controller.addDevice(device);

            }
        }
    }

    private void findView() {
        mDevListView = (ListView) findViewById(R.id.dev_list);
    }

    private void setListener() {
//        mDevListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    if(info!=null&&info.has("endTime")&&!isPast(info.getLong("endTime"))){
//                        Intent intent = new Intent();
//                        intent.putExtra("INTENT_DEV_ID", mDevList.get(position));
//                        intent.setClass(DevListActivity.this, DevMoreActivity.class);
//                        startActivity(intent);
//                    }else{
//                        SysUtil.makeText("请去充值！");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        mDevListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DevListActivity.this, ReNameActivity.class);
                intent.putExtra("info", mDevList.get(i));
                DevListActivity.this.startActivityForResult(intent, 0);
                return true;
            }
        });

    }

    public void devConfig(View view) {
//        try {
//            if (info != null && info.has("endTime") && !isPast(info.getLong("endTime"))) {
                Intent intent = new Intent();
                intent.setClass(DevListActivity.this, DevConfigActivity.class);
                startActivity(intent);
//            } else {
//                SysUtil.makeText("请去充值！");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 判断是否过期
     *
     * @return
     */
    private boolean isPast(Long endtime) {
        if (endtime > System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    //退出登录
    public void logOut(View view) {
        BLApplcation.mBLUserInfoUnits.loginOut();
        Intent intent = new Intent();
        intent.setClass(DevListActivity.this, LoginActivity.class);
        startActivity(intent);
        DevListActivity.this.finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        startQuerySPStatusTimer();
        devProbleListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopQuerySPStatusTimer();
        BLLet.Controller.stopProbe();
        if (BLApplcation.mDevList.size() > 0) {
            saveinfo();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("uzinfo" ,"DevListActivity onDestroy");
        BLApplcation.mDevList.clear();
    }

    private void devProbleListener() {
        BLLet.Controller.startProbe();
        BLLet.Controller.setOnDeviceScanListener(new BLDeviceScanListener() {
            @Override
            public void onDeviceUpdate(BLDNADevice dnaDevice, boolean isNewDev) {
                if (isNewDev) {
                    boolean added = true;
                    if (mDevList.size() > 0) {
                        for (int i = 0; i < mDevList.size(); i++) {
                            if (mDevList.get(i).getMac().equals(dnaDevice.getMac())) {
                                added = false;
                                break;
                            }
                        }
                    }
                    if (added) {
                        mDevList.add(dnaDevice);
                        new DownLoadScriptTask(dnaDevice).execute();
                    }

                    BLLet.Controller.removeAllDevice();
                    BLLet.Controller.addDevice(mDevList);

                    byte[] bytes = ParcelableUtil.marshall(dnaDevice);
                    SharedPreferences.Editor parcelableEditor = parcelable.edit();
                    parcelableEditor.putString("parcelable", Base64.encodeToString(bytes, 0));
                    parcelableEditor.commit();

                } else {
                    //TODO 更新设备信息
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDeviceAdapter.notifyDataSetChanged();
                    }
                });
            }

//            @Override
//            public boolean shouldAdd(BLDNADevice bldnaDevice) {
////                return super.shouldAdd(bldnaDevice);
//                BLApplcation.mDevList.add(bldnaDevice);
//                return false;
//            }
        });
    }


    protected void saveinfo() {

        Set<String> info = new HashSet<>();
        for (BLDNADevice dnaDevice : BLApplcation.mDevList
                ) {
            byte[] bytes = ParcelableUtil.marshall(dnaDevice);
            info.add(Base64.encodeToString(bytes, 0));
        }

        SharedPreferences.Editor parcelableEditor = parcelable.edit();
        parcelableEditor.putStringSet("parcelable", info);
        parcelableEditor.commit();
    }

    //设备列表适配器
    private class DeviceAdapter extends ArrayAdapter<BLDNADevice> {
        public DeviceAdapter(Context context, List<BLDNADevice> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.adapter_device, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
//                viewHolder.mac = (TextView) convertView.findViewById(R.id.tv_mac);
                viewHolder.ip = (TextView) convertView.findViewById(R.id.tv_ip);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final BLDNADevice device = getItem(position);

            String name = DeviceUtil.getName(DevListActivity.this, device.getMac()).length() > 0 ? DeviceUtil.getName(DevListActivity.this, device.getMac()) : device.getName();
            viewHolder.name.setText(name);
//            viewHolder.mac.setText(device.getMac());
//            viewHolder.ip.setText("" + BLLet.Controller.queryDeviceIp(device.getDid()));

            final SPControlModel mSPControlModel = new SPControlModelImpl(new SPControlListener() {
                @Override
                public void deviceStatusShow(int pwr) {
                    if (pwr == PWR_ON) {
                        viewHolder.ip.setText("开");
                    } else if (pwr == PWR_OFF) {
                        viewHolder.ip.setText("关");
                    }
                }

                ProgressDialog mProgressDialog;

                @Override
                public void controlStart() {
                    mProgressDialog = new ProgressDialog(DevListActivity.this);
                    mProgressDialog.setMessage("控制中...");
                    mProgressDialog.show();
                }

                @Override
                public void controlEnd() {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void controlSuccess(int pwr) {
                    deviceStatusShow(pwr);
                    BLCommonUtils.toastShow(DevListActivity.this, "控制成功");
                }

                @Override
                public void controlFail(BLBaseResult result) {
                    BLCommonUtils.toastShow(DevListActivity.this, "控制失败");
                }

                @Override
                public void taskSuccess() {
                    BLCommonUtils.toastShow(DevListActivity.this, "任务设置成功");
                }

                @Override
                public void taskFaile(String msg) {
                    BLCommonUtils.toastShow(DevListActivity.this, "任务设置失败 :" + msg);
                }
            });

            mSPControlModel.queryDevStatus(device.getDid());

            viewHolder.ip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    try {
//                        if (info != null && info.has("endTime") && !isPast(info.getLong("endTime"))) {
//                            Intent intent = new Intent();
//                            intent.putExtra("INTENT_DEV_ID", mDevList.get(position));
//                            intent.setClass(DevListActivity.this, DevMoreActivity.class);
//                            startActivity(intent);
                            if (viewHolder.ip.getText().toString().trim().equals("开")) {
                                mSPControlModel.controlDevPwr(device.getDid(), PWR_OFF);
                            } else {
                                mSPControlModel.controlDevPwr(device.getDid(), PWR_ON);
                            }
//                        } else {
//                            SysUtil.makeText("请去充值！");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                }
            });
//            public void spOpen(View view){
//                mSPControlModel.controlDevPwr(mDNADevice.getDid(), PWR_ON);
//            }
//
//            public void spClose(View view){
//                mSPControlModel.controlDevPwr(mDNADevice.getDid(), PWR_OFF);
//            }

            return convertView;
        }

        private class ViewHolder {
            TextView name;
//            TextView mac;
            TextView ip;
        }
    }

    private JSONObject info;

    public void getdata() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("Tel", BLApplcation.mBLUserInfoUnits.getTel());


        NetUtil.getDataJsonObject("http://182.92.157.16:8080/anqun/manager/getByTel", param, new NetUtil.NetReqListener() {
            @Override
            public void formatData(String url, int ret, String code, String msg, int count, Object data) {
                if (code != null && code.equals("success")) {
                    if (data != null) {
                        info = (JSONObject) data;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDeviceAdapter.notifyDataSetChanged();
    }


    /**
     * 查询SP设备状态定时器
     **/
    private Timer mQuerySPStatusTimer;

    private void startQuerySPStatusTimer() {
        if (mQuerySPStatusTimer == null) {
            mQuerySPStatusTimer = new Timer();
            mQuerySPStatusTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendMessage(new Message());
                }
            }, 0, 30000);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mDeviceAdapter != null) {
                mDeviceAdapter.notifyDataSetChanged();
            } else {
                mDeviceAdapter = new DeviceAdapter(DevListActivity.this, mDevList);
                mDevListView.setAdapter(mDeviceAdapter);
            }
        }
    };

    private void stopQuerySPStatusTimer() {
        if (mQuerySPStatusTimer != null) {
            mQuerySPStatusTimer.cancel();
            mQuerySPStatusTimer = null;
        }
    }

    //脚本版本查询
    class DownLoadScriptTask extends AsyncTask<Void, Void, BLDownloadScriptResult> {
//        private ProgressDialog progressDialog;

        private BLDNADevice dnaDevice;

        public DownLoadScriptTask(BLDNADevice dnaDevice) {
            this.dnaDevice = dnaDevice;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(DevMoreActivity.this);
//            progressDialog.setMessage("脚本下载中...");
//            progressDialog.show();
        }

        @Override
        protected BLDownloadScriptResult doInBackground(Void... params) {
            return BLLet.Controller.downloadScript(dnaDevice.getPid());
        }

        @Override
        protected void onPostExecute(BLDownloadScriptResult result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            if (result != null && result.getStatus() == BLControllerErrCode.SUCCESS) {
                Log.e("DownLoad", result.getSavePath());
//                BLCommonUtils.toastShow(DevMoreActivity.this, "Script Path ：" + result.getSavePath());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
