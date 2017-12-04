package com.aqsystem.aqsystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.MainActivity;
import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.adapter.LeaderAdapter;
import com.aqsystem.aqsystem.fragment.LeadFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.result.account.BLLoginResult;


/**
 * 启动页面
 * Created by YeJin on 2016/5/9.
 */
public class LoadingActivity extends BaseActivity {

    private ViewPager vp_lead;
    private View view_show;
    private List<Fragment> fragments = new ArrayList<>();
    private LeaderAdapter leanderAdapter;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        sp = getSharedPreferences("saveinfo", 0);
        boolean isfirst = sp.getBoolean("isfirst", true);
        if (isfirst) {
            initView();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        } else {
            handleToActivity();
        }
    }

    /**
     * 延迟进入主页
     */
    private void handleToActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toActivity();
            }
        }, 3000);
    }

    private void initView() {
        vp_lead = findView(R.id.vp_lead);
        view_show = findView(R.id.view_show);

        fragments.add(LeadFragment.newInstance(R.mipmap.lead1));
        fragments.add(LeadFragment.newInstance(R.mipmap.lead2));
        fragments.add(LeadFragment.newInstance(R.mipmap.lead3));
        fragments.add(LeadFragment.newInstance(R.mipmap.loading));

        leanderAdapter = new LeaderAdapter(getSupportFragmentManager(), fragments);
        vp_lead.setAdapter(leanderAdapter);

        vp_lead.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    view_show.setVisibility(View.VISIBLE);
                    view_show.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                    handleToActivity();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void toActivity() {
        if (!TextUtils.isEmpty(BLApplcation.mBLUserInfoUnits.getUserid())
                && !TextUtils.isEmpty(BLApplcation.mBLUserInfoUnits.getLoginsession())) {
            Intent intent = new Intent();
//           intent.setClass(LoadingActivity.this, DevListActivity.class);
            intent.setClass(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            //本地登录
            BLLoginResult loginResult = new BLLoginResult();
            loginResult.setUserid(BLApplcation.mBLUserInfoUnits.getUserid());
            loginResult.setLoginsession(BLApplcation.mBLUserInfoUnits.getLoginsession());
            loginResult.setIconpath(BLApplcation.mBLUserInfoUnits.getIconpath());
            loginResult.setNickname(BLApplcation.mBLUserInfoUnits.getNickname());
            loginResult.setSex(BLApplcation.mBLUserInfoUnits.getSex());
            loginResult.setLoginip(BLApplcation.mBLUserInfoUnits.getLoginip());
            loginResult.setLogintime(BLApplcation.mBLUserInfoUnits.getLogintime());
//           loginResult.setFlag(BLApplcation.mBLUserInfoUnits.getFlag());
            BLLet.Account.localLogin(loginResult);
        } else {
            Intent intent = new Intent();
            intent.setClass(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
