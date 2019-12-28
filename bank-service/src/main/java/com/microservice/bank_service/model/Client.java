package com.microservice.bank_service.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String clientId;

    @Column
    private String clientName;

    @Column(length = 30)
    private String merchantId;

    @Column(length = 100)
    private String merchantPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "CLIENT_ID"), inverseJoinColumns = @JoinColumn(name = "PAYMENT_TYPE_ID"))
    private List<PaymentType> paymentTypes;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String MERCHANT_ID) {
        this.merchantId = MERCHANT_ID;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String MERCHANT_PASSWORD) {
        this.merchantPassword = MERCHANT_PASSWORD;
    }
}
