package com.bue.shoppingplanner.free.helpers;

import java.io.Serializable;

import com.bue.shoppingplanner.free.model.Address;
import com.bue.shoppingplanner.free.model.Shop;
import com.bue.shoppingplanner.free.model.ShopDescription;

public class ShopElementHelper implements Serializable{
	private String name,
					address,
					number,
					city,
					area,
					country,
					zip,
					type;

	public ShopElementHelper(String name, String address, String number,
			String city, String area, String country, String zip, String type) {
		super();
		this.name = name;
		this.address = address;
		this.number = number;
		this.city = city;
		this.area = area;
		this.country = country;
		this.zip = zip;
		this.type = type;
	}
	
	public ShopElementHelper(Shop shop, Address address, ShopDescription sDesc){
		name=shop.getName();
		this.address = address.getStreetName();
		this.number = address.getNumber();
		this.city = address.getCity();
		this.area = address.getArea();
		this.country = address.getCountry();
		this.zip = address.getZip();
		this.type = sDesc.getName();
	}

	public ShopElementHelper() {
		super();
		name=null;
		address=null;
		number=null;
		city=null;
		area=null;
		country=null;
		zip=null;
		type=null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String encodeObject(){
		return name+"\n\t\t"+address+"\n\t\t"+number+"\n\t\t"+
				city+"\n\t\t"+area+"\n\t\t"+country+"\n\t\t"+
				zip+"\n\t\t"+type;
	}
	
	public void decode(String encoded){
		String regEx="\n\t\t";
		String[] splitted=encoded.split(regEx);
		name=splitted[0];
		address=splitted[1];
		number=splitted[2];
		city=splitted[3];
		area=splitted[4];
		country=splitted[5];
		zip=splitted[6];
		type=splitted[7];
	}

	@Override
	public String toString() {
		return "ShopElementHelper [name=" + name + ", address=" + address
				+ ", number=" + number + ", city=" + city + ", area=" + area
				+ ", country=" + country + ", zip=" + zip + ", type=" + type
				+ "]";
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public boolean isNull(){
		return (name==null && address==null && number==null && 
				city==null && area==null && country==null 
				&& zip==null && type==null);
	}

	public boolean hasName(){
		return name!=null;
	}
	
	
	
}
