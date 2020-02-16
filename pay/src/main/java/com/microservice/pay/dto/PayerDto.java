package com.microservice.pay.dto;

public class PayerDto {
	private String payment_method;
	
	public PayerDto() {
		super();
		this.payment_method = "paypal";
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	
}
