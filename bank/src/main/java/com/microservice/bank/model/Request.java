package com.microservice.bank.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String MERCHANT_ID;

    @Column(length = 100)
    private String MERCHANT_PASSWORD;

    @Column
    private Double AMOUNT;

    @Column
    private Integer MERCHANT_ORDER_ID;

    @Column
    private Date MERCHANT_TIMESTAMP;

    @Column
    private String SUCCESS_URL;

    @Column
    private String FAILED_URL;

    @Column
    private String ERROR_URL;

    @Column(length = 30)
    private String PAYMENT_ID;

    public Request(){
    }

    public String getMERCHANT_ID() {
        return MERCHANT_ID;
    }

    public void setMERCHANT_ID(String MERCHANT_ID) {
        this.MERCHANT_ID = MERCHANT_ID;
    }

    public Double getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(Double AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public Integer getMERCHANT_ORDER_ID() {
        return MERCHANT_ORDER_ID;
    }

    public void setMERCHANT_ORDER_ID(Integer MERCHANT_ORDER_ID) {
        this.MERCHANT_ORDER_ID = MERCHANT_ORDER_ID;
    }

    public Date getMERCHANT_TIMESTAMP() {
        return MERCHANT_TIMESTAMP;
    }

    public void setMERCHANT_TIMESTAMP(Date MERCHANT_TIMESTAMP) {
        this.MERCHANT_TIMESTAMP = MERCHANT_TIMESTAMP;
    }

    public String getSUCCESS_URL() {
        return SUCCESS_URL;
    }

    public void setSUCCESS_URL(String SUCCESS_URL) {
        this.SUCCESS_URL = SUCCESS_URL;
    }

    public String getFAILED_URL() {
        return FAILED_URL;
    }

    public void setFAILED_URL(String FAILED_URL) {
        this.FAILED_URL = FAILED_URL;
    }

    public String getERROR_URL() {
        return ERROR_URL;
    }

    public void setERROR_URL(String ERROR_URL) {
        this.ERROR_URL = ERROR_URL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMERCHANT_PASSWORD() {
        return MERCHANT_PASSWORD;
    }

    public void setMERCHANT_PASSWORD(String MERCHANT_PASSWORD) {
        this.MERCHANT_PASSWORD = MERCHANT_PASSWORD;
    }

    public String getPAYMENT_ID() {
        return PAYMENT_ID;
    }

    public void setPAYMENT_ID(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }
}
