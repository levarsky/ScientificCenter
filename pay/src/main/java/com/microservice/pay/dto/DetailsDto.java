package com.microservice.pay.dto;

public class DetailsDto{
	private String subtotal;
	private String tax;
	private String shipping;
	private String handling_fee;
	private String shipping_discount;
	private String insurance;
	
	public DetailsDto(String subtotal) {
		super();
		this.subtotal = subtotal;
		this.tax = "0.00";
		this.shipping = "0.00";
		this.handling_fee = "0.00";
		this.shipping_discount = "0.00";
		this.insurance = "0.00";
	}
	public DetailsDto() {
		super();
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public String getHandling_fee() {
		return handling_fee;
	}
	public void setHandling_fee(String handling_fee) {
		this.handling_fee = handling_fee;
	}
	public String getShipping_discount() {
		return shipping_discount;
	}
	public void setShipping_discount(String shipping_discount) {
		this.shipping_discount = shipping_discount;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
}
