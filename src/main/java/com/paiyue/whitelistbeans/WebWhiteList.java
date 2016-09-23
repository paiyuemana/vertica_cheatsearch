package com.paiyue.whitelistbeans;

import java.util.List;

/**
 * web白名单筛选方法
 * @author paiyue
 *
 */
public class WebWhiteList implements WhiteListSelected{
	
	private String whiteListName="web_whitelist";//白名单文件名
	public void setWhiteListName(String name){
		this.whiteListName=name;
	}
	public String getWhiteListName(){
		return this.whiteListName;
	}
	public boolean existInList(String domain,List<String> whiteList){
		if(domain.equals("other")||domain.equals("game.baguala.cn")){
			return true;
		}
		for(String whiteListDomain : whiteList){
			if(domain.contains(whiteListDomain)){
				return true;
			}
		}
		return false;
	}

}
