package com.microservice.bank_service.model;

public class Response {

    private String PAYMENT_URL;
    private String PAYMENT_ID;

    public String getPAYMENT_URL() {
        return PAYMENT_URL;
    }

    public void setPAYMENT_URL(String PAYMENT_URL) {
        this.PAYMENT_URL = PAYMENT_URL;
    }

    public String getPAYMENT_ID() {
        return PAYMENT_ID;
    }

    public void setPAYMENT_ID(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }

}
