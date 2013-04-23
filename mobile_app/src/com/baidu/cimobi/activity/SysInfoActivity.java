package com.baidu.cimobi.activity;

import java.util.HashMap;

import com.baidu.cimobi.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.cimobi.mobileinfo.MobileModel;
import java.util.Iterator;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InetAddress;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import com.baidu.cimobi.data.SharedData;
import com.baidu.cimobi.connect.ConnectStatus;

public class SysInfoActivity extends CommonActivity {
	private ImageView imageViewInfo;
	private TextView info_type;
	private TextView info_version;
	private TextView info_ip;
	private TextView info_id;
	private TextView info_browser;
	private TextView info_status;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysinfo);
		imageViewInfo = (ImageView)findViewById(R.id.menu_info_img);
		imageViewInfo.setImageResource(R.drawable.menu_info_pressed);
		addMenuEvent();
		init();				
	}
	
	private void init(){
		info_type = (TextView)findViewById(R.id.info_type);
		info_version = (TextView)findViewById(R.id.info_version);
		info_ip = (TextView)findViewById(R.id.info_ip);
		info_id = (TextView)findViewById(R.id.info_id);
		info_browser = (TextView)findViewById(R.id.info_browser);
		info_status = (TextView)findViewById(R.id.info_status);
		MobileModel mobileModel = new MobileModel(this);
		HashMap<String, String> browserHashMap =  mobileModel.getMobileInfo().getBrowserList();		
		String browsers = "";		
		Iterator<String> paIter = browserHashMap.keySet().iterator();
		String browser = "";
		while (paIter.hasNext()){
			browser = paIter.next();
			browsers+= "◆ " + browser + " 浏览器\n";
		}
		info_type.setText(mobileModel.getMobileInfo().getMobileType());
		info_version.setText(mobileModel.getMobileInfo().getAndroidVersion());
		info_ip.setText("0.0.0.0");
		info_id.setText(mobileModel.getMobileInfo().getUid());
		info_browser.setText(browsers);
		info_status.setText(ConnectStatus.getConnectStatus());
//		info_status.setTextColor(1);
	}
	
	/*
	 * 3G环境获取IP
	 * */
	public String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); 
			en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                     return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (Exception ex) {
	       
	    }
	    return "网络未连接";
	}
	
	/*
	 * wifi环境获取ip
	 * */
    private String getIp(){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(this.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);  
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();     
        int ipAddress = wifiInfo.getIpAddress(); 
        String ip = intToIp(ipAddress); 
        return ip;
    }
    
    private String intToIp(int i) {             
        return (i & 0xFF ) + "." +     
      ((i >> 8 ) & 0xFF) + "." +     
      ((i >> 16 ) & 0xFF) + "." +     
      ( i >> 24 & 0xFF) ;
   } 
}
