package com.aqsystem.aqsystem.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aqsystem.aqsystem.R;
import com.aqsystem.aqsystem.util.PermissionsChecker;
import com.umeng.analytics.MobclickAgent;

import cn.com.broadlink.sdk.BLLet;

/**
 * Created by Administrator on 2017/1/21 0021.
 */

public class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

//        Manifest.permission_group.PHONE;

//        //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
//        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS};
//        ActivityCompat.requestPermissions(this,mPermissionList, 100);
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setTitle(String title){
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
    }

    /**
     * 设置返回按钮可见
     */
    protected void setBackViewVisiable() {
        ImageView iv_back = findView(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 获取控件
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 简单弹框
     * @param text
     */
    protected void showText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void formatData(String url, int ret, String code, String msg, int count, Object data) {
//        Log.d("tag", url + "  " + ret + "  " + code + "  " + msg + "  ");
//    }

    /**
     * 分享
     */
//    protected void shareInfo(){
//        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
//                {
//                        SHARE_MEDIA.WEIXIN,
//                        SHARE_MEDIA.WEIXIN_CIRCLE,
////                        SHARE_MEDIA.SINA,
//                        SHARE_MEDIA.QQ,
//                        SHARE_MEDIA.QZONE
//                };
//
//        UMImage image = new UMImage(BaseActivity.this,
//                BitmapFactory.decodeResource(getResources(), R.mipmap.xinle_icon));
//
//        new ShareAction(this).setDisplayList( displaylist )
//                .withText("因为信了，自然有了")
//                .withTitle("信了")
//                .withTargetUrl(AddrInterf.HOSTSERVER +AddrInterf.ROOT+ "a/userRegister?vid=" + Utils.vipId)
////                .withTargetUrl("http://www.baidu.com")
//                .withMedia(image)
//                .setListenerList(umShareListener)
//                .open();
//
//    }

//    private UMShareListener umShareListener= new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
////            Toast.makeText(BaseActivity.this,platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
////            Toast.makeText(BaseActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
////            Toast.makeText(BaseActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
//    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

//    private void startPermissionsActivity() {
//        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
//    }

//        @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
//        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//            finish();
//        }
//    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
