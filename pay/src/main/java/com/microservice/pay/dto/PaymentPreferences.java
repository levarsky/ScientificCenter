package com.microservice.pay.dto;

public class PaymentPreferences {
	private Boolean auto_bill_outstanding;

	public PaymentPreferences(Boolean auto_bill_outstanding) {
		super();
		this.auto_bill_outstanding = auto_bill_outstanding;
	}

	public Boolean getAuto_bill_outstanding() {
		return auto_bill_outstanding;
	}

	public void setAuto_bill_outstanding(Boolean auto_bill_outstanding) {
		this.auto_bill_outstanding = auto_bill_outstanding;
	}
}
