package com.microservice.pay.dto;

import java.util.List;

public class SellerDataDto {
	private String token;
    private String type;
    private List<ProductDTO> products;
    private Double amount;
    private String firstName;
    private String lastName;
    private String email;
    private String transactionId;
    private String url;
    
    public SellerDataDto(String token, String type, List<ProductDTO> products, Double amount, String firstName,
			String lastName, String email, String transactionId, String url) {
		super();
		this.token = token;
		this.type = type;
		this.products = products;
		this.amount = amount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.transactionId = transactionId;
		this.url = url;
	}
	public SellerDataDto() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
