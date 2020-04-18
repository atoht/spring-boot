package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

	private Long id;
	private String name;
	private String password;
	private LocalDateTime updateDate;
	private LocalDateTime createDate;
}
