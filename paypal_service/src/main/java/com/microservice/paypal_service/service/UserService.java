package com.microservice.paypal_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.paypal_service.model.User;
import com.microservice.paypal_service.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	public User findByUserEmail(String email) {
		return userRepo.findByUserEmail(email);
	}
}
