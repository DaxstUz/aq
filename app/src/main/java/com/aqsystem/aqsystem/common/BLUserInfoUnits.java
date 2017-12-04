package com.aqsystem.aqsystem.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import cn.com.broadlink.sdk.data.controller.BLDNADevice;

/**
 * 用户登录信息返回保存
 * Created by YeJin on 2016/5/9.
 */
public class BLUserInfoUnits {

    private SharedPreferences mSharedPreferences;

    /**用户ID**/
    private String userid;

    /**用户session**/
    private String loginsession;

    /**昵称**/
    private String nickname;

    /**头像地址**/
    private String iconpath;

    /**登录ID**/
    private String loginip;

    /**登录时间**/
    private String logintime;

    /**性别**/
    private String sex;

    /**手机**/
    private String tel;

    private String flag;

    public BLUserInfoUnits(Context context){
        mSharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        this.userid = mSharedPreferences.getString("userid", null);
        this.loginsession = mSharedPreferences.getString("loginsession", null);
        this.nickname = mSharedPreferences.getString("nickname", null);
        this.iconpath = mSharedPreferences.getString("iconpath", null);
        this.loginip = mSharedPreferences.getString("loginip", null);
        this.logintime = mSharedPreferences.getString("logintime", null);
        this.sex = mSharedPreferences.getString("sex", null);
        this.flag = mSharedPreferences.getString("flag", null);
        this.tel = mSharedPreferences.getString("tel", null);
    }

    public void login(String userid, String loginsession, String nickname, String iconpath, String loginip,
                      String logintime, String sex, String flag,String tel){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("userid", userid);
        editor.putString("loginsession", loginsession);
        editor.putString("nickname", nickname);
        editor.putString("iconpath", iconpath);
        editor.putString("loginip", loginip);
        editor.putString("logintime", logintime);
        editor.putString("sex", sex);
        editor.putString("flag", flag);
        editor.putString("tel", tel);
        editor.commit();
        this.userid = userid;
        this.loginsession = loginsession;
        this.nickname = nickname;
        this.iconpath = iconpath;
        this.loginip = loginip;
        this.logintime = logintime;
        this.sex = sex;
        this.flag = flag;
        this.tel=tel;
    }

    public void loginOut(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("userid", null);
        editor.putString("loginsession", null);
        editor.putString("nickname", null);
        editor.putString("iconpath", null);
        editor.putString("loginip", null);
        editor.putString("logintime", null);
        editor.putString("sex", null);
        editor.putString("flag", null);
        editor.commit();
        this.userid = null;
        this.loginsession = null;
        this.nickname = null;
        this.iconpath = null;
        this.loginip = null;
        this.logintime = null;
        this.sex = null;
        this.flag = null;
    }


    public String getUserid() {
        return userid;
    }

    public String getLoginsession() {
        return loginsession;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIconpath() {
        return iconpath;
    }

    public String getLoginip() {
        return loginip;
    }

    public String getLogintime() {
        return logintime;
    }

    public String getSex() {
        return sex;
    }

    public String getFlag() {
        return flag;
    }

    public String getTel() {
        return tel;
    }

}
