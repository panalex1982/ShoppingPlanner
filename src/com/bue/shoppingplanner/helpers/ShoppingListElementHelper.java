package com.bue.shoppingplanner.helpers;

import java.io.Serializable;

public class ShoppingListElementHelper implements Serializable {
	private String product;
	private String brand;
	private String barcode;
	private double price;
	private int quantity;
	private String group;
	private String kind;
	private boolean isChecked;
	private String listName;
	
	public ShoppingListElementHelper() {
		super();
		barcode="-1";
		listName="-1";
	}
	
	public ShoppingListElementHelper(String product, String brand,
			double price, int quantity, String group, String kind, boolean isChecked) {
		super();
		this.product = product;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.group = group;
		this.kind = kind;
		this.isChecked=isChecked;
		barcode="-1";
		listName="-1";
	}
	
	public ShoppingListElementHelper(String product, String brand, String barcode,
			double price, int quantity, String group, String kind, boolean isChecked) {
		super();
		this.product = product;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.group = group;
		this.kind = kind;
		this.isChecked=isChecked;
		this.barcode=barcode;
		listName="-1";
	}
	

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
	

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String encodeObject(){
		String encode=product+"\n\t\t"+brand+"\n\t\t"+price+"\n\t\t"+quantity+"\n\t\t"+group+"\n\t\t"+kind+"\n\t\t"+barcode+"\n\t\t"+Boolean.toString(isChecked)+listName;		
		return encode;
	}
	
	public void decode(String encoded){
		String regEx="\n\t\t";
		String[] splitted=encoded.split(regEx);
		product=splitted[0];
		brand=splitted[1];
		price=Double.parseDouble(splitted[2]);
		quantity=Integer.parseInt(splitted[3]);
		group=splitted[4];
		kind=splitted[5];
		barcode=splitted[6];
		isChecked=Boolean.parseBoolean(splitted[7]);
		listName=splitted[8];
	}

	@Override
	public String toString() {
		return "ShoppingListElementHelper [product=" + product + ", brand="
				+ brand + ", barcode=" + barcode + ", price=" + price
				+ ", quantity=" + quantity + ", group=" + group + ", kind="
				+ kind + ", isChecked=" + isChecked + "]";
	}
	
	
	
	
}
