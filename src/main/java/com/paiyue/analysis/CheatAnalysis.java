package com.paiyue.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.paiyue.vertica.ConnVertica;
import com.paiyue.whitelistbeans.WhiteListSelected;
import com.paiyue.cheatrulebeans.CheatRules;

public class CheatAnalysis {
	
	//连接hdfs
	private static Configuration configuration = new Configuration();
	static {
		System.setProperty("HADOOP_USER_NAME", "na.ma");
		configuration.set("dfs.block.size", String.valueOf(256 * 1024 * 1024));
		configuration.set("dfs.replication", String.valueOf(2));
		configuration.set("dfs.replication.min", String.valueOf(2));
		configuration.set("fs.defaultFS", "hdfs://bigdatacluster");
		configuration.set("dfs.nameservices","bigdatacluster");
		configuration.set("dfs.ha.namenodes.bigdatacluster", "nn2,nn1");
		configuration.set("dfs.namenode.rpc-address.bigdatacluster.nn1","10.1.0.120:8020");
		configuration.set("dfs.namenode.rpc-address.bigdatacluster.nn2", "10.1.0.121:8020");
		configuration.set("dfs.client.failover.proxy.provider.bigdatacluster","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		configuration.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		configuration.set("fs.file.impl",org.apache.hadoop.fs.LocalFileSystem.class.getName());
	}
	//用户根路径
	private String hdfsPath="/user/na.ma/cheat_weight_data/";
	private WhiteListSelected whiteList;
	private CheatRules cheatRules;
//	private String whiteListName;
//	private String cheatFileName;
	private String sql;
	/**
	 * 
	 * @param whiteList 白名单类
	 * @param cheatRules 作弊规则类，如webbanner
	 * @param sql
	 */
	public void setParam(WhiteListSelected whiteList,CheatRules cheatRules,String sql){
		this.whiteList=whiteList;
		this.cheatRules=cheatRules;
//		this.whiteListName=whiteListName;
//		this.cheatFileName=cheatFileName;
		this.sql=sql;
	}
	/**
	 * 查询sql，并筛选白名单，根据作弊规则，得到对应权重，写入文件
	 */
	public void getCheatListWithWeight(){
		
		List<String> webList=new ArrayList<String>();
		//查询白名单
		List<String> whiteList=this.selectWhiteList();
		//连接vertica
		Connection cv=ConnVertica.getConnection();
		Statement ps=null;
		ResultSet rs=null;
		try {
			ps = cv.createStatement();
			rs=ps.executeQuery(this.sql);
			while(rs.next()){
				String media=rs.getString(1);
				//判断是否在白名单中，若在，则直接跳过该domain
				if(this.whiteList.existInList(media,whiteList)==true){
					continue;
				}
				int num=rs.getInt(2);
				float rate=rs.getFloat(3);
				//根据参数和规则，判断对应权重
				String weightVsReason=cheatRules.getWeightByRule(rate,num);
				webList.add(media+"\t"+num+"\t"+rate+"\t"+weightVsReason);
			}
			this.write2File(webList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnVertica.closeAll(cv, ps, rs);
		}
	}
	//从hdfs查询对应白名单
	private List<String> selectWhiteList(){
		FSDataInputStream hdfsInStream=null;
		BufferedReader br=null;
		FileSystem hdfs=null;
		List<String> list=new ArrayList<String>();
		try {
			hdfs = FileSystem.get(configuration);
			hdfsInStream = hdfs.open(new Path(hdfsPath+this.whiteList.getWhiteListName()));
			br=new BufferedReader(new InputStreamReader(hdfsInStream));
			String line="";
			while((line=br.readLine())!=null){
				list.add(line);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(hdfsInStream!=null){
				try {
					hdfsInStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(hdfs!=null){
				try {
					hdfs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	//向hdfs写入文件
	private void write2File(List<String> list){
		FileSystem hdfs=null;
		FSDataOutputStream os=null;
        try {
        	hdfs = FileSystem.get(configuration);
			os = hdfs.create(new Path(hdfsPath+this.cheatRules.getCheatFileName()+"_"+new SimpleDateFormat("yyyyMMdd").format(new Date())));
	        for(String strlist : list){
	        	os.write((strlist+"\n").getBytes()); 
	        }
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(hdfs!=null){
				try {
					hdfs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
