package com.bue.shoppingplanner.model;

/**
 * Description examples: Supermarket, mini market, specialized shop...
 * @author Panagiotis
 *
 */
public class ShopDescription {
	private int id;
	private String name;
	public ShopDescription(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ShopDescription(String name) {
		super();
		this.name = name;
	}

	public ShopDescription() {
		super();
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
