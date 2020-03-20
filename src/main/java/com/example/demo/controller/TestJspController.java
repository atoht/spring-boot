package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestJspController {

	@RequestMapping("/test0")
	public String test0() {
		return "NewFile";
	}
}
