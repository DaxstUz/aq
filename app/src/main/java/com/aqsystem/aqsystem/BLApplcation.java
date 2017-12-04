package com.aqsystem.aqsystem;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;

import com.aqsystem.aqsystem.activity.PermissionsActivity;
import com.aqsystem.aqsystem.common.BLUserInfoUnits;
import com.aqsystem.aqsystem.service.NetStateChangeService;
import com.aqsystem.aqsystem.util.CrashHandler;
import com.aqsystem.aqsystem.util.PermissionsChecker;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.youzan.sdk.YouzanSDK;

import java.io.File;
import java.util.ArrayList;

import cn.com.broadlink.sdk.BLLet;
import cn.com.broadlink.sdk.data.controller.BLDNADevice;

import static cn.com.broadlink.sdk.BLLet.finish;


/**
 * Created by YeJin on 2016/5/9.
 */
public class BLApplcation extends Application{

    public static ArrayList<BLDNADevice> mDevList = new ArrayList<BLDNADevice>();

    public static BLUserInfoUnits mBLUserInfoUnits;

    private static BLApplcation application;

    public BLApplcation() {
        application = this;
    }

    public static BLApplcation getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBLUserInfoUnits = new BLUserInfoUnits(this);

        sdkInit();
    }


    private void sdkInit(){
//        UserAgent userAgent;
        YouzanSDK.init(this, "946cee54b742d529841488763686702");


            BLLet.init(this);
            BLLet.DebugLog.on();

// 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
//        .showImageOnLoading(R.drawable.banner_load) //设置图片在下载期间显示的图片
//                .showImageForEmptyUri(R.mipmap.ic_default_adimage) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.ic_default_adimage) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565) // 防止内存溢出的，图片太多就这这个。还有其他设置
//                 .displayer(new FadeInBitmapDisplayer(3000))//是否图片加载好后渐入的动画时间
//                 .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build(); // 创建配置过得DisplayImageOption对象

        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");  //缓存目录


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                //解释：当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .denyCacheImageMultipleSizesInMemory()  //拒绝缓存多个图片。
                .discCacheFileCount(100) //缓存的文件数量
                //.discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheSize(6 * 1024 * 1024)
                .memoryCache(new WeakMemoryCache())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 10 * 1000, 20 * 1000)) // connectTimeout (3 s), readTimeout (10 s)超时时间
                //.discCache(new TotalSizeLimitedDiscCache(cacheDir, 10 * 1024 * 1024))
                .build();
        ImageLoader.getInstance().init(config);

        //注册网络状态改变监听服务
        Intent serviceIntent = new Intent(this, NetStateChangeService.class);
        this.startService(serviceIntent);
    }

    public void appFinish(){
        finish();
    }
}
