package org.sjiay.demo.controller;

import lombok.Data;

import java.util.Date;

/**
 * @program: SpringBlade
 * @description
 * @author: YouName
 * @create: 2020-07-31 14:14
 **/
@Data
public class User {
	private Long id;
	private String name;
	private Integer age;
	private String school;
	private Date accessTime;

	public User(Long id, String name, Integer age, String school) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.school = school;
	}

	public User(Date accessTime) {
		this.accessTime = accessTime;
	}
}
