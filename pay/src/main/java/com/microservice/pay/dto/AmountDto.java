package com.microservice.pay.dto;

public class AmountDto {
	private String total;
	private String currency;
	private DetailsDto details;
	
	public AmountDto(String total, DetailsDto details) {
		super();
		this.total = total;
		this.currency = "USD";
		this.details = details;
	}
	public AmountDto() {
		super();
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public DetailsDto getDetails() {
		return details;
	}
	public void setDetails(DetailsDto details) {
		this.details = details;
	}	
}
