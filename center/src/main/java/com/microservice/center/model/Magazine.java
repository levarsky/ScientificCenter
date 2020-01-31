package com.microservice.center.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String issn;

    @Column
    private Float price;

    @JsonIgnore
    @ManyToMany
    private List<ScentificArea> scientificArea;

    @Column
    private Boolean readerPays;

    @JsonIgnore
    @ManyToMany(mappedBy = "magazineRedactor")
    private List<User> redactors;

    @JsonIgnore
    @ManyToMany(mappedBy = "magazineReviewer")
    private List<User> reviewer;

    @JsonIgnore
    @OneToMany(mappedBy = "magazine")
    private List<Publication> publications;

    @JsonIgnore
    @ManyToMany(mappedBy = "subscriptions")
    private List<User> readers;

    @JsonIgnore
    @OneToMany(mappedBy = "magazine")
    private List<Transaction> transactions;

    public Magazine() {
        scientificArea = new ArrayList<>();
        redactors = new ArrayList<>();
        reviewer = new ArrayList<>();
        publications = new ArrayList<>();
        readers = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public List<ScentificArea> getScientificArea() {
        return scientificArea;
    }

    public void setScientificArea(List<ScentificArea> scientificArea) {
        this.scientificArea = scientificArea;
    }

    public List<User> getRedactors() {
        return redactors;
    }

    public void setRedactors(List<User> redactors) {
        this.redactors = redactors;
    }

    public List<User> getReviewer() {
        return reviewer;
    }

    public void setReviewer(List<User> reviewer) {
        this.reviewer = reviewer;
    }

    public Boolean getReaderPays() {
        return readerPays;
    }

    public void setReaderPays(Boolean readerPays) {
        this.readerPays = readerPays;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public List<User> getReaders() {
        return readers;
    }

    public void setReaders(List<User> readers) {
        this.readers = readers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
