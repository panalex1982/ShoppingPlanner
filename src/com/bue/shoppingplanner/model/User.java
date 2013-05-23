package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *  @author Panagiotis Alexandropoulos
 *  User
 *  CREATE_TABLE_USER = "CREATE TABLE "
 *	+ TABLE_USER + "(" + USER_ID
 *	+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + USER_NAME
 *	+ " TEXT NOT NULL)";
 */
public class User {
	private int id;
	private String name;
	
	public User() {
		super();
	}
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public User(String name) {
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
	public void addUser(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.USER_NAME, getName());

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_USER, null, values);
		db.close(); // Closing database connection
	}

	public static User getUser(DatabaseHandler handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_USER,
				new String[] { DatabaseHandler.USER_NAME, }, DatabaseHandler.USER_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		User user=new User();
		if (cursor != null)
			if(cursor.moveToFirst()){
				user = new User(id, cursor.getString(0));
			}
		cursor.close();
		db.close();

		return user;
	}

	// Getting All User
	public static List<User> getAllUser(DatabaseHandler handler) {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_USER;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setName(cursor.getString(1));
				// Adding contact to list
				userList.add(user);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// return user list
		return userList;
	}

	// Updating single User
	public int updateUser(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.USER_NAME, getName());

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_USER, values,
				DatabaseHandler.USER_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single User
	public void deleteUser(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_USER, DatabaseHandler.USER_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting User
	public static int getUserCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_USER;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	/**
	 * Return PK of the User.
	 * @param db
	 * @return
	 */
	public int getUserId(DatabaseHandler db){
		int userId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(DatabaseHandler.TABLE_USER, new String[]{DatabaseHandler.USER_ID,},DatabaseHandler.USER_NAME+"=?",
				new String[] {String.valueOf(name)},null, null, null, null);
		if (cursor != null)
            if(cursor.moveToFirst())
            	userId=cursor.getInt(0);
		
		cursor.close();
		readable.close();
		return userId;
	}
	
}
