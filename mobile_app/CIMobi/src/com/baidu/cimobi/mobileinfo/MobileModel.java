package com.baidu.cimobi.mobileinfo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.baidu.cimobi.javabean.MobileInfo;
import java.util.HashMap;
import java.util.List;
import android.app.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import com.baidu.cimobi.data.Config;


public class MobileModel {
  
   private MobileInfo MobileInstance;
   private String Uid;
   private HashMap<String,String> packageList;
   private HashMap<String,String>browserList;
   private HashMap<String,String>browserActivityList;
   private Context service;
   
   public MobileModel(Context service){
	   this.MobileInstance  = new MobileInfo();
	   this.service = service;
	   packageList = getPackages();
	   browserActivityList = getBrowser(); 
	   Uid = getDeviceId();
	   MobileInstance.setUid(Uid);
	   MobileInstance.setMobileType(android.os.Build.MODEL);              //手机的品牌及型号
	   MobileInstance.setAndroidVersion(android.os.Build.VERSION.RELEASE);//系统的版本
	   MobileInstance.setAlias(Config.getConfig(service).get(2));
//	   MobileInstance.setPackageList(getPackages());
	   MobileInstance.setBrowserList(browserList);
   }
   
   
   public MobileInfo getMobileInfo(){
	   return MobileInstance;
   }
   
   public HashMap<String,String>getBrowserActivityList(){
	   return browserActivityList;
   }
   
   
	/*
	 * 获取手机中已安装的程序
	 * */
   private HashMap<String,String> getPackages(){
   	HashMap<String,String> packageList = new HashMap<String,String>();
   	List<PackageInfo> packages = service.getPackageManager().getInstalledPackages(0); 
   	String packageLable = "";
   	String packageName = "";
          
       for(int i=0;i<packages.size();i++) { 
	        PackageInfo packageInfo = packages.get(i);
	        packageLable = packageInfo.applicationInfo.loadLabel(service.getPackageManager()).toString(); //app名称
	        packageName = packageInfo.packageName;                                                        //app包名 
	        packageList.put(packageLable, packageName);
       }
       return packageList;        
   }
   
   
   /*
    * 获取mobile中的浏览器及入口activity
    * */ 
   private HashMap<String,String> getBrowser(){
   	   HashMap<String,String> browserMap = new HashMap<String,String>();
       browserList = new HashMap<String,String>();
       String packageName = "";
       Intent intent = new Intent(Intent.ACTION_VIEW);    
       Uri content_url = Uri.parse("http://www.baidu.com");   
       intent.setData(content_url);     
       
       PackageManager pm = service.getPackageManager(); 
       List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0); 
       if (null != activities) { 
           for (int i = 0; i < activities.size(); i++) { 
               ResolveInfo info = activities.get(i); 
               packageName = info.activityInfo.packageName;
               browserMap.put(packageName, info.activityInfo.name);
               if(isFind(".\\.*opera\\..*",packageName)){
            	   browserList.put("opera",packageName);
               }else if(isFind(".*\\.tencent\\..*",packageName)){
            	   browserList.put("QQ",packageName);
               }else if(isFind(".*UC.*",packageName)){
            	   browserList.put("uc",packageName);
               }else if(isFind(".*chrome.*",packageName)){
            	   browserList.put("chrome",packageName);
               }else if(isFind(".*com\\.android\\.browser.*",packageName)){
            	   browserList.put("native",packageName);
               }else{
            	   browserList.put(packageName,packageName);
               }
           } 
       }
   	return browserMap;
   }
   
   
   /*
    * 获取手机的唯一标识
    * */
   private String getDeviceId(){
       DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(service);
       String Uid = deviceUuidFactory.getDeviceUuid().toString();
//       Uid = Uid.substring(0, 13);
       return Uid;
   }
   
	private boolean isFind(String reg,String target){
		Pattern p = Pattern.compile(reg);
		Matcher m =  p.matcher(target);
		if(m.find()){
			return true;
		}
		return false;
	}
}

