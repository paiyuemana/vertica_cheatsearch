package com.paiyue.vertica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnVertica {
	private static Connection conn = null;

	public static Connection getConnection() {
		try {
			
			String driver = "com.vertica.jdbc.Driver";
			
			String url = "jdbc:vertica://10.1.0.18:5433/warehouse";
			
			
			// MySQL配置时的用户名
			String user = "report";
			// Java连接MySQL配置时的密码
			String password = "paireport";
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		} finally {
			
		}
		return conn;
	}
	
	public static void closeAll(Connection conn,Statement ps,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public static void main(String [] args){
//		
//		Connection con = getConnection();
//		System.out.println(con);
//	}
}