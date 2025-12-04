package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.User;
import com.app.service.JwtService;
import com.app.service.UserService;

@RestController
public class UserController {

    private final JwtService jwtService;

	@Autowired
	private UserService userService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }
	
	@PostMapping("register")
	public User register(@RequestBody User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		System.out.println("user password: " + user.getPassword());
		
		return userService.saveUser(user);
		
	}
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("login")
	public String login(@RequestBody User user) {
		
		System.out.println("login called");
		
		Authentication authentication= authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()) );
		
		if ( authentication.isAuthenticated() ) {
			System.out.println(jwtService.generateSecretKey());
			
			return jwtService.generateToken(user.getUsername());
		}
		else {
			return "login failed";
		}
					
		
	}
	
	
	
}





