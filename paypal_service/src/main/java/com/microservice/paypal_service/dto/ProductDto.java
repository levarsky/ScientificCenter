package com.microservice.paypal_service.dto;

import java.io.Serializable;

public class ProductDto implements Serializable{
	private String id;
	private String name;
	private String description;
	private ProductType type;
	private ProductCategory category;
	private String image_url;
	
	public ProductDto(String id, String name, String description, ProductType type, ProductCategory category,
			String image_url) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.category = category;
		this.image_url = image_url;
	}
	
	public ProductDto(String name, String description, ProductType type, ProductCategory category, String image_url) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		this.category = category;
		this.image_url = image_url;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	@Override
	public String toString() {
		return "\"name\": \"" + name + "\", \"description\": \"" + description + "\", \"type: \"" + type + "\", \"category\": \"" + category
				+ "\", \"image_url\": \"" + image_url + "\"";
	}
}
