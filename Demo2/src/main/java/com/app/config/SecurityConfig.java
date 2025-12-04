package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//		// now we are not defining anything over here
//		return http.build();
//		
//	}
	
	// now open browser in incognito mode 
	// http
	
	@Bean 
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(customizer -> customizer.disable());
		
		http.authorizeHttpRequests(request -> request
				.requestMatchers("register", "login")
				.permitAll()
				.anyRequest().authenticated());
		
		http.httpBasic(Customizer.withDefaults());
		
		http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
	
//	@Bean
//	UserDetailsManager userDetailsManager() {
//		
//		UserDetails user1 = User.withDefaultPasswordEncoder()
//				.username("john")
//				.password("abc123")
//				.roles("USER")
//				.build();
//		
//		UserDetails user2 = User.withDefaultPasswordEncoder()
//				.username("meenu")
//				.password("pqr123")
//				.roles("USER", "ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1, user2);
//		
//	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService);
		
		provider.setPasswordEncoder( new BCryptPasswordEncoder(12) );
		
		return provider;
		
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	

}







