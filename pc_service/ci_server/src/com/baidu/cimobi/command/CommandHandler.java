package com.baidu.cimobi.command;

import com.baidu.cimobi.mobileinfo.MobileModel;
import com.baidu.cimobi.javabean.CommandDelivery;
import com.baidu.cimobi.javabean.CommandExecute;
import com.baidu.cimobi.mobileinfo.MobileInstanceModel;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * 1.对命令进行处理
 * 2.找到合适的mobile
 * */
public class CommandHandler {
   private MobileModel mobiles;
   private CommandDelivery commandDelivery;
   private Socket socket;
   private CommandExecute commandExecute;
   public CommandHandler(MobileModel mobiles,CommandDelivery commandDelivery){
	   this.mobiles = mobiles;
	   this.commandDelivery = commandDelivery;
	   commandExecute = new CommandExecute();
   }
   
   private String listToString(ArrayList<String>list,String tag){
	   String result = "";
	   for(int i=0;i<list.size();i++){
		   if(i!=list.size()-1){
			   result+=list.get(i)+tag;
		   }else{
			   result+=list.get(i);
		   }
	   }
	   return result;
   }
   
   private String[] stringToArray(String str,String tag){
	   return str.split(tag);
   }
   
   public String doHandler(){
	   MobileInstanceModel mobileInstanceModel;
	   ArrayList<MobileInstanceModel> mobileList = mobiles.getMobileList();
	   HashMap<String,String>argument = new HashMap<String,String>();
	   ObjectOutputStream oOut = null;
	   mobileInstanceModel = mobiles.getMobileInstanceById(commandDelivery.getId()); 
	   if(mobileInstanceModel!=null){
		   oOut = mobileInstanceModel.getoOut();
		   commandExecute.setAction(commandDelivery.getAction());
		   String[] bro = stringToArray(commandDelivery.getBrowsers().get(0),"@");
		   argument.put("browsers", mobileInstanceModel.getBrowserList().get(bro[0]));
		   argument.put("url", commandDelivery.getUrl());
		   commandExecute.setArgument(argument);
		   oOut = mobileInstanceModel.getoOut();
		   try{					  
			   oOut.writeObject(commandExecute); //输出命令
			   return "execution success!";
		   }catch(Exception e){
			   e.printStackTrace();
			   return "execution failed!";
		   }
	   }else{
		   return "Mobile disconnect!";
	   }
	   
	   
//	   for(int i=0; i<mobileList.size(); i++){
//		   mobileInstanceModel = mobileList.get(i);
//		   if(commandDelivery.getBrowsers().size()>0){
//			   if(mobileInstanceModel.getBrowsers().contains(commandDelivery.getBrowsers().get(0))){ //如果某个浏览器中有满足条件的浏览器 
//				   commandExecute.setAction(commandDelivery.getAction());
//				   String browser = commandDelivery.getBrowsers().get(0);
//				   argument.put("browsers", mobileInstanceModel.getBrowserList().get(browser));
//				   argument.put("url", commandDelivery.getUrl());
//				   commandExecute.setArgument(argument);
//				   oOut = mobileInstanceModel.getoOut();
//				   try{					  
//					   oOut.writeObject(commandExecute);
//					   return "ok";
//				   }catch(Exception e){
//					   e.printStackTrace();
//					   return "网络错误";
//				   }
//				   
//			   }
//		   }
//	   }
//	   return "没有满足条件的mobile";
   }
   
   private boolean isSatisfy(String condition){
	   if(null == condition){
		   return true;
	   }else{
		   return false;
	   }
   }
   
   public Socket getSocket(){
       return socket;
   }
   
   public CommandExecute getCommandExecute(){
	   return commandExecute;
   }
   
}
