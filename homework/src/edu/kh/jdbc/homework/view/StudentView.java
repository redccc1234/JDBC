package edu.kh.jdbc.homework.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.dto.Student;
import edu.kh.jdbc.homework.model.service.StudentService;

public class StudentView {
		
		private StudentService service= new StudentService();
		private Scanner sc = new Scanner(System.in);
		
		
		public void mainMenu() {
			
			int input = 0;
			
			do {
				
				try {
					
					System.out.println("\n<<<<학생 관리 프로그램>>>>\n");
					System.out.println("1. 학생 등록");
					System.out.println("2. 전체 학생 조회");
					System.out.println("3. 학생 정보 수정");
					System.out.println("4. 학생 정보 삭제");
					System.out.println("5.전공별 학생 조회");
					System.out.println("5.프로그램 종료");
					System.out.print("\n번호 : ");
					input = sc.nextInt();
					
					switch(input) {
					
					case 1 : studentInsert(); break;
					case 2 : studentSelect(); break;
					case 3 : studentUpdate(); break;
					case 4 : studentDelete(); break;
					case 5 : majorSelect(); break;
					case 0 : System.out.println("프로그램을 종료하겠습니다...."); break;
					default : System.out.println("해당하는 번호가 업습니다.");
					
					}
					
				} catch (InputMismatchException e) {
					System.out.println("\n*********잘못 입력하셨습니다.*********\n");
					
					input = -1;
					sc.nextLine();	
					
					} catch (Exception e) {
						e.printStackTrace();
				}
				
			}while(input != 0);
			
			
			
		}


		/** 1. 학생 등록 (INSERT)
		 * 
		 */
		private void studentInsert() throws Exception {

			System.out.println("\n<1. 학생 등록>\n");
			
			System.out.print("학생 이름 입력 : ");
			String name = sc.next();
			
			System.out.print("학생 나이 입력 : ");
			int age = sc.nextInt();
		
			System.out.print("학생 전공 입력 : ");
			String major = sc.next();
			
			Student student = new Student();
			
			student.setName(name);
			student.setAge(age);
			student.setMajor(major);
			
			int result = service.studentInsert(student);
			
			if(result > 0 ) System.out.println("\n 학생 등록을 성공하였습니다.\n");
			else            System.out.println("\n학생 등록을 실패하였습니다.\n");
			
		}


		/** 2. 전체 학생 조회 (SELECT)
		 * 
		 */
		private void studentSelect() throws Exception{
			
			System.out.println("\n<2. 전체 학생 조회>\n");
			
			List<Student> studentList = service.studentSelect();
			
			if(studentList.isEmpty()) {
				System.out.println("\n***조회 결과가 없습니다***\n");
				return;
			} 
			
			for(Student student : studentList) {
				System.out.println(student);
			}
		}


		/** 3. 학생 정보 수정 (UPDATE)
		 * 
		 */
		private void studentUpdate() throws Exception{
			
			System.out.println("\n<3. 학생 정보 수정>\n");
			
			int input = 0;
		
		  do {
			
			System.out.println("1. 이름 수정  ");
			System.out.println("2. 나이 수정  ");
			System.out.println("3. 전공 수정  ");
			System.out.print("수정할 정보 번호 입력 : ");
			input = sc.nextInt();
		
			switch(input) {
				
			case 1 : System.out.print("\n수정할 이름 입력 : \n" );
					 String name = sc.next();
					 
					 int result1 = service.studentUpdateName(name);
					 
					 if(result1 > 0) System.out.println("수정 성공");
					 else           System.out.println("수정 실패");
					 
					 break;
					 
			case 2 : System.out.print("\n수정할 나이 입력 : \n");
					 int age = sc.nextInt();
					 
					 int result2 = service.studentUpdateAge(age);
					 
					 if(result2 > 0) System.out.println("수정 성공");
				     else           System.out.println("수정 실패");
					 
					 break;
					 
			case 3 : System.out.print("\n수정할 전공 입력 : \n");
					 String major = sc.next(); 
					 
					 int result3 = service.studentUpdateMajor(major);
					 
					 if(result3 > 0) System.out.println("수정 성공");
					 else           System.out.println("수정 실패");
					 
					 break;
			default : System.out.println("\n해당하는 번호가 없습니다.\n");	
			
			}
			
			
		  }while(input != 0);
		  
		  
		  
		}


		/** 4. 학생 정보 삭제
		 * 
		 */
		private void studentDelete() throws Exception{
			// TODO Auto-generated method stub
			
		}


		/** 5.전공별 학생 조회
		 * 
		 */
		private void majorSelect() throws Exception{
			// TODO Auto-generated method stub
			
		}
	
}
