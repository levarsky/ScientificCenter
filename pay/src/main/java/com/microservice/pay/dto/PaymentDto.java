package com.microservice.pay.dto;

import java.util.List;

public class PaymentDto {
	private String intent;
	private PayerDto payer;
	private List<TransactionDto> transactions;
	private ApplicationContext redirect_urls;
	
	public PaymentDto() {
		super();
	}
	public PaymentDto(List<TransactionDto> transactions, ApplicationContext redirect_urls) {
		super();
		this.intent = "sale";
		this.payer = new PayerDto();
		this.transactions = transactions;
		this.redirect_urls = redirect_urls;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public PayerDto getPayer() {
		return payer;
	}
	public void setPayer(PayerDto payer) {
		this.payer = payer;
	}
	public List<TransactionDto> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionDto> transactions) {
		this.transactions = transactions;
	}
	public ApplicationContext getRedirect_urls() {
		return redirect_urls;
	}
	public void setRedirect_urls(ApplicationContext redirect_urls) {
		this.redirect_urls = redirect_urls;
	}
}
