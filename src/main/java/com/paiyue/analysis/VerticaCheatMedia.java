package com.paiyue.analysis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.paiyue.cheatrulebeans.AppBannerClickrateCheatRule;
import com.paiyue.cheatrulebeans.AppBannerCpcCheatRule;
import com.paiyue.cheatrulebeans.MobWebBannerClickrateCheatRule;
import com.paiyue.cheatrulebeans.MobWebBannerCpcCheatRule;
import com.paiyue.cheatrulebeans.WebBannerClickrateCheatRule;
import com.paiyue.cheatrulebeans.WebBannerCpcCheatRule;
import com.paiyue.whitelistbeans.AppWhiteList;
import com.paiyue.whitelistbeans.WebWhiteList;



/**
 * 查询vertica数据库，分析得到作弊媒体
 *
 */
public class VerticaCheatMedia 
{
    public static void main( String[] args )
    {
    	//vertica 分析-sql查询vertica，分析点击率，cpc
		CheatAnalysis ca=new CheatAnalysis();
		//获取前7天的日期
    	Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, - 7);  
        String date=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        String date2=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	
        String webbanner_clickrate_sql="select domain,sum(imp),(sum(click)/sum(imp)) click_rate from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and device_type = 'General' and date >= '"+date+"' and date <'"+date2+"' group by domain having sum(imp)>20000 and (sum(click)/sum(imp)<0.00005 or sum(click)/sum(imp)>0.015);";
        //webbanner clickrate
        ca.setParam(new WebWhiteList(), new WebBannerClickrateCheatRule(), webbanner_clickrate_sql);
		//分析权重，并放入hdfs文件中
        ca.getCheatListWithWeight();
		
		String webbanner_cpc_sql="select domain,sum(click),(sum(in_cost)/sum(click)) cpc from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and device_type = 'General' and date >= '"+date+"' and date <'"+date2+"' group by domain having (sum(imp)>20000 and sum(click)>0 and sum(in_cost)/sum(click)<0.05);";
		//webbanner cpc
		ca.setParam(new WebWhiteList(), new WebBannerCpcCheatRule(), webbanner_cpc_sql);
		ca.getCheatListWithWeight();
		
		String mobwebbanner_clickrate_sql="select domain,sum(imp),(sum(click)/sum(imp)) click_rate from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and (device_type = 'Phone' or device_type = 'Mobile' or device_type = 'Tablet') and date >= '"+date+"' and date <'"+date2+"' group by domain having (sum(imp)>20000 and (sum(click)/sum(imp)>0.01 or sum(click)/sum(imp)<0.00005));";
		//MobWebBanner Clickrate
		ca.setParam(new WebWhiteList(), new MobWebBannerClickrateCheatRule(), mobwebbanner_clickrate_sql);
		ca.getCheatListWithWeight();
		
		String mobwebbanner_cpc_sql="select domain,sum(click),(sum(in_cost)/sum(click)) cpc from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and (device_type = 'Phone' or device_type = 'Mobile' or device_type = 'Tablet') and date >= '"+date+"' and date <'"+date2+"' group by domain having (sum(imp)>20000 and sum(click)>0 and sum(in_cost)/sum(click)<0.05);";
		//MobWebBanner cpc
		ca.setParam(new WebWhiteList(), new MobWebBannerCpcCheatRule(), mobwebbanner_cpc_sql);
		ca.getCheatListWithWeight();
		
		String appbanner_clickrate_sql="select app_id,sum(imp),(sum(click)/sum(imp)) click_rate from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'App' and date >= '"+date+"' and date <'"+date2+"' group by app_id having (sum(imp)>20000 and (sum(click)/sum(imp)>0.05 or sum(click)/sum(imp)<0.00035));";
		//app banner Clickrate
		ca.setParam(new AppWhiteList(), new AppBannerClickrateCheatRule(), appbanner_clickrate_sql);
		ca.getCheatListWithWeight();
		
