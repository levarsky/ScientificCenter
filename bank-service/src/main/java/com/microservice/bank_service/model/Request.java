package com.microservice.bank_service.model;

import java.util.Date;

public class Request {

    private String MERCHANT_ID;
    private String MERCHANT_PASSWORD;
    private Double AMOUNT;
    private Integer MERCHANT_ORDER_ID;
    private Date MERCHANT_TIMESTAMP;
    private String SUCCESS_URL;
    private String FAILED_URL;
    private String ERROR_URL;

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

    public String getMERCHANT_PASSWORD() {
        return MERCHANT_PASSWORD;
    }

    public void setMERCHANT_PASSWORD(String MERCHANT_PASSWORD) {
        this.MERCHANT_PASSWORD = MERCHANT_PASSWORD;
    }
}
