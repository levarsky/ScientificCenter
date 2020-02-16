package com.microservice.sellers_service.model;

import java.io.Serializable;

public class ProductDTO implements Serializable{
	private String name;
	private Double amount;
	
	public ProductDTO() {
		super();
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
