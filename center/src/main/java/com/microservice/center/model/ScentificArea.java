package com.microservice.center.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ScentificArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @ManyToMany(mappedBy = "scientificArea")
    private List<Magazine> magazineList;

    @ManyToMany
    private List<User> interestedUsers;

    public ScentificArea() {
        magazineList = new ArrayList<>();
        interestedUsers = new ArrayList<>();
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

    public List<Magazine> getMagazineList() {
        return magazineList;
    }

    public void setMagazineList(List<Magazine> magazineList) {
        this.magazineList = magazineList;
    }

    public List<User> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(List<User> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }
}
