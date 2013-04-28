package com.baidu.cimobi.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;

import com.baidu.cimobi.mobileinfo.MobileModel;
import com.baidu.cimobi.javabean.InfoType;
import com.baidu.cimobi.javabean.CommandExecute;
import com.baidu.cimobi.data.Config;
import com.baidu.cimobi.data.Log;
import com.baidu.cimobi.util.SystemTime;
import com.baidu.cimobi.data.SharedData;

/*
 * @auth:jiangshuguang
 * 1.建立socket连接；
 * 2.log信息；
 * 3.异常重连
 * 
 * @service :用于广播
 * @mobileInstance :mobile基本信息
 * */
public class ConnectHandler{
	private Socket clientSocket = null;
	private PrintWriter out;
	private ObjectOutputStream oOut;
	private ObjectInputStream oIn;
	private BufferedReader in;
	private OutputStream bOut;
	private ConnectThread connectThread ;
	private Service service;
	private Intent intent;
	private MobileModel mobileInstance;
	private InfoType infoType;
	private HashMap<String,String>browserMap;
	private String ip;
	private int port;
	private String alias;
	private int secCount; //1s 重连次数
	private int minCount; //1分钟重连次数
	private int tenMinCount; //10分钟重连次数 
	private boolean isStop;
	
	public ConnectHandler(Service service,MobileModel mobileInstance){
		this.mobileInstance = mobileInstance;
		this.service = service;
		this.browserMap = mobileInstance.getBrowserActivityList();
		this.intent = new Intent("com.baidu.cimobi.service.broadcast");
		this.infoType = new InfoType();
		this.isStop = false;
		ArrayList<String> config = Config.getConfig(service);
		ip = config.get(0);
		port = Integer.parseInt(config.get(1));
		alias = config.get(2);
		initCount();
	}
	    	
	class ConnectThread extends Thread{		 
			public void run(){
				try{
					clientSocket = new Socket();
					SocketAddress remoteAddr = new InetSocketAddress(ip,port);
					clientSocket.connect(remoteAddr,2000);
					printLog("服务器连接成功");
					ConnectStatus.setConnectStatus(2);          //设置连接状态为"已连接"
					initCount();
//					out = new PrintWriter(clientSocket.getOutputStream(),true);
					oOut = new ObjectOutputStream(clientSocket.getOutputStream());
//					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					oIn = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));					
					infoType.setType("mobileInfo");
					oOut.writeObject(infoType);
					oOut.writeObject(mobileInstance.getMobileInfo());
					
					while(!isStop){						
//						clientSocket.sendUrgentData(0xFF); //判断连接是否断开	
						CommandExecute commandExecute = (CommandExecute)oIn.readObject();
                        String action = commandExecute.getAction();
                        String url = commandExecute.getArgument().get("url");
                        String browser = commandExecute.getArgument().get("browsers");
//                        String browser[] = browsers.split("@");
                        openBrowser(browser,url);
                        printLog(action+" "+browser+" "+url); 
				    }
				 }catch(UnknownHostException e){
					 printLog("远程主机不存在");
					 ConnectStatus.setConnectStatus(3);
				 }catch(ConnectException e){
					 printLog("端口不存在");
					 ConnectStatus.setConnectStatus(3);
				 }catch(SocketTimeoutException e){
					 printLog("连接超时");
					 reConnect();
					 ConnectStatus.setConnectStatus(3);
				 }catch(Exception e) {
					 printLog("连接断开");
					 ConnectStatus.setConnectStatus(3);
					 reConnect();
				 }
			}
			
			/*
			 * 终止线程
			 * */
			public void end(){  
				isStop = true;
				this.interrupt();
				printLog("线程连接终止");
			}
			
			/*
			 * 异常重连
			 * */
			private void reConnect(){
				printLog("正在重连...");
				try{
					int time = getConnectRate();
					if(time>0){
						sleep(time);
						if(null != clientSocket){
							clientSocket.close();
						}
						run();
					}else{
						ConnectStatus.setConnectStatus(3);
					}
				}catch(Exception e){
					printLog("重新连接失败 ");
				}
			}
			
	}
	
	

	 
//	 /*
//	  * 异常重连
//	  * */
//    private void reConnet(Thread thread){
//       printLog("正在重连...");
//  	   try{
//  		   int time = getConnectRate();
//  		   if(time>0){
//  			   thread.sleep(time);
//  	  		   if(null != clientSocket){
//  	  			  clientSocket.close();
//  	  		   }
//  	  		   connectThread = new ConnectThread();
//  	  		   connectThread.start(); 
//  		   }else{  			   
//  			 ConnectStatus.setConnectStatus(3);
//  		   }
//  	   }catch(Exception e){
//  		  printLog("重新连接失败");
//  	   }
//	}
    
    /*
     * 启动连接线程
     * */
    public void start(){
    	connectThread = new ConnectThread();
    	connectThread.start();
    }
	
    
	/*
	 * 终止线程
	 * 对外接口 
	 * */
	public void stop(){
		connectThread.end();
	}
	
	/*
	 * 保证只有一个连接线程 
	 * */
	public Thread getConnectThread(){
		return connectThread;
	}
    
    /*
     * 启动浏览器
     * */
    private void openBrowser(String browser,String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri content_url = Uri.parse(url);   
        intent.setData(content_url);
        intent.setClassName(browser,browserMap.get(browser));
        service.startActivity(intent);
    }
      
    
    /*
     * 提供输入接口
     * */
	public PrintWriter getSocketOut(){
		return this.out;
	}
	
	public BufferedReader getSocketIn(){
		return this.in;
	}
	
	public Socket getSocket(){
		return this.clientSocket;
	}
	
	public ConnectThread getConnnet(){
		return this.connectThread;
	}
	
	/*
	 * log信息处理
	 * 1.广播
	 * 2.永久保存
	 * */
	private void printLog(String log){
		String time = SystemTime.getSystemTime();
		intent.putExtra("log", time+"@*@"+log+"\n");
		service.sendBroadcast(intent);
		Log.insert(service, time, log);
	}
	
	
	private void initCount(){
		secCount = 0;
		minCount = 0;
		tenMinCount = 0;
	}
	
	/*
	 * 控制重连的频率 
	 * 1. 先以5s的频率重连5次
	 * 2. 再以1分钟频率重连5次
	 * 3. 然后以10分钟的频率重连
	 * 4. 如果两天内连接都失败，放弃重连，必须以手动的方式重连
	 * */
	private int getConnectRate(){
		if(secCount<20){
			secCount++;
			return 5*1000;
		}else if(minCount<5){
			minCount++;
			return 60*1000;
		}else if(tenMinCount<288){
			tenMinCount++;
			return 10*60*1000;
		}else{
			return 0;
		}
	}	 
}




