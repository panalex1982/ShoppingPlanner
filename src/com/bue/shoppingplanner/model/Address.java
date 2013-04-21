package com.bue.shoppingplanner.model;

public class Address {
	private int id;
	private String streetName, number, area, city, zip, country;
	public Address() {
		super();
	}
	public Address(int id, String streetName, String number, String area,
			String city, String zip, String country) {
		super();
		this.id = id;
		this.streetName = streetName;
		this.number = number;
		this.area = area;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}
	
	
	public Address(String streetName, String number, String area, String city,
			String zip, String country) {
		super();
		this.streetName = streetName;
		this.number = number;
		this.area = area;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
