package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dto.User;
import edu.kh.jdbc.model.service.UserSerivice;

// View : 사용자와 직접 상호작용하는 화면 (UI)를 담당
// (사용자에게) 입력을 받고 결과를 출력하는 역할
public class UserView {

	// 필드
	private UserSerivice service = new UserSerivice();
	private Scanner sc = new Scanner(System.in);

	/**
	 * User 관리 프로그램 메인 메뉴 UI (View)
	 */
	public void mainMenu() {
		
		int input = 0; // 메뉴 선택용 변수
		
		do {
			 try {

				 System.out.println("\n===== User 관리 프로그램 =====\n");
					System.out.println("1. User 등록(INSERT)");
					System.out.println("2. User 전체 조회(SELECT)");
					System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
					System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
					System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
					System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
					System.out.println("7. User 등록(아이디 중복 검사)");
					System.out.println("8. 여러 User 등록하기");
					System.out.println("0. 프로그램 종료");
					System.out.print("메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine(); // 버퍼에 남은 개행문자 제거
					switch (input) {
					case 1: insertUser(); break;
					case 2: selectAll(); break;
					case 3: selectName(); break;
					case 4: selectUser(); break;
					case 5: deleteUser(); break;
					case 6: updateName(); break;
					case 7: insertUser2(); break;
					case 8: multiInsertUser(); break;
					case 0: System.out.println("\n[프로그램 종료]\n"); break;
					default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
					}
					System.out.println("\n-------------------------------------\n");

				 
			} catch (InputMismatchException e) {
				// Scanner 를 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n***잘못 입력 하셨습니다***\n");
				
				input = -1; // 잘못 입력해서 whlie문 멈추는 걸 방지
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자 제거
				
				
			} catch(Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
				
			}
			
		}while(input != 0);
		
	}

	/** 8. 여러 User 등록하기
	 * 
	 */
	private void multiInsertUser() throws Exception{
		
		System.out.println("\n=====8. 여러 User 등록하기=====\n");
		
		System.out.print("등록할 User 의 수 입력 : ");
		int input = sc.nextInt();
		sc.nextLine(); // 버퍼 개행 문자 제거
		
		// 입력 받을 회원 정보를 저장할 List 객체 생성
		List<User> userList = new ArrayList<User>();
		
		for(int i = 0; i < input ; i++) {
			
			String id = null; // 입력된 아이디를 저장할 변수
			
			while(true){
				
				System.out.print((i+1) + " 번째 ID : ");
				id = sc.next();
				
				// 입력받은 id가 중복인지 검사하는 서비스(SELECT) 호출 후 결과 반환(int) 받기
				// 중복이 있을 땐 1 , 사용 가능 할 땐 0
				int count = service.idCheck(id);
				
				if(count ==  0) { // 중복이 아닌 경우
					System.out.println("사용 가능한 아이디 입니다");
					break;
				}
				System.out.println("이미 사용 중인 아이디 입니다. 다시 입력하세요.");

			}
			
			// pw,name 입력받기
			System.out.print((i+1)+ " 번째 PW : ");
			String pw = sc.next();
			
			System.out.print((i+1)+ " 번째 NAME : ");
			String name = sc.next();
			
			// 입력받은 값 3개를 한 번에 묶어서 전달할 수 있도록
			// User DTO 객체를 생성한 후 필드에 값 세팅
			User user = new User();
			
			user.setUserId(id);
			user.setUserPw(pw);
			user.setUserName(name);
			
			// userList 에 방금 만든 User 추가
			userList.add(user);
			
		}// for문 종료 지점
		
		// 입력받은 모든 User을 INSERT하는 서비스 호출
		// -> 결과로 삽입된 행의 갯수 반환
		int result = service.multInsertUser(userList);
		
		if (result == userList.size()) {
			System.out.println("전체 삽입 성공!!!");
		}else {
			System.out.println("삽입 실패");
		}
		
	}

	/** 7. User 등록(아이디 중복 검사)
	 * @throws Exception
	 */
	private void insertUser2() throws Exception{
		
		System.out.println("\n=====7. User 등록(아이디 중복 검사)=====\n");
		
		String id = null; // 입력된 아이디를 저장할 변수
		
		while(true){
			
			System.out.print("ID 입력 : ");
			id = sc.next();
			
			// 입력받은 id가 중복인지 검사하는 서비스(SELECT) 호출 후 결과 반환(int) 받기
			// 중복이 있을 땐 1 , 사용 가능 할 땐 0
			int count = service.idCheck(id);
			
			if(count ==  0) { // 중복이 아닌 경우
				System.out.println("사용 가능한 아이디 입니다");
				break;
			}
			System.out.println("이미 사용 중인 아이디 입니다. 다시 입력하세요.");
		}
		
		// pw,name 입력 받기
		System.out.print("PW 입력 : ");
		String pw = sc.next();
		
		System.out.print("NAME 입력 : ");
		String name = sc.next();
		
		// 입력받은 값 3개를 한 번에 묶어서 전달할 수 있도록
		// User DTO 객체를 생성한 후 필드에 값 세팅
		User user = new User();
		
		user.setUserId(id);
		user.setUserPw(pw);
		user.setUserName(name);
		
		// service 호출 후 결과 값 반환
		int result = service.insertUser(user);
		
		if(result > 0) {
			System.out.println("\n" + id + "사용자가 등록되었습니다.\n");
		}else {
			System.out.println("\n***등록 실패***\n");
		}
		
	}
		
	/** 6. ID,PW가 일치하는 회원이 있을 경우 이름 수정
	 *  -> ID,PW가 일치하는 회원 (조회 SELECT)
	 *  -> 있을 경우 이름 수정 (수정 UPDATE)
	 */
	private void updateName() throws Exception{
		
		System.out.println("\n=====6. ID, PW가 일치하는 회원의 이름 수정=====\n");
		
		System.out.print("ID 입력 : ");
		String id = sc.next();
		
		System.out.print("PW 입력 : ");
		String pw = sc.next();
		
		// 입력받은 ID,PW 가 일치하는 회원이 존재하는지 조회 (SELECT)
		// -> 수정할 때 필요한 데이터 USER_NO 조회해오기.
		int userNo = service.selectUserNo(id,pw);
		
		// 조회결과가 없을 때
		if(userNo == 0) {
			System.out.println("아이디, 비밀번호가 일치하는 사용자가 없음");
			return;
		}
		
		// 조회결과가 있을 때
		System.out.print("수정할 이름 입력 :");
		String name = sc.next();
		
		// 위에서 조회된 회원(userNo)의 이름을 수정
		// 서비스 호출(UPDATE) 후 결과 반환(int)
		int result = service.updateName(name,userNo);
		
		if(result > 0) System.out.println("수정 성공");
		else           System.out.println("수정 실패");
		
		
	}

	/** 5. USER_NO를 입력받아 일치하는 User 삭제 (DELETE)
	 * * DML
	 * 
	 * -- 삭제 성공했을 때 : 삭제 성공
	 * -- 삭제 실패했을 때 : 사용자 번호가 일치하는 User가 존재하지 않음
	 * 
	 */
	private void deleteUser() throws Exception{

		System.out.println("\n=====5. USER_NO를 입력 받아 일치하는 User 삭제=====\n");
		
		System.out.print("삭제할 사용자 번호 입력 : ");
		int input = sc.nextInt();
		
		// DML -> 행의 갯수로 넘어옴 (int형)
		int result = service.deleteUser(input);
		
		if(result > 0) System.out.println("\n***삭제 성공***\n");
		else           System.out.println("\n***사용자 번호가 일치하는 User가 존재하지 않음***\n");
		
		
	}

	/** 4. USER_NO 를 입력받아 일치하는 User 조회
	 * * 딱 1행만 조회되거나 or 일치하는 것 못찾았거나
	 * 
	 * -- 찾았을 때 : User 객체 출력
	 * -- 못 찾았을 때 : USER_NO가 일치하는 회원이 없음
	 * 
	 */
	private void selectUser() throws Exception{
		
		System.out.println("\n=====4.USER_NO를 입력 받아 일치하는 User 조회=====\n");
		
		System.out.print("사용자 번호 입력 : ");
		int input = sc.nextInt();
		
		// service 호출 후 결과 반환 받기
		// USER_NO (PK) == 중복 값이 없다.
		// == 일치하는 사용자가 있다면 반드시 딱 1행만 조회
		// -> 1행의 조회 결과를 담기 위해서 User DTO 객체 1개 사용
		User user = service.selectUser(input);
		// 최종적으로 user에 값이 담겨 있다. (view <- service <- dao)
		
		if(user == null) {
			System.out.println("USER_NO와 일치하는 회원을 찾지 못하였습니다.");
			return;
		}
		System.out.println(user);
	}

	/** 3. User 중 이름에 검색어가 포함된 회원 조회
	 * 검색어 입력 : 유
	 * 
	 */
	private void selectName() throws Exception{
		
		System.out.println("\n=====3.User 중 이름에 검색어가 포함된 회원 조회=====\n");
		
		System.out.print("검색어 입력 : ");
		String input = sc.next();
		
		// Service 호출 후 결과 반환받기
		List<User> searchList = service.selectName(input);
		
		if(searchList.isEmpty()) {
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
		for(User user : searchList) {
			System.out.println(user);
		}
		
	}

	/** 2. user 전체 조회 관련된 View (SELECT)
	 * 
	 */
	private void selectAll() throws Exception{
 
		System.out.println("\n=====2. User 전체 조회=====\n");
		
		// Service 호출(SELECT) 후 결과값 반환(List<User>) 받기 
		List<User> userList = service.selectAll();
		
		// 조회 결과가 없을 경우
		if(userList.isEmpty()) {
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
		// 조회 결과가 있을 경우
		// userList에 있는 모든 User 객체 출력
		// 향상된 for문
		for(User user : userList) {
			System.out.println(user);
		}
		
		
	}

	/** 1. User 등록 관련된 View
	 * 
	 */
	private void insertUser() throws Exception{

		System.out.println("\n=====1. User 등록=====\n");
		
		System.out.print("ID : ");
		String id = sc.next();
		
		System.out.print("PW : ");
		String password = sc.next();
		
		System.out.print("NAME : ");
		String name = sc.next();
		
		// 입력받은 값 3개를 한 번에 묶어서 전달할 수 있도록
		// User DTO 객체를 생성한 후 필드에 값을 세팅
		User user = new User(); // 기본생성자 -> 기본값으로 null이 들어감(JML에 의해서!!)
		
		// setter 이용해서 값을 세팅
		// USER_NO 고유번호(PRIMARY KEY)이고 EMROLL_DATE는 등록된 시점인 현재 시간(SYSDATE)으로 들어가기 때문에 작성 안해도 됨.
		user.setUserId(id);
		user.setUserPw(password);
		user.setUserName(name);
		
		// Service 호출(INSERT) 후 결과 반환(int,결과 행의 갯수) 받기
		int result = service.insertUser(user);
		// service 객체(UserService)에 있는 insertUser()라는 이름의 메서드를 호출하겠다
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0) {
			System.out.println("\n" + id + "사용자가 등록되었습니다\n");
		}else {
			System.out.println("\n***등록 실패***\n");
		}
			
	}
	
}
