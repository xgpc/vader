package com.vader.attackfly;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class db {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";


	public String name;

	private static Connection mConnect;
	
	public static void connectdb() {
		try {
			System.out.println("connectdb---");
			Class.forName(JDBC_DRIVER);

			mConnect = DriverManager.getConnection(
					"jdbc:mysql://39.106.149.207:3306/AircraftWars?autoReconnect=true&rewriteBatchedStatements=true",
					"vader", "123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setinfo(int score){
		connectdb();
		PreparedStatement stmt = null;
        String sql = "INSERT INTO `AircraftWars`.`database` (`score`, `time`, `name`) VALUES (?, ?, ?)";
        try {
        	System.out.println("db 初始化");
    		InetAddress addr = InetAddress.getLocalHost();
    		String name = addr.getHostName().toString(); // 获取本机计算机名称
    		
            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, score); 
            stmt.setString(2, new Timestamp(new Date().getTime()).toString());
            stmt.setString(3, name);
            int result =stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println("插入成功"+ result);
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        close();

	}
	
	public static List<String> getinfo() {
		connectdb();
		String sql;
		List<String> qs = new ArrayList<String>();
		sql = "SELECT * FROM `database` order by score desc limit 5;";
		ResultSet rs = null;
		int num = 0;
		
		try {
			rs = getConnection().createStatement().executeQuery(sql);
			
		     while(rs.next()){
		            // 通过字段检索
		            int id  = rs.getInt("id");
		            String name = rs.getString("name");
		            String score = rs.getString("score");
		            String time = rs.getString("time");
		            num++;
		            
		            qs.add(num + "." + "<" + name + ">" + score + "分  --" + time );
		            // 输出数据
//		            System.out.print("ID: " + id);
//		            System.out.print(", 名称: " + name);
//		            System.out.print(", 分数: " + score);
//		            System.out.print(", 时间: " + time);
//		            System.out.print("\n");
		        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

   
		close();
		return qs;

	}

	public static Connection getConnection() {
		return mConnect;

	}

	public static void close() {
		try {
			mConnect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		db d = new db();
		System.out.println(d.getinfo());
		d.setinfo(10);
	}
}
