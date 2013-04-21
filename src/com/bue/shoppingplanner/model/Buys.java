package com.bue.shoppingplanner.model;

public class Buys {
	private int id;
	private int product, shop, user;//User not used in first version
	private double unitPrice;
	private int amount;
	private int group;
	private String date; //Not sure for the package, i used android package I may change it to sql
	
	public Buys() {
		super();
	}
	public Buys(int id, int product, int shop, int user, double unit_price, int amount, String date, int group) {
		super();
		this.product = product;
		this.shop = shop;
		this.user = user;
		this.unitPrice = unit_price;
		this.amount = amount;
		this.date = date;
		this.id=id;
		this.group=group;
	}
	
	public Buys(int id, int product, int shop, double unitPrice, int amount, String date, int group) {
		super();
		this.id = id;
		this.product = product;
		this.shop = shop;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.date = date;
		this.group=group;
		user=-1;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public int getShop() {
		return shop;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public double getUnit_price() {
		return unitPrice;
	}
	public void setUnit_price(double unit_price) {
		this.unitPrice = unit_price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	
}
