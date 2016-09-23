package com.paiyue.cheatrulebeans;

public class MobWebBannerCpcCheatRule implements CheatRules{
	
	private String cheatFileName="mobwebbanner_cpc";
	public String getCheatFileName(){
		return this.cheatFileName;
	}
	public void setCheatFileName(String name){
		this.cheatFileName=name;
	}
	/**
	 * 传入参数，根据mobile web banner cpc的作弊规则判断权重
	 */
	public String getWeightByRule(float rate,int num){
		int weight=0;
		String reason="CPC过低";
		if(rate<0.05){//权重分级待分析
			weight=4;
		}
		return weight+"\t"+reason;
	}
}
