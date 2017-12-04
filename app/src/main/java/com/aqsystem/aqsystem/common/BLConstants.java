package com.aqsystem.aqsystem.common;

public class BLConstants {

    /**保存APP中的信息，方便修改成中性APP版本**/
    public static class APPConfigInfo{
        /**app文件夹目录**/
        public static final String BASE_FILE_PATH = "broadlink/eControl";
        
        /**app版本号**/
        public static final int APP_VERSION = 6;
        
        public static final String BROADLINK = "BroadLink";
    }
    
    /**MS1 12个音乐源 的最低版本**/
    public static final int MS1_12_SOURE_MIN_VERSION = 71;

    /**名称最大长度**/
    public static final int NAME_MAX_LENGTH = 50;
    
    public static final String SHARED_PRE_WIFI_FILE = "SHARED_PRE_WIFI_FILE";
    
//    /**第三方 qq登录类型**/
//    public static final int QQ_TYPE = 0;
//    /**第三方 weibo登录类型**/
//    public static final int WEIBO_TYPE = 1;
//    /**第三方 facebook登录类型**/
//    public static final int FACKBOOK_TYPE = 2;
//    /**第三方 twitter登录类型**/
//    public static final int TWITTER_TYPE = 3;

    /**海康APP_KEY**/
    public static final String HIK_APP_KEY = "6e3cb18794b441f499c7c4ec79810590";

    /**语音识别APP ID**/
    public static final String VOICE_APP_ID = "appid=5600bf01";

    /**MD5加密后缀*/
    public static final String STR_BODY_ENCRYPT = "xgx3d*fe3478$ukx";
    
    public static final String STR_RM_KEY_PF = "aas45^#*";
    public static final byte[] BYTES_RM_CODE_IV = {(byte) 0xea, (byte) 0xa4, 0x7a, 0x3a, (byte) 0xeb, 0x08, 
 			0x22, (byte) 0xa2, 0x19, 0x18, (byte) 0xc5, (byte) 0xd7, 0x1d, 0x36, 0x15, (byte) 0xaa};
 	
    /**图标类型**/
    public static final String IMAGE_TYPE_PNG = ".png";
    
    /**临时图标地址**/
    public static final String TEMP_IMAGE = "temp.png";

    /** 文件夹名称 存放一些APP临时数据**/
    public static final String FILE_DATA = "data";
    //分享备份文件夹
    public static final String FILE_SHARE = "SharedData";
    //云空调控制指令
    public static final String FILE_CON_CODE = "ConCode";
    //设备图标
    public static final String FILE_DEVICE_ICON = "DeviceIcon";
    //RM数据
    public static final String FILE_IR_DATA = "IrData";
    //场景图标
    public static final String SCENE_NAME = "SceneIcon";
    //设备脚本文件
    public static final String FILE_SCRIPTS = "Scripts";
    //存放下载下来的HTML5文件夹
    public static final String FILE_DRPS = "Drps";
    //MS1背景图片
    public static final String FILE_MS1 = "MS1";
    //家庭文件夹名称
    public static final String FILE_FAMILY = "Family";
    //离线图标
    public static final String OFF_LINE_ICON = "OffLineIcon";

    /** 隐藏文件夹 存放S1的传感器信息文件 **/
    public static final String FILE_S1 = ".S1";

    /** 数据库ID起始值 **/
    public static final int BASE_ID = 1;
    
