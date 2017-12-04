package com.aqsystem.aqsystem.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.util.DeviceUtil;

import cn.com.broadlink.sdk.data.controller.BLDNADevice;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class ReNameActivity extends BaseActivity {

    private BLDNADevice mDNADevice;

    private EditText et_name;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename);
        setTitle("重命名");

        mDNADevice = getIntent().getParcelableExtra("info");

        et_name=findView(R.id.et_name);

//        if(mDNADevice!=null&&mDNADevice.getName()!=null){
//            et_name.setText(mDNADevice.getName());
//        }
    }


    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_commit:
                if(mDNADevice!=null&&mDNADevice.getName()!=null){
                    DeviceUtil.saveName(this,mDNADevice.getMac(),et_name.getEditableText().toString());
                }
                finish();
                break;
            default:
                break;
        }
    }
}
