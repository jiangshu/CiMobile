package com.baidu.cimobi.mobileinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class MobileInstanceModel {
	private String ip;
	private String id;
	private String alias;
	private String infoReg;
	private String mobileType;                  //手机的品牌及型号
	private String androidVersion;              //系统的版本
	private ArrayList<String> packages;         //系统中安装的程序列表 ，仅为程序的名称
	private ArrayList<String> browsers;         //浏览器列表,仅为浏览器的名称
	private HashMap<String,String> packageList; //系统中安装的程序列表 
	private HashMap<String,String> browserList; //系统中中浏览器列表
	private ObjectOutputStream oOut;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getInfoReg() {
		return infoReg;
	}
	public void setInfoReg(String infoReg) {
		this.infoReg = infoReg;
	}
	public String getIp() {
		return ip;
	}
	public ObjectOutputStream getoOut() {
		return oOut;
	}
	public void setoOut(ObjectOutputStream oOut) {
		this.oOut = oOut;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	public ArrayList<String> getPackages() {
		return packages;
	}
	public void setPackages(ArrayList<String> packages) {
		this.packages = packages;
	}
	public ArrayList<String> getBrowsers() {
		return browsers;
	}
	public void setBrowsers(ArrayList<String> browsers) {
		this.browsers = browsers;
	}
	public HashMap<String, String> getPackageList() {
		return packageList;
	}
	public void setPackageList(HashMap<String, String> packageList) {
		this.packageList = packageList;
	}
	public HashMap<String, String> getBrowserList() {
		return browserList;
	}
	public void setBrowserList(HashMap<String, String> browserList) {
		this.browserList = browserList;
	}
}
