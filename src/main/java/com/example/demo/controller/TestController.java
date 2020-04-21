package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.TestForm;
//import com.example.demo.mapper.Registered;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestController
@Controller
public class TestController {

//	@Autowired
//	private Registered registered;
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/")
	public String index() {
//		userDetailsService.loadUserByUsername("");
//		return token;
		return "index";
	}
	
	@GetMapping("/success")
	@ResponseBody
	public String success( ) {
//		log.debug(form.getName());
		return "success";
	}
	@GetMapping("/success2")
	@ResponseBody
	public String success2( ) {
//		log.debug(form.getName());
		return "success2";
	}
	
	
	@PostMapping("/registered")
	public String registered(String name, String password) {
//		registered.insert(name, encoder.encode(password));
		return "success";
	}
	
	@PostMapping("/testPost")
	public String testPost(@Valid @RequestBody TestForm form) {
		log.debug(form.getName());
		return "";
	}
	
//	@PostMapping("/testPost")
//	public String testPost(String id, String name) {
//		log.debug(name);
//		return "";
//	}
}
