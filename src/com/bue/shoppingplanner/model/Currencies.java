package com.bue.shoppingplanner.model;

import java.util.ArrayList;

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
	
	public void addCurrencies(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.CURRENCIES_ID, id);
		values.put(Dbh.CURRENCIES_RATE_TO_USD, rateToUsd);

		// Inserting Row
		db.insert(Dbh.TABLE_CURRENCIES, null, values);
		db.close(); // Closing database connection
	}
	
	public static ArrayList<Currencies> getAllCurrencies(Dbh handler){
		ArrayList<Currencies> rates=new ArrayList<Currencies>();
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_CURRENCIES,
				new String[] { Dbh.CURRENCIES_ID, Dbh.CURRENCIES_RATE_TO_USD, }, null,
				null, null, null, null, null);
		
		if (cursor != null)
			while(cursor.moveToNext()){
				Currencies currencyObject=new Currencies();
				currencyObject = new Currencies(cursor.getString(0), cursor.getDouble(1));
				rates.add(currencyObject);
			}
		cursor.close();
		db.close();

		return rates;
	}

	public static Currencies getCurrencies(Dbh handler, String currency) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_CURRENCIES,
				new String[] { Dbh.CURRENCIES_RATE_TO_USD, }, Dbh.CURRENCIES_ID + "=?",
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
	 public int updateCurrencies(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.CURRENCIES_RATE_TO_USD, rateToUsd);

		// updating row
		int updateMessage = db.update(Dbh.TABLE_CURRENCIES, values,
				Dbh.CURRENCIES_ID + " = ?",
				new String[] { id });
		db.close();
		return updateMessage;
	 }

	@Override
	public String toString() {
		return "Currencies [id=" + id + ", rateToUsd=" + rateToUsd + "]";
	}
	
	 

}
