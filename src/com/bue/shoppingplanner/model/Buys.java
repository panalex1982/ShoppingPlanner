package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Buys {
	private int id;
	private int product, shop, user;//User not used in first version
	private double unitPrice;
	private int amount;
	private int group;
	private String date; //Not sure for the package, i used android package I may change it to sql
	
	public Buys() {
		super();
	}
	public Buys(int id, int product, int shop, int user, double unit_price, int amount, String date, int group) {
		super();
		this.product = product;
		this.shop = shop;
		this.user = user;
		this.unitPrice = unit_price;
		this.amount = amount;
		this.date = date;
		this.id=id;
		this.group=group;
	}
	
	public Buys(int id, int product, int shop, double unitPrice, int amount, String date, int group) {
		super();
		this.id = id;
		this.product = product;
		this.shop = shop;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.date = date;
		this.group=group;
		user=-1;
	}
	
	
	
	public Buys(int product, int shop, double unitPrice, int amount, int group,
			String date) {
		super();
		this.product = product;
		this.shop = shop;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.group = group;
		this.date = date;
		user=-1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public int getShop() {
		return shop;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public double getUnit_price() {
		return unitPrice;
	}
	public void setUnit_price(double unit_price) {
		this.unitPrice = unit_price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	/*
	 * "CREATE TABLE "+ TABLE_BUYS +"("+ BUYS_ID+
	 * " INTEGER PRIMARY KEY AUTOINCREMENT ," + BUYS_PRODUCT +
	 * " INTEGER NOT NULL," + BUYS_SHOP + " INTEGER NOT NULL," + BUYS_UNIT_PRICE
	 * + " REAL NOT NULL," + BUYS_AMOUNT + " INTEGER NOT NULL," + BUYS_DATE +
	 * " TEXT NOT NULL," 
	 * + BUYS_PRODUCT_GROUP_ID + " INTEGER NOT NULL,"
	 * + "FOREIGN KEY("+BUYS_PRODUCT+") REFERENCES "+
	 * TABLE_PRODUCT+"("+PRODUCT_ID+"),"+
	 * "FOREIGN KEY("+BUYS_SHOP+") REFERENCES "+ TABLE_SHOP+"("+SHOP_ID+")"+
	 * ")";
	 * 
	 * Buys
	 */

	public int addBuys(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BUYS_PRODUCT, getProduct());
		values.put(DatabaseHandler.BUYS_SHOP, getShop());
		values.put(DatabaseHandler.BUYS_UNIT_PRICE, getUnit_price());
		values.put(DatabaseHandler.BUYS_AMOUNT, getAmount());
		values.put(DatabaseHandler.BUYS_DATE, getDate());
		values.put(DatabaseHandler.BUYS_PRODUCT_GROUP_ID, getGroup());

		// Inserting Row
		int tmp=(int) db.insert(DatabaseHandler.TABLE_BUYS, null, values);
		db.close(); // Closing database connection
		return tmp;
	}

	public Buys getBuys(DatabaseHandler handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_BUYS, new String[] { DatabaseHandler.BUYS_PRODUCT,
				DatabaseHandler.BUYS_SHOP, DatabaseHandler.BUYS_UNIT_PRICE, DatabaseHandler.BUYS_AMOUNT, 
				DatabaseHandler.BUYS_DATE, DatabaseHandler.BUYS_PRODUCT_GROUP_ID, }, DatabaseHandler.BUYS_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Buys buys = new Buys(id, cursor.getInt(0), cursor.getInt(1),
				cursor.getDouble(2), cursor.getInt(3), cursor.getString(4),
				cursor.getInt(5));
		cursor.close();
		db.close();

		return buys;
	}

	// Getting All Buys
	public static List<Buys> getAllBuys(DatabaseHandler handler) {
		List<Buys> buysList = new ArrayList<Buys>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_BUYS;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Buys buys = new Buys();
				buys.setId(cursor.getInt(0));
				buys.setProduct(cursor.getInt(1));
				buys.setShop(cursor.getInt(2));
				buys.setUnit_price(cursor.getDouble(3));
				buys.setAmount(cursor.getInt(4));
				buys.setDate(cursor.getString(5));
				buys.setGroup(cursor.getInt(6));

				// Adding contact to list
				buysList.add(buys);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return buysList;
	}

	// Updating single Buys
	public int updateBuys(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BUYS_PRODUCT, getProduct());
		values.put(DatabaseHandler.BUYS_SHOP, getShop());
		values.put(DatabaseHandler.BUYS_UNIT_PRICE, getUnit_price());
		values.put(DatabaseHandler.BUYS_AMOUNT, getAmount());
		values.put(DatabaseHandler.BUYS_DATE, getDate());
		values.put(DatabaseHandler.BUYS_PRODUCT_GROUP_ID, getGroup());

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_BUYS, values, DatabaseHandler.BUYS_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Buys
	public void deleteBuys(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_BUYS, DatabaseHandler.BUYS_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Buys count
	public static int getBuysCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	public static double getTotalSpending(DatabaseHandler handler){
		double spending=0.0;
		String query="SELECT sum("+DatabaseHandler.BUYS_UNIT_PRICE+"*"+DatabaseHandler.BUYS_AMOUNT+") FROM "+DatabaseHandler.TABLE_BUYS;//
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			if(cursor.moveToFirst())
				spending = cursor.getDouble(0);
		cursor.close();
		db.close();
		return spending;
	}
	
	
}
