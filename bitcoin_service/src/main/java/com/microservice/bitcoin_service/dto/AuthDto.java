package com.microservice.bitcoin_service.dto;

public class AuthDto {
	private String path;
	private String secret;
	private String accessKey;
	private String passphrase;
	
	public AuthDto() {
		super();
	}
	public AuthDto(String path, String secret, String accessKey, String passphrase) {
		super();
		this.path = path;
		this.secret = secret;
		this.accessKey = accessKey;
		this.passphrase = passphrase;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
}
