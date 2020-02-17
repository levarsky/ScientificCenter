package com.microservice.bitcoin_service.dto;

public class ProductDto {
	private String name;
	private Double amount;
	
	public ProductDto() {
		super();
	}
	public ProductDto(String name, Double amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
