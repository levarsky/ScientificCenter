package com.microservice.pay.dto;

public class Resp {
    private String url;

    public Resp(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
