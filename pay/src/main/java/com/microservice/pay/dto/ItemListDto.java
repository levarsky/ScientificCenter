package com.microservice.pay.dto;

import java.util.List;

public class ItemListDto {
	private List<ItemDto> items;
	
	public ItemListDto() {
		super();
	}

	public ItemListDto(List<ItemDto> items) {
		super();
		this.items = items;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItems(List<ItemDto> items) {
		this.items = items;
	}
}
