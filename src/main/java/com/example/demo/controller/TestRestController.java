package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

	@Autowired
	UserDetailsService userDetailsService;
	
	@PostMapping("/login_test")
	public String login(String username, String password) {
		userDetailsService.loadUserByUsername(username);
		return "login_test";
	}
	
	@GetMapping("/sessionTimeOut")
	public String sessionTimeOut() {
		return "sessionTimeOut";
	}
}
