package com.wapp.core.models;

import lombok.Data;

@Data
public class UserModel {

	private Float weight;
	private Integer height;
	private Float bodyFat;
	private String gender;
	private Integer age;
	private String insertedAt;

	private String username;
	private String name;
	private String email;

}
