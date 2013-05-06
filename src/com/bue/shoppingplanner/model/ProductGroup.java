package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *  @author Panagiotis Alexandropoulos
 *  ProductGroup categorizes ProductGroup in similar general groups
 *  eg. Food, drinks etc. can be first need products,
 *  Electricity, water, telephone etc. necessary payments
 *
 */
public class ProductGroup {
	private int id;
	private String name;
	public ProductGroup() {
		super();
	}
	public ProductGroup(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public ProductGroup(String name) {
		super();
		this.name = name;
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
	
	//Database Operetions
	/*
	 * Product Group "CREATE TABLE " +TABLE_PRODUCT_GROUP +"(" PRODUCT_GROUP_ID
	 * + " INTEGER PRIMARY KEY AUTOINCREMENT ," + PRODUCT_GROUP_NAME +
	 * " TEXT NOT NULL)";
	 */
	public void addProductGroup(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.PRODUCT_GROUP_NAME, getName());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_PRODUCT_GROUP, null, values);
		db.close(); // Closing database connection
	}

	public ProductGroup getProductGroup(DatabaseHandler handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_PRODUCT_GROUP,
				new String[] { DatabaseHandler.PRODUCT_GROUP_NAME, }, DatabaseHandler.PRODUCT_GROUP_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ProductGroup productGroup = new ProductGroup(id, cursor.getString(0));
		cursor.close();
		db.close();

		return productGroup;
	}

	// Getting All ProductGroup
	public static List<ProductGroup> getAllProductGroup(DatabaseHandler handler) {
		List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PRODUCT_GROUP;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ProductGroup productGroup = new ProductGroup();
				productGroup.setId(Integer.parseInt(cursor.getString(0)));
				productGroup.setName(cursor.getString(1));
				// Adding contact to list
				productGroupList.add(productGroup);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// return product group list
		return productGroupList;
	}

	// Updating single ProductGroup
	public int updateProductGroup(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.PRODUCT_GROUP_NAME, getName());

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_PRODUCT_GROUP, values,
				DatabaseHandler.PRODUCT_GROUP_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single ProductGroup
	public void deleteProductGroup(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_PRODUCT_GROUP, DatabaseHandler.PRODUCT_GROUP_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting ProductGroup
	public static int getProductGroupCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PRODUCT_GROUP;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * Return PK of the ProductGroup.
	 * @param db
	 * @return
	 */
	public int getProductGroupId(DatabaseHandler db){
		int productGroupId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(DatabaseHandler.TABLE_PRODUCT_GROUP, new String[]{DatabaseHandler.PRODUCT_GROUP_ID,},DatabaseHandler.PRODUCT_GROUP_NAME+"=?",
				new String[] {String.valueOf(name)},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	productGroupId=cursor.getInt(0);
		
		cursor.close();
		readable.close();
		return productGroupId;
	}
	
}
