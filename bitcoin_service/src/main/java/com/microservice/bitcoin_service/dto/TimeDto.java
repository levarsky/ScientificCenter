package com.microservice.bitcoin_service.dto;

public class TimeDto {
	
	private String iso;
	private float epoch;
	
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public float getEpoch() {
		return epoch;
	}
	public void setEpoch(float epoch) {
		this.epoch = epoch;
	}
}