		String appbanner_cpc_sql="select app_id,sum(click),(sum(in_cost)/sum(click)) cpc from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'App' and date >= '"+date+"' and date <'"+date2+"' group by app_id having (sum(imp)>20000 and sum(click)>0 and sum(in_cost)/sum(click)<0.01);";
		//app banner cpc
		ca.setParam(new AppWhiteList(), new AppBannerCpcCheatRule(), appbanner_cpc_sql);
		ca.getCheatListWithWeight();
		
    	
//    	//vertica 分析-sql查询vertica，分析点击率，cpc
//		CheatAnalysis ca=new CheatAnalysis();
//		//获取前7天的日期
//    	Calendar c = Calendar.getInstance();  
//        c.add(Calendar.DATE, - 7);  
//        String date=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  	
//        String webbanner_clickrate_sql="select domain,sum(imp),(sum(click)/sum(imp)) click_rate from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and device_type = 'General' and date >= '"+date+"' group by domain having sum(imp)>20000 and (sum(click)/sum(imp)<0.00005 or sum(click)/sum(imp)>0.015);";
//    	//webbanner clickrate
//    	ca.setParam(new WebWhiteList(), new WebBannerClickrateCheatRule(), webbanner_clickrate_sql);
//		//分析权重，并放入hdfs文件中
//    	ca.getCheatListWithWeight();
//		
//		String webbanner_cpc_sql="select domain,sum(click),(sum(in_cost)/sum(click)) cpc from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and device_type = 'General' and date >= '"+date+"' group by domain having (sum(imp)>20000 and sum(click)>0 and sum(in_cost)/sum(click)<0.05);";
//		//webbanner cpc
//    	ca.setParam(new WebWhiteList(), new WebBannerCpcCheatRule(), webbanner_cpc_sql);
//		ca.getCheatListWithWeight();
		
//		String mobwebbanner_clickrate_sql="select domain,sum(imp),(sum(click)/sum(imp)) click_rate from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and (device_type = 'Phone' or device_type = 'Mobile' or device_type = 'Tablet') and date >= '"+date+"' group by domain having (sum(imp)>20000 and (sum(click)/sum(imp)>0.01 or sum(click)/sum(imp)<0.00005));";
//		//MobWebBanner Clickrate
//    	ca.setParam(new WebWhiteList(), new MobWebBannerClickrateCheatRule(), mobwebbanner_clickrate_sql);
//		ca.getCheatListWithWeight();
//		
//		String mobwebbanner_cpc_sql="select domain,sum(click),(sum(in_cost)/sum(click)) cpc from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'Site' and (device_type = 'Phone' or device_type = 'Mobile' or device_type = 'Tablet') and date >= '"+date+"' group by domain having (sum(imp)>20000 and sum(click)>0 and sum(in_cost)/sum(click)<0.05);";
//		//MobWebBanner cpc
//    	ca.setParam(new WebWhiteList(), new MobWebBannerCpcCheatRule(), mobwebbanner_cpc_sql);
//		ca.getCheatListWithWeight();
//		
//		String appbanner_clickrate_sql="select app_id,sum(imp),(sum(click)/sum(imp)) click_rate from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'App' and date >= '"+date+"' group by app_id having (sum(imp)>20000 and (sum(click)/sum(imp)>0.05 or sum(click)/sum(imp)<0.00035));";
//		//app banner Clickrate
//    	ca.setParam(new AppWhiteList(), new AppBannerClickrateCheatRule(), appbanner_clickrate_sql);
//		ca.getCheatListWithWeight();
//		
//		String appbanner_cpc_sql="select app_id,sum(click),(sum(in_cost)/sum(click)) cpc from warehouse.effect where platform != 'Stats' and ad_unit_type = 'Banner' and agent_type = 'App' and date >= '"+date+"' group by app_id having (sum(imp)>20000 and sum(click)>0 and sum(in_cost)/sum(click)<0.01);";
//		//app banner cpc
//    	ca.setParam(new AppWhiteList(), new AppBannerCpcCheatRule(), appbanner_cpc_sql);
//		ca.getCheatListWithWeight();
		
		//web video 和 app video由于量较少，仍未制定规则
		
		
    }
}
