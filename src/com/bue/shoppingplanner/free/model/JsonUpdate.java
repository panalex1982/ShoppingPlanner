package com.bue.shoppingplanner.free.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Json Update Model
 * "CREATE TABLE " + TABLE_JSON_UPDATE+ "("
 * + JSON_UPDATE_ID+" INTEGER PRIMARY_KEY, "
 * + JSON_UPDATE_DATE+"DATE NOT NULL)"
 * @author Panagiotis Alexandropoulos
 *
 */
public class JsonUpdate {
	private int id;
	private String date;
	
	public JsonUpdate() {
		super();
		id=1;
		SimpleDateFormat dateFormater = new SimpleDateFormat("ddMMyyyyhhmmss");		
		date=dateFormater.format(new Date());
	}
	
	public JsonUpdate(int id, String date) {
		super();
		this.id = id;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void addJsonUpdate(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.JSON_UPDATE_ID, id);
		values.put(Dbh.JSON_UPDATE_DATE, date);

		// Inserting Row
		db.insert(Dbh.TABLE_JSON_UPDATE, null, values);
		db.close(); // Closing database connection
	}

	public static JsonUpdate getJsonUpdate(Dbh handler) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_JSON_UPDATE,
				new String[] { Dbh.JSON_UPDATE_DATE, }, Dbh.JSON_UPDATE_ID + "=?",
				new String[] { String.valueOf(1) }, null, null, null, null);
		JsonUpdate jsonUpdate=new JsonUpdate(-1,"");
		if (cursor != null)
			if(cursor.moveToFirst()){
				jsonUpdate = new JsonUpdate(1, cursor.getString(0));
			}
		cursor.close();
		db.close();

		return jsonUpdate;
	}
	
	// Updating single JsonUpdate
	 public int updateJsonUpdate(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.JSON_UPDATE_DATE, date);

		// updating row
		int updateMessage = db.update(Dbh.TABLE_JSON_UPDATE, values,
				Dbh.JSON_UPDATE_ID + " = ?",
				new String[] { String.valueOf(1) });
		db.close();
		return updateMessage;
	 }
	
	

}
