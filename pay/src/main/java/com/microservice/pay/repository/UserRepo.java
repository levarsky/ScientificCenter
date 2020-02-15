package com.microservice.pay.repository;

import com.microservice.pay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>{
	public User findByUserEmail(String email);
}
