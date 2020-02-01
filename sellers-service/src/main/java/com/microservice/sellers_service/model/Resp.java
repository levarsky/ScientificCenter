package com.microservice.sellers_service.model;

import java.io.Serializable;

public class Resp implements Serializable{
    private String url;
    
    public Resp() {
		super();
	}

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
