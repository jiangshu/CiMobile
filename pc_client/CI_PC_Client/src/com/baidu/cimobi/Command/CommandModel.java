package com.baidu.cimobi.Command;

import java.util.ArrayList;
import java.util.HashMap;


import com.baidu.cimobi.javabean.CommandDelivery;
public class CommandModel {
   private CommandDelivery commandDelivery;
   
   public CommandModel(String action,String id,String androidVersion,
			String mobileType,boolean isAll,ArrayList<String> browsers,String url,HashMap<String,String>attach){
      this.commandDelivery = new CommandDelivery();
	  commandDelivery = new CommandDelivery();
	  commandDelivery.setAndroidVersion(androidVersion);
	  commandDelivery.setAttach(attach);
	  commandDelivery.setBrowsers(browsers);
	  commandDelivery.setId(id);
	  commandDelivery.setIsAll(isAll);
      commandDelivery.setMobileType(mobileType);
	  commandDelivery.setAction(action);
	  commandDelivery.setUrl(url);
   } 
   
   public CommandDelivery getCommand(){
	   return this.commandDelivery;
   }
}
