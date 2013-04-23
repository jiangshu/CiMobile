package com.baidu.cimobi.javabean;

import java.util.HashMap;

/*
 * mobile基本信息bean
 * */

public class MobileInfo implements java.io.Serializable{
    private String Uid;            //手机的唯一标识  
	private String mobileType;     //手机的品牌及型号
	private String androidVersion; //系统的版本
	private String alias;          //手机别名
	private HashMap<String,String> packageList; //系统中安装的程序列表 
	private HashMap<String,String> browserList; //系统中中浏览器列表
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getMobileType() {
		return mobileType;
	}
	
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	
	public String getAndroidVersion() {
		return androidVersion;
	}
	
	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public HashMap<String, String> getBrowserList() {
		return browserList;
	}

	public void setBrowserList(HashMap<String, String> browserList) {
		this.browserList = browserList;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	
	public HashMap<String,String> getPackageList() {
		return packageList;
	}
	
	public void setPackageList(HashMap<String,String> packageList) {
		this.packageList = packageList;
	}
}
