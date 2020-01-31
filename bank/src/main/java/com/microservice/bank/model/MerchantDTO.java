package com.microservice.bank.model;

import javax.persistence.Column;

public class MerchantDTO {

    private String merchantId;

    private String merchantPassword;

    public MerchantDTO() {
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }
}
