package com.baidu.cimobi.mobileinfo;

import com.baidu.cimobi.javabean.MobileFullInfo;
import java.net.Socket;

/*
 * 一个mobile连接实例数据结构
 * @id: 自动分配
 * @ip: mobile ip
 * @socket: mobile当前socket
 * @mobileFullInfo：moible系统信息
 * */

public class MobileInstanceModel1 {
    private String id;
    private String ip;
    private Socket socket;
    private MobileFullInfo mobileFullInfo;
    
    public MobileInstanceModel1(String id,String ip,Socket socket,MobileFullInfo mobileFullInfo){
    	this.id = id;
    	this.socket = socket;
    	this.ip = ip;
    	this.mobileFullInfo = mobileFullInfo;
    }

	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public MobileFullInfo getMobileFullInfo() {
		return mobileFullInfo;
	}

	public void setMobileFullInfo(MobileFullInfo mobileFullInfo) {
		this.mobileFullInfo = mobileFullInfo;
	}  
}
