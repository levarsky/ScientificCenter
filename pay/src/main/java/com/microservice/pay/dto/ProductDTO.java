package com.microservice.pay.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable{
    private String name;
    private String description;
    private String type;
    private String subscriberGivenName;
    private String subscriberSurname;
    private String subscriberEmail;
    private Double amount;
    private String clientId;

    public ProductDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubscriberGivenName() {
        return subscriberGivenName;
    }

    public void setSubscriberGivenName(String subscriberGivenName) {
        this.subscriberGivenName = subscriberGivenName;
    }

    public String getSubscriberSurname() {
        return subscriberSurname;
    }

    public void setSubscriberSurname(String subscriberSurname) {
        this.subscriberSurname = subscriberSurname;
    }

    public String getSubscriberEmail() {
        return subscriberEmail;
    }

    public void setSubscriberEmail(String subscriberEmail) {
        this.subscriberEmail = subscriberEmail;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}