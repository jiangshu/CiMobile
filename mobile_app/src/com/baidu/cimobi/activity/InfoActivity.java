package com.baidu.cimobi.activity;

import com.baidu.cimobi.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.PrintWriter;


import android.app.Activity;
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

import com.baidu.cimobi.service.*;
import com.baidu.cimobi.service.MainService.MainServiceBinder;
import com.baidu.cimobi.util.*;
import com.baidu.cimobi.connect.*;
import android.util.Log;

import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.IntentFilter;
import android.content.ComponentName;

/*
 * auth:jiangshuguang
 * Log信息显示主界面
 * 
 * */

public class InfoActivity extends Activity 
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
        
        Log.i(TAG, "onCreate"); 
        
        conButton.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {

			}
		});
        
        smdButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

			}
		});
        
        sendButton.setOnClickListener(new View.OnClickListener() 
        {           
            public void onClick(View v) 
            {    
            }
        });        
    }
    
     
    ServiceConnection mainsrvConn = new ServiceConnection() {    
    	
    	public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "onServiceConnected "+name);
			MainServiceBinder binder = (MainServiceBinder) service;
			binder.doLog(" Demo_ServiceActivity_CONTENT");
		}

    	public void onServiceDisconnected(ComponentName name) {
    	
			Log.d(TAG, "onServiceDisconnected:"+name);
		}		
	};
    
    
    
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
    
    public boolean onKeyDown(int keyCode,KeyEvent event){
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		displayToast("后退按钮");
    	}else if(keyCode == KeyEvent.KEYCODE_HOME){
    		displayToast("home按钮");
    	}
    	return super.onKeyDown(keyCode, event);
//    	return false;
    }
}