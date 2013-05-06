package com.bue.shoppingplanner.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * UnknownBarcode Model
 * @author Panagiotis Alexandropoulos
 * CREATE_TABLE_UNKNOWN_BARCODE="CREATE TABLE " + TABLE_UNKNOWN_BARCODE + "(" + UNKNOWN_BARCODE_ID
 *	+ " INTEGER PRIMARY KEY AUTOINCREMENT" 
 *	+ UNKNOWN_BARCODE_VALUE+" INTEGER NOT NULL)";
 *
 */
public class UnknownBarcode {
	private int id;
	private int barcode;
	
	
	
	public UnknownBarcode() {
		super();
	}
	
	/**
	 * Creates UnkwnowBarcode object with id=1;
	 * @param handler
	 */
	public UnknownBarcode(DatabaseHandler handler){
		SQLiteDatabase db = handler.getReadableDatabase();
		id=1;
		
		Cursor cursor = db.query(DatabaseHandler.TABLE_UNKNOWN_BARCODE,
				new String[] { DatabaseHandler.UNKNOWN_BARCODE_VALUE, }, DatabaseHandler.UNKNOWN_BARCODE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			if(cursor.moveToFirst()){
				//If this does not exist, something was not initialized correctly
				barcode=cursor.getInt(0);
			}
		
		cursor.close();
		db.close();
	}

	public UnknownBarcode(int barcode) {
		super();
		this.barcode = barcode;
	}
	
	public UnknownBarcode(int id, int barcode) {
		super();
		this.id = id;
		this.barcode = barcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}
	
	public void addUnknownBarcode(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.UNKNOWN_BARCODE_VALUE, barcode);

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_UNKNOWN_BARCODE, null, values);
		db.close(); // Closing database connection
	}

	public UnknownBarcode getUnknownBarcode(DatabaseHandler handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_UNKNOWN_BARCODE,
				new String[] { DatabaseHandler.UNKNOWN_BARCODE_VALUE, }, DatabaseHandler.UNKNOWN_BARCODE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		UnknownBarcode productGroup = new UnknownBarcode(id, cursor.getInt(0));
		cursor.close();
		db.close();

		return productGroup;
	}
	
	// Updating single UnknownBarcode
		public int updateUnknownBarcode(DatabaseHandler handler) {
			SQLiteDatabase db = handler.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(DatabaseHandler.UNKNOWN_BARCODE_VALUE, (barcode-1));

			// updating row
			int updateMessage = db.update(DatabaseHandler.TABLE_UNKNOWN_BARCODE, values,
					DatabaseHandler.UNKNOWN_BARCODE_ID + " = ?",
					new String[] { String.valueOf(id) });
			db.close();
			return updateMessage;
		}
	

}
