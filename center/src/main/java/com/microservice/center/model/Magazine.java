package com.microservice.center.model;

import java.util.List;

public class Magazine {

    private String name;
    private String issn;
    private List<ScentificArea> scentificArea;
    private PaymentMethod paymentMethod;
    private List<User> redactors;
    private List<User> reviewer;
}
