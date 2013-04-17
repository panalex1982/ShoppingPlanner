package com.bue.shoppingplanner.model;

/**
 *  @author Panagiotis
 *  ProductGroup categorizes ProductKind in similar general groups
 *  eg. Food, drinks etc. can be first need products,
 *  Electricity, water, telephone etc. necessary payments
 *
 */
public class ProductGroup {
	private int id;
	private String name;
	public ProductGroup() {
		super();
	}
	public ProductGroup(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
