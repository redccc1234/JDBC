package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {
		
		
		// 아이디, 비밀번호, 이름을 입력받아
		// TB_USER 테이블에 삽입 (INSERT) 하기
		
		
		/*
		 *  java.sql.PreparedStatement
		 *  - SQL 중간에 ? (위치홀더,placeholder)를 작성하여
		 *  ? 자리에 java 값을 대입할 준비가 되어있는 Statement
		 * 
		 * 장점 1: SQL 작성이 간편
		 * 장점 2: ? 에 값 대입 시 자료형에 맞는 형태의 리터럴으로 대입됨!!
		 *       EX) String 대입 -> '값' (자동으로 ''추가)
		 *       EX) int    대입 -> 값
		 * 장점 3: 성능, 속도에서 우위를 가지고 있음
		 * 
		 * 위치홀더(?) -> 무조건 pstmt
		 * 없을 때 -> pstmt,stmt
		 * 
		 * ***PrepareStatement는 Statement 자식***
		 * */
	
		
		//1. JDBC 객체 참조 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// SELECT 가 아니기 때문에 ResultSet 필요없음
		
		Scanner sc =null;
		
		try {
			
			// 2. DriverManger 를 이용해 Connection 객체 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
			String userName = "kh_cya";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			// 3. SQL작성
			sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.next();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.next();
			
			System.out.print("이름 입력 : ");
			String name = sc.next();
			
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			
			// 4. PreparedStatement 객체 생성
			// -> 객체 생성과 동시에 SQL 담겨짐
			// -> 미리 ? (위치홀더)에 값을 받을 준비를 해야되기 때문에..
			
			pstmt = conn.prepareStatement(sql);
			
			// 5. ? 위치홀더에 알맞은 값 대입
			// pstmt.set 자료형(?순서,대입할 값);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
		    // -> 여기까지 수행되면 온전한 SQL이 완성된 상태!!
			
			// 6. SQL(INSERT) 수행 후 결과(int)반환 받기
			// + DML 수행 정 해야할 것!!
			// Connection의 AutoCommit 끄기
			// -> 왜 끄는거냐 개발자가 트랜잭션을 마음대로 제어하기 위해서
			conn.setAutoCommit(false);
			
			// executeUpdate() : DML 수행,결과 행 갯수(int)반환
			// -> 보통 DML 실패 0 , 성공 시 0 초과된 값이 반환
			
			// pstmt에서 executeUpdate() , executeQuery()
			// 매개변수 자리에 sql 들어오면 안됨
			int result =  pstmt.executeUpdate();
			
			// 7. result 값에 따른 결과 처리 + 트랜잭션 제어처리
		    if(result > 0) { // INSERT 성공 시
			conn.commit(); // COMMIT 수행 -> DB에 INSERT 데이터 영구 반영
		    System.out.println(name + "님이 추가되었습니다.");
							
			} else { // 실패
			 conn.rollback(); // 실패 시 ROLLBACK
			System.out.println("추가 실패");
							
		    }

		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			
			// 8. 사용한 JDBC 객체 자원 반환
			try {
				
				if(pstmt != null)pstmt.close();
				if(conn!= null)conn.close();
				if(sc!= null)sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
