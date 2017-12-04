package com.aqsystem.aqsystem.cache;

import java.util.HashMap;

/**
 * 保存网络状态信息
 * @author xc.li
 * @date 2015年7月20日
 */
public class NetInfoCache {
	/**
	 * 通用key： wifi
	 */
	public static final String NET_WIFI = "wifi";
	/**
	 * 通用key： 2g
	 */
	public static final String NET_2G = "2g";
	/**
	 * 通用key： 3g
	 */
	public static final String NET_3G = "3g";
	/**
	 * 通用key： 4g
	 */
	public static final String NET_4G = "4g";
	
	/**
	 * 网络信息缓存
	 */
	public static HashMap<String, Integer> netInfos = new HashMap<String, Integer>();

	/**
	 * 设置缓存
	 * @param key
	 * @param netinfo
	 */
	public static void setNetInfo(String key, int level){
		netInfos.put(key, level);
	}
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public static int getNetInfo(String key){
		return netInfos.get(key)==null ? 0 : netInfos.get(key);
	}
	
	/**
	 * 获取网络状态
	 * @return 0：无网络或信号强度为0；1：2G网络；2：wifi,3G,4G网络信号差
	 */
	public static int getNetState(){
		int flag = 0;
		int wifiStrongth = 0, g4Strongth = 0, g3Strongth = 0, g2Strongth = 0;
		if(netInfos.containsKey(NetInfoCache.NET_WIFI)){
			wifiStrongth = netInfos.get(NetInfoCache.NET_WIFI);
		}
		if(netInfos.containsKey(NetInfoCache.NET_4G)){
			g4Strongth = netInfos.get(NetInfoCache.NET_4G);
		}
		if(netInfos.containsKey(NetInfoCache.NET_3G)){
			g3Strongth = netInfos.get(NetInfoCache.NET_3G);
		}
		if(netInfos.containsKey(NetInfoCache.NET_2G)){
			g2Strongth = netInfos.get(NetInfoCache.NET_2G);
		}
		
		if(wifiStrongth==0 && g4Strongth==0 && g3Strongth==0 && g2Strongth==0){
			flag = 0;
		}else if(wifiStrongth==0 && g4Strongth==0 && g3Strongth==0 && g2Strongth>0){//2g
			flag = 1;
		}else if(wifiStrongth==0 && g4Strongth==0 && g3Strongth>0 && g2Strongth==0){//3g
			flag = 2;
		}else{
			flag = 3;
		}
		
		return flag;
	}
}
