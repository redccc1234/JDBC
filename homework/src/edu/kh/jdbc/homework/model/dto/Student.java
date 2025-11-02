package edu.kh.jdbc.homework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Student {

	private int num;
	private String name;
	private int age;
	private String major;
	private String enrolldate;
	
}


