package com.microservice.pay.dto;

public class PaypalProductDto{
	private String name;
	private String type;
	
	public PaypalProductDto() {
		super();
	}
	public PaypalProductDto(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
