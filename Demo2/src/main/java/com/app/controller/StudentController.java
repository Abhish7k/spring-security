package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Student;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class StudentController {

	List<Student> students = new ArrayList<>(List.of(
	
			new Student(1, "Rahul", "Java"),
			new Student(2, "Atharv", "Blockchain"),
			new Student(3, "Pankaj", "IOT")
			));
	
	@GetMapping("students")
	public List<Student> getStudents() {
		return students;
	}
	
	@PostMapping("students")
	public void addStudent(@RequestBody Student theStudent) {
		
		System.out.println("post hit");
		
		students.add(theStudent);
	}
	
	@GetMapping("csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf") ;
	}
	
	
}








