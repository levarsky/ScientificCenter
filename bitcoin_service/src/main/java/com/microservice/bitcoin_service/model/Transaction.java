package com.microservice.bitcoin_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column
	private String seedSender;
	
	@Column
	private long amount;
	
	@Column
	private String seedReceiver;
	
	@Column
	private String date;
	
	@Column
	private Boolean isSuccessful;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Boolean getIsSuccessful() {
		return isSuccessful;
	}
	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
}
