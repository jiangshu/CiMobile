package com.baidu.cimobi.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import android.os.Binder;
import android.os.IBinder;
import com.baidu.cimobi.connect.ConnectHandler;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import java.io.PrintWriter;

import com.baidu.cimobi.mobileinfo.DeviceUuidFactory;
import com.baidu.cimobi.mobileinfo.MobileModel;

/*
 * auth:jiangshuguang
 * 
 * 1.后台程序
 * 2.处理连接等问题 
 * 
 * */

public class MainService extends Service {
  
	private static final String TAG = "MainService"; 
	private PrintWriter out = null;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind"); 
		return null;
	}
	
	public void onCreate(){
		Log.i(TAG, "onCreate"); 
		super.onCreate();
		MobileModel mobileInstance = new MobileModel(this); 
		ConnectHandler connectHandler = new ConnectHandler(this,mobileInstance);
		connectHandler.start();	
//		Socket socket = connectHandler.getSocket();
//		out = connectHandler.getSocketOut();
//        IntentFilter filter = new IntentFilter("com.baidu.cimobi.activity.broadcast");
//        registerReceiver(receiverLog,filter);
	}
	
	
	
    /*
     * 接收 MainService的广播消息；
     * */
    private BroadcastReceiver receiverLog = new BroadcastReceiver(){
    	public void onReceive(Context context, Intent intent) {
    		String str = intent.getExtras().getString("info");
    		Log.i(TAG, str); 
//    		out.println(str);
    	}
    }; 
    
    
	/*
	 * 获取手机中已安装的程序
	 * */
    private HashMap<String,String> getPackages(){
    	HashMap<String,String> packageList = new HashMap<String,String>();
    	List<PackageInfo> packages = getPackageManager().getInstalledPackages(0); 
    	String packageLable = "";
    	String packageName = "";
          
        for(int i=0;i<packages.size();i++) { 
	        PackageInfo packageInfo = packages.get(i);
	        packageLable = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(); //app名称
	        packageName = packageInfo.packageName;                                                //app包名 
	        packageList.put(packageLable, packageName);
        }
        return packageList;        
    }
	
    
    /*
     * 获取mobile中的浏览器及入口activity
     * */ 
    private HashMap<String,String> getBrowser(){
    	HashMap<String,String> browserMap = new HashMap<String,String>();
        Intent intent = new Intent(Intent.ACTION_VIEW);    
        Uri content_url = Uri.parse("http://www.baidu.com");   
        intent.setData(content_url);     
        
        PackageManager pm = getPackageManager(); 
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0); 
        if (null != activities) { 
            for (int i = 0; i < activities.size(); i++) { 
                ResolveInfo info = activities.get(i); 
                browserMap.put(info.activityInfo.packageName, info.activityInfo.name);
            } 
        }
    	return browserMap;
    }
    
    /*
     * 获取手机的唯一标识
     * */
    private String getDeviceId(){
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(this);
        return deviceUuidFactory.getDeviceUuid().toString();
    }
	
    
    public void onStart(Intent intent, int startId) { 
        Log.i(TAG, "onStart"); 
        super.onStart(intent, startId); 
    }
       
    
	public class MainServiceBinder extends Binder{
		public void doLog(String logContent){
			Log.d(MainService.TAG, "invoke MyServiceBinder doLog:"+logContent);
		}
	}

}
