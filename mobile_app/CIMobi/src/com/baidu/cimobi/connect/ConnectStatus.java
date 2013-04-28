package com.baidu.cimobi.connect;

/*
 * 全局连接状态
 * */
public class ConnectStatus {
	/*
	 * @connectStatus
	 *  0:"未知"
	 *  1:"未连接"
	 *  2:"已连接"
	 * */
    public static int connectStatus = 1;

	public static String getConnectStatus() {
		if(connectStatus == 0){
			return "超过重连次数";
		}else if(connectStatus == 1){
			return "未连接";
		}else if(connectStatus == 2){
			return "已连接";
		}else{
			return "连接错误";
		}
	}
	
	public static boolean isConnected(){
		if(connectStatus == 2){
			return true;
		}else{
			return false;
		}
	}

	public static void setConnectStatus(int connectStatus) {
		ConnectStatus.connectStatus = connectStatus;
	}
    
}
