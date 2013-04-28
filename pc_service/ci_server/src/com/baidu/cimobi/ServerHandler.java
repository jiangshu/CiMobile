package com.baidu.cimobi;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import com.baidu.cimobi.command.CommandHandler;
import com.baidu.cimobi.javabean.CommandDelivery;
import com.baidu.cimobi.javabean.InfoType;
import com.baidu.cimobi.javabean.MobileFullInfo;
import com.baidu.cimobi.javabean.MobileInfo;
import com.baidu.cimobi.mobileinfo.MobileInfoDao;
import com.baidu.cimobi.mobileinfo.MobileInfoXml;
import com.baidu.cimobi.mobileinfo.MobileModel;
import com.baidu.cimobi.util.Log;
import com.baidu.cimobi.util.SystemTime;
import com.baidu.cimobi.mobileinfo.MobileInfoXml;

public class ServerHandler{	
	private JLabel serverInfoLable;
	private JTextArea serverInfo;
	private JLabel sendInfoLable;
	private JTextField sendInfo;
	private JButton sendBtn;
	private JButton closeBtn;
	private int i;
	PrintWriter out_out;
	ObjectOutputStream oout_oout;
	private Socket curSocket;
	private ArrayList<MobileFullInfo> mobilelist;
	private HashMap<Integer,Socket> socketList;
	private MobileModel mobiles;
	private boolean isGraphical; //是否显示图形化界面
	
	public ServerHandler(boolean isGraphical){
		this.i = 0;
		this.isGraphical = isGraphical;
		if(isGraphical){
			MainFrame mainFrame = new MainFrame();
			mainFrame.makeFrame();
		}
		mobiles = new MobileModel();
	}	
	
	
	/*
	 * 服务守护进程
	 * 开启端口，等待客户端的连接
	 * */
	public void start(){
		try{
			ServerSocket server = new ServerSocket(3204);
			printLog("服务器已启动");
			for(;;){
				Socket incoming = server.accept();
				curSocket = incoming;
				new SocketHandler(incoming).start();	
				i++;
			}
	    }catch(SocketException e){
	    	printLog("客户端连接失败");
	    }catch(Exception e){
	    	printLog("客户端连接失败");
	    }
	}
	
	
	/*
	 *日志处理
	 *1. 写入log.txt文件中
	 *2. 在面板中显示 
	 * */
	private void printLog(String log){
		String logItem = SystemTime.get()+"   "+log+"\n";
		Log.insert(logItem);
		if(isGraphical){
			serverInfo.append(logItem);
		}
	}
		   
	/*
	 * socket监听线程
	 * */
	private class SocketHandler extends Thread{
		private Socket incoming;
		private int clientId;
		public SocketHandler(Socket incoming){
			this.incoming = incoming;
		}
		public void run(){			
			BufferedReader in;
			PrintWriter out;
			ObjectInputStream oIn;
			ObjectOutputStream oOut;
			Object obj = null;
			MobileInfo mobileInfo;
			InfoType infoType;
			CommandDelivery commandDelivery;
			String mobileIp = incoming.getInetAddress().toString();
			String ip;
			String id = ""; //为删除断开连接的mobile做准备

			try{
				oIn = new ObjectInputStream(new BufferedInputStream(incoming.getInputStream()));
			    oOut = new ObjectOutputStream(incoming.getOutputStream());  
				while(true){					
					if((obj = oIn.readObject())!=null){
						infoType = (InfoType)obj;
						String type = infoType.getType();
						type.replaceAll("\n", "");
						if(type.startsWith("mobileInfo")){
							oout_oout = oOut;
							if((obj = oIn.readObject())!=null){
								mobileInfo = (MobileInfo)obj;	
								ip = incoming.getInetAddress().toString();
								MobileInfoDao mobileInfoDao = 
									new MobileInfoDao(mobileInfo,oOut,ip);
								id = mobileInfoDao.getMobileInstance().getId();
								mobiles.addInstance(mobileInfoDao.getMobileInstance());
								MobileInfoXml.insertItem(mobileInfoDao.getMobileInstance());
								printLog(mobileInfoDao.getMobileInstance().getMobileType()+" 连接成功");
							}
						}else if(type.startsWith("command")){
							if((obj = oIn.readObject())!=null){
								commandDelivery = (CommandDelivery)obj;
								CommandHandler commandHandler = new CommandHandler(mobiles,commandDelivery);
								String status = commandHandler.doHandler();
								InfoType oStatus = new InfoType();
								oStatus.setType(status);
								oOut.writeObject(oStatus);
								printLog("执行命令"+commandDelivery.getAction()+"命令");
							}
						}
					}
				} 			
				
			}catch(Exception e){
				mobiles.removeInstance(id);    //  全局mobile信息列表删除此mobile的信息
				MobileInfoXml.deleteItem(id);  //  log中删除此mobile的信息
				try{
					if(null != this.incoming){
						printLog(this.incoming.getInetAddress()+" 连接断开");
						this.incoming.close();
					}
					
				}catch(Exception ee){
					printLog("连接断开异常");
				}					
			}
		}
	}


   /*
    * 主图形化界面
    * */
   private class MainFrame extends WindowAdapter implements ActionListener{	
		public void makeFrame(){
			JFrame frame = new JFrame("服务器端");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	frame.setLayout(null);
	    	frame.setSize(500,600);
	    	frame.setResizable(false);//设置窗口大小固定
	    	frame.addWindowListener(this);
	    	
	    	serverInfoLable = new JLabel("日志信息");
	    	frame.add(serverInfoLable);
	    	serverInfoLable.setBounds(50, 20, 400, 30);
	    	
	    	serverInfo = new JTextArea();
	    	serverInfo.setEditable(false);
	    	frame.add(serverInfo);
	    	serverInfo.setBounds(50, 50, 400, 470);
	    	
//	    	closeBtn = new JButton("关闭连接");
//	    	frame.add(closeBtn);
//	    	closeBtn.setBounds(200, 450, 120, 30);
//	    	closeBtn.addActionListener(this);
//	    	
//	    	
//	    	sendInfoLable = new JLabel("命令");
//	    	frame.add(sendInfoLable);
//	    	sendInfoLable.setBounds(20, 490, 400, 30);
//	    	
//	    	sendInfo = new JTextField("Start browser");
//	    	frame.add(sendInfo);
//	    	sendInfo.setBounds(20, 520, 360, 30);
//	    	
//	    	sendBtn = new JButton("执行");
//	    	frame.add(sendBtn);
//	    	sendBtn.setBounds(400, 520, 80, 30);
//	    	sendBtn.addActionListener(this);
	    		    	
	    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        Dimension frameSize = frame.getSize();
	        frame.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		    frame.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent e){
//
//			if(e.getSource() == closeBtn){
//				try{
//					curSocket.close();
//				}catch(Exception ee){
//					ee.printStackTrace();
//				}
//			}else if(e.getSource() == sendBtn){
//				CommandDelivery command = new CommandDelivery();
//				command.setAction("openBrowser");
//				try{
//					oout_oout.writeObject(command);					
////					String str = sendInfo.getText();
////					sendInfo.setText("");
////					out_out.println(str);
//					
//				}catch(Exception ee){
//					ee.printStackTrace();
//				}
//			}
		}
		
	    public void windowClosing(WindowEvent e){
	    	System.exit(0);
	    }
   }
   
   
}
