package com.microservice.pay.dto;

import java.util.List;

public class PlanDto {
	private String product_id;
	private String name;
	private String status;
	private List<BillingCycle> billing_cycles;
	private PaymentPreferences payment_preferences;
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BillingCycle> getBilling_cycles() {
		return billing_cycles;
	}
	public void setBilling_cycles(List<BillingCycle> billing_cycles) {
		this.billing_cycles = billing_cycles;
	}
	public PaymentPreferences getPayment_preferences() {
		return payment_preferences;
	}
	public void setPayment_preferences(PaymentPreferences payment_preferences) {
		this.payment_preferences = payment_preferences;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
