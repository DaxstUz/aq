package com.aqsystem.aqsystem;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.aqsystem.aqsystem.activity.DevListActivity;
import com.aqsystem.aqsystem.activity.HomeActivity;
import com.aqsystem.aqsystem.activity.NewsActivity;
import com.aqsystem.aqsystem.activity.PersonActivity;

public class MainActivity extends TabActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup bottomRg;

    private TabHost tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bottomRg= (RadioGroup) findViewById(R.id.bottomRg);
        bottomRg.setOnCheckedChangeListener(this);



        tabhost = getTabHost();

        Intent homeActivity=new Intent(this, HomeActivity.class);
        tabhost.addTab(tabhost.newTabSpec("tabhome").setIndicator("首页").setContent(homeActivity));

        Intent cardActivity=new Intent(this, DevListActivity.class);
        tabhost.addTab(tabhost.newTabSpec("tabcard").setIndicator("信用卡").setContent(cardActivity));

//        Intent methActivity=new Intent(this, NewsActivity.class);
//        tabhost.addTab(tabhost.newTabSpec("tabmethod").setIndicator("攻略").setContent(methActivity));

        Intent personActivity=new Intent(this, PersonActivity.class);
        tabhost.addTab(tabhost.newTabSpec("tabperson").setIndicator("个人中心").setContent(personActivity));

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_home:
                this.tabhost.setCurrentTabByTag("tabhome");
                break;
            case R.id.rb_card:
                this.tabhost.setCurrentTabByTag("tabcard");
                break;
//            case R.id.rb_method:
//                this.tabhost.setCurrentTabByTag("tabmethod");
//                break;
            case R.id.rb_person:
                this.tabhost.setCurrentTabByTag("tabperson");
                break;
        }
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }

}

