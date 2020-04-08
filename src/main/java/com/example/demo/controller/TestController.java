package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.TestForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestController {

	@GetMapping("/test")
	public String test1( TestForm form) {
		log.debug(form.getName());
		return "NewFile";
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
