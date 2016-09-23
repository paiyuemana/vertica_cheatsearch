package com.paiyue.whitelistbeans;

import java.util.List;

/**
 * app白名单筛选方法
 * @author paiyue
 *
 */
public class AppWhiteList implements WhiteListSelected{
	
	private String whiteListName="app_whitelist";//白名单文件名
	public void setWhiteListName(String name){
		this.whiteListName=name;
	}
	public String getWhiteListName(){
		return this.whiteListName;
	}
	public boolean existInList(String appid,List<String> whiteList){
		if(whiteList.contains(appid)){
			return true;
		}
		return false;
	}
	
}
