package com.microservice.pay.dto;

public class ItemDto {
	private String name;
	private String quantity;
	private String price;
	private String tax;
	private String currency;
	
	public ItemDto(String name, String price) {
		super();
		this.name = name;
		this.quantity = "1";
		this.price = price;
		this.tax = "0.00";
		this.currency = "USD";
	}
	public ItemDto() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
