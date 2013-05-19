package com.bue.shoppingplanner.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * "CREATE TABLE " + TABLE_CURRENCIES+ "("
 * + CURRENCIES_ID+" TEXT PRIMARY_KEY, "
 * + CURRENCIES_RATE_TO_USD+"REAL NOT NULL)";
 * 
 * @author Panagiotis Alexandopoulos
 *
 */
public class Currencies {
	
	private String id;
	private Double rateToUsd;
	
	public Currencies(String id, Double rateToUsd) {
		super();
		this.id = id;
		this.rateToUsd = rateToUsd;
	}
	
	/**
	 * No argument constructor creates USD currency.
	 */
	public Currencies() {
		super();
		id="USD";
		rateToUsd=1.0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getRateToUsd() {
		return rateToUsd;
	}

	public void setRateToUsd(Double rateToUsd) {
		this.rateToUsd = rateToUsd;
	}
	
	public void addCurrencies(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CURRENCIES_ID, id);
		values.put(DatabaseHandler.CURRENCIES_RATE_TO_USD, rateToUsd);

		// Inserting Row
		db.insert(DatabaseHandler.TABLE_CURRENCIES, null, values);
		db.close(); // Closing database connection
	}

	public static Currencies getCurrencies(DatabaseHandler handler, String currency) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_CURRENCIES,
				new String[] { DatabaseHandler.CURRENCIES_RATE_TO_USD, }, DatabaseHandler.CURRENCIES_ID + "=?",
				new String[] { currency }, null, null, null, null);
		Currencies currencyObject=new Currencies();
		if (cursor != null)
			if(cursor.moveToFirst()){
				currencyObject = new Currencies(currency, cursor.getDouble(0));
			}
		cursor.close();
		db.close();

		return currencyObject;
	}
	
	// Updating single Currencies
	 public int updateCurrencies(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CURRENCIES_RATE_TO_USD, rateToUsd);

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_CURRENCIES, values,
				DatabaseHandler.CURRENCIES_ID + " = ?",
				new String[] { id });
		db.close();
		return updateMessage;
	 }

	@Override
	public String toString() {
		return "Currencies [id=" + id + ", rateToUsd=" + rateToUsd + "]";
	}
	
	 

}
