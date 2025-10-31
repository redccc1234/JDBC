package edu.kh.jdbc.homework.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.homework.common.JDBCTemplate;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public int studentInsert(Connection conn, Student student) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					INSERT INTO KH_STUDENT
                    VALUES(SQE_STUDENT.NEXTVAL,?,?,?,DEFAULT)
					
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, student.getName());
			pstmt.setInt(2,student.getAge());
			pstmt.setString(3, student.getMajor());
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public List<Student> studentSelect(Connection conn)  throws Exception{

		List<Student> studentList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO,STD_NAME,STD_AGE,MAJOR,
                    TO_CHAR(ENT_DATE,'YYYY"년" MM"월" DD"일"') ENROLL_DATE
                    FROM KH_STUDENT
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String major = rs.getString("MAJOR");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				Student student = new Student(stdNo,stdName,stdAge,major,enrollDate);
				
				studentList.add(student);
			}
		
			
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}

		return studentList;
	}

	public int studentUpdateName(Connection conn, String name) throws Exception{
		
		int result1 =0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,name);
			
			result1 = pstmt.executeUpdate();
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result1;
	}


	public int studentUpdateAge(Connection conn, int age) throws Exception{

	int result2 =0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET STD_AGE= ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,age);
			
			result2 = pstmt.executeUpdate();
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result2;
	
	}

	public int studentUpdateMajor(Connection conn, String major) throws Exception{

	int result3 =0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET MAJOR = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,major);
			
			result3 = pstmt.executeUpdate();
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result3;
		
	}
	
	
}
