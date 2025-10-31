package edu.kh.jdbc.model.dao;

// import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와
//                 클래스명.메서드명() 이 아닌 메서드명()만 작성해도 호출 가능하게 함
import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.model.dto.User;

// (Model 중 하나) DAO (Date Access Object : 데이터 접근 객체)
// 데이터가 저장된 곳(DB)에 접근하는 용도의 객체
// -> DB에 접근하여 Java에서 원하는 결과를 얻기 위해
// SQL을 수행하고 결과를 반환하는 역할
public class UserDAO {

	
	// 필드
	// - DB 접근 관련한 JDBC 객체 참조 변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	

	/** 1. User 등록 DAO
	 * @param conn : DB 연결 정보가 담겨있는 Connection 객체
	 * @param user : 입력받은 id,pw,name 이 세팅된 User 객체
	 * @return INSERT 된 결과 행의 갯수 
	 */
	public int insertUser(Connection conn, User user) throws Exception{

		// 1. 결과 저장용 변수 선언
		int result = 0; // 초기값은 0 으로 세팅
		
		try {
			   
			// 2. SQL 작성
			  String sql = """
			    		INSERT INTO TB_USER
			    		VALUES(SEQ_USER_NO.NEXTVAL,?,?,?,DEFAULT)
			    		""";
			    
			// 3. PreparedStatement 객체 생성 
			  pstmt = conn.prepareStatement(sql);
			    
			// 4. ? 위치홀더에 알맞은 값 대임
			  pstmt.setString(1, user.getUserId());
			  pstmt.setString(2, user.getUserPw());
			  pstmt.setString(3, user.getUserName());
			    
			 // 5. SQL(INSERT) 수행(executeUpate()) 후 결과(삽입된 행의 갯수) 반환 받기
			  result = pstmt.executeUpdate();
			
		} finally {
			
			// 6. 사용한 JDBC 객체 반환하기
			 close(pstmt);
		}
		
		// 결과 저장용 변수에 저장된 최종값을 반환
		return result;
	}


	/** 2. User 전체 조회 DAO
	 * @param conn
	 * @return userList
	 */
	public List<User> selectAll(Connection conn) throws Exception{

		// 1. 결과 저장용 변수 선언
		List<User> userList = new ArrayList<User>(); // 아직 들어온 값은 없음
		
		try {
			
			// 2. SQL 작성
			 String sql = """
			 		SELECT USER_NO,USER_ID,USER_PW,USER_NAME,
			 		TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"') ENROLL_DATE
			 		FROM TB_USER
			 		ORDER BY USER_NO
			 		""";
			
			 // 3. PreparedStatement 생성
			 pstmt = conn.prepareStatement(sql);
			 
			 // 4. ? 위치홀더에 알맞은 값 대입(없으면 패스)
			 
			 // 5. SQL(SELECT) 수행(excuteQuery()) 후 결과 반환(ResultSet) 받기
			 rs = pstmt.executeQuery();
			 
			 // 6. 조회 결과(rs)를 1행씩 접근하여 컬럼값 얻어오기 
			 // 몇 행이 조회될지 모른다 -> while
			 // 무조건 1행만 조회     -> if
			 while(rs.next()) {
				 
				 int userNo = rs.getInt("USER_NO");
				 String userId = rs.getString("USER_ID");
				 String userPw = rs.getString("USER_PW");
				 String userName = rs.getString("USER_NAME");
				 String enrollDate = rs.getString("ENROLL_DATE");
				 // -> java.sql.Date 타입으로 값을 저장하지 않은 이유
				 // -> SELECT 문에서 TO_CHAR()를 이용하여 문자열 형태로 변환해 조회해왔기 때문
				 
				 // User 객체 새로 생성하여 DB에서 얻어온 컬럼값 필드로 세팅
				 User user = new User(userNo,userId,userPw,userName,enrollDate);
				 
				 // user에 값이 채워진 후에 userList에 user을 추가한다
				 userList.add(user);
	
			 }
			 
		} finally {
			// 7. 사용한 자원 반환
			close(rs);
			close(pstmt);
		}
	
		// 조회 결과가 담긴 List 반환
		return userList;
	}


