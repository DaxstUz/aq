package com.aqsystem.aqsystem.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aqsystem.aqsystem.BLApplcation;
import com.aqsystem.aqsystem.bean.BaseReqData;
import com.aqsystem.aqsystem.cache.NetInfoCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求帮助类
 *
 * @author DaxstUz 2416738717
 *         2015年7月6日
 */
public class NetUtil {
    //appkey
    public static final String appKey = "2e137f008c51cd3134685c6480bdb5ce";

    public static final RequestQueue rqueue = Volley.newRequestQueue(BLApplcation.getApplication());

    /**
     * 判断当前网络状态是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }


    public static void getDataJsonObject(String url, HashMap<String, Object> param, NetReqListener listener) {
        if (checkNetWorkStatus(BLApplcation.getApplication())) {
            sendReq(1, Request.Method.POST, url, new JSONObject(param), listener);
        }else{
            listener.formatData(url, 1, "-100", "没网络！", 0, null);
        }
    }

    public static void getDataJsonList(String url, NetReqListener listener) {
        if (checkNetWorkStatus(BLApplcation.getApplication())) {
            sendReq(2, Request.Method.POST, url, new JSONObject(new BaseReqData().getParam()), listener);
        }else{
            listener.formatData(url, 1, "-100", "没网络！", 0, null);
        }

    }

    public static void getDataJsonList(String url, BaseReqData param, NetReqListener listener) {
        if (checkNetWorkStatus(BLApplcation.getApplication())) {
            sendReq(2, Request.Method.POST, url, new JSONObject(param.getParam()), listener);
        }else{
            listener.formatData(url, 1, "-100", "没网络！", 0, null);
        }

    }

    public static void getDataJsonList(String url, HashMap<String, Object> param, NetReqListener listener) {
        if (checkNetWorkStatus(BLApplcation.getApplication())) {
            sendReq(2, Request.Method.POST, url, new JSONObject(param), listener);
        }else{
            listener.formatData(url, 1, "-100", "没网络！", 0, null);
        }

    }

    public static void getDataJsonList(String url, JSONObject jsonParam, NetReqListener listener) {
        if (checkNetWorkStatus(BLApplcation.getApplication())) {
            sendReq(2, Request.Method.POST, url, jsonParam, listener);
        }else{
            listener.formatData(url, 1, "-100", "没网络！", 0, null);
        }
    }

    /**
     * 发起网络请求
     *
     * @param method   请求方法
     * @param url      请求地址
     * @param param    请求参数
     * @param listener 请求监听
     * @param type     请求类型  2表示请求数组数据 1表示拿单个对象数据
     */
    private static void sendReq(final int type, int method, final String url, JSONObject param, final NetReqListener listener) {

        int netState = NetInfoCache.getNetState();
        if (netState == 0) {
            listener.formatData(url, 1, "-100", "没网络！", 0, null);
//            SysUtil.makeText(AppApplication.getApplication(), "亲，没有网络哦^_^！", Toast.LENGTH_LONG);
            return;
        } else if (netState == 1) {
            SysUtil.makeText(BLApplcation.getApplication(), "当前为2G网络，可能会影响到您的浏览哦！", Toast.LENGTH_LONG);
        } else if (netState == 2) {
            SysUtil.makeText(BLApplcation.getApplication(), "亲，当前为3G网络，可能会影响到您的浏览哦！",
                    Toast.LENGTH_LONG);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("uzinfo", "onResponse " + jsonObject.toString());
                int ret = 0;
                int count = 0;
                String code = null;
                String msg = null;
                Object datas = null;
                try {
//                    ret = jsonObject.getInt("ret");
                    if (jsonObject.has("rcCode")) {
                        code = jsonObject.getString("rcCode");
                    }
                    if (jsonObject.has("msg")) {
                        msg = jsonObject.getString("msg");
                    }
                    if (2 == type) {
                        if (jsonObject.has("data")) {
                            if (jsonObject.get("data") instanceof JSONObject && jsonObject.getJSONObject("data").has("list")) {
                                JSONArray array = jsonObject.getJSONObject("data").getJSONArray("list");
                                List<JSONObject> templist = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    templist.add(array.getJSONObject(i));
                                }
                                datas = templist;
                                count = jsonObject.getJSONObject("data").getInt("count");
                            } else {
                                JSONArray array = jsonObject.getJSONArray("data");
                                List<JSONObject> templist = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    templist.add(array.getJSONObject(i));
                                }
                                datas = templist;
                                count = templist.size();
                            }
                        }
                    }

                    if (1 == type) {
                        if (jsonObject.has("data")) {
                            datas = jsonObject.getJSONObject("data");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.formatData(url, ret, code, msg, count, datas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("uzinfo", "onErrorResponse " + volleyError.toString());
                listener.formatData(url, -1, "", volleyError.toString(), 0, null);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        NetUtil.rqueue.add(jsonObjectRequest);
    }

    /**
     * 监听事件
     */
    public interface NetReqListener {
        /**
         * 结果解析
         *
         * @param url   请求的地址
         * @param ret   返回的结果
         * @param code  返回的状态码
         * @param msg   返回的状态消息
         * @param count 返回的总页数
         * @param data  返回的数据
         */
        public void formatData(String url, int ret, String code, String msg, int count, Object data);
    }

}
