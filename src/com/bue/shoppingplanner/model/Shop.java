package com.bue.shoppingplanner.model;

public class Shop {
	private int id, shopDescription, address;
	private String name;
	public Shop() {
		super();
	}
	public Shop(int id, int shopDescription, int address, String name) {
		super();
		this.id = id;
		this.shopDescription = shopDescription;
		this.address = address;
		this.name = name;
	}
	
	public Shop(int shopDescription, int address, String name) {
		super();
		this.shopDescription = shopDescription;
		this.address = address;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShopDescription() {
		return shopDescription;
	}
	public void setShopDescription(int shopDescription) {
		this.shopDescription = shopDescription;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
