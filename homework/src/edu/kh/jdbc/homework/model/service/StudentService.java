package edu.kh.jdbc.homework.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.homework.common.JDBCTemplate;
import edu.kh.jdbc.homework.model.dao.StudentDAO;
import edu.kh.jdbc.homework.model.dto.Student;

public class StudentService {

	private StudentDAO dao = new StudentDAO();

	/** 1. 학생 등록 (INSERT) service
	 * @param student
	 * @return result
	 * @throws Exception
	 */
	public int studentInsert(Student student) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.studentInsert(conn,student);
		
		if(result > 0 ) JDBCTemplate.commit(conn);
		else            JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/**2. 전체 학생 조회 (SELECT) service
	 * @return studentList
	 * @throws Exception
	 */
	public List<Student> studentSelect() throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> studentList = dao.studentSelect(conn);
		
		JDBCTemplate.close(conn);
		
		return studentList;
	}

	/**  3. 학생 정보 수정 (UPDATE) 이름 수정 service
	 * @param name
	 * @param stdNo
	 * @return result1
	 * @throws Exception
	 */
	public int studentUpdateName(String name , int stdNo) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result1 = dao.studentUpdateName(conn,name,stdNo);
		
		if(result1 > 0 ) JDBCTemplate.commit(conn);
		else             JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result1;
	}

	/**  3. 학생 정보 수정 (UPDATE) 나이 수정 service
	 * @param age
	 * @param stdNo
	 * @return result2
	 * @throws Exception
	 */
	public int studentUpdateAge(int age , int stdNo) throws Exception{
		
        Connection conn = JDBCTemplate.getConnection();
		
		int result2 = dao.studentUpdateAge(conn,age,stdNo);
		
		if(result2 > 0 ) JDBCTemplate.commit(conn);
		else             JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
	
		return result2;
	}

	/**  3. 학생 정보 수정 (UPDATE) 전공 수정 service
	 * @param major
	 * @param stdNo
	 * @return result3
	 * @throws Exception
	 */
	public int studentUpdateMajor(String major , int stdNo) throws Exception{
		
        Connection conn = JDBCTemplate.getConnection();
		
		int result3 = dao.studentUpdateMajor(conn,major,stdNo);
		
		if(result3 > 0 ) JDBCTemplate.commit(conn);
		else             JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result3;
	}

	/** 4. 학생 정보 삭제 (DELETE) service
	 * @param input
	 * @return result
	 * @throws Exception
	 */
	public int studentDelete(int input) throws Exception{
	
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.studentDelete(conn,input);
		
		if(result > 0) JDBCTemplate.commit(conn);
		else           JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** 5.전공별 학생 조회 service
	 * @return studentList
	 * @throws Exception
	 */
	public List<Student> majorSelect() throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> studentList = dao. majorSelect(conn);
		
		JDBCTemplate.close(conn);
		
		return studentList;
	}

	
	
	
	
	}


