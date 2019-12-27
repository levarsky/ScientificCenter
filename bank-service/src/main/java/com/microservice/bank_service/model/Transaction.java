package com.microservice.bank_service.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer MERCHANT_ORDER_ID;

    @Column
    private String clientId;

    @Column
    private Double amount;

    @Column
    private Date timestamp;

    @Column
    private Boolean successful;

    public Transaction(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMERCHANT_ORDER_ID() {
        return MERCHANT_ORDER_ID;
    }

    public void setMERCHANT_ORDER_ID(Integer MERCHANT_ORDER_ID) {
        this.MERCHANT_ORDER_ID = MERCHANT_ORDER_ID;
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
