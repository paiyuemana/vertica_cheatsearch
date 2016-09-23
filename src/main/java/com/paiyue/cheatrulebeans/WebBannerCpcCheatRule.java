package com.paiyue.cheatrulebeans;


public class WebBannerCpcCheatRule implements CheatRules {
	
	private String cheatFileName="webbanner_cpc";
	public String getCheatFileName(){
		return this.cheatFileName;
	}
	public void setCheatFileName(String name){
		this.cheatFileName=name;
	}
	/**
	 * 传入参数，根据web banner cpc的作弊规则判断权重
	 */
	public String getWeightByRule(float rate,int num){
		int weight=0;
		String reason="CPC过低";
		if(rate>=0.02){
			weight=4;
		}else{
			if(rate<0.02){
				weight=20;
			}
		}
		return weight+"\t"+reason;
	}
}
