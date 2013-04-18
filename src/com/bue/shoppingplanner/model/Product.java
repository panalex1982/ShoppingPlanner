package com.bue.shoppingplanner.model;

/**
 * General Product, does not include the firm
 * @author Panagiotis
 *
 */
		
public class Product {
	private int id;
	private String name;
	private String barcode;//Can ignored
	private int kind;
	private int group;
	public Product() {
		super();
	}
	
	public Product(String name, String barcode, int kind, int group) {
		super();
		this.name = name;
		this.barcode = barcode;
		this.kind = kind;
		this.group=group;
	}

	public Product(int id, String name, String barcode, int kind, int group) {
		super();
		this.id = id;
		this.name = name;
		this.barcode = barcode;
		this.kind = kind;
		this.group=group;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	
	
	
	
	
}
