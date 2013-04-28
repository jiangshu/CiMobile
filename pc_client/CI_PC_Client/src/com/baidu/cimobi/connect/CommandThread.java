package com.baidu.cimobi.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.cimobi.javabean.CommandDelivery;
import com.baidu.cimobi.javabean.InfoType;

/*
 * @auth:jiangshuguang
 * 传送命令的socket
 * */
public class CommandThread{

	String connetInfo = "";
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	ObjectInputStream oIn;
	ObjectOutputStream oOut;
	String ip;
	InfoType status;
	int port;
	int reConnetCount = 0;
	CommandDelivery commandDelivery;
	
	public CommandThread(String ip,String port,CommandDelivery commandDelivery){		
		this.ip = ip;
		this.port = Integer.parseInt(port) ;
        this.commandDelivery = commandDelivery;
	}
	
	private class ConnnetThread extends Thread{
		public void run(){
			try{
				socket = new Socket();
				SocketAddress remoteAddr = new InetSocketAddress(ip,port);
				socket.connect(remoteAddr,2000);
				oOut = new ObjectOutputStream(socket.getOutputStream());
				
				//指定client为发送命令
				InfoType infoType = new InfoType();
				infoType.setType("command");
				oOut.writeObject(infoType);
				
				//指定具体的command
				oOut.writeObject(commandDelivery);				
				oIn = new ObjectInputStream(socket.getInputStream());				
				while(true){
					status = (InfoType)oIn.readObject();
					if(null != status){
						System.out.println(status.getType());
						break;
					}
				}

			 }catch(UnknownHostException e){
				 System.out.println("argument error!");
//				 System.out.println("远程主机不存在");
			 }catch(ConnectException e){
//				 System.out.println("端口不存在");
			 }catch(SocketTimeoutException e){
//				 System.out.println("连接超时");
				 reConnect(this);
			 }catch(Exception e) {				 
				 reConnect(this);
			 }
		}
	}
	
	   /*
	    * 异常重连
	    * */	
	   public void reConnect(Thread thread){
		   if(reConnetCount>5){
			   return;
		   }
		   System.out.println("客户端连接已中断\n");
		   System.out.println("正在重连....\n");
		   try{
			   thread.sleep(2000);
			   if(null != socket){
					socket.close();
			   }
			   (new ConnnetThread()).start();
			   
		   }catch(Exception e){
			   System.out.println("重新连接失败");
		   }
		   reConnetCount++;
	   }
	   
	   /*
	    * 启动线程
	    * */
	  public void start(){
		  (new ConnnetThread()).start();
	  }
	  
	  /*
	   * 获取状态信息
	   * */
//	  public String getStatus(){
//		  return status;
//	  }
	   
}
