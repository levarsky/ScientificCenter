package com.microservice.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.pay.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	public User findByUserEmail(String email);
}
