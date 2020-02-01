package com.microservice.bank.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ResponseFromBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String status;

    @Column
    private Long ACQUIRER_ORDER_ID;

    @Column
    private Date ACQUIRER_TIMESTAMP;

    @Column
    private Long ISSUER_ORDER_ID;

    @Column
    private Date ISSUER_TIMESTAMP;

    public ResponseFromBank() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getISSUER_ORDER_ID() {
        return ISSUER_ORDER_ID;
    }

    public void setISSUER_ORDER_ID(Long ISSUER_ORDER_ID) {
        this.ISSUER_ORDER_ID = ISSUER_ORDER_ID;
    }

    public Date getISSUER_TIMESTAMP() {
        return ISSUER_TIMESTAMP;
    }

    public void setISSUER_TIMESTAMP(Date ISSUER_TIMESTAMP) {
        this.ISSUER_TIMESTAMP = ISSUER_TIMESTAMP;
    }
}
