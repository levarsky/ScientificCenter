package com.microservice.pay.dto;

public class PaymentOptionDto {
	private String allowed_payment_method;
	
	public PaymentOptionDto() {
		super();
		this.allowed_payment_method = "INSTANT_FUNDING_SOURCE";
	}

	public String getAllowed_payment_method() {
		return allowed_payment_method;
	}

	public void setAllowed_payment_method(String allowed_payment_method) {
		this.allowed_payment_method = allowed_payment_method;
	}
}
