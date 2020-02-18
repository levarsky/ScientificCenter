package com.microservice.bitcoin_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String clientId;
	@Column
	private String bitcoinToken;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBitcoinToken() {
		return bitcoinToken;
	}
	public void setBitcoinToken(String bitcoinToken) {
		this.bitcoinToken = bitcoinToken;
	}
}
