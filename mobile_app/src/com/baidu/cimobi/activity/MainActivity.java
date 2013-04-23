package com.baidu.cimobi.activity;

import com.baidu.cimobi.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.PrintWriter;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.cimobi.service.MainService;
import com.baidu.cimobi.service.MainService.MainServiceBinder;
import com.baidu.cimobi.util.*;
import com.baidu.cimobi.connect.*;
import android.util.Log;

import android.content.ServiceConnection;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.os.IBinder;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.graphics.Color;

import java.util.*;
import java.text.*;

/*
 * auth:jiangshuguang
 * CiMobile��ʾ������
 *  1.��ʾ��־��Ϣ��
 *  2.���÷�����ip��
 *  3.�쳣���������� 
 * */

public class MainActivity extends Activity 
{
    private EditText serverInfo = null;
    private EditText sendInfo = null;
    private Button sendButton = null;
    private Button cmdButton = null;
    private Button conButton = null; 
    private Socket clientSocket = null;
    private Thread connect = null;
    private ConnectHandler1 connetHandler = null;
    private MessageHandler messageHandler;
    private static final String TAG = "MainActivity"; 

    public void onCreate(Bundle savedInstanceState) 
    {   
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);        
		serverInfo = (EditText)findViewById(R.id.server_info);
		sendInfo = (EditText)findViewById(R.id.send_info);         
        sendButton = (Button)findViewById(R.id.sendBtn);
        cmdButton = (Button)findViewById(R.id.cmdBtn);
        conButton = (Button)findViewById(R.id.conBtn);
        showNotification();//����֪ͨ 
        Log.i(TAG, "onCreate"); 

        conButton.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {
				displayToast("����service");
				bindService(new Intent(MainActivity.this,MainService.class),mainSrvConn,BIND_AUTO_CREATE);
			}
		});
        
        cmdButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
//		        Intent intent = new Intent();
//		        intent.setAction("android.intent.action.VIEW");    
//		        Uri content_url = Uri.parse("http://www.baidu.com");   
//		        intent.setData(content_url);           
////		        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity"); 
//		        intent.setClassName("com.tencent.mtt","com.tencent.mtt.MainActivity");
//		        startActivity(intent);
//                displayToast("���������");
				
		        Intent intent = new Intent(Intent.ACTION_VIEW);  
		        Uri content_url = Uri.parse("http://www.baidu.com");   
		        intent.setData(content_url);     
		        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity"); 
		        intent.setClassName("com.tencent.mtt","com.tencent.mtt.SplashActivity");
		        startActivity(intent);
				
				
//				Intent broadcastIntent = new Intent("com.baidu.cimobi.activity.broadcast");
//				broadcastIntent.putExtra("info", "111111111");
//				sendBroadcast(broadcastIntent);
			
			}
		});
        
        sendButton.setOnClickListener(new View.OnClickListener() 
        {           
            public void onClick(View v) 
            {  
                try{
					unbindService(mainSrvConn);
				}catch (Exception e) {
					
			    }
            }
        });
        
        IntentFilter filter = new IntentFilter("com.baidu.cimobi.service.broadcast");
        registerReceiver(receiverLog,filter);
    }
      
    /*
     * ���� MainService�Ĺ㲥��Ϣ��
     * */
    private BroadcastReceiver receiverLog = new BroadcastReceiver(){
    	public void onReceive(Context context, Intent intent) {
    		serverInfo.append(intent.getExtras().getString("log"));
    	}
    }; 
    
     
    /*
     * ServiceConnection ��ʽ���� service��
     * */
    ServiceConnection mainSrvConn = new ServiceConnection() {    
    	
    	public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "onServiceConnected "+name);
			MainServiceBinder binder = (MainServiceBinder) service;
			binder.doLog("Demo_ServiceActivity_CONTENT");
		}

    	public void onServiceDisconnected(ComponentName name) {    	
			Log.d(TAG, "onServiceDisconnected:"+name);
		}		
	};
	
   
	/*
	 * ֪ͨ������
	 * */
    public void showNotification(){
    	NotificationManager notificationManager = (NotificationManager)this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    	Notification notification = new Notification(R.drawable.ic_launcher,"CiMObile",System.currentTimeMillis());
    	
    	notification.flags |= Notification.FLAG_ONGOING_EVENT;
    	notification.flags |= Notification.FLAG_NO_CLEAR;
    	notification.flags |= Notification.FLAG_SHOW_LIGHTS;
    	notification.ledARGB = Color.BLUE; 
    	notification.ledOnMS =5000; 
    	
    	CharSequence contentTitle ="CiMObile";        //֪ͨ������
    	CharSequence contentText ="����CiMObile������";//֪ͨ������
    	
    	Intent notificationIntent =new Intent(MainActivity.this, MainActivity.class);  //�����activity����ת����activity
    	PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    	notification.setLatestEventInfo(this, contentTitle, contentText, contentItent);   	
    	notificationManager.notify(0, notification); 
    	
    }
    
    
    //��ʾToast����
    private void displayToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    
    protected void onStop(){ //activity�����̨ʱ�������onstop()����
    	super.onStop();
//    	Log.v("BACKGROUND","��������̨");
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
    
    public boolean onKeyDown(int keyCode,KeyEvent event){
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		displayToast("���˰�ť");
    		return false;
    	}else if(keyCode == KeyEvent.KEYCODE_HOME){
    		displayToast("home��ť");
    	}
    	return super.onKeyDown(keyCode, event);
//    	return false;
    }
    
    public void getSystemTime(){
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance();
//    	DateFormat d1 = DateFormat.getDateInstance();
    	DateFormat d2 = DateFormat.getInstance();
    	String str1 = d2.format(now);
    	displayToast(str1);
    	serverInfo.append(str1+"\n");
    }
    
}