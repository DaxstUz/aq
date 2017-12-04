/**
 * 
 */
package com.aqsystem.aqsystem.util;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.aqsystem.aqsystem.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.lang.reflect.Field;


/**
 * 分辨率适配工具类,以1920X1280 为基准分辨率
 * @author ouyangshuai
 */
public class UIUtils
{
	public static final int STANDARD_WIDTH = 1080;

	public static final int STANDARD_HEIGHT = 1920;

	private static final String DIMEN_CLAZZ = "com.android.internal.R$dimen";

	private static UIUtils uIUtils;

	public float displayMetricsWidth;

	public float displayMetricsHeight;

	private int[] screensize = new int[2];

    int systemStatusHight;
    int systemNavigationBarHight;

    private Context mContext;
	private UIUtils(Context context)
	{
        mContext = context;
		if (displayMetricsWidth == 0 || displayMetricsHeight == 0)
		{
			WindowManager windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
			DisplayMetrics displayMetrics = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(displayMetrics);


			systemStatusHight = getStatusBarHeight(context);  // 计算系统状态栏高度
			systemNavigationBarHight = getNavigationBarHeight(context);  // 计算系统工具栏(虚拟按键栏)高度，displayMetrics.heightPixels 没有把系统工具栏算进去
            float whScaleDefualt = STANDARD_WIDTH*1.0f/STANDARD_HEIGHT*1.0f;

//			if (displayMetrics.widthPixels < displayMetrics.heightPixels)
//			{
				//displayMetrics.heightPixels这个高度是除掉了系统工具栏的高度 不是实际屏幕高度
				displayMetricsWidth = displayMetrics.widthPixels ;
				displayMetricsHeight = displayMetrics.heightPixels;

//            float whScaleDevice = displayMetricsWidth*1.0f/displayMetricsHeight*1.0f;
//            if(whScaleDevice > whScaleDefualt)
//            {
//                displayMetricsHeight += systemNavigationBarHight;
//            }

			screensize[0] = (int) displayMetricsWidth;
			screensize[1] = (int) displayMetricsHeight;
		}
	}

    public void getScreenInfo()
    {
        System.out.println("WIDTH=" + displayMetricsWidth + "///" + "HEIGHT = " +displayMetricsHeight+"///systemNavigationBarHight="
                +systemNavigationBarHight + "//systemStatusHight = "+systemStatusHight+
                "//1080w ="+mContext.getResources().getDimensionPixelSize(R.dimen.x1080));
    }

	public static int getSystemBarHeight(Context context)
	{
		return getValue(context, DIMEN_CLAZZ, "system_bar_height", 48);
	}

