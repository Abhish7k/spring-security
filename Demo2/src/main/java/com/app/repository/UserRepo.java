package com.app.repository;

import org.springframework.stereotype.Repository;

import com.app.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	
	User findByUsername(String username);
	
	
}
