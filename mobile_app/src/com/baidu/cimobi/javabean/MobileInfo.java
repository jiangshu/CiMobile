package com.baidu.cimobi.javabean;

import java.util.HashMap;

/*
 * mobile������Ϣbean
 * */

public class MobileInfo implements java.io.Serializable{
    private String Uid;            //�ֻ���Ψһ��ʶ  
	private String mobileType;     //�ֻ���Ʒ�Ƽ��ͺ�
	private String androidVersion; //ϵͳ�İ汾
	private String alias;          //�ֻ�����
	private HashMap<String,String> packageList; //ϵͳ�а�װ�ĳ����б� 
	private HashMap<String,String> browserList; //ϵͳ����������б�
	
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
