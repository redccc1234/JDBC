package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;



public class JDBCEXample {

	public static void main(String[] args) {
		
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
						
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
						
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) :
		// 3000000
		// 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
						
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 217  | 전지연 | F    | 3660000 | 대리   | 인사관리부
		// -------------------------------------------------------
				
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부

		// JDBC 객체 만들기
		
		Connection conn = null; // 연결 정보 저장용
		PreparedStatement pstmt = null; // DB 수행 결과 반환용
		ResultSet rs = null; // 수행 결과 저장
		
		Scanner sc = null; // 입력용
		
		try {
			
			// DriverManger 를 이용해서 Connection 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String urs = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_cya";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(urs,userName,password);
			
			// SQL 작성 
			sc = new Scanner(System.in);
			
			System.out.print("조회할 성별(M/F) : ");
			String gender = sc.next().toUpperCase();
			
			System.out.print("급여 범위(최대 최소) : ");
			int min = sc.nextInt();
			int max = sc.nextInt();
			
			System.out.print("급여 정렬(1.ABS , 2.DESC) : ");
			int sort = sc.nextInt();
			
			String sql = """
				    SELECT EMP_ID, EMP_NAME, 
					DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') GENDER,
					SALARY, JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE
					FROM EMPLOYEE
					JOIN JOB USING(JOB_CODE)
					LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)
					WHERE DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = ?
					AND SALARY BETWEEN ? AND ?
					ORDER BY SALARY 
					""";  // ORDER BY ? (위치홀더) 사용 시 오류 : SQL 명령어가 올바르게 종료되지 않았습니다.
					      // 왜? PrepareStatement 의 위치 홀더(?)는 ** 데이터 값을 대체하는 용도로만 사용 가능
					      // -> SQL 에서 ORDER BY 절의 정렬 기준(ABS,DESC) 과 같은
			              // -> SQL 구분(문법)의 일부는 PreparedStatement의 위치 홀더(?)로 대체할 수 없음
					// 급여의 오름차순인지 내림차순인지 조건에 따라 SQL보완하기
			        if(sort == 1) sql += "ASC";
					else          sql += "DESC";
					
		    // Preparedstatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? 위치 홀더에 알맞은 값 세팅
			pstmt.setString(1,gender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			
			// SQL 수행 후 결과 반환
			rs = pstmt.executeQuery();
			
			// 커서를 이용해서 한 행씩 접근해 컬럼값 얻어오기
			boolean flag = true; // true : 조회결과 없음 false : 조회결과 존재
			
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("--------------------------------------------------------");
			
			while(rs.next()) {
				
				flag =false;
				// while 문이 1회 이상 반복 == 조회 결과가 1행이라도 있다
				
				String empId     = rs.getString("EMP_ID");
				String empName   = rs.getString("EMP_NAME");
				String gen       = rs.getString("GENDER");
				int salary       = rs.getInt("SALARY");
				String jobName   = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				


                    System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
					empId, empName, gen, salary, jobName, deptTitle);
			}
			
			if(flag) {
				System.out.println("조회 결과 없음");
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			// 사용한 JDBC 객체 자원 반환
			try {
				
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
				if(sc!= null)sc.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
