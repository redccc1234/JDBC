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
	
	/** 1. 학생 등록 (INSERT) DAO
	 * @param conn
	 * @param student
	 * @return result
	 * @throws Exception
	 */
	public int studentInsert(Connection conn, Student student) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					INSERT INTO KH_STUDENT
                    VALUES(SEQ_STUDENT.NEXTVAL,?,?,?,DEFAULT)
					
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

	/** 2. 전체 학생 조회 (SELECT) DAO
	 * @param conn
	 * @return studentList
	 * @throws Exception
	 */
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

	/**  3. 학생 정보 수정 (UPDATE) 이름 수정 DAO
	 * @param conn
	 * @param name
	 * @param stdNo
	 * @return result1
	 * @throws Exception
	 */
	public int studentUpdateName(Connection conn, String name , int stdNo) throws Exception{
		
		int result1 =0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,name);
			pstmt.setInt(2, stdNo);
			
			result1 = pstmt.executeUpdate();
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result1;
	}


	/**  3. 학생 정보 수정 (UPDATE) 나이 수정 DAO
	 * @param conn
	 * @param age
	 * @param stdNo
	 * @return result2
	 * @throws Exception
	 */
	public int studentUpdateAge(Connection conn, int age , int stdNo) throws Exception{

	int result2 =0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET STD_AGE= ?
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,age);
			pstmt.setInt(2, stdNo);
			
			result2 = pstmt.executeUpdate();
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result2;
	
	}

	/**  3. 학생 정보 수정 (UPDATE) 전공 수정 DAO
	 * @param conn
	 * @param major
	 * @param stdNo
	 * @return result3
	 * @throws Exception
	 */
	public int studentUpdateMajor(Connection conn, String major , int stdNo) throws Exception{

	int result3 =0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET MAJOR = ?
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,major);
			pstmt.setInt(2,stdNo);
			
			result3 = pstmt.executeUpdate();
			
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result3;
		
	}

	/** 4. 학생 정보 삭제 (DELETE) DAO
	 * @param conn
	 * @param input
	 * @return result
	 * @throws Exception
	 */
	public int studentDelete(Connection conn, int input) throws Exception{
	
		int result = 0;
		
		try {
			
			String sql = """
				    DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	/** 5.전공별 학생 조회 DAO
	 * @param conn
	 * @return studentList
	 * @throws Exception
	 */
	public List<Student> majorSelect(Connection conn) throws Exception{
		
		List<Student> studentList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO,STD_NAME,STD_AGE,MAJOR,
                    TO_CHAR(ENT_DATE,'YYYY"년" MM"월" DD"일"') ENROLL_DATE
                    FROM KH_STUDENT
                    WHERE MAJOR
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
	
	
}
