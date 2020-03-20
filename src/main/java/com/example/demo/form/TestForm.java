package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TestForm {

	@NotEmpty
	private String id;
	
	@NotEmpty
	@Max(value = 3, message = "不能超过3")
	private String name;
}
