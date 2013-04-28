package com.baidu.cimobi.javabean;

import java.util.HashMap;
import java.util.ArrayList;
/*
 * 命令bean
 * */
public class CommandDelivery implements java.io.Serializable{
	
    private String action;  //命令的动作
    private String id;      //
    private String androidVersion; //
    private String mobileType;     //
    private boolean isAll;          // 
    private ArrayList<String> browsers; //
    private String url;

    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	private HashMap<String,String> attach;   //
    
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAndroidVersion() {
		return androidVersion;
	}
	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	
	public boolean getIsAll() {
		return isAll;
	}
	public void setIsAll(boolean isAll) {
		this.isAll = isAll;
	}
	
	public ArrayList<String> getBrowsers() {
		return browsers;
	}
	public void setBrowsers(ArrayList<String> browsers) {
		this.browsers = browsers;
	}
	
	public HashMap<String, String> getAttach() {
		return attach;
	}
	public void setAttach(HashMap<String, String> attach) {
		this.attach = attach;
	}
}
