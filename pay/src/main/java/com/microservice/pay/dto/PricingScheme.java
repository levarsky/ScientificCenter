package com.microservice.pay.dto;

public class PricingScheme {
	private FixedPrice fixed_price;

	public PricingScheme(FixedPrice fixed_price) {
		super();
		this.fixed_price = fixed_price;
	}

	public PricingScheme() {
		super();
	}

	public FixedPrice getFixed_price() {
		return fixed_price;
	}

	public void setFixed_price(FixedPrice fixed_price) {
		this.fixed_price = fixed_price;
	}
}
