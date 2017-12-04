package com.aqsystem.aqsystem.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class DeviceUtil {

    private static SharedPreferences sp;

    public static void saveName(Context content ,String mac,String name){
        sp=content.getSharedPreferences("device",0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(mac,name);
        editor.commit();
    }

    public static String getName(Context content ,String mac){
        sp=content.getSharedPreferences("device",0);
        return  sp.getString(mac,"");
    }
}
