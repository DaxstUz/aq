package com.aqsystem.aqsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.adapter.NewsAdapter;
import com.aqsystem.aqsystem.util.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21 0021.
 */

public class HomeActivity extends BaseActivity {

//    https://kdt.im/DVFrlr

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onclick(View view){
        Intent intent=new Intent(HomeActivity.this,WebActivity.class);
        intent.putExtra("url","https://kdt.im/DVFrlr");
        HomeActivity.this.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