    public static final String INTENT_IMAGE_PATH = "INTENT_IMAGE_PATH";
    public static final String INTENT_IMAGE_URI = "INTENT_IMAGE_URI";
    public static final String INTENT_CROP_X = "INTENT_CROP_X";
    public static final String INTENT_CROP_Y = "INTENT_CROP_Y";
    public static final String INTENT_WIDTH = "INTENT_WIDTH";
    public static final String INTENT_HEIGH = "INTENT_HEIGH";
    public static final String INTENT_PASSWORD = "INTENT_PASSWORD";
    public static final String INTENT_EMAIL = "INTENT_EMAIL";
    public static final String INTENT_PHONE = "INTENT_PHONE";
    public static final String INTENT_REGISTER_FLAG = "INTENT_REGISTER_FLAG";
    public static final String INTENT_CODE = "INTENT_CODE";
    public static final String INTENT_NAME = "INTENT_NAME";
    public static final String INTENT_ICON = "INTENT_ICON";
    public static final String INTENT_URL = "INTENT_URL";
    public static final String INTENT_ID = "INTENT_ID";
    public static final String INTENT_FAMILY = "INTENT_FAMILY";
    public static final String INTENT_ROOM = "INTENT_ROOM";
    public static final String INTENT_TYPE = "INTENT_TYPE";
    public static final String INTENT_DEVICE = "INTENT_DEVICE";
    public static final String INTENT_SUB_DEVICE = "INTENT_SUB_DEVICE";
    public static final String INTENT_ADDRESS = "INTENT_ADDRESS";
    public static final String INTENT_ACTION = "INTENT_ACTION";
    public static final String INTENT_FAMILY_ID = "INTENT_FAMILY_ID";
    public static final String INTENT_PAGE_ID = "INTENT_PAGE_ID";
    public static final String INTENT_MODEL = "INTENT_MODEL";
    public static final String INTENT_MODULE = "INTENT_MODULE";
    public static final String INTENT_OBJECT = "INTENT_OBJECT";
    public static final String INTENT_TASK = "INTENT_TASK";
    public static final String INTENT_TITLE = "INTENT_TITLE";
    public static final String INTENT_ARRAY = "INTENT_ARRAY";
    public static final String INTENT_CAT = "INTENT_CAT";
    public static final String INTENT_SCENE = "INTENT_SCENE";
    public static final String INTENT_BTN = "INTENT_BTN";
    public static final String INTENT_VALUE = "INTENT_VALUE";
    public static final String INTENT_START_TIME = "INTENT_START_TIME";
    public static final String INTENT_END_TIME = "INTENT_END_TIME";
    public static final String INTENT_SENSOR_TYPE = "INTENT_SENSOR_TYPE";
    public static final String INTENT_SENSOR_TRIGGER = "INTENT_SENSOR_TRIGGER";
    public static final String INTENT_TRIGGER_VALUE = "INTENT_TRIGGER_VALUE";
    public static final String INTENT_PARAM = "INTENT_PARAM";
    public static final String INTENT_PARAMNAME = "INTENT_PARAMNAME";
    public static final String INTENT_IFTTT = "IFTTT";
    public static final String INTENT_S1_IF_VALUE = "INTENT_S1_IF_VALUE";
    public static final String INTENT_HINT = "INTENT_HINT";
    public static final String INTENT_POSITION = "INTENT_POSITION";
    public static final String INTENT_BIND_LIST = "INTENT_BIND_LIST";
    public static final String INTENT_ADD_TIMER = "INTENT_ADD_TIMER";
    public static final String INTENT_MS1_STATUS = "INTENT_MS1_STATUS";
    public static final String INTENT_UPDATE = "INTENT_UPDATE";
    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String INTENT_CODE_DATA = "INTENT_CODE_DATA";
    public static final String INTENT_RELATION = "INTENT_RELATION";
    //
    public static final String TAOBAO_APP_KEY = "23009131";
    public static final String TAOBAO_SECRET = "1724558166c2455272d492cbd6eea5c4";

    public static final String INTENT_CONFIG = "INTENT_CONFIG";
    public static final String INTENT_EDIT_TYPE = "INTENT_EDIT_TYPE";
    public static final String INTENT_INDEX = "INTENT_INDEX";
    public static final String INTENT_IFTTT_COUNT = "INTENT_IFTTT_COUNT";
    public static final String INTENT_CLASS = "INTENT_CLASS";

    //添加定时任务类型
    public static final String INTENT_TIME_TASK_TYPE = "INTENT_TIME_TASK_TYPE";
    public static final String INTENT_WEEKS = "INTENT_WEEKS";
    public static final String INTENT_DEVICE_ID = "INTENT_DEVICE_ID";
    public static final String INTENT_TIME_EDIT = "INTENT_TIME_EDIT";


    /**获取产品目录URL**/
    public static final int RES_TYPE_GET_PRODUCT = 101;

    /**私有数据 运营商key**/
    public static final String PRI_DATA_KEY_PROVIDER = "PRI_DATA_KEY_PROVIDER";
}
