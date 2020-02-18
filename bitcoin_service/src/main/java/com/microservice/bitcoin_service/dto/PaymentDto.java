package com.microservice.bitcoin_service.dto;

public class PaymentDto {
	private String order_id;
	private double price_amount;
	private String price_currency;
	private String receive_currency;
	private String description;
	private String cancel_url;
	private String success_url;
	
	public PaymentDto() {
		super();
	}
	public PaymentDto(String order_id, double price_amount, String description, String cancel_url, String success_url) {
		super();
		this.order_id = order_id;
		this.price_amount = price_amount;
		this.price_currency = "USD";
		this.receive_currency = "BTC";
		this.description = description;
		this.cancel_url = cancel_url;
		this.success_url = success_url;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public double getPrice_amount() {
		return price_amount;
	}
	public void setPrice_amount(double price_amount) {
		this.price_amount = price_amount;
	}
	public String getPrice_currency() {
		return price_currency;
	}
	public void setPrice_currency(String price_currency) {
		this.price_currency = price_currency;
	}
	public String getReceive_currency() {
		return receive_currency;
	}
	public void setReceive_currency(String receive_currency) {
		this.receive_currency = receive_currency;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCancel_url() {
		return cancel_url;
	}
	public void setCancel_url(String cancel_url) {
		this.cancel_url = cancel_url;
	}
	public String getSuccess_url() {
		return success_url;
	}
	public void setSuccess_url(String success_url) {
		this.success_url = success_url;
	}
}
