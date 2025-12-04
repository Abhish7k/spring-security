package com.app.security;



import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

//	@Bean
//	InMemoryUserDetailsManager userDetailsManager() {
//		
//		UserDetails john = User.builder()
//								.username("john")
//								.password("{noop}john")
//								.roles("EMPLOYEE")
//								.build();
//		
//		UserDetails mary = User.builder()
//				.username("mary")
//				.password("{noop}mary")
//				.roles("EMPLOYEE", "MANAGER")
//				.build();
//		
//		
//		return new InMemoryUserDetailsManager(john, mary);
//		
//	}
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
	
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		// define query to retrieve a user by username
		jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, enabled from members where user_id=?");
		
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");
		
		return jdbcUserDetailsManager;
	
	
	}
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(configurer -> 
			
			configurer
			.requestMatchers("/").hasRole("EMPLOYEE")
			.requestMatchers("/leaders/**").hasRole("MANAGER")
			.requestMatchers("/systems/**").hasRole("ADMIN")
		
			.anyRequest().authenticated())
		
			.formLogin(form -> form
								.loginPage("/showMyLoginPage")
								.loginProcessingUrl("/authenticateTheUser")
								//.defaultSuccessUrl("/")
								.permitAll())
								.logout(logout -> logout.permitAll())
			
			.exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
		
		
		return http.build();
	}
	
	
}



















