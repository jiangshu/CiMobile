package com.baidu.cimobi.connect;

/*
 * ȫ������״̬
 * */
public class ConnectStatus {
	/*
	 * @connectStatus
	 *  0:"δ֪"
	 *  1:"δ����"
	 *  2:"������"
	 * */
    public static int connectStatus = 1;

	public static String getConnectStatus() {
		if(connectStatus == 0){
			return "������������";
		}else if(connectStatus == 1){
			return "δ����";
		}else if(connectStatus == 2){
			return "������";
		}else{
			return "���Ӵ���";
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
