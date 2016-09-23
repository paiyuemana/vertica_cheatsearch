package com.paiyue.cheatrulebeans;

public class AppBannerClickrateCheatRule implements CheatRules {
	private String cheatFileName="appbanner_clickrate";
	public String getCheatFileName(){
		return this.cheatFileName;
	}
	public void setCheatFileName(String name){
		this.cheatFileName=name;
	}
	/**
	 * 传入参数，根据app banner clickrate的作弊规则判断权重
	 */
	public String getWeightByRule(float rate,int num){
		int weight=0;
		String reason="";
		if(rate<0.00035){
			reason="点击率过低";
			if((rate>=0.00005)||(rate==0&&num<100000)){
				weight=4;
			}else{
				if((rate<0.00005&&rate>0) || (rate==0&&num>=100000)){
					weight=20;
				}
			}
		}
		if(rate>0.05){
			reason="点击率过高";
			if(rate>=0.1){
				weight=20;
			}else{
				weight=4;
			}
		}
		return weight+"\t"+reason;
	}
}
