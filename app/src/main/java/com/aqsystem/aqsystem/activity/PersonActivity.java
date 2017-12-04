package com.aqsystem.aqsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.common.BLUserInfoUnits;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youzan.sdk.YouzanSDK;

import cn.com.broadlink.sdk.BLLet;

/**
 * Created by Administrator on 2017/1/21 0021.
 */

public class PersonActivity extends BaseActivity {

//    https://h5.koudaitong.com/v2/goods/1y8wyvqgdlonp

    private TextView tv_name;
    private TextView tv_sex;
    private RoundedImageView iv_head;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        setTitle("个人中心");

        initView();
    }

    private void initView() {
        tv_name=findView(R.id.tv_name);
        tv_name.setText(BLApplcation.mBLUserInfoUnits.getNickname());
        tv_sex=findView(R.id.tv_sex);
        if("male".equals(BLApplcation.mBLUserInfoUnits.getSex())){
            tv_sex.setText("男");
        }else{
            tv_sex.setText("女");
        }

        iv_head=findView(R.id.iv_head);
//        ImageLoader.getInstance().displayImage(BLApplcation.mBLUserInfoUnits.getIconpath(),iv_head);
    }

    //退出登录
    public void logOut(View view){
        BLApplcation.mBLUserInfoUnits.loginOut();
        Intent intent = new Intent();
        intent.setClass(PersonActivity.this, LoginActivity.class);
        startActivity(intent);
        PersonActivity.this.finish();

        //清空数据
        YouzanSDK.userLogout(this);

        BLApplcation.mDevList.clear();
        BLLet.Controller.removeAllDevice();
    }

//    https://h5.youzan.com/v2/goods/2ohp6ija0aaw5
    public void charge(View view){
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra("url","https://h5.koudaitong.com/v2/goods/1y8wyvqgdlonp");
        this.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
