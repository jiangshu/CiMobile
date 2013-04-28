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
 * ����socket����
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
					messageHandler.addMessage("���ӷ������ɹ�");
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
							messageHandler.addMessage("��������"+objectTest.getA());
						}
//						clientSocket.sendUrgentData(0xFF); //�ж������Ƿ�Ͽ�	
//						ObjectTest objectTest = (ObjectTest)oin.readObject();					
//			            msg.obj = "��������"+objectTest.getA();
//			            mHandler.sendMessage(msg);
						
//						String str = in.readLine();
//						ObjectTest objectTest = (ObjectTest)oin.readObject();	
//						if(null != objectTest){
//							addMessage("��������"+str);
//							ObjectTest objectTest = (ObjectTest)oin.readObject();	
//							messageHandler.addMessage("��������"+objectTest.getA());
//						}else{
//							reConnet(this);
//							break;
//						}

				    }
				 }catch(UnknownHostException e){
			          messageHandler.addMessage("Զ������������");
				 }catch(ConnectException e){
					  messageHandler.addMessage("�˿ڲ�����");
				 }catch(SocketTimeoutException e){
					 messageHandler.addMessage("���ӳ�ʱ");
					 reConnet(this);
				 }catch(Exception e) {
					 e.printStackTrace();
					 messageHandler.addMessage("���ӶϿ�");
					 reConnet(this);
//				     msg.obj = "�߳̽���";
//				     mHandler.sendMessage(msg);  ���������⣬���̻߳�û���������������̱߳���
				 }
			}		
	}
	 
	 /*
	  * �쳣����
	  * */
  
    private void reConnet(Thread thread){
       messageHandler.addMessage("��������...");
  	   try{
  		   thread.sleep(5000);
  		   if(null != clientSocket){
  			  clientSocket.close();
  		   }
  		   connect = new DoConnet();
  		   connect.start();
  		   
  	   }catch(Exception e){
  	       messageHandler.addMessage("��������ʧ��");
//  	       reConnect(thread);
  	   }
	}
    
    /*
     * ���������߳�
     * */
    public void start(){
		 connect = new DoConnet();
		 connect.start();
    }
	
    /*
     * �ṩ����ӿ�
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




