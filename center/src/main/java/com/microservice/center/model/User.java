package com.microservice.center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column
    private String username;

    @JsonIgnore
    @Column
    private String password;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String email;

    @Column
    private Boolean isEnabled ;

    @JsonIgnore
    @Column
    private Date lastPasswordResetDate;

    @Column
    private Boolean isVerified ;

    @JsonIgnore
    @ManyToMany(mappedBy = "interestedUsers")
    private List<ScentificArea> scentificAreaList;

    @JsonIgnore
    @ManyToMany
    private List<Magazine> magazineRedactor;

    @JsonIgnore
    @ManyToMany
    private List<Magazine> magazineReviewer;

    @JsonIgnore
    @ManyToMany
    private List<Magazine> subscriptions;

    @JsonIgnore
    @ManyToMany(mappedBy = "readers")
    private List<Publication> purchased;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
