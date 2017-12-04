/*
 * author: lachang@youzan.com
 * Copyright (C) 2016 Youzan, Inc. All Rights Reserved.
 */
package com.aqsystem.aqsystem.syncregister;

/**
 * 演示: 一个用户登录管理类
 */
public class User {
    private static User instance = null;
    private String userId;
    private boolean isLogin;

    private User() {
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void logout() {
        userId = null;
        isLogin = false;
    }
}
