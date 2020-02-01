package com.microservice.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.paypal_service.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	public User findByUserEmail(String email);
}
