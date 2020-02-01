package com.microservice.paypal_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String userEmail;
	@Column
	private String paypalUserEmail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPaypalUserEmail() {
		return paypalUserEmail;
	}

	public void setPaypalUserEmail(String paypalUserEmail) {
		this.paypalUserEmail = paypalUserEmail;
	}
}
