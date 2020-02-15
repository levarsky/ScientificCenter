package com.microservice.pay.dto;

import java.io.Serializable;

public class NameDto implements Serializable{
	private String given_name;
	private String surname;
	
	public NameDto(String given_name, String surname) {
		super();
		this.given_name = given_name;
		this.surname = surname;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
