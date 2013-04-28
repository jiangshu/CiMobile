package com.baidu.cimobi;

import com.baidu.cimobi.connect.CommandThread;
import com.baidu.cimobi.Command.CommandModel;
import java.util.HashMap;
import java.util.ArrayList;

/*
 * auth:jiangshuguang
 * 命令输入端入口
 * */

public class MainClient {
	/*
	 * @ip=172.22.184.90                         服务器ip
	 * port=3204                                              服务器的端口
	 * action=openBrowser        命令的类型
	 * browser=uc&native&chrome  命令作用的浏览器集合
	 * androidVersion=4.2.2                系统版本
	 * mobileType=htc            mobile类型及型号
	 * id=djig_jiegj             mobile的ID
	 * isAll=true                是否对所有满足条件的mobile执行命令
	 * url                       浏览对应打开的url  
	 * */

	public static void main(String args[]) {
		
	    String ip = "10.48.32.115";
	    String port = "3204";
	    String action = null;
	    String id  = null;
	    String androidVersion = null;
	    String mobileType = null;
	    Boolean isAll = false;
	    String url = null;
	    ArrayList<String> browsers = new ArrayList<String>();
	    HashMap<String,String> attach = new HashMap<String,String>();
		
		String argumentItem = "";
		String argumentArr[];
		
		for(int i=0;i<args.length;i++){
			argumentItem = args[i];
			argumentArr = argumentItem.split("=");
			if(argumentArr[0].equals("ip")){
				ip = argumentArr[1];
			}else if(argumentArr[0].equals("port")){
				port = argumentArr[1];
			}else if(argumentArr[0].equals("action")){
				action = argumentArr[1];
			}else if(argumentArr[0].equals("browser")){
				argumentItem = argumentArr[1];
				argumentArr = argumentItem.split("&");
				for(int j=0; j<argumentArr.length; j++){
					browsers.add(argumentArr[j]);
				}
			}else if(argumentArr[0].equals("androidVersion")){		
				androidVersion = argumentArr[1];
			}else if(argumentArr[0].equals("mobileType")){
				mobileType = argumentArr[1];
			}else if(argumentArr[0].equals("id")){
				id = argumentArr[1];
			}else if(argumentArr[0].equals("url")){
				url = argumentArr[1];
			}else if(argumentArr[0].equals("isAll")){
				if(argumentArr[1].equals("true")){
					isAll = true;
				}else{
					isAll  = false;
				}
			}else{
				attach.put(argumentArr[0], argumentArr[1]);
			}			
		}

		CommandModel commandModel = new CommandModel(action,id,androidVersion,
				mobileType,isAll,browsers,url,attach);
		CommandThread commandThread = new CommandThread(ip,port,commandModel.getCommand());
		commandThread.start();  

    }
}
