package com.aqsystem.aqsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class NewsActivity extends BaseActivity {
    private SwipeRefreshLayout srl_fresh;
    private ListView lv_news;
    private NewsAdapter adapter;
    private List<JSONObject> news = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setTitle("新闻");

        srl_fresh = findView(R.id.srl_fresh);
        srl_fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
            }
        });

        lv_news = findView(R.id.lv_news);
        adapter = new NewsAdapter(this, news);
        lv_news.setAdapter(adapter);

        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject newsItem = news.get(i);
                if (newsItem.has("url")) {
                    try {
                        String url = newsItem.getString("url");
                        String[] urls = url.split("/");
                        if (urls.length>=3) {
                            String showurl = "http://182.92.157.16:8080/anqun/article/" + urls[3];
                            Intent intent=new Intent(NewsActivity.this,WebNewsActivity.class);
                            intent.putExtra("url",showurl);
                            NewsActivity.this.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        getdata();
    }

    public void getdata() {
        NetUtil.getDataJsonList("http://182.92.157.16:8080/anqun/article/listArticle", new NetUtil.NetReqListener() {
            @Override
            public void formatData(String url, int ret, String code, String msg, int count, Object data) {
                srl_fresh.setRefreshing(false);
                if (code != null && code.equals("success")) {
                    news.clear();
                    news.addAll((List<JSONObject>) data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
