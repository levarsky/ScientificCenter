package com.microservice.bitcoin_service.dao;

public class TransactionDao {
	
	private String seedSender;
	
	private long amount;
	
	private String seedReceiver;
	
	
	public String getSeedSender() {
		return seedSender;
	}
	public void setSeedSender(String seedSender) {
		this.seedSender = seedSender;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getSeedReceiver() {
		return seedReceiver;
	}
	public void setSeedReceiver(String seedReceiver) {
		this.seedReceiver = seedReceiver;
	}
	
}
