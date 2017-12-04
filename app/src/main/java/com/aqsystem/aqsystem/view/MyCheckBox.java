package com.aqsystem.aqsystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 自定义CheckBox
 * @author xc.li
 * @date 2015年12月26日
 */
public class MyCheckBox extends LinearLayout {

	public MyCheckBox(Context context) {
		super(context);
	}

	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MyCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	 * 是否选中
	 * @return
	 */
	public boolean isChecked(){
		Object tag = this.getTag();
		if(tag != null){
			return Boolean.valueOf(tag.toString());
		}else{
			return false;
		}
	}
	
	/**
	 * 获取checkbox的值
	 * @return
	 */
	public String getValue(){
		View v = this.getChildAt(0);
		if(v != null){
			Object tag = v.getTag();
			if(tag != null){
				return tag.toString();
			}
		}
		return "";
	}
}
