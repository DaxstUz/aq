/**
 * Copyright 2015 Bartosz Lipinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aqsystem.aqsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aqsystem.aqsystem.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * 课堂卡片
 */
public class LeadFragment extends Fragment{
    public static LeadFragment newInstance(int drableres) {
        LeadFragment fragment = new LeadFragment();
        Bundle bdl = new Bundle();
        bdl.putSerializable("data",drableres);
        fragment.setArguments(bdl);
        return fragment;
    }


    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(v!=null){
            ViewGroup parent= (ViewGroup) v.getParent();
            if(parent!=null){
                parent.removeView(v);
            }
            return v;
        }

        v = inflater.inflate(R.layout.lead_layout, container, false);
        Bundle bdl = getArguments();
        int dataBean= (int) bdl.getInt("data");
        initView(dataBean);
        return v;
    }

    /**
     * 初始化界面数据
     */
    private void initView(int drableres) {
        ImageView iv_show= (ImageView) v.findViewById(R.id.iv_show);
        iv_show.setImageResource(drableres);
    }

}
