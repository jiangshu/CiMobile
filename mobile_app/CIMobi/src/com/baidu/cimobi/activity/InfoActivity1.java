package com.baidu.cimobi.activity;

import com.baidu.cimobi.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.cimobi.service.*;
import com.baidu.cimobi.util.*;
import com.baidu.cimobi.connect.*;
import android.util.Log;
import java.util.ArrayList;

import com.baidu.cimobi.mobileinfo.ObjectTest;


public class InfoActivity1 extends Activity 
{
    private EditText serverInfo = null;
    private EditText sendInfo = null;
    private Button sendButton = null;
    private Button smdButton = null;
    private Button conButton = null; 
    private Socket clientSocket = null;
    private Thread connect = null;
    private ConnectHandler1 connetHandler = null;
    private MessageHandler messageHandler;
    private static final String TAG = "InfoActivity"; 


    public void onCreate(Bundle savedInstanceState) 
    {   
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);        
		serverInfo = (EditText)findViewById(R.id.server_info);
		sendInfo = (EditText)findViewById(R.id.send_info);         
        sendButton = (Button)findViewById(R.id.sendBtn);
        smdButton = (Button)findViewById(R.id.cmdBtn);
        conButton = (Button)findViewById(R.id.conBtn);
        
        messageHandler = new MessageHandler(serverInfo);
        messageHandler.addMessage("服务器已启动");
        Log.i(TAG, "onCreate"); 
//        getPackages();
        
//        (new TestThread()).start();
//        startService();


        
        conButton.setOnClickListener(new View.OnClickListener() {
			
			@Override	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				connetHandler = new ConnectHandler1(messageHandler);
				connetHandler.start();
//				out = connect.getSocketOut();
//				in = connect.getSocketIn();
//				clientSocket = connect.getSocket();
			}
		});
        
        smdButton.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent();
		        intent.setAction("android.intent.action.VIEW");    
		        Uri content_url = Uri.parse("http://www.baidu.com");   
		        intent.setData(content_url);           
		        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity"); 
		        startActivity(intent);
                displayToast("启动浏览器");
			}
		});
        
        sendButton.setOnClickListener(new View.OnClickListener() 
        {           
            @Override
            public void onClick(View v) 
            {    
            	try{
            		clientSocket = connetHandler.getSocket();
            		OutputStream out =clientSocket.getOutputStream();
    				String sendText = sendInfo.getText().toString()+"\n";
    				byte[] sendByte = sendText.getBytes("GBK");
    				serverInfo.append(sendText);
    				out.write(sendByte);
    				sendInfo.getText().clear();
            		
                 } catch (IOException e) {
 
                      e.printStackTrace();
                  }    
             }
        });
        
        
//        public boolean onKeyDown(int keyCode, KeyEvent event) {
//        	
//        }
        
    }
    
    private void startService(){
    	Intent intent=new Intent(this,MainService.class);
//    	intent.putExtra("serverInfo", serverInfo);
    	startService(intent);
    }
    
    
    public boolean onKeyDown(int keyCode,KeyEvent event){
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		displayToast("后退按钮");
    	}else if(keyCode == KeyEvent.KEYCODE_HOME){
    		displayToast("home按钮");
    	}
//    	return super.onKeyDown(keyCode, event);
    	return false;
    }
    
    
    
    //显示Toast函数
    private void displayToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    
    protected void onStop(){ //activity进入后台时将会调用onstop()方法
    	super.onStop();
//    	Log.v("BACKGROUND","程序进入后台");
////    	showNotification();
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy(); 
//        if(null != connetHandler){
//            connect = connetHandler.getConnnet();
//            if(connect != null)
//            {
//                connect.interrupt();
//            }
//        }
    }
    
    public void getMobileInfo(){
    	messageHandler.addMessage(android.os.Build.PRODUCT);
    	messageHandler.addMessage(android.os.Build.TAGS);
    	messageHandler.addMessage(""+android.os.Build.VERSION_CODES.BASE);
    	messageHandler.addMessage(android.os.Build.MODEL);
    	messageHandler.addMessage(android.os.Build.VERSION.SDK);
    	messageHandler.addMessage(android.os.Build.VERSION.RELEASE);
    	messageHandler.addMessage(android.os.Build.DEVICE);
    	messageHandler.addMessage(android.os.Build.DISPLAY);
    	messageHandler.addMessage(android.os.Build.BRAND);
    	messageHandler.addMessage(android.os.Build.BOARD);
    	messageHandler.addMessage(android.os.Build.FINGERPRINT);
    	messageHandler.addMessage(android.os.Build.ID);
    	messageHandler.addMessage(android.os.Build.MANUFACTURER);
    	messageHandler.addMessage(android.os.Build.USER);
    }
    
    
    public HashMap<String,String> getPackages(){
    	HashMap<String,String> packageList = new HashMap<String,String>();
//        ArrayList<HashMap<String,String>> packageList = new ArrayList<HashMap<String,String>>();
    	List<PackageInfo> packages = getPackageManager().getInstalledPackages(0); 
    	String packageLable = "";
    	String packageName = "";
          
        for(int i=0;i<packages.size();i++) { 
	        PackageInfo packageInfo = packages.get(i);
	        packageLable = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(); //app名称
	        packageName = packageInfo.packageName;                                                //app包名 
	        packageList.put(packageLable, packageName);
//          AppInfo tmpInfo = new AppInfo(); 
//          tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(); 
//          tmpInfo.packageName = packageInfo.packageName; 
//          tmpInfo.versionName = packageInfo.versionName; 
//          tmpInfo.versionCode = packageInfo.versionCode; 
//          tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
        }
        
        Iterator<String> paIter = packageList.keySet().iterator();
        while (paIter.hasNext()) {
			packageLable = paIter.next();
			packageName = packageList.get(packageLable);
			messageHandler.addMessage("**************");
			messageHandler.addMessage(packageLable);
			messageHandler.addMessage(packageName);
        }
        
        return packageList;        
    }
    
    class TestThread extends Thread{
    	public void run(){
    		while(true){
    			try{
    				sleep(2000);
    				Log.i(TAG, "service_beat");
    			}catch(Exception e){
    				Log.i(TAG, "service_error");
    			}
    			
    		}
    	}
    }
     
}