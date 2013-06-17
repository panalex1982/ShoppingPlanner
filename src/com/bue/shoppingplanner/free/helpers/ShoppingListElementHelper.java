package com.bue.shoppingplanner.free.helpers;

import java.io.Serializable;

public class ShoppingListElementHelper implements Serializable {
	private String product;
	private String brand;
	private String barcode;
	private double price;
	private int quantity;
	private String user;
	private String kind;
	private boolean isChecked;
	private String currency;
	private double vat;
	//private String listName;
	
	public ShoppingListElementHelper() {
		super();
		barcode="-1";
		//listName="-1";
	}
	
	public ShoppingListElementHelper(String product, String brand,
			double price, int quantity, String user, String kind, boolean isChecked,
			String currency, double vat) {
		super();
		this.product = product;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.user = user;
		this.kind = kind;
		this.isChecked=isChecked;
		barcode="-1";
		this.currency=currency;
		//listName="-1";
	}
	
	public ShoppingListElementHelper(String product, String brand, String barcode,
			double price, int quantity, String user, String kind, boolean isChecked, 
			String currency, double vat) {
		super();
		this.product = product;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.user = user;
		this.kind = kind;
		this.isChecked=isChecked;
		this.barcode=barcode;
		this.currency=currency;
		//listName="-1";
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
	
	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String encodeObject(){
		String encode=product+"\n\t\t"+brand+"\n\t\t"+price+"\n\t\t"+quantity+"\n\t\t"+user+"\n\t\t"+kind+"\n\t\t"+barcode+"\n\t\t"+Boolean.toString(isChecked)
				+"\n\t\t"+currency+"\n\t\t"+vat;//+listName;		
		return encode;
	}
	
	public void decode(String encoded){
		String regEx="\n\t\t";
		String[] splitted=encoded.split(regEx);
		product=splitted[0];
		brand=splitted[1];
		price=Double.parseDouble(splitted[2]);
		quantity=Integer.parseInt(splitted[3]);
		user=splitted[4];
		kind=splitted[5];
		barcode=splitted[6];
		isChecked=Boolean.parseBoolean(splitted[7]);
		currency=splitted[8];
		vat=Double.parseDouble(splitted[9]);
		//listName=splitted[8];
	}

	@Override
	public String toString() {
		return "ShoppingListElementHelper [product=" + product + ", brand="
				+ brand + ", barcode=" + barcode + ", price=" + price
				+ ", quantity=" + quantity + ", user=" + user + ", kind="
				+ kind + ", currency=" + currency + "]";
	}
	
	
	
	
}
