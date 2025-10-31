package edu.kh.jdbc.homework.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.homework.common.JDBCTemplate;
import edu.kh.jdbc.homework.model.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {

	private StudentDAO dao = new StudentDAO();

	public int studentInsert(Student student) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.studentInsert(conn,student);
		
		if(result > 0 ) JDBCTemplate.commit(conn);
		else            JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public List<Student> studentSelect() throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> studentList = dao.studentSelect(conn);
		
		JDBCTemplate.close(conn);
		
		return studentList;
	}

	public int studentUpdateName(String name) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result1 = dao.studentUpdateName(conn,name);
		
		if(result1 > 0 ) JDBCTemplate.commit(conn);
		else             JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return 0;
	}

	public int studentUpdateAge(int age) throws Exception{
		
        Connection conn = JDBCTemplate.getConnection();
		
		int result2 = dao.studentUpdateAge(conn,age);
		
		if(result2 > 0 ) JDBCTemplate.commit(conn);
		else             JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
	
		return result2;
	}

	public int studentUpdateMajor(String major) throws Exception{
		
        Connection conn = JDBCTemplate.getConnection();
		
		int result3 = dao.studentUpdateMajor(conn,major);
		
		if(result3 > 0 ) JDBCTemplate.commit(conn);
		else             JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result3;
	}

	
	
	
	
	}


