package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	
	//Database Operations
	
	/*
	 * "CREATE TABLE "+ TABLE_ADDRESS +"("+ADDRESS_ID+
	 * " INTEGER PRIMARY KEY AUTOINCREMENT ," +ADDRESS_STREET_NAME+
	 * " TEXT NOT NULL," +ADDRESS_NUMBER+ " TEXT,"+ADDRESS_AREA+ " TEXT,"+
	 * ADDRESS_CITY+ " TEXT,"+ADDRESS_COUNTY+ " TEXT,"+ADDRESS_COUNTRY+
	 * " TEXT)";
	 * 
	 * Address
	 */
	
	/**
	 * Add new address and return the PK of the record, if no record created -1 return.
	 * @param handler
	 * @return
	 */
	public int addAddress(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ADDRESS_STREET_NAME, getStreetName());
		values.put(DatabaseHandler.ADDRESS_NUMBER, getNumber());
		values.put(DatabaseHandler.ADDRESS_AREA, getArea());
		values.put(DatabaseHandler.ADDRESS_CITY, getCity());
		values.put(DatabaseHandler.ADDRESS_ZIP, getZip());
		values.put(DatabaseHandler.ADDRESS_COUNTRY, getCountry());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_ADDRESS, null, values);
		db.close(); // Closing database connection
		return getAddressId(handler);
	}

	public Address getAddress(DatabaseHandler handler,int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_ADDRESS, new String[] {
				DatabaseHandler.ADDRESS_STREET_NAME, DatabaseHandler.ADDRESS_NUMBER, DatabaseHandler.ADDRESS_AREA,
				DatabaseHandler.ADDRESS_CITY, DatabaseHandler.ADDRESS_ZIP, DatabaseHandler.ADDRESS_COUNTRY, },
				DatabaseHandler.ADDRESS_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		Address address = new Address(id, cursor.getString(0),
				cursor.getString(1), cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4));
		cursor.close();
		db.close();

		return address;
	}

	// Getting All Address
	public static List<Address> getAllAddress(DatabaseHandler handler) {
		List<Address> addressList = new ArrayList<Address>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ADDRESS;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Address address = new Address();
				address.setId(cursor.getInt(0));
				address.setStreetName(cursor.getString(1));
				address.setNumber(cursor.getString(2));
				address.setArea(cursor.getString(3));
				address.setCity(cursor.getString(4));
				address.setZip(cursor.getString(5));
				address.setCountry(cursor.getString(6));
				// Adding contact to list
				addressList.add(address);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return addressList;
	}

	// Updating single Address
	public int updateAddress(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ADDRESS_STREET_NAME, getStreetName());
		values.put(DatabaseHandler.ADDRESS_NUMBER, getNumber());
		values.put(DatabaseHandler.ADDRESS_AREA, getArea());
		values.put(DatabaseHandler.ADDRESS_CITY, getCity());
		values.put(DatabaseHandler.ADDRESS_ZIP, getZip());
		values.put(DatabaseHandler.ADDRESS_COUNTRY, getCountry());

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_ADDRESS, values, DatabaseHandler.ADDRESS_ID
				+ " = ?", new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Address
	public void deleteCommercialAddress(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_ADDRESS, DatabaseHandler.ADDRESS_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Address
	public static int getAddressCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_ADDRESS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}

	
	/**
	 * Return PK of the Address.
	 * @param db
	 * @return
	 */
	public int getAddressId(DatabaseHandler db){
		int addressId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(DatabaseHandler.TABLE_ADDRESS, new String[]{DatabaseHandler.ADDRESS_ID,},DatabaseHandler.ADDRESS_STREET_NAME+"=? AND "
									+DatabaseHandler.ADDRESS_NUMBER+"=? AND "+DatabaseHandler.ADDRESS_CITY+"=? AND "+DatabaseHandler.ADDRESS_AREA+"=? AND "
				+DatabaseHandler.ADDRESS_COUNTRY+"=? AND "+DatabaseHandler.ADDRESS_ZIP+"=?",
				new String[] {String.valueOf(getStreetName()),String.valueOf(getNumber()),getCity(),getArea(),getCountry(),getZip()},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	addressId=cursor.getInt(0);
		cursor.close();
		readable.close();
		return addressId;
	}
	
	/**
	 * Return the default id=1 when the address is Unknown or Not Specified.
	 * @return
	 */
	public int getUnknownAddressId(){
		return 1;
	}
	
}
