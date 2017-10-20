package com.charles.tmall.utils;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {

	static String ip ="127.0.0.1";
	static int port=3306;
	static String database="tmall";
	static String encoding="utf-8";
	static String loginname="root";
	static String password="root";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
		 String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
		 return DriverManager.getConnection(url, loginname, password);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getConnection());
	}
}
