package com.microservice.bank.model;

import java.util.Date;

public class Request {

    private String MERCHANT_ID;
    private String PASSWORD_ID;
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

    public String getPASSWORD_ID() {
        return PASSWORD_ID;
    }

    public void setPASSWORD_ID(String PASSWORD_ID) {
        this.PASSWORD_ID = PASSWORD_ID;
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
}
