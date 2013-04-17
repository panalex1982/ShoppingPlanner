package com.bue.shoppingplanner.model;

public class ProductKind {
	private int id;
	private String name;
	//private int groupId;
	
	public ProductKind() {
		super();
	}

	public ProductKind(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		//this.groupId = group_id;
	}
	
	

	public ProductKind(String name) {
		super();
		this.name = name;
		//this.groupId = groupId;
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

	/*public int getGroup_id() {
		return groupId;
	}

	public void setGroup_id(int group_id) {
		this.groupId = group_id;
	}*/
	
	

}
