package com.baidu.cimobi.activity;


import com.baidu.cimobi.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import com.baidu.cimobi.data.Config;
import java.util.ArrayList;
import com.baidu.cimobi.data.SharedData;
import com.baidu.cimobi.data.Log;
import com.baidu.cimobi.service.MainService;
import com.baidu.cimobi.service.MainService.MainServiceBinder;
import com.baidu.cimobi.util.SystemTime;
import com.baidu.cimobi.connect.ConnectStatus;

public class ConfigActivity extends CommonActivity {
	private Button saveBtn = null;
	private Button connectBtn = null;
	private EditText ipText = null;
	private EditText portText = null;
	private EditText aliasText = null;
	private String ip;
	private String port;
	private String alias;
	private ImageView imageViewConfig;
	private Activity me;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		me = this;
		setContentView(R.layout.config);
		init();//初始化
		showNotification();
		
		saveBtn.setOnTouchListener(new SaveEvent());
		connectBtn.setOnTouchListener(new ConncetEvent());
		
//		String status = SharedData.get(this, "connect_status");
//		if("未知".equals(status)){
//			SharedData.set(this, "connect_status", "未连接");
//		}else if("已连接".equals(status)){
//			connectBtn.setEnabled(false);
//		}
		
		if(ConnectStatus.isConnected()){
			connectBtn.setEnabled(false);
		}
	}
	
	/*
	 * 通知处理方法
	 * */
    private void showNotification(){
    	NotificationManager notificationManager = (NotificationManager)this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    	Notification notification = new Notification(R.drawable.ic_launcher,"CiMObile",System.currentTimeMillis());
    	
    	notification.flags |= Notification.FLAG_ONGOING_EVENT;
    	notification.flags |= Notification.FLAG_NO_CLEAR;
    	notification.flags |= Notification.FLAG_SHOW_LIGHTS;
    	notification.ledARGB = Color.BLUE; 
    	notification.ledOnMS =5000; 
    	
    	CharSequence contentTitle ="CiMObile";        //通知栏标题
    	CharSequence contentText ="进入CiMObile主界面";//通知栏内容
    	
    	Intent notificationIntent =new Intent(ConfigActivity.this, ConfigActivity.class);  //点击该activity后跳转到的activity
    	PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    	notification.setLatestEventInfo(this, contentTitle, contentText, contentItent);   	
    	notificationManager.notify(0, notification); 
    	
    }
	

		
	/*
	 * 保存按钮事件
	 * */
	class SaveEvent implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == event.ACTION_DOWN) {
				
//				if(ConnectStatus.isConnected()){
//					ConnectStatus.setConnectStatus(1);
//					connectBtn.setEnabled(true);
//				}else{
//					ConnectStatus.setConnectStatus(2);
//					connectBtn.setEnabled(false);
//				}				
				String ip = ipText.getText().toString();
				String port = portText.getText().toString();
				String alias = aliasText.getText().toString();
				if(ip.equals("")){
					displayToast("ip不能为空");
				}else if(port.equals("")){
					displayToast("port不能为空");
				}else if(alias.equals("")){
					displayToast("别名不能为空");
				}else{
					Config.setConfig(me, ip, port, alias);
					displayToast("保存成功");
					Log.insert(me,SystemTime.getSystemTime(),"服务器配置成功");
				}
				
			}
			return false;
		}
	}
	
	
	
	/*
	 * 注册连接按钮事件 
	 * */
	class ConncetEvent implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == event.ACTION_DOWN) {
				bindService(new Intent(me,MainService.class),mainSrvConn,BIND_AUTO_CREATE);
				startActivity(new Intent(getApplicationContext(),LogActivity.class));
			}
			return false;
		}
	}
	
    /*
     * ServiceConnection 方式处理 service；
     * */
    ServiceConnection mainSrvConn = new ServiceConnection(){   	
    	public void onServiceConnected(ComponentName name, IBinder service) {
			MainServiceBinder binder = (MainServiceBinder) service;
			binder.doLog("Demo_ServiceActivity_CONTENT");
		}

    	public void onServiceDisconnected(ComponentName name) {
    		
		}		
	};
	
	/*
	 * 初始化，写得比较龊，需要优化
	 * */
	private void init(){
		imageViewConfig = (ImageView)findViewById(R.id.menu_config_img);
		imageViewConfig.setImageResource(R.drawable.menu_config_pressed);
		addMenuEvent();
		saveBtn = (Button)findViewById(R.id.set_save);
		connectBtn = (Button)findViewById(R.id.set_connect);
		ipText = (EditText)findViewById(R.id.set_ip);
		portText = (EditText)findViewById(R.id.set_port);
		aliasText = (EditText)findViewById(R.id.set_alias);
		
		ArrayList<String> config = Config.getConfig(me);
		ipText.setText(config.get(0));
		portText.setText(config.get(1));
		aliasText.setText(config.get(2));
	}
	
    //显示Toast函数
    private void displayToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }    
}