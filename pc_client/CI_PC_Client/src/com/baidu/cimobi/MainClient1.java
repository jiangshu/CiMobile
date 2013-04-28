package com.baidu.cimobi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.regex.*;
import com.baidu.cimobi.javabean.CommandDelivery;
import com.baidu.cimobi.javabean.InfoType;

public class MainClient1 {
	
//	public static void main(String args[]) {
//		for(int i=0;i<args.length;i++){
//			System.out.println(args[i]);
//		}
//		DoClient1 doClient = new DoClient1();
//		doClient.start();	
//    }
}


/*
 * client 
 * */
class DoClient1{
	private JLabel serverInfoLable;
	private JTextArea serverInfo;
	private JLabel sendInfoLable;
	private JTextField sendInfo;
	private JButton sendBtn;
	private JButton conBtn;
	private JButton unconBtn;
	String connetInfo = "";
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	ObjectInputStream oIn;
	ObjectOutputStream oOut;
	ConnectHandler connectHandler;
	String status = "";
	public DoClient1(){
		
	}
		
	public void start(){
		MainFrame mainFrame = new MainFrame();
		mainFrame.makeFrame();
	}
	
	
	class ConnectHandler extends Thread{
		
		public void run(){
			try{
				socket = new Socket();
				SocketAddress remoteAddr = new InetSocketAddress("172.22.184.90",3204);
				socket.connect(remoteAddr,2000);
				System.out.println("连接服务器成功");
				serverInfo.append("连接服务器成功\n");
				oOut = new ObjectOutputStream(socket.getOutputStream());
				
				//指定client为发送命令
				InfoType infoType = new InfoType();
				infoType.setType("command");
				oOut.writeObject(infoType);
				
				//指定具体的command
				CommandDelivery command = new CommandDelivery();
				command.setAction("openBrowser");
				oOut.writeObject(command);
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				
				while(true){
					status = in.readLine();
					if(null != status){
						System.out.println(status);
						break;
					}
				}

			 }catch(UnknownHostException e){
				 System.out.println("远程主机不存在");				 
			 }catch(ConnectException e){
				 System.out.println("端口不存在");

			 }catch(SocketTimeoutException e){
				 System.out.println("连接超时");
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
	   System.out.println("连接已关闭");
	   serverInfo.append("客户端连接已中断\n");
	   serverInfo.append("正在重连....\n");
	   try{
		   thread.sleep(5000);
		   if(null != socket){
				socket.close();
		   }
		   connectHandler = new ConnectHandler();
		   connectHandler.start();
		   
	   }catch(Exception e){
		   System.out.println("重新连接失败");
	   }
   }
   
   /*
    * 客户端UI
    * */
   class MainFrame extends WindowAdapter implements ActionListener{
		
		public void makeFrame(){
			JFrame frame = new JFrame("客户端");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	frame.setLayout(null);
	    	frame.setSize(500,600);
	    	frame.setResizable(false);//设置窗口大小固定
	    	frame.addWindowListener(this);
	    	
	    	serverInfoLable = new JLabel("信息面板");
	    	frame.add(serverInfoLable);
	    	serverInfoLable.setBounds(50, 20, 400, 30);
	    	
	    	serverInfo = new JTextArea();
	    	serverInfo.setEditable(false);
	    	frame.add(serverInfo);
	    	serverInfo.setBounds(50, 50, 400, 370);
	    	
	    	conBtn = new JButton("连接");
	    	frame.add(conBtn);
	    	conBtn.setBounds(80, 450, 80, 30);
	    	conBtn.addActionListener(this);
	    	
	    	unconBtn = new JButton("断开连接");
	    	frame.add(unconBtn);
	    	unconBtn.setBounds(200, 450, 120, 30);
	    	unconBtn.addActionListener(this);
	    	
	    	
	    	sendInfoLable = new JLabel("命令:");
	    	frame.add(sendInfoLable);
	    	sendInfoLable.setBounds(20, 500, 60, 30);
	    	
	    	sendInfo = new JTextField();
	    	frame.add(sendInfo);
	    	sendInfo.setBounds(80, 500, 300, 30);
	    	
	    	sendBtn = new JButton("发送");
	    	frame.add(sendBtn);
	    	sendBtn.setBounds(400, 500, 80, 30);
	    	sendBtn.addActionListener(this);
	    		    	
	    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        Dimension frameSize = frame.getSize();
	        frame.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		    frame.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == sendBtn){				
//				String readline = sendInfo.getText();
//				sendInfo.setText("");
//				out.println(readline);
//				out.flush();
				
				CommandDelivery command = new CommandDelivery();
				command.setAction("openBrowser");
				try{
					oOut.writeObject(command);
				}catch(Exception ee){
					ee.printStackTrace();
				}
				
				
			}else if(e.getSource() == conBtn){
//				try{
//					socket=new Socket("127.0.0.1",3204);
//					clientHandler = new ClientHandler(socket); 
//					clientHandler.start();
//
//				 }catch(Exception ee) {
//				     ee.printStackTrace();
//				 }
				
				   connectHandler = new ConnectHandler();
				   connectHandler.start();
				
			}else if(e.getSource() == unconBtn){
				try{
//					out.println("off");
//					out.flush();
//					System.out.println("连接已关闭");
//					connectHandler.interrupt();
					socket.close();
					
//					clientHandler.interrupt();
					
				}catch(Exception ee){
					ee.printStackTrace();
				}

			}
		}
		
	    public void windowClosing(WindowEvent e){
	    	System.exit(0);
	    }
    }
   
}




///*
//* socket处理线程
//* 
//* */	
//class ClientHandler extends Thread{
// 
//	private Socket incoming;
//	private int clientId;
//	public ClientHandler(Socket incoming){
//		this.incoming = incoming;
//	}
//	
//	public void run(){
//	   try{			   		
//		out =new PrintWriter(socket.getOutputStream());
////		in =new BufferedReader(new InputStreamReader(socket.getInputStream()));	
//		oin = new ObjectInputStream(socket.getInputStream());
//      
//
//      
//		while(true){
//			ObjectTest objectTest = (ObjectTest)oin.readObject();
//			serverInfo.append("服务器："+objectTest.getA()+"\n");
////		    try{
////			   socket.sendUrgentData(0xFF); //判断连接是否中断
////			}catch(Exception ex){
////              this.incoming.close();
////				break;
//////			      reconnect();
////			}
////			String str = in.readLine();
//
//		}
//		
//		 
//		
//		}catch(Exception e){
////			try{
////				this.incoming.close();
////			}catch(Exception ee){
////				
////			}
//			System.out.println("连接已关闭");
//			serverInfo.append("客户端连接已中断\n");
//			reConnect();
////			System.out.println(e);
//		}
//	}
//}
//