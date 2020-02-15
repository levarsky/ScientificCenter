package com.microservice.pay.model;

import javax.persistence.*;

@Entity
public class Client {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String clientId;
	@Column
	private String paypalClientId;
	@Column
	private String paypalSecret;
	
	public Client() {
		super();
	}
	
	public Client(Long id, String clientId, String paypalClientId, String paypalSecret) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.paypalClientId = paypalClientId;
		this.paypalSecret = paypalSecret;
	}
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
	public String getPaypalClientId() {
		return paypalClientId;
	}
	public void setPaypalClientId(String paypalClientId) {
		this.paypalClientId = paypalClientId;
	}
	public String getPaypalSecret() {
		return paypalSecret;
	}
	public void setPaypalSecret(String paypalSecret) {
		this.paypalSecret = paypalSecret;
	}
}
