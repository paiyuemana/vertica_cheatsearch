package com.paiyue.cheatrulebeans;

/**
 * mobile web banner clickrate 作弊规则
 * @author paiyue
 *
 */
public class MobWebBannerClickrateCheatRule implements CheatRules{
	
	private String cheatFileName="mobwebbanner_clickrate";
	public String getCheatFileName(){
		return this.cheatFileName;
	}
	public void setCheatFileName(String name){
		this.cheatFileName=name;
	}
	/**
	 * 传入参数，根据mobile web banner clickrate的作弊规则判断权重
	 */
	public String getWeightByRule(float rate,int num){
		int weight=0;
		String reason="";
		if(rate<0.00005){
			reason="点击率过低";
			if((rate>=0.00002)||(rate==0&&num<100000)){
				weight=4;
			}else{
				if((rate<0.00002&&rate>0) || (rate==0&&num>=100000)){
					weight=20;
				}
			}
		}
		if(rate>0.015){//权重分级需要再分析
			weight=4;
			reason="点击率过高";
		}
		return weight+"\t"+reason;
	}
}
