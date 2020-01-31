package com.microservice.bank.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RequestFromBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private Long ACQUIRER_ORDER_ID;

    @Column
    private Date ACQUIRER_TIMESTAMP;

    @Column
    private String PAN;

    @Column
    private String SECURITY_CODE;

    @Column
    private String CARD_HOLDER_NAME;

    @Column
    private Date EXPIRATION_DATE;

    @Column
    private Double amount;

    public RequestFromBank() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getACQUIRER_ORDER_ID() {
        return ACQUIRER_ORDER_ID;
    }

    public void setACQUIRER_ORDER_ID(Long ACQUIRER_ORDER_ID) {
        this.ACQUIRER_ORDER_ID = ACQUIRER_ORDER_ID;
    }

    public Date getACQUIRER_TIMESTAMP() {
        return ACQUIRER_TIMESTAMP;
    }

    public void setACQUIRER_TIMESTAMP(Date ACQUIRER_TIMESTAMP) {
        this.ACQUIRER_TIMESTAMP = ACQUIRER_TIMESTAMP;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getSECURITY_CODE() {
        return SECURITY_CODE;
    }

    public void setSECURITY_CODE(String SECURITY_CODE) {
        this.SECURITY_CODE = SECURITY_CODE;
    }

    public String getCARD_HOLDER_NAME() {
        return CARD_HOLDER_NAME;
    }

    public void setCARD_HOLDER_NAME(String CARD_HOLDER_NAME) {
        this.CARD_HOLDER_NAME = CARD_HOLDER_NAME;
    }

    public Date getEXPIRATION_DATE() {
        return EXPIRATION_DATE;
    }

    public void setEXPIRATION_DATE(Date EXPIRATION_DATE) {
        this.EXPIRATION_DATE = EXPIRATION_DATE;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
