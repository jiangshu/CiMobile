package com.baidu.cimobi.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.lang.Object;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.baidu.cimobi.mobileinfo.ObjectTest;
import com.baidu.cimobi.util.*;

/*
 * 建立socket连接
 * */
public class ConnectHandler1 {
	private Socket clientSocket = null;
	private MessageHandler messageHandler;
	private PrintWriter out;
	private BufferedReader in;
	private DoConnet connect ;
	private ObjectInputStream oin;
	
	public ConnectHandler1(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
	}
    	
	 class DoConnet extends Thread{		 
			public void run(){
				try{
					
					clientSocket = new Socket();
					SocketAddress remoteAddr = new InetSocketAddress("192.168.1.105",3204);
					clientSocket.connect(remoteAddr,2000);
					messageHandler.addMessage("连接服务器成功");
					out =new PrintWriter(clientSocket.getOutputStream());
//					oin = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
					oin = new ObjectInputStream(clientSocket.getInputStream());
					in = new BufferedReader
							(new InputStreamReader(clientSocket.getInputStream()));
//					InputStream inputStream =  clientSocket.getInputStream();
					
					Object obj = null; 
					while(true){
						if((obj=oin.readObject())!=null){
							ObjectTest objectTest = (ObjectTest)obj;
							messageHandler.addMessage("服务器："+objectTest.getA());
						}
//						clientSocket.sendUrgentData(0xFF); //判断连接是否断开	
//						ObjectTest objectTest = (ObjectTest)oin.readObject();					
//			            msg.obj = "服务器："+objectTest.getA();
//			            mHandler.sendMessage(msg);
						
//						String str = in.readLine();
//						ObjectTest objectTest = (ObjectTest)oin.readObject();	
//						if(null != objectTest){
//							addMessage("服务器："+str);
//							ObjectTest objectTest = (ObjectTest)oin.readObject();	
//							messageHandler.addMessage("服务器："+objectTest.getA());
//						}else{
//							reConnet(this);
//							break;
//						}

				    }
				 }catch(UnknownHostException e){
			          messageHandler.addMessage("远程主机不存在");
				 }catch(ConnectException e){
					  messageHandler.addMessage("端口不存在");
				 }catch(SocketTimeoutException e){
					 messageHandler.addMessage("连接超时");
					 reConnet(this);
				 }catch(Exception e) {
					 e.printStackTrace();
					 messageHandler.addMessage("连接断开");
					 reConnet(this);
//				     msg.obj = "线程结束";
//				     mHandler.sendMessage(msg);  这里有问题，本线程还没结束，又启动新线程报错
				 }
			}		
	}
	 
	 /*
	  * 异常重连
	  * */
  
    private void reConnet(Thread thread){
       messageHandler.addMessage("正在重连...");
  	   try{
  		   thread.sleep(5000);
  		   if(null != clientSocket){
  			  clientSocket.close();
  		   }
  		   connect = new DoConnet();
  		   connect.start();
  		   
  	   }catch(Exception e){
  	       messageHandler.addMessage("重新连接失败");
//  	       reConnect(thread);
  	   }
	}
    
    /*
     * 启动连接线程
     * */
    public void start(){
		 connect = new DoConnet();
		 connect.start();
    }
	
    /*
     * 提供输入接口
     * */
	public PrintWriter getSocketOut(){
		return this.out;
	}
	
	/*
	 * 
	 * */
	public BufferedReader getSocketIn(){
		return this.in;
	}
	
	public Socket getSocket(){
		return this.clientSocket;
	}
	
	public DoConnet getConnnet(){
		return this.connect;
	}
}




