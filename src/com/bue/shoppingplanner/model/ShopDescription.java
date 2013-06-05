package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	
	//Database Operations
	
	/*
	 * "CREATE TABLE "+ TABLE_SHOP_DESCRIPTION +"("+ SHOP_DESCRIPTION_ID +
	 * " INTEGER PRIMARY KEY AUTOINCREMENT ," + SHOP_DESCRIPTION_NAME+
	 * " TEXT NOT NULL)"
	 * 
	 * Shop Description
	 */
	
	/**
	 * Add new shop description. Returns the PK or -1 if error occurred.
	 * @param handler
	 * @return
	 */
	public int addShopDescription(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.SHOP_DESCRIPTION_NAME, getName());

		// Inserting Row
		db.insert(Dbh.TABLE_SHOP_DESCRIPTION, null, values);
		db.close(); // Closing database connection
		return this.getShopDescriptionId(handler);
	}

	public static ShopDescription getShopDescription(Dbh handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_SHOP_DESCRIPTION,
				new String[] { Dbh.SHOP_DESCRIPTION_NAME, }, Dbh.SHOP_DESCRIPTION_ID
						+ "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		ShopDescription shopDescription=new ShopDescription();
		if (cursor != null)
			if(cursor.moveToFirst()){
				shopDescription = new ShopDescription(id,
				cursor.getString(0));
			}
		cursor.close();
		db.close();

		return shopDescription;
	}

	// Getting All Shop Description
	public static List<ShopDescription> getAllShopDescription(Dbh handler) {
		List<ShopDescription> shopDescriptionList = new ArrayList<ShopDescription>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Dbh.TABLE_SHOP_DESCRIPTION;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopDescription shopDescription = new ShopDescription();
				shopDescription.setId(cursor.getInt(0));
				shopDescription.setName(cursor.getString(1));
				// Adding contact to list
				shopDescriptionList.add(shopDescription);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return shopDescriptionList;
	}

	// Updating single Shop Description
	public int updateShopDescription(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.SHOP_DESCRIPTION_NAME, getName());

		// updating row
		int updateMessage = db.update(Dbh.TABLE_SHOP_DESCRIPTION, values,
				Dbh.SHOP_DESCRIPTION_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Shop Description
	public void deleteShopDescription(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(Dbh.TABLE_SHOP_DESCRIPTION, Dbh.SHOP_DESCRIPTION_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Shop Description
	public static int getShopDescriptiontCount(Dbh handler) {
		String countQuery = "SELECT  * FROM " + Dbh.TABLE_SHOP_DESCRIPTION;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * Return PK of the ShopDescription.
	 * @param db
	 * @return
	 */
	public int getShopDescriptionId(Dbh db){
		int shopDescId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(Dbh.TABLE_SHOP_DESCRIPTION, new String[]{Dbh.SHOP_DESCRIPTION_ID,},Dbh.SHOP_DESCRIPTION_NAME+"=?",
				new String[] {String.valueOf(name)},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	shopDescId=cursor.getInt(0);
		
		cursor.close();
		readable.close();
		return shopDescId;
	}
	

}
