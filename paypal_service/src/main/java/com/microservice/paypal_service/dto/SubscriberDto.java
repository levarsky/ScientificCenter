package com.microservice.paypal_service.dto;

import java.io.Serializable;

public class SubscriberDto implements Serializable{
	private NameDto name;
	private String email_address;
	
	public SubscriberDto(NameDto name, String email_address) {
		super();
		this.name = name;
		this.email_address = email_address;
	}

	public NameDto getName() {
		return name;
	}

	public void setName(NameDto name) {
		this.name = name;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
}


