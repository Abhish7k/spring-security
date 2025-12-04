package com.app.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {

	@GetMapping("hello")
	public String greet(HttpServletRequest request) {
		return "Hello world: " + request.getSession().getId();
	}

	@GetMapping("about")
	public String aboutUs(HttpServletRequest request) {
		return "We work in Pune: " + request.getSession().getId();
	}

	
}
