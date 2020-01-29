package com.microservice.paypal_service.dto;

public class SubscriptionDto {
	private String plan_id;
	private SubscriberDto subscriber;
	
	public SubscriptionDto(String plan_id, SubscriberDto subscriber) {
		super();
		this.plan_id = plan_id;
		this.subscriber = subscriber;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public SubscriberDto getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(SubscriberDto subscriber) {
		this.subscriber = subscriber;
	}
}
