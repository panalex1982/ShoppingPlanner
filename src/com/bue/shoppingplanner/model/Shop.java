package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Shop {
	private int id, shopDescription, address;
	private String name;
	public Shop() {
		super();
	}
	public Shop(int id, int shopDescription, int address, String name) {
		super();
		this.id = id;
		this.shopDescription = shopDescription;
		this.address = address;
		this.name = name;
	}
	
	public Shop(int shopDescription, int address, String name) {
		super();
		this.shopDescription = shopDescription;
		this.address = address;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShopDescription() {
		return shopDescription;
	}
	public void setShopDescription(int shopDescription) {
		this.shopDescription = shopDescription;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Database operations
	/*
	 * "CREATE TABLE "+ TABLE_SHOP +"("+ SHOP_ID +
	 * " INTEGER PRIMARY KEY AUTOINCREMENT ," + SHOP_NAME+ " TEXT NOT NULL," +
	 * SHOP_ADDRESS + " INTEGER NOT NULL," +
	 * SHOP_DESCRIPTION+" INTEGER NOT NULL," +
	 * "FOREIGN KEY("+SHOP_ADDRESS+") REFERENCES "+
	 * TABLE_ADDRESS+"("+ADDRESS_ID+"),"+
	 * "FOREIGN KEY("+SHOP_DESCRIPTION+") REFERENCES "+
	 * TABLE_SHOP_DESCRIPTION+"("+SHOP_DESCRIPTION_ID+")"+ ")";
	 * 
	 * Shop
	 */
	
	
	/**
	 * Add Shop from the shop object.
	 * @param handler
	 * @return
	 */
	
	public int addShop(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.SHOP_NAME, getName());
		values.put(Dbh.SHOP_ADDRESS, getAddress());
		values.put(Dbh.SHOP_DESCRIPTION, getShopDescription());

		// Inserting Row
		db.insert(Dbh.TABLE_SHOP, null, values);		
		db.close(); // Closing database connection
		return this.getShopId(handler);
	}
	
	public Shop getShop(Dbh handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_SHOP, new String[] { Dbh.SHOP_NAME,
				Dbh.SHOP_ADDRESS, Dbh.SHOP_DESCRIPTION, }, Dbh.SHOP_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Shop shop = new Shop(id, cursor.getInt(1), cursor.getInt(2),
				cursor.getString(0));
		cursor.close();
		db.close();

		return shop;
	}
	
	public void getShopByName(Dbh handler) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_SHOP, new String[] { Dbh.SHOP_ID,
				Dbh.SHOP_ADDRESS, Dbh.SHOP_DESCRIPTION, }, Dbh.SHOP_NAME + "=?",
				new String[] { String.valueOf(name) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		id=cursor.getInt(0);
		address=cursor.getInt(1);
		shopDescription=cursor.getInt(2);
		
		cursor.close();
		db.close();
	}

	// Getting All Shop
	public static List<Shop> getAllShop(Dbh handler) {
		List<Shop> shopList = new ArrayList<Shop>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Dbh.TABLE_SHOP;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Shop shop=new Shop();
				shop.setId(cursor.getInt(0));
				shop.setName(cursor.getString(1));
				shop.setAddress(cursor.getInt(2));
				shop.setShopDescription(cursor.getInt(3));
				// Adding contact to list
				shopList.add(shop);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return shopList;
	}

	// Updating single Shop
	public int updateShop(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.SHOP_NAME, getName());
		values.put(Dbh.SHOP_ADDRESS, getAddress());
		values.put(Dbh.SHOP_DESCRIPTION, getShopDescription());

		// updating row
		int updateMessage = db.update(Dbh.TABLE_SHOP, values, Dbh.SHOP_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Shop
	public void deleteShop(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(Dbh.TABLE_SHOP, Dbh.SHOP_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	/**
	 * Getting Shop count.
	 * @param handler
	 * @return
	 */
	public static int getShopCount(Dbh handler) {
		String countQuery = "SELECT  * FROM " + Dbh.TABLE_SHOP;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * Return PK of the Shop.
	 * @param db
	 * @return
	 */
	public int getShopId(Dbh db){
		int shopId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(Dbh.TABLE_SHOP, new String[]{Dbh.SHOP_ID,},Dbh.SHOP_NAME+"=?",
				new String[] {String.valueOf(name)},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	shopId=cursor.getInt(0);
		
		cursor.close();
		readable.close();
		return shopId;
	}
	
	/**
	 * Return the default id=1 when the shop is Unknown or Not Specified.
	 * @return
	 */
	public int getUnknownShopId(){
		return 1;
	}
	
	
}
