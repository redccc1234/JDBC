package edu.kh.jdbc.homework.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	// getconnection
	// commit / rollback
	// Conntection
	// Statement
	// ResultSet
	
	private static Connection conn = null;
	
	
	public static Connection getConnection() {
		
		try {
			
			// 이전에 Connection이 만들어짐
			// conn에 값이 들어가 있고 close()가 되지 않았다면 
			if(conn != null && conn.isClosed()) return conn;
			
			// properties 객체 생성
			Properties prop = new Properties();
			
			// xml에 들어있는 driver 읽어오기
			prop.loadFromXML(new FileInputStream("driver.xml"));
			
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"),
					                           prop.getProperty("userName"),
					                           prop.getProperty("password"));
			
			conn.setAutoCommit(false); // Autocommit 끄기
			
		} catch (Exception e) {

			System.out.println("커넥션 생성 중 예외발생");
			e.printStackTrace();
		}
		return conn;
		
	}
	
	public static void commit (Connection conn) {
		
		try {
			
			if(conn != null &&  !conn.isClosed()) conn.commit();
			
		} catch (Exception e) {
			System.out.println("커밋 중 예외 발생");
			e.printStackTrace();
		}
		
	}
	
	public static void rollback (Connection conn) {
		
			try {
				
				if(conn != null && !conn.isClosed()) conn.rollback();
				
			} catch (Exception e) {
				System.out.println("롤백 중 예외발생");
				e.printStackTrace();
			}
	}
	
	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) conn.close();
		} catch (Exception e) {
			System.out.println("Connection close() 중 예외 발생");
			e.printStackTrace();
		}
		
	}
	
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) stmt.close();
		} catch (Exception e) {
			System.out.println("Statement close() 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		
		try {
			if(rs != null && !rs.isClosed()) rs.close();
		} catch (Exception e) {
			System.out.println("ResultSet close() 중 예외 발생");
			e.printStackTrace();
		}
	}
}
