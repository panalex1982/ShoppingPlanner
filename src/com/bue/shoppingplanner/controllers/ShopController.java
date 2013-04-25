package com.bue.shoppingplanner.controllers;

import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;

public class ShopController {
	ShopElementHelper element;

	public ShopController(ShopElementHelper element) {
		super();
		this.element = element;
	}

	public ShopController() {
		super();
		this.element=new ShopElementHelper();
	}

	public ShopElementHelper getElement() {
		return element;
	}
	
	public void selectShopElementHelperByShopName(DatabaseHandler db, String shopName) {
		Shop shopModel=new Shop();
		shopModel.setName(shopName);
		shopModel.getShopByName(db);
		Address address=new Address();
		address=address.getAddress(db, shopModel.getAddress());
		ShopDescription shopDesc=new ShopDescription();
		shopDesc=shopDesc.getShopDescription(db, shopModel.getShopDescription());
		element=new ShopElementHelper(shopModel, address, shopDesc);
	}

	public void setElement(ShopElementHelper element) {
		this.element = element;
	}
	
	public String getName() {
		return element.getName();
	}

	public void setName(String name) {
		element.setName(name);
	}

	public String getAddress() {
		return element.getAddress();
	}

	public void setAddress(String address) {
		element.setAddress(address);
	}

	public String getNumber() {
		return element.getNumber();
	}

	public void setNumber(String number) {
		element.setNumber(number);
	}

	public String getCity() {
		return element.getCity();
	}

	public void setCity(String city) {
		element.setCity(city);
	}

	public String getArea() {
		return element.getArea();
	}

	public void setArea(String area) {
		element.setArea(area);
	}

	public String getCountry() {
		return element.getCountry();
	}

	public void setCountry(String country) {
		element.setCountry(country);
	}

	public String getZip() {
		return element.getZip();
	}

	public void setZip(String zip) {
		element.setZip(zip);
	}

	public String getType() {
		return element.getType();
	}

	public void setType(String type) {
		element.setType(type);
	}
	
	

}
