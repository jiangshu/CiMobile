package com.baidu.cimobi.mobileinfo;

import com.baidu.cimobi.javabean.MobileInfo;
import com.baidu.cimobi.javabean.MobileFullInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @auth:jiangshuguang 
 * 对mobile端的信息进行包装处理
 * @return mobile的详细信息，包括浏览器列表 
 * */
public class MobileInfoDao1 {
	private MobileInfo mobileInstance;
	private MobileFullInfo mobileFullInstance;
	public MobileInfoDao1(MobileInfo mobileInstance){
	   this.mobileInstance = mobileInstance;
	   mobileFullInstance = new MobileFullInfo();
	   setMobileFullInfo();
	}
	
	public MobileFullInfo getMobileFullInfo(){	                                  
	    return mobileFullInstance;
    }
	
	private void setMobileFullInfo(){
		mobileFullInstance.setUid(mobileInstance.getUid());
		mobileFullInstance.setAndroidVersion(mobileInstance.getAndroidVersion());
		mobileFullInstance.setMobileType(mobileInstance.getMobileType());
		mobileFullInstance.setBrowserList(mobileInstance.getBrowserList());
		mobileFullInstance.setPackageList(mobileInstance.getPackageList());
		ArrayList<String>browsers = new ArrayList<String>();
		ArrayList<String>packages = new ArrayList<String>();
		String key;
		Iterator<String> paIter;
		paIter = mobileInstance.getPackageList().keySet().iterator();
		while (paIter.hasNext()){
			key = paIter.next();
			packages.add(key);
		}
		
		paIter = mobileInstance.getBrowserList().keySet().iterator();
		while (paIter.hasNext()){
			key = paIter.next();
			browsers.add(key);
		}
		mobileFullInstance.setBrowsers(browsers);
		mobileFullInstance.setPackages(packages);
	}
	
	
//	private MobileInfo mobileInstance;
//	private MobileFullInfo mobileFullInstance;
//	private ArrayList<String>browsers;
//	private ArrayList<String>packageLables;
//	public MobileInfoDao(MobileInfo mobileInstance){
//		this.mobileInstance = mobileInstance;
//		this.mobileFullInstance = new MobileFullInfo();
//		browsers = new ArrayList<String>();
//		packageLables = new ArrayList<String>();
//		classifyBrowser();
//		setMobileFullInfo();
//		saveMobileInfo();
//	}
//	
//	public void setMobileFullInfo(){
//		mobileFullInstance.setMobileType(mobileInstance.getMobileType());
//		mobileFullInstance.setAndroidVersion(mobileInstance.getAndroidVersion());
//		mobileFullInstance.setBrowsers(browsers);
//		mobileFullInstance.setPackageList(packageLables);	
//	}
//	
//	
//	public MobileFullInfo getMobileFullInfo(){	                                  
//		return mobileFullInstance;
//	}
//	
//	/*
//	 * @ native：com.android.browser;  
//	 * @ opera:  com.opera.mini.android;
//	 * @ QQ:     com.tencent.mtt;
//	 * @ UC:     com.UCMobile;
//	 * @ chrome: com.android.chrome;
//	 * */
//	private void classifyBrowser(){
//      Iterator<String> paIter = mobileInstance.getPackageList().keySet().iterator();
//      String packageLable;
//      String packageName;
//      while (paIter.hasNext()) {
//			packageLable = paIter.next();
//			packageName = mobileInstance.getPackageList().get(packageLable);
//			if(isFind(".\\.*opera\\..*",packageName)       ||
//					isFind(".*\\.tencent\\..*",packageName)||
//					isFind(".*UC.*",packageName)           ||
//					isFind(".*chrome.*",packageName)){
//				browsers.add(packageLable);
//			}
//			packageLables.add(packageLable);
//      }
//      browsers.add("native browser");
//      packageLables.add("native browser");
//	}
//	
//	private boolean isFind(String reg,String target){
//		Pattern p = Pattern.compile(reg);
//		Matcher m =  p.matcher(target);
//		if(m.find()){
//			return true;
//		}
//		return false;
//	}
//	
//	private void saveMobileInfo(){
//		
//	}

}
