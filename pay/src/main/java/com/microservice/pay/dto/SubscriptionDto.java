package com.microservice.pay.dto;

public class SubscriptionDto {
	private String plan_id;
	private SubscriberDto subscriber;
	private ApplicationContext application_context;
	

	public SubscriptionDto(String plan_id, SubscriberDto subscriber, ApplicationContext application_context) {
		super();
		this.plan_id = plan_id;
		this.subscriber = subscriber;
		this.application_context = application_context;
	}

	public SubscriptionDto() {
		super();
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

	public ApplicationContext getApplication_context() {
		return application_context;
	}

	public void setApplication_context(ApplicationContext application_context) {
		this.application_context = application_context;
	}
	
	
}
