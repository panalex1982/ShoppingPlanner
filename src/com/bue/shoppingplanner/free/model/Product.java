package com.bue.shoppingplanner.free.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * General Product, does not include the firm
 * 
 * @author Panagiotis
 * 
 */

public class Product {
	private int id;
	private String name;
	private String barcode;// Can ignored
	private int kind;

	public Product() {
		super();
	}

	public Product(String name, String barcode, int kind) {
		super();
		this.name = name;
		this.barcode = barcode;
		this.kind = kind;

	}

	public Product(int id, String name, String barcode, int kind) {
		super();
		this.id = id;
		this.name = name;
		this.barcode = barcode;
		this.kind = kind;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	// Database operations
	/*
	 * "CREATE TABLE " + TABLE_PRODUCT +"("+PRODUCT_ID +
	 * " INTEGER PRIMARY KEY AUTOINCREMENT ," +PRODUCT_NAME + " TEXT NOT NULL,"
	 * +PRODUCT_BARCODE + " TEXT NOT NULL," +PRODUCT_KIND+ " INTEGER NOT NULL,"+
	 * PRODUCT_GROUP_ID_FK+ " INTEGER NOT NULL,"+
	 * "FOREIGN KEY("+PRODUCT_BARCODE+") REFERENCES "+
	 * TABLE_COMMERCIAL_PRODUCT+"("+COMMERCIAL_PRODUCT_BARCODE+"),"+
	 * "FOREIGN KEY("+PRODUCT_KIND+") REFERENCES "+
	 * TABLE_PRODUCT_KIND+"("+PRODUCT_KIND_ID+")"+
	 * "FOREIGN KEY("+PRODUCT_GROUP_ID_FK+") REFERENCES "+
	 * TABLE_PRODUCT_GROUP+"("+PRODUCT_GROUP_ID+")"+")";Product
	 */
	public int addProduct(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.PRODUCT_NAME, getName());
		values.put(Dbh.PRODUCT_BARCODE, getBarcode());
		values.put(Dbh.PRODUCT_KIND, getKind());

		// Inserting Row
		db.insert(Dbh.TABLE_PRODUCT, null, values);
		db.close(); // Closing database connection
		return getProductId(handler);
	}

	public static Product getProduct(Dbh handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_PRODUCT, new String[] {
				Dbh.PRODUCT_NAME, Dbh.PRODUCT_BARCODE, Dbh.PRODUCT_KIND, },
				Dbh.PRODUCT_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		Product product = new Product();
		if (cursor != null)
			if (cursor.moveToFirst()) {
				product = new Product(id, cursor.getString(0),
						cursor.getString(1), cursor.getInt(2));
			}
		cursor.close();
		db.close();

		return product;
	}

	public static Product getProduct(Dbh handler, String productName) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_PRODUCT, new String[] {
				Dbh.PRODUCT_ID, Dbh.PRODUCT_BARCODE, Dbh.PRODUCT_KIND, },
				Dbh.PRODUCT_NAME + "=?", new String[] { productName }, null,
				null, null, null);
		Product product = new Product();
		if (cursor != null)
			if (cursor.moveToFirst()) {
				product = new Product(cursor.getInt(0), productName,
						cursor.getString(1), cursor.getInt(2));
			}
		cursor.close();
		db.close();

		return product;
	}

	// Getting All Product
	public static List<Product> getAllProduct(Dbh handler) {
		List<Product> productList = new ArrayList<Product>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Dbh.TABLE_PRODUCT;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Product product = new Product();
				product.setId(cursor.getInt(0));
				product.setName(cursor.getString(1));
				product.setBarcode(cursor.getString(2));
				product.setKind(cursor.getInt(3));

				// Adding contact to list
				productList.add(product);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return productList;
	}

	//This function must moved to Commercial Product
	/**
	 * Returns all products combined with Commercial product name.
	 * @param handler
	 * @param kind
	 * @return
	 */
	public static List<String> getAllProductNamesOfKind(Dbh handler, String kind) {
		List<String> productList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  " +Dbh.TABLE_PRODUCT+"."
				+ Dbh.PRODUCT_NAME +", "+Dbh.TABLE_COMMERCIAL_PRODUCT+"." +Dbh.COMMERCIAL_PRODUCT_COMPANY_BRAND+" FROM "
				+ Dbh.TABLE_PRODUCT +" "+ Dbh.JOIN_PRODUCT_KIND
				+" "+ Dbh.JOIN_COMMERCIALPRODUCT
				+" WHERE "+Dbh.TABLE_PRODUCT_KIND+"."
				+ Dbh.PRODUCT_KIND_NAME + " = \"" + kind+"\"";

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		while (cursor.moveToNext()) {
			// Adding product name to list
			productList.add(cursor.getString(0)+": "+cursor.getString(1));
		}
		cursor.close();
		db.close();
		// return contact list
		return productList;
	}

	// Updating single Product
	public int updateProduct(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.PRODUCT_NAME, getName());
		values.put(Dbh.PRODUCT_BARCODE, getBarcode());
		values.put(Dbh.PRODUCT_KIND, getKind());

		// updating row
		int updateMessage = db.update(Dbh.TABLE_PRODUCT, values, Dbh.PRODUCT_ID
				+ " = ?", new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Product
	public void deleteProduct(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(Dbh.TABLE_PRODUCT, Dbh.PRODUCT_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Product
	public static int getProductCount(Dbh handler) {
		String countQuery = "SELECT  * FROM " + Dbh.TABLE_PRODUCT;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}

	/**
	 * Return PK of the Product.
	 * 
	 * @param db
	 * @return
	 */
	public int getProductId(Dbh db) {
		int productId = -1;
		SQLiteDatabase readable = db.getReadableDatabase();
		Cursor cursor = readable.query(Dbh.TABLE_PRODUCT,
				new String[] { Dbh.PRODUCT_ID, }, Dbh.PRODUCT_NAME + "=? AND "
						+ Dbh.PRODUCT_BARCODE + "=? AND " + Dbh.PRODUCT_KIND
						+ "=?", new String[] { String.valueOf(name), barcode,
						String.valueOf(kind) }, null, null, null, null);
		if (cursor != null)
			if (cursor.moveToFirst())
				productId = cursor.getInt(0);
		cursor.close();
		readable.close();
		return productId;
	}

	public static Product getProductFromBarcode(Dbh handler, String barcode) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_PRODUCT, new String[] {
				Dbh.PRODUCT_ID, Dbh.PRODUCT_NAME, Dbh.PRODUCT_KIND, },
				Dbh.PRODUCT_BARCODE + "=?", new String[] { barcode }, null,
				null, null, null);
		Product product = new Product();
		if (cursor != null)
			if (cursor.moveToFirst()) {
				product = new Product(cursor.getInt(0), cursor.getString(1),
						barcode, cursor.getInt(2));
			}
		cursor.close();
		db.close();

		return product;
	}
}
