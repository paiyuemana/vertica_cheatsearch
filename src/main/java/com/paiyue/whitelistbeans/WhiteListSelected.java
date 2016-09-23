package com.paiyue.whitelistbeans;

import java.util.List;


public interface WhiteListSelected {
	public boolean existInList(String media,List<String> whiteList);
//    String whiteListName="";
	public void setWhiteListName(String name);
	public String getWhiteListName();
}
