package edu.kh.jdbc.model.service;

import edu.kh.jdbc.model.dao.UserDAO;

// (Model 중 하나) Service : 비즈니스 로직을 처리하는 계층
// 데이터를 가공하고 트랜잭션(Commit , Rollback) 수행
public class UserSerivice {

	
	// 필드
	private UserDAO udao = new UserDAO();
	
}
