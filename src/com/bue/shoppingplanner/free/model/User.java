package com.bue.shoppingplanner.free.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Panagiotis Alexandropoulos User CREATE_TABLE_USER = "CREATE TABLE " +
 *         TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
 *         USER_NAME + " TEXT NOT NULL)";
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

	// Database Operetions
	public void addUser(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.USER_NAME, getName());

		// Inserting Row
		db.insert(Dbh.TABLE_USER, null, values);
		db.close(); // Closing database connection
	}

	public static User getUser(Dbh handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_USER,
				new String[] { Dbh.USER_NAME, }, Dbh.USER_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		User user = new User();
		if (cursor != null)
			if (cursor.moveToFirst()) {
				user = new User(id, cursor.getString(0));
			}
		cursor.close();
		db.close();

		return user;
	}

	// Getting All User
	public static List<User> getAllUser(Dbh handler) {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Dbh.TABLE_USER;

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

	// Getting All User
	public static List<String> getAllUserNames(Dbh handler) {
		List<String> userList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Dbh.TABLE_USER;

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		while (cursor.moveToNext()) {
			String user = cursor.getString(1);
			// Adding user to list
			userList.add(user);
		}
		cursor.close();
		db.close();

		// return user list
		return userList;
	}

	// Updating single User
	public int updateUser(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.USER_NAME, getName());

		// updating row
		int updateMessage = db.update(Dbh.TABLE_USER, values, Dbh.USER_ID
				+ " = ?", new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single User
	public void deleteUser(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(Dbh.TABLE_USER, Dbh.USER_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}
	
	// Deleting single User
	public static String deleteUser(Dbh handler, String userName) {
			String error="";
			SQLiteDatabase db = handler.getWritableDatabase();
			try {
				db.execSQL("pragma foreign_keys=on;");
				db.delete(Dbh.TABLE_USER, Dbh.USER_NAME + " = ?",
						new String[] { userName });
			} catch (SQLiteConstraintException ex) {
				error="constrain_error";
			}
			
			db.close();
			return error;
		}

	// Getting User
	public static int getUserCount(Dbh handler) {
		String countQuery = "SELECT  * FROM " + Dbh.TABLE_USER;
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
	 * 
	 * @param db
	 * @return
	 */
	public int getUserId(Dbh db) {
		int userId = -1;
		SQLiteDatabase readable = db.getReadableDatabase();
		Cursor cursor = readable.query(Dbh.TABLE_USER,
				new String[] { Dbh.USER_ID, }, Dbh.USER_NAME + "=?",
				new String[] { String.valueOf(name) }, null, null, null, null);
		if (cursor != null)
			if (cursor.moveToFirst())
				userId = cursor.getInt(0);

		cursor.close();
		readable.close();
		return userId;
	}

}
