package com.microservice.bank.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String cardHolderName;

    @Column
    @Length(max = 4, min = 3)
    private String cvv;

    @Column(length = 16,unique=true)
    private String cardNumber;

    @Column
    private Date expirationDate;

    @Column
    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    private Double amount;

    @OneToMany(mappedBy="account",cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account(){}

    public Account(Long id, String cardHolderName, String cvv, String cardNumber, Date expirationDate, Double amount) {
        this.id = id;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
