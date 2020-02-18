package com.microservice.pay.dto;

public class BillingCycle {
	private Frequency frequency;
	private String tenure_type;
	private Integer sequence;
	private Integer total_cycles;
	private PricingScheme pricing_scheme;
	
	public BillingCycle(Frequency frequency, String tenure_type, Integer sequence, Integer total_cycles,
			PricingScheme pricing_scheme) {
		super();
		this.frequency = frequency;
		this.tenure_type = tenure_type;
		this.sequence = sequence;
		this.total_cycles = total_cycles;
		this.pricing_scheme = pricing_scheme;
	}
	public BillingCycle() {
		super();
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public String getTenure_type() {
		return tenure_type;
	}
	public void setTenure_type(String tenure_type) {
		this.tenure_type = tenure_type;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getTotal_cycles() {
		return total_cycles;
	}
	public void setTotal_cycles(Integer total_cycles) {
		this.total_cycles = total_cycles;
	}
	public PricingScheme getPricing_scheme() {
		return pricing_scheme;
	}
	public void setPricing_scheme(PricingScheme pricing_scheme) {
		this.pricing_scheme = pricing_scheme;
	}
	
}
