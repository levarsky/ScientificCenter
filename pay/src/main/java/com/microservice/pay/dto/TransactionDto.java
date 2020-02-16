package com.microservice.pay.dto;

import java.util.List;

public class TransactionDto {
	private AmountDto amount;
	private PaymentOptionDto payment_options;
	private List<ItemDto> item_list;
	
	public TransactionDto(List<ItemDto> item_list) {
		super();
		double amount = 0.00;
		for(ItemDto item : item_list) {
			amount += Double.parseDouble(item.getPrice());
		}
		this.amount = new AmountDto(String.valueOf(amount), new DetailsDto(String.valueOf(amount)));
		this.payment_options = new PaymentOptionDto();
		this.item_list = item_list;
	}
	public TransactionDto() {
		super();
	}
	public AmountDto getAmount() {
		return amount;
	}
	public void setAmount(AmountDto amount) {
		this.amount = amount;
	}
	public PaymentOptionDto getPayment_options() {
		return payment_options;
	}
	public void setPayment_options(PaymentOptionDto payment_options) {
		this.payment_options = payment_options;
	}
	public List<ItemDto> getItem_list() {
		return item_list;
	}
	public void setItem_list(List<ItemDto> item_list) {
		this.item_list = item_list;
	}
}