	/** 3. User 중 이름에 검색어가 포함된 회원 조회용 DAO
	 * @param conn
	 * @param input
	 * @return searchList : 조회된 회원 리스트
	 */
	public List<User> selectName(Connection conn ,String input) throws Exception{
		
		List<User> searchList = new ArrayList<User>();
		
		try {
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, 
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO
					""";
					
			
			pstmt = conn.prepareStatement(sql);
			
			// ? 에 알맞은 값 대입
			pstmt.setString(1,input);
			
			// DB 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				 int userNo = rs.getInt("USER_NO");
				 String userId = rs.getString("USER_ID");
				 String userPw = rs.getString("USER_PW");
				 String userName = rs.getString("USER_NAME");
				 String enrollDate = rs.getString("ENROLL_DATE");
				 
				 User user = new User(userNo,userId,userPw,userName,enrollDate);
				 
				searchList.add(user);
				
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return searchList;
	}


	/** 4. USER_NO를 입력받아 일치하는 USER 조회 DAO
	 * @param conn
	 * @param input
	 * @return 조회결과가 있다면 user, 조회결과가 없다면 null로 반환
	 * @throws Exception
	 */
	public User selectUser(Connection conn, int input) throws Exception{
		
		// 결과 반환용 
		User user = null;
	
		try {
			
			// SQL 작성
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, 
                    TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			// pstmt는 service를 통해 만들어짐
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 1행 또는 0행만 나오기 때문에 if 사용
				
				 int userNo = rs.getInt("USER_NO");
				 String userId = rs.getString("USER_ID");
				 String userPw = rs.getString("USER_PW");
				 String userName = rs.getString("USER_NAME");
				 String enrollDate = rs.getString("ENROLL_DATE");
				 
				 user = new User(userNo,userId,userPw,userName,enrollDate);
	
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}

		return user;
	}


	/** 5. USER_NO를 입력받아 일치하는 User 삭제 DAO
	 * @param conn
	 * @param input
	 * @return result
	 * @throws Exception
	 */
	public int deleteUser(Connection conn, int input) throws Exception{
		
		// 결과 저장용 
		int result = 0;
		
		try {
			
			String sql = """
					DELETE FROM TB_USER 
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,input);
			
			// 결과값 반환
			result = pstmt.executeUpdate(); // DML -> 무조건 executeUpdate() 사용
				
		} finally {
			
			close(pstmt);
		}

		return result;
	}
 
	
	/** 6-1. ID,PW가 일치하는 회원이 있는지 조회(SELECT) DAO
	 * @param conn
	 * @param id
	 * @param pw
	 * @return
	 */
	public int selectUser(Connection conn, String id, String pw) throws Exception{
		
		int userNo = 0;
		
		try {
			
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID = ?
					AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			pstmt.setString(2,pw);
			
			rs = pstmt.executeQuery();
			
			// 조회된 행이 1개가 있을 경우
			if(rs.next()) {
				 userNo = rs.getInt("USER_NO");
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
		}

		return userNo; // 조회성공 USER_NO , 실패 0 반환
	}
	
	
	/** 6-2. USER_NO가 일치하는 회원의 이름 수정 DAO
	 * @param conn
	 * @param name
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public int updateName(Connection conn, String name, int userNo) throws Exception{

		int result = 0;
		
		try {
			
			String sql = """
					UPDATE TB_USER
					SET USER_NAME = ?
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setInt(2,userNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		return result;
	}

	
	/** 7. 아이디 중복 검사 DAO
	 * @param conn
	 * @param id
	 * @return
	 */
	public int idCheck(Connection conn, String id) throws Exception{
		
		int count = 0;
		
		try {
			
			String sql = """
					SELECT COUNT(*)
					FROM TB_USER
					WHERE USER_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				count = rs.getInt(1); // 조회된 컬럼 순서 번호를 이용해 컬럼값 얻어오기
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
		}
		
		return count;
	}




}
