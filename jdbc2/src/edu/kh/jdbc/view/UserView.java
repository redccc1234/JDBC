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

	private void multiInsertUser() {
		// TODO Auto-generated method stub
		
	}

	private void insertUser2() throws Exception{
		
		System.out.println("\n=====7. User 등록(아이디 중복 검사)=====\n");
		
		System.out.print("ID 입력 : ");
		String id = sc.next();
		
		System.out.print("PW 입력 : ");
		String pw = sc.next();
		
		System.out.print("이름 입력 : ");
		String name = sc.next();
		
		User user = new User();
		
		user.setUserId(id);
		user.setUserPw(pw);
		user.setUserName(name);
		
		int result = service.insertUser2(user);
		
		
		
	}

	private void updateName() throws Exception{
		
		System.out.println("\n=====6. ID, PW가 일치하는 회원이 있을 경우 이름 수정=====\n");
		
		System.out.print("ID 입력 : ");
		String id = sc.next();
		
		System.out.print("PW 입력 : ");
		String pw = sc.next();
		
		System.out.print("이름 입력 : ");
		String name = sc.next();
		
		User user = new User();
		
		user.setUserId(id);
		user.setUserPw(pw);
		user.setUserName(name);
		
		int result = service.updateUser(user);
		
		if(result > 0) {
			System.out.println("\n***수정 성공***\n");
		}else {
			System.out.println("\n***수정 실패***\n");
		}
		
		
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
		
		System.out.print("USER_NO 입력 : ");
		int input = sc.nextInt();
		
		int result = service.deleteUser(input);
		
		if(result > 0) {
			System.out.println("\n***삭제 성공***\n");
		}else {
			System.out.println("\n***삭제 실패***\n");
		}
		
		
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
		
		System.out.print("USER_NO을 입력 : ");
		int input = sc.nextInt();
		
		User user = service.selectUser(input);
		
		if(user != null) {
			System.out.println(user);
		}else {
			System.out.println("찾지 못하였습니다.");
		}
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
