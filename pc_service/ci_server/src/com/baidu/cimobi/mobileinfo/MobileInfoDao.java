package com.baidu.cimobi.mobileinfo;

import java.util.ArrayList;
import java.util.Iterator;

import com.baidu.cimobi.javabean.MobileInfo;
import com.baidu.cimobi.mobileinfo.MobileInstanceModel;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class MobileInfoDao {
	private MobileInfo mobileInfo;
	private MobileInstanceModel mobileInstanceModel;
	private ObjectOutputStream oOut;
	private String ip;
	public MobileInfoDao(MobileInfo mobileInfo,ObjectOutputStream oOut,String ip){
	   this.mobileInfo = mobileInfo;
	   mobileInstanceModel = new MobileInstanceModel();
	   this.oOut = oOut;
	   this.ip = ip;
	   setMobileFullInfo();
	}
	
	public MobileInstanceModel getMobileInstance(){	                                  
	    return mobileInstanceModel;
    }
	
	public String getInfoReg(){
		String reg = "";
		String key;
		reg = reg+mobileInfo.getMobileType()+",";
		reg = reg+mobileInfo.getAndroidVersion()+",";
		Iterator<String> paIter = mobileInfo.getBrowserList().keySet().iterator();
		while (paIter.hasNext()){
			key = paIter.next();
			reg = reg + key + "&";
		}
		return reg;
	}
	
	private void setMobileFullInfo(){
		mobileInstanceModel.setId(mobileInfo.getUid());
		mobileInstanceModel.setAndroidVersion(mobileInfo.getAndroidVersion());
		mobileInstanceModel.setMobileType(mobileInfo.getMobileType());
		mobileInstanceModel.setBrowserList(mobileInfo.getBrowserList());
		mobileInstanceModel.setAlias(mobileInfo.getAlias());
		mobileInstanceModel.setInfoReg(getInfoReg());
//		mobileInstanceModel.setPackageList(mobileInfo.getPackageList());
		mobileInstanceModel.setoOut(oOut);
		mobileInstanceModel.setIp(ip);
		ArrayList<String>browsers = new ArrayList<String>();
//		ArrayList<String>packages = new ArrayList<String>();
		String key;
		Iterator<String> paIter;
//		paIter = mobileInfo.getPackageList().keySet().iterator();
//		while (paIter.hasNext()){
//			key = paIter.next();
//			packages.add(key);
//		}
		
		paIter = mobileInfo.getBrowserList().keySet().iterator();
		while (paIter.hasNext()){
			key = paIter.next();
			browsers.add(key);
		}
		mobileInstanceModel.setBrowsers(browsers);
//		mobileInstanceModel.setPackages(packages);
	}
}
