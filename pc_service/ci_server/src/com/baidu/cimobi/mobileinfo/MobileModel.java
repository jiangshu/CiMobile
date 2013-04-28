package com.baidu.cimobi.mobileinfo;

import java.util.ArrayList;

/*
 * @auth:jiangshuguang
 * 已连接的mobile集合
 * */

public class MobileModel {
    private ArrayList<MobileInstanceModel> mobileList;
    public MobileModel(){
    	mobileList = new ArrayList<MobileInstanceModel>();
    }
    
    public void addInstance(MobileInstanceModel mobileInstanceModel){    	
    	mobileList.add(mobileInstanceModel);
    }
    
    public void removeInstance(String id){
    	for(int i=0; i<mobileList.size(); i++){
    		if(id == mobileList.get(i).getId()){
    			mobileList.remove(id);
    		}
    	}
    }
    
    public MobileInstanceModel getMobileInstanceById(String id){
    	for(int i=0; i<mobileList.size(); i++){
    		if(id.equals(mobileList.get(i).getId())){
    			return mobileList.get(i);
    		}
    	}
    	return null;
    }
        
    public ArrayList<MobileInstanceModel> getMobileList(){
    	return mobileList;
    } 
    
}
