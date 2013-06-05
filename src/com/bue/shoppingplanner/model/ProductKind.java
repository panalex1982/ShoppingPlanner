package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProductKind {
	private int id;
	private String name;
	//private int groupId;
	
	public ProductKind() {
		super();
	}

	public ProductKind(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		//this.groupId = group_id;
	}
	
	

	public ProductKind(String name) {
		super();
		this.name = name;
		//this.groupId = groupId;
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

	// Product Kind
		public void addProductKind(Dbh handler) {
			SQLiteDatabase db = handler.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(Dbh.PRODUCT_KIND_NAME, getName()); // ProductKind
																	// Name
			// values.put(PRODUCT_KIND_GROUP_ID, productKind.getGroup_id());

			// Inserting Row
			db.insert(Dbh.TABLE_PRODUCT_KIND, null, values);
			db.close(); // Closing database connection
		}

		public static ProductKind getProductKind(Dbh handler,int id) {
			SQLiteDatabase db = handler.getReadableDatabase();

			Cursor cursor = db.query(Dbh.TABLE_PRODUCT_KIND,
					new String[] { Dbh.PRODUCT_KIND_NAME, }, Dbh.PRODUCT_KIND_ID + "=?",
					new String[] { String.valueOf(id) }, null, null, null, null);
			ProductKind productKind = new ProductKind();
			if (cursor != null)
				if(cursor.moveToFirst()){
					productKind = new ProductKind(id, cursor.getString(0));
				}
			
			cursor.close();
			db.close();

			return productKind;
		}

		// Getting All ProductKind
		public static List<ProductKind> getAllProductKind(Dbh handler) {
			List<ProductKind> productKindList = new ArrayList<ProductKind>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + Dbh.TABLE_PRODUCT_KIND;

			SQLiteDatabase db = handler.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					ProductKind productKind = new ProductKind();
					productKind.setId(Integer.parseInt(cursor.getString(0)));
					productKind.setName(cursor.getString(1));
					// productKind.setGroup_id(Integer.parseInt(cursor.getString(2)));
					// Adding product kind to list
					productKindList.add(productKind);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();

			// return product kind list
			return productKindList;
		}

		// Updating single ProductKind
		public int updateProductKind(Dbh handler) {
			SQLiteDatabase db = handler.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(Dbh.PRODUCT_KIND_NAME, getName());
			// values.put(PRODUCT_KIND_GROUP_ID, productKind.getGroup_id());

			// updating row
			int updateMessage = db.update(Dbh.TABLE_PRODUCT_KIND, values,
					Dbh.PRODUCT_KIND_ID + " = ?",
					new String[] { String.valueOf(getId()) });
			db.close();
			return updateMessage;
		}

		// Deleting single ProductKind
		public void deleteProductKind(Dbh handler) {
			SQLiteDatabase db = handler.getWritableDatabase();
			db.delete(Dbh.TABLE_PRODUCT_KIND, Dbh.PRODUCT_KIND_ID + " = ?",
					new String[] { String.valueOf(getId()) });
			db.close();
		}

		// Getting ProductKind
		public static int getProductKindCount(Dbh handler) {
			String countQuery = "SELECT  * FROM " + Dbh.TABLE_PRODUCT_KIND;
			SQLiteDatabase db = handler.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);

			// return count
			int count = cursor.getCount();
			cursor.close();
			db.close();
			return count;
		}
		
		/**
		 * Return PK of the ProductKind.
		 * @param db
		 * @return
		 */
		public int getProductKindId(Dbh db){
			int productKindId=-1;
			SQLiteDatabase readable=db.getReadableDatabase();
			Cursor cursor =readable.query(Dbh.TABLE_PRODUCT_KIND, new String[]{Dbh.PRODUCT_KIND_ID,},Dbh.PRODUCT_KIND_NAME+"=?",
					new String[] {String.valueOf(name)},null, null, null, null);
			if (cursor != null)
	            if(cursor.moveToFirst())
	            	productKindId=cursor.getInt(0);
			
			cursor.close();
			readable.close();
			return productKindId;
		}
	
	

}
