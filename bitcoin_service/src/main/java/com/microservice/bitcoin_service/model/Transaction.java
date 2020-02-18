package com.microservice.bitcoin_service.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String merchantOrderId;

    @Column
    private String clientId;

    @Column
    private Double amount;

    @Column
    private Date timestamp;

    @Column
    private Boolean successful;

    public Transaction(){}

    public Transaction(String merchantOrderId, String clientId, Double amount, Date timestamp, Boolean successful) {
		super();
		this.merchantOrderId = merchantOrderId;
		this.clientId = clientId;
		this.amount = amount;
		this.timestamp = timestamp;
		this.successful = successful;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
}