	/**
	 * 获取顶部status bar 高度
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
		int height = resources.getDimensionPixelSize(resourceId);
		if(height <= 0)
		{
			height = 64;
		}
		return height;
	}

	/**
	 * 获取底部 navigation bar 高度
	 * @return
	 */
	private int getNavigationBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		if(height <= 0)
		{
			height = 0;
		}
		return height;
	}

	private static int getValue(Context context, String attrGroupClass, String attrName, int defValue)
	{
		try
		{
			Class<?> clazz = Class.forName(attrGroupClass);
			Object obj = clazz.newInstance();
			Field field = clazz.getField(attrName);
			int x = Integer.parseInt(field.get(obj).toString());
			return context.getResources().getDimensionPixelSize(x);
		}
		catch (Exception e)
		{
			return defValue;
		}
	}

	/**
	 * 
	 * <p>获取UIUtils 实例</p>
	 * @param context
	 * @return
	 */
	public synchronized static UIUtils getInstance(Context context)
	{
		if (uIUtils == null)
		{
			uIUtils = new UIUtils(context);
		}
		return uIUtils;
	}

	/**
	 * 
	 * <p>获取屏幕的像素宽度</p>
	 * @return 屏幕的像素宽度
	 */
	public int getWidth()
	{
		return (int) displayMetricsWidth;
	}

	/**
	 * 
	 * <p>获取屏幕的像素高度</p>
	 * @return 屏幕的像素高度
	 */
	public int getHeight()
	{
		return (int) displayMetricsHeight;
	}

	/**
	 * 
	 * <p>获取分辨率适配后的宽度</p>
	 * @param width 1920分辨率下的宽度
	 * @return 分辨率适配后的宽度
	 */
	public float getWidth(float width)
	{
		return (width * displayMetricsWidth) / STANDARD_WIDTH;
	}

	/**
	 * 
	 * <p>获取分辨率适配后的宽度</p>
	 * @param width 1920x1280分辨率下的宽度
	 * @return 分辨率适配后的宽度
	 */
	public int getWidth(int width)
	{
		return Math.round((width * displayMetricsWidth) / STANDARD_WIDTH);
	}

	/**
	 * 
	 * <p>获取分辨率适配后的高度</p>
	 * @param height 1920x1280分辨率下的高度
	 * @return 分辨率适配后的高度
	 */
	public float getHeight(float height)
	{
		return (height * displayMetricsHeight) / STANDARD_HEIGHT;
	}

	/**
	 * 
	 * <p>获取分辨率适配后的高度</p>
	 * @param height 1920x1280分辨率下的高度
	 * @return 分辨率适配后的高度
	 */
	public int getHeight(int height)
	{
		return Math.round((height * displayMetricsHeight) / STANDARD_HEIGHT);
	}

	/**
	 * 获取屏幕的类型,默认返回1280*800类型的
	 * 
	 * @return 1为1024 2为1280 3为1920
	 */
	public int getScreenType()
	{
		if (displayMetricsWidth == 1024 || displayMetricsHeight == 1024)
		{
			return 1;
		}
		else if (displayMetricsWidth == 1280 || displayMetricsHeight == 1280)
		{
			return 2;
		}
		else if (displayMetricsWidth == 1920 || displayMetricsHeight == 1920)
		{
			return 3;
		}

		return 2;
	}

	/**
	 * 
	 * <p>获取UIUtils 实例</p>
	 * @param view view对象
	 * @return UIUtils 实例
	 */
	public static UIUtils getInstace(View view)
	{
		return getInstance(view.getContext());
	}

	/**
	 * 
	 * <p>获取UIUtils 实例</p>
	 * @param view 容器对象
	 * @return UIUtils 实例
	 */
	public static UIUtils getInstace(ViewGroup view)
	{
		return getInstance(view.getContext());
	}

	/**
	 * 
	 * <p>获取屏幕分辨率,高度是去掉工具类的高度</p>
	 * @return 屏幕分辨率
	 */
	public int[] getScreenSize()
	{
		return screensize;
	}

//	public static DisplayImageOptions optionsbanner = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.icon_consult_banner)//设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.icon_consult_banner)//设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.icon_consult_banner)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//			.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//			.considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//			.build();

//	public static DisplayImageOptions optionshead = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.public_icon_customer)//设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.public_icon_customer)//设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.public_icon_customer)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//			.cacheInMemory(false)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//			.considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//			.build();

//	public static DisplayImageOptions optionshead2 = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.public_icon_head)//设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.public_icon_head)//设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.public_icon_head)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//			.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//			.considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//			.build();

//    public static DisplayImageOptions optionsflash= new DisplayImageOptions.Builder()
////            .showImageOnLoading(R.drawable.start_up)//设置图片在下载期间显示的图片
////            .showImageForEmptyUri(R.drawable.start_up)//设置图片Uri为空或是错误的时候显示的图片
////            .showImageOnFail(R.drawable.start_up)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//            .cacheInMemory(false)//设置下载的图片是否缓存在内存中
//            .cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//            .considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//            .imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//            .build();

//    public static DisplayImageOptions optionsheaddef = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.avatar_def)//设置图片在下载期间显示的图片
//            .showImageForEmptyUri(R.drawable.avatar_def)//设置图片Uri为空或是错误的时候显示的图片
//            .showImageOnFail(R.drawable.avatar_def)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
//            .cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//            .considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//            .imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//            .build();

//    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
////            .showImageOnLoading(R.drawable.public_icon_customer_small)//设置图片在下载期间显示的图片
////    .showImageForEmptyUri(R.drawable.public_icon_customer_small)//设置图片Uri为空或是错误的时候显示的图片
////    .showImageOnFail(R.drawable.public_icon_customer_small)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
//            .cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//            .considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//            .imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//            .build();

