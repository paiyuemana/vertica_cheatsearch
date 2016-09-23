package com.paiyue.cheatrulebeans;

public class AppBannerCpcCheatRule implements CheatRules{
	
	private String cheatFileName="appbanner_cpc";
	public String getCheatFileName(){
		return this.cheatFileName;
	}
	public void setCheatFileName(String name){
		this.cheatFileName=name;
	}
	/**
	 * 传入参数，根据app banner cpc的作弊规则判断权重
	 */
	public String getWeightByRule(float rate,int num){
		int weight=0;
		String reason="CPC过低";
		if(rate<0.004){
			weight=20;
		}else{
			weight=4;
		}
		return weight+"\t"+reason;
	}
}
