package com.microservice.bank.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String merchantId;

    @Column(length = 100)
    private String merchantPassword;

    @Column
    private Double amount;

    @Column
    private Integer merchantOrderId;

    @Column
    private Date merchantTimestamp;

    @Column
    private String successUrl;

    @Column
    private String failedUrl;

    @Column
    private String errorUrl;

    @Column(length = 30)
    private String paymentId;

    public Request(){
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double AMOUNT) {
        this.amount = AMOUNT;
    }

    public Integer getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(Integer MERCHANT_ORDER_ID) {
        this.merchantOrderId = MERCHANT_ORDER_ID;
    }

    public Date getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(Date MERCHANT_TIMESTAMP) {
        this.merchantTimestamp = MERCHANT_TIMESTAMP;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String SUCCESS_URL) {
        this.successUrl = SUCCESS_URL;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String FAILED_URL) {
        this.failedUrl = FAILED_URL;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String ERROR_URL) {
        this.errorUrl = ERROR_URL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String PAYMENT_ID) {
        this.paymentId = PAYMENT_ID;
    }
}
