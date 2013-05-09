package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * General Product, does not include the firm
 * @author Panagiotis
 *
 */
		
public class Product {
	private int id;
	private String name;
	private String barcode;//Can ignored
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
	
	//Database operations
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
	public int addProduct(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.PRODUCT_NAME, getName());
		values.put(DatabaseHandler.PRODUCT_BARCODE, getBarcode());
		values.put(DatabaseHandler.PRODUCT_KIND, getKind());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_PRODUCT, null, values);
		db.close(); // Closing database connection
		return getProductId(handler);
	}

	public static Product getProduct(DatabaseHandler handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_PRODUCT, new String[] { DatabaseHandler.PRODUCT_NAME,
				DatabaseHandler.PRODUCT_BARCODE, DatabaseHandler.PRODUCT_KIND, }, DatabaseHandler.PRODUCT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		Product product=new Product();
		if (cursor != null)
			if(cursor.moveToFirst()){
		 product = new Product(id, cursor.getString(0),
				cursor.getString(1), cursor.getInt(2));
			}
		cursor.close();
		db.close();

		return product;
	}

	// Getting All Product
	public static List<Product> getAllProduct(DatabaseHandler handler) {
		List<Product> productList = new ArrayList<Product>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PRODUCT;

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

	// Updating single Product
	public int updateProduct(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.PRODUCT_NAME, getName());
		values.put(DatabaseHandler.PRODUCT_BARCODE, getBarcode());
		values.put(DatabaseHandler.PRODUCT_KIND, getKind());

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_PRODUCT, values, DatabaseHandler.PRODUCT_ID
				+ " = ?", new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Product
	public void deleteProduct(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_PRODUCT, DatabaseHandler.PRODUCT_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Product
	public static int getProductCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PRODUCT;
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
	 * @param db
	 * @return
	 */
	public int getProductId(DatabaseHandler db){
		int productId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(DatabaseHandler.TABLE_PRODUCT, new String[]{DatabaseHandler.PRODUCT_ID,},DatabaseHandler.PRODUCT_NAME+"=? AND "
									+DatabaseHandler.PRODUCT_BARCODE+"=? AND "+DatabaseHandler.PRODUCT_KIND+"=?",
				new String[] {String.valueOf(name),barcode,String.valueOf(kind)},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	productId=cursor.getInt(0);
		cursor.close();
		readable.close();
		return productId;
	}
}
