package com.microservice.center.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String email;

    @ManyToMany(mappedBy = "interestedUsers")
    private List<ScentificArea> scentificAreaList;

    @ManyToMany
    private List<Magazine> magazineRedactor;

    @ManyToMany
    private List<Magazine> magazineReviewer;

    @ManyToMany
    private List<Magazine> subscriptions;

    @ManyToMany
    private List<Publication> purchased;

    public User() {
        scentificAreaList = new ArrayList<>();
        magazineRedactor = new ArrayList<>();
        magazineReviewer = new ArrayList<>();
        subscriptions = new ArrayList<>();
        purchased = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ScentificArea> getScentificAreaList() {
        return scentificAreaList;
    }

    public void setScentificAreaList(List<ScentificArea> scentificAreaList) {
        this.scentificAreaList = scentificAreaList;
    }

    public List<Magazine> getMagazineRedactor() {
        return magazineRedactor;
    }

    public void setMagazineRedactor(List<Magazine> magazineRedactor) {
        this.magazineRedactor = magazineRedactor;
    }

    public List<Magazine> getMagazineReviewer() {
        return magazineReviewer;
    }

    public void setMagazineReviewer(List<Magazine> magazineReviewer) {
        this.magazineReviewer = magazineReviewer;
    }

    public List<Magazine> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Magazine> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Publication> getPurchased() {
        return purchased;
    }

    public void setPurchased(List<Publication> purchased) {
        this.purchased = purchased;
    }
}
