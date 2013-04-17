package com.bue.shoppingplanner.model;

public class Address {
	private int id;
	private String streetName, number, area, city, county, country;
	public Address() {
		super();
	}
	public Address(int id, String streetName, String number, String area,
			String city, String county, String country) {
		super();
		this.id = id;
		this.streetName = streetName;
		this.number = number;
		this.area = area;
		this.city = city;
		this.county = county;
		this.country = country;
	}
	
	
	public Address(String streetName, String number, String area, String city,
			String county, String country) {
		super();
		this.streetName = streetName;
		this.number = number;
		this.area = area;
		this.city = city;
		this.county = county;
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
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
