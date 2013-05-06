package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Commercial firm and name of the product.
 * @author Panagiotis Alexandropoulos
 *
 */
public class CommercialProduct {
	private String barcode;
	private String commercialName,//this field is for future use, 
								  //if i distinguish between product name 
								  //and commercial product name
					companyBrand;
	public CommercialProduct() {
		super();
	}
	public CommercialProduct(String barcode, String commercialName,
			String companyBrand) {
		super();
		this.barcode = barcode;
		this.commercialName = commercialName;
		this.companyBrand = companyBrand;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getCommercialName() {
		return commercialName;
	}
	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	public String getCompanyBrand() {
		return companyBrand;
	}
	public void setCompanyBrand(String companyBrand) {
		this.companyBrand = companyBrand;
	}
	
	/*
	 * "CREATE TABLE " + TABLE_COMMERCIAL_PRODUCT+"("+
	 * COMMERCIAL_PRODUCT_BARCODE+ " INTEGER PRIMARY KEY,"+
	 * COMMERCIAL_PRODUCT_NAME+ " TEXT NOT NULL,"+
	 * COMMERCIAL_PRODUCT_COMPANY_BRAND+" TEXT)";
	 * 
	 * Commercial Product
	 */
	public String addCommercialProduct(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.COMMERCIAL_PRODUCT_BARCODE, getBarcode());
		values.put(DatabaseHandler.COMMERCIAL_PRODUCT_NAME,
				getCommercialName());
		values.put(DatabaseHandler.COMMERCIAL_PRODUCT_COMPANY_BRAND,
				getCompanyBrand());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_COMMERCIAL_PRODUCT, null, values);
		db.close(); // Closing database connection
		return getCommercialProductId(handler);
	}

	public CommercialProduct getCommercialProduct(DatabaseHandler handler, String barcode) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_COMMERCIAL_PRODUCT, new String[] {
				DatabaseHandler.COMMERCIAL_PRODUCT_NAME, DatabaseHandler.COMMERCIAL_PRODUCT_COMPANY_BRAND, },
				DatabaseHandler.COMMERCIAL_PRODUCT_BARCODE + "=?",
				new String[] { String.valueOf(barcode) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		CommercialProduct commercialProduct = new CommercialProduct(barcode,
				cursor.getString(0), cursor.getString(1));
		cursor.close();
		db.close();

		return commercialProduct;
	}

	// Getting All Commercial Product
	public static List<CommercialProduct> getAllCommercialProduct(DatabaseHandler handler) {
		List<CommercialProduct> commercialProductList = new ArrayList<CommercialProduct>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_COMMERCIAL_PRODUCT;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				CommercialProduct commercialProduct = new CommercialProduct();
				commercialProduct.setBarcode(cursor.getString(0));
				commercialProduct.setCommercialName(cursor.getString(1));
				commercialProduct.setCompanyBrand(cursor.getString(2));
				// Adding contact to list
				commercialProductList.add(commercialProduct);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return commercialProductList;
	}

	// Updating single Commercial Product
	public int updateCommercialProduct(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.COMMERCIAL_PRODUCT_NAME,
				getCommercialName());
		values.put(DatabaseHandler.COMMERCIAL_PRODUCT_COMPANY_BRAND,
				getCompanyBrand());

		// updating row
		int updateMessage = db
				.update(DatabaseHandler.TABLE_COMMERCIAL_PRODUCT, values,
						DatabaseHandler.COMMERCIAL_PRODUCT_BARCODE + " = ?",
						new String[] { String.valueOf(getBarcode()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Commercial Product
	public void deleteCommercialProduct(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_COMMERCIAL_PRODUCT,
				DatabaseHandler.COMMERCIAL_PRODUCT_BARCODE + " = ?",
				new String[] { String.valueOf(getBarcode()) });
		db.close();
	}

	// Getting Commercial Product
	public static int getCommercialProductCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_COMMERCIAL_PRODUCT;
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
	public String getCommercialProductId(DatabaseHandler db){
		String commercialProductId="-1";
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(DatabaseHandler.TABLE_COMMERCIAL_PRODUCT, new String[]{DatabaseHandler.COMMERCIAL_PRODUCT_BARCODE,},DatabaseHandler.COMMERCIAL_PRODUCT_NAME+"=? AND "
									+DatabaseHandler.COMMERCIAL_PRODUCT_COMPANY_BRAND+"=?",
				new String[] {commercialName,companyBrand},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	commercialProductId=cursor.getString(0);
		cursor.close();
		readable.close();
		return commercialProductId;
	}
	
}
