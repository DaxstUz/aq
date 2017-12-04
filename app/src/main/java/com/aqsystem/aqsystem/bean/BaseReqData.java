package com.aqsystem.aqsystem.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 请求基本类封装
 * Created by DaxstUz on 2016/7/12.
 */
public class BaseReqData implements Serializable {
    private String pageIndex = "1";
    private String pageSize = "10";
//    private String orderBy = "";

    private long timestamp = System.currentTimeMillis();
//    private String sign = "8888";

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

//    public String getOrderBy() {
//        return orderBy;
//    }
//
//    public void setOrderBy(String orderBy) {
//        this.orderBy = orderBy;
//    }

    private HashMap<String, Object> param = new HashMap<>();

    public HashMap<String, Object> getParam() {
        param.put("pageIndex", pageIndex);
        param.put("pageSize", pageSize);
        param.put("timestamp", timestamp);
        return param;
    }

}