//    public static DisplayImageOptions options_nocache = new DisplayImageOptions.Builder()
////            .showImageOnLoading(R.drawable.public_icon_customer_small)//设置图片在下载期间显示的图片
////    .showImageForEmptyUri(R.drawable.public_icon_customer_small)//设置图片Uri为空或是错误的时候显示的图片
////    .showImageOnFail(R.drawable.public_icon_customer_small)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//            .cacheInMemory(false)//设置下载的图片是否缓存在内存中
//            .cacheOnDisk(false)// 设置下载的资源是否缓存在SD卡中
//            .considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//            .imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//            .build();

//    public static DisplayImageOptions options_cachefalse = new DisplayImageOptions.Builder()
////            .showImageOnLoading(R.drawable.public_icon_customer_small)//设置图片在下载期间显示的图片
////    .showImageForEmptyUri(R.drawable.public_icon_customer_small)//设置图片Uri为空或是错误的时候显示的图片
////    .showImageOnFail(R.drawable.public_icon_customer_small)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
//            .cacheOnDisk(false)// 设置下载的资源是否缓存在SD卡中
//            .considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//设置图片以何种编码方式显示
//            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//            .build();

//	public static DisplayImageOptions options16_9 = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.icon_16_9)//设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.icon_16_9)//设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.icon_16_9)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//			.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//			.considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//			.build();

	public static DisplayImageOptions options2_1 = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.mipmap.icon_2_1)//设置图片在下载期间显示的图片
			.showImageForEmptyUri(R.mipmap.icon_2_1)//设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.mipmap.icon_2_1)//设置图片加载/解码过程中错误时候显示的图片
//			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
			.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
			.considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
//    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
			.build();

//    public static DisplayImageOptions options4_3_nocache = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.icon_4_3)//设置图片在下载期间显示的图片
//            .showImageForEmptyUri(R.drawable.icon_4_3)//设置图片Uri为空或是错误的时候显示的图片
//            .showImageOnFail(R.drawable.icon_4_3)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//            .cacheInMemory(false)//设置下载的图片是否缓存在内存中
//            .cacheOnDisk(false)// 设置下载的资源是否缓存在SD卡中
//            .considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//设置图片以何种编码方式显示
//            .bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//            .build();

//    public static DisplayImageOptions options4_3 = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.icon_4_3)//设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.icon_4_3)//设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.icon_4_3)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//			.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//			.considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//			.build();
//
//	public static DisplayImageOptions optionsbanner_home = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.home_lunbo_920_220)//设置图片在下载期间显示的图片
//			.showImageForEmptyUri(R.drawable.home_lunbo_920_220)//设置图片Uri为空或是错误的时候显示的图片
//			.showImageOnFail(R.drawable.home_lunbo_920_220)//设置图片加载/解码过程中错误时候显示的图片
////			.delayBeforeLoading(1000)//设置延时多少时间后开始下载
//			.cacheInMemory(false)//设置下载的图片是否缓存在内存中
//			.cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
//			.considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
//			.imageScaleType(ImageScaleType.EXACTLY)//设置图片以何种编码方式显示
//			.bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
////    .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
////            .displayer(new FadeInBitmapDisplayer(1000))//是否图片加载好后渐入的动画时间
//			.build();
//
//
//    public static BitmapDrawable dr(Context c, int r) {
//        Bitmap bitmap = readBitMap(c, r);
//        BitmapDrawable bd = new BitmapDrawable(bitmap);
//// bd.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//        bd.setDither(true);
//        return bd;
//    }
//
//    public static Bitmap readBitMap(Context context, int resId) {
//        BitmapFactory.Options opt = new BitmapFactory.Options();
//        opt.inPreferredConfig = Bitmap.Config.RGB_565;
//        opt.inPurgeable = true;
//        opt.inInputShareable = true;
//// 获取资源图片
//        InputStream is = context.getResources().openRawResource(resId);
//        return BitmapFactory.decodeStream(is, null, opt);
//    }
}
