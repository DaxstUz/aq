package com.aqsystem.aqsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.util.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/4/23 0023.
 */

public class NewsAdapter extends BaseAdapter {

    private Context context;

    private List<JSONObject> news;

    public NewsAdapter(Context context, List<JSONObject> news) {
        super();
        this.context = context;
        this.news = news;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_new_item, null);
            ImageView iv_main_show = (ImageView) view.findViewById(R.id.iv_main_show);
            TextView new_sum = (TextView) view.findViewById(R.id.new_sum);
            JSONObject jsonObject = news.get(i);

            try {
                if (jsonObject.has("description")) {
                    new_sum.setText(jsonObject.getString("description"));
                }

                ImageLoader.getInstance().displayImage("http://182.92.157.16:8080"+jsonObject.getString("imageSrc"), iv_main_show, UIUtils.options2_1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }
}
