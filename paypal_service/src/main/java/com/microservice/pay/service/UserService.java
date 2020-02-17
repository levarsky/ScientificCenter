package com.microservice.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.pay.model.User;
import com.microservice.pay.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	public User findByUserEmail(String email) {
		return userRepo.findByUserEmail(email);
	}
}
