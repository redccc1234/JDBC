package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.UserDAO;
import edu.kh.jdbc.model.dto.User;

// (Model 중 하나) Service : 비즈니스 로직을 처리하는 계층
// 데이터를 가공하고 트랜잭션(Commit , Rollback) 수행
public class UserSerivice {

	
	// 필드
	private UserDAO dao = new UserDAO();

	/** 1. User 등록 서비스
	 * @param user : 입력받은 id,pw,name 이 세팅된 객체
	 * @return INSERT 된 결과 행의 갯수 
	 */
	public int insertUser(User user) throws Exception{
		
		// 1. Connection 생성
		Connection conn = JDBCTemplate.getConnection();
				
		// 2. 데이터 가공 ( 할 것이 없으면 생략)
		
		// 3. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.insertUser(conn,user);
		
		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어 처리
		if(result > 0) { // INSERT 성공
			JDBCTemplate.commit(conn);
		}else {// INSERT 실패
			JDBCTemplate.rollback(conn);
		}
		
		// 5. Connection 반환하기
			JDBCTemplate.close(conn);
			
		// 6. 결과 반환
		return result;
	}

	/** 2. User 전체 조회 서비스
	 * @return 조회된 User 객체들이 담긴 list
	 */
	public List<User> selectAll() throws Exception {
		
		// 1. Connection 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. DAO 메서드 호출(SELECT) 후 결과(List<User>)반환 받기
		List<User> userList = dao.selectAll(conn);
		
		// 3. Connection 반환
		JDBCTemplate.close(conn);
		
		// 4. 결과 반환
		return userList;
	}

	/** 3. User 중 이름에 검색어가 포함된 회원 조회 서비스
	 * @param input : 입력받은 키워드
	 * @return searchList : 조회된 회원 리스트
	 */
	public List<User> selectName(String input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<User> searchList = dao.selectName(conn,input);
		
		JDBCTemplate.close(conn);
		
		return searchList;
	}

	/** 4. USER_NO를 입력받아 일치하는 User 조회
	 * @param input
	 * @return user
	 * @throws Exception
	 */
	public User selectUser(int input) throws Exception{
		
		// Connection 객체 만들기 
		Connection conn = JDBCTemplate.getConnection();
		
		// DAO 호출하기 
		User user = dao.selectUser(conn,input);
		// user 에는 값이 있을 수도 있고 null 일 수도 있다.
		
		JDBCTemplate.close(conn);
		
		return user;
	}

	/** 5. USER_NO를 입력받아 일치하는 User 삭제
	 * @param input
	 * @return result
	 * @throws Exception
	 */
	public int deleteUser(int input) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.deleteUser(conn,input);	
		
		if(result > 0) JDBCTemplate.commit(conn); // delete 성공
		else           JDBCTemplate.rollback(conn); // delete 실패
		
		// 사용한 Connection 닫기
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	/** 6-1. ID,PW가 일치하는 회원이 있는지 조회(SELECT)
	 * @param id
	 * @param pw
	 * @return
	 */
	public int selectUserNo(String id, String pw) throws Exception{
	
		Connection conn = JDBCTemplate.getConnection();
		
		int userNo = dao.selectUser(conn,id,pw);
		
		JDBCTemplate.close(conn);
		
		return userNo;
	}

	/** 6-2. USER_NO가 일치하는 회원의 이름 수정 (UPDATE)
	 * @param name
	 * @param userNo
	 * @return
	 */
	public int updateName(String name, int userNo) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateName(conn,name,userNo);
		
		if(result > 0 ) JDBCTemplate.commit(conn);
		else            JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** 7. 아이디 중복 확인 서비스
	 * @param id
	 * @return
	 */
	public int idCheck(String id) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = dao.idCheck(conn,id);
		
		JDBCTemplate.close(conn); 
		
		return 0;
	}

	/** 8. userList 에 있는 모든 User객체를 INSERT하는 서비스
	 * @param userList
	 * @return
	 */
	public int multInsertUser(List<User> userList) throws Exception {

		// 다중 INSERT 사용법
		// 1) SQL 을 이용한 다중 INSERT
	    // 2) Java 에서 반복문을 이용한 다중 INSERT 
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = 0; // 삽입 성공한 행의 갯수 count
		 
		for( User user : userList) {
			int result = dao.insertUser(conn, user);
			count+=result; // 성공 1 누적 --> 성공 1 누적 --> 성공 1 누적 == 3(성공)
						   // 성공 1 누적 --> 실패 --> 성공 1 누적 == 2(실패)
			               // 삽입 성공한 행의 갯수를 count 누적
		}
		
		// count--;
		
		// 전체 삽입 성공 시 commit / 실패 시 rollback(일부 삽입,전체 실패)
		if(count == userList.size()) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return count;
	}


}
