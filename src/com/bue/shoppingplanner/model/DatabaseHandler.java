package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All public static variables
	// Database Version
	private static final int DATABASE_VERSION = 5;

	// Database Name
	public static final String DATABASE_NAME = "shoppingPlannerDB";

	// Tables name
	public static final String TABLE_PRODUCT_GROUP = "productGroup";
	public static final String TABLE_PRODUCT_KIND = "productKind";
	public static final String TABLE_PRODUCT = "product";
	public static final String TABLE_COMMERCIAL_PRODUCT = "commercialProduct";
	public static final String TABLE_SHOP = "shop";
	public static final String TABLE_SHOP_DESCRIPTION = "shopDescription";
	public static final String TABLE_ADDRESS = "address";
	public static final String TABLE_BUYS = "buys";
	public static final String TABLE_UNKNOWN_BARCODE="unknownBarcode";
	public static final String TABLE_CURRENCIES="currencies";
	public static final String TABLE_JSON_UPDATE="jsonUpdate";
	//public static final String TABLE_LIST="list";

	// Column Names
	// Product Group
	public static final String PRODUCT_GROUP_ID = "id";
	public static final String PRODUCT_GROUP_NAME = "name";

	// Product Kind Table
	public static final String PRODUCT_KIND_ID = "id";
	public static final String PRODUCT_KIND_NAME = "name";
	// private public static final String PRODUCT_KIND_GROUP_ID="groupId";

	// Product Table
	public static final String PRODUCT_ID = "id";
	public static final String PRODUCT_NAME = "name";
	public static final String PRODUCT_BARCODE = "barcode";
	public static final String PRODUCT_KIND = "kind";

	// Commercial Product
	public static final String COMMERCIAL_PRODUCT_BARCODE = "barcode";
	public static final String COMMERCIAL_PRODUCT_NAME = "commercialName";
	public static final String COMMERCIAL_PRODUCT_COMPANY_BRAND = "companyBrand";

	// Shop
	public static final String SHOP_ID = "id";
	public static final String SHOP_DESCRIPTION = "shopDescription";
	public static final String SHOP_ADDRESS = "address";
	public static final String SHOP_NAME = "name";

	// Shop Description
	public static final String SHOP_DESCRIPTION_ID = "id";
	public static final String SHOP_DESCRIPTION_NAME = "name";

	// Address
	public static final String ADDRESS_ID = "id";
	public static final String ADDRESS_STREET_NAME = "streetName";
	public static final String ADDRESS_NUMBER = "number";
	public static final String ADDRESS_AREA = "area";
	public static final String ADDRESS_CITY = "city";
	public static final String ADDRESS_ZIP = "zip";
	public static final String ADDRESS_COUNTRY = "country";

	// Buys
	public static final String BUYS_ID = "id";
	public static final String BUYS_PRODUCT = "product";
	public static final String BUYS_SHOP = "shop";
	// user;//User not used in first version
	public static final String BUYS_UNIT_PRICE = "unitPrice";
	public static final String BUYS_AMOUNT = "amount";
	public static final String BUYS_PRODUCT_GROUP_ID = "productGroupId";
	public static final String BUYS_DATE = "date";//List must not have date, but buy must have
	public static final String BUYS_LIST_NAME="listName";//If this field is -1 means this is normal buy else means it is list
	
	// UnknownBarcodes
	public static final String UNKNOWN_BARCODE_ID="id";
	public static final String UNKNOWN_BARCODE_VALUE="barcode";
	
	// Currencies
	public static final String CURRENCIES_ID="id";
	public static final String CURRENCIES_RATE_TO_USD="rateToUsd";
	
	// Json Last Update
	public static final String JSON_UPDATE_ID="id";
	public static final String JSON_UPDATE_DATE = "date";
	
	

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Product Group
		String CREATE_TABLE_PRODUCT_GROUP = "CREATE TABLE "
				+ TABLE_PRODUCT_GROUP + "(" + PRODUCT_GROUP_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + PRODUCT_GROUP_NAME
				+ " TEXT NOT NULL)";

		// Product Kind Table
		String CREATE_TABLE_PRODUCT_KIND = "CREATE TABLE " + TABLE_PRODUCT_KIND
				+ "(" + PRODUCT_KIND_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + PRODUCT_KIND_NAME
				+ " TEXT NOT NULL)";
		// + PRODUCT_KIND_GROUP_ID + " INTEGER)";
		// + "FOREIGN KEY("+PRODUCT_KIND_GROUP_ID+") REFERENCES "+
		// TABLE_PRODUCT_GROUP+"("+PRODUCT_GROUP_ID+")";

		// Commercial Product
		String CREATE_TABLE_COMMERCIAL_PRODUCT = "CREATE TABLE "
				+ TABLE_COMMERCIAL_PRODUCT + "(" + COMMERCIAL_PRODUCT_BARCODE
				+ " TEXT PRIMARY KEY," + COMMERCIAL_PRODUCT_NAME
				+ " TEXT NOT NULL," + COMMERCIAL_PRODUCT_COMPANY_BRAND
				+ " TEXT)";

		// Product Table
		String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + "("
				+ PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ PRODUCT_NAME + " TEXT NOT NULL," + PRODUCT_BARCODE
				+ " TEXT NOT NULL," + PRODUCT_KIND + " INTEGER NOT NULL,"
				+ "FOREIGN KEY("
				+ PRODUCT_BARCODE + ") REFERENCES " + TABLE_COMMERCIAL_PRODUCT
				+ "(" + COMMERCIAL_PRODUCT_BARCODE + ")," + "FOREIGN KEY("
				+ PRODUCT_KIND + ") REFERENCES " + TABLE_PRODUCT_KIND + "("
				+ PRODUCT_KIND_ID + ")" + ")";

		// Shop Description
		String CREATE_TABLE_SHOP_DESCRIPTION = "CREATE TABLE "
				+ TABLE_SHOP_DESCRIPTION + "(" + SHOP_DESCRIPTION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ SHOP_DESCRIPTION_NAME + " TEXT NOT NULL)";

		// Address
		String CREATE_TABLE_ADDRESS = "CREATE TABLE " + TABLE_ADDRESS + "("
				+ ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ ADDRESS_STREET_NAME + " TEXT NOT NULL," + ADDRESS_NUMBER
				+ " TEXT," + ADDRESS_AREA + " TEXT," + ADDRESS_CITY + " TEXT,"
				+ ADDRESS_ZIP + " TEXT," + ADDRESS_COUNTRY + " TEXT)";

		// Shop Table
		String CREATE_TABLE_SHOP = "CREATE TABLE " + TABLE_SHOP + "(" + SHOP_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + SHOP_NAME
				+ " TEXT NOT NULL," + SHOP_ADDRESS + " INTEGER NOT NULL,"
				+ SHOP_DESCRIPTION + " INTEGER NOT NULL," + "FOREIGN KEY("
				+ SHOP_ADDRESS + ") REFERENCES " + TABLE_ADDRESS + "("
				+ ADDRESS_ID + ")," + "FOREIGN KEY(" + SHOP_DESCRIPTION
				+ ") REFERENCES " + TABLE_SHOP_DESCRIPTION + "("
				+ SHOP_DESCRIPTION_ID + ")" + ")";

		// Buys
		String CREATE_TABLE_BUYS = "CREATE TABLE " + TABLE_BUYS + "(" + BUYS_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + BUYS_PRODUCT
				+ " INTEGER NOT NULL," + BUYS_SHOP + " INTEGER NOT NULL,"
				+ BUYS_UNIT_PRICE + " REAL NOT NULL," + BUYS_AMOUNT
				+ " INTEGER NOT NULL," + BUYS_DATE + " TIMESTAMP NOT NULL,"
				+ BUYS_PRODUCT_GROUP_ID + " INTEGER NOT NULL,"
				+BUYS_LIST_NAME+" TEXT, "
				+ "FOREIGN KEY(" + BUYS_PRODUCT + ") REFERENCES "
				+ TABLE_PRODUCT + "(" + PRODUCT_ID + ")," + "FOREIGN KEY("
				+ BUYS_SHOP + ") REFERENCES " + TABLE_SHOP + "(" + SHOP_ID
				+ ")" + "FOREIGN KEY(" + BUYS_PRODUCT_GROUP_ID
				+ ") REFERENCES " + TABLE_PRODUCT_GROUP + "("
				+ PRODUCT_GROUP_ID + ")" + ")";
		
		//UNKNOWN_BARCODE
		String CREATE_TABLE_UNKNOWN_BARCODE="CREATE TABLE " + TABLE_UNKNOWN_BARCODE + "(" + UNKNOWN_BARCODE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ UNKNOWN_BARCODE_VALUE+" INTEGER NOT NULL)";
		
		// Currencies
		String CREATE_TABLE_CURRENCIES="CREATE TABLE " + TABLE_CURRENCIES+ "("
				+ CURRENCIES_ID+" TEXT PRIMARY_KEY, "
				+ CURRENCIES_RATE_TO_USD+" REAL NOT NULL)";
		
		// Json Last Update
		String CREATE_TABLE_JSON_UPDATE="CREATE TABLE " + TABLE_JSON_UPDATE+ "("
				+ JSON_UPDATE_ID+" INTEGER PRIMARY_KEY, "
				+ JSON_UPDATE_DATE+" DATE NOT NULL)";
				

		// Create all tables
		db.execSQL(CREATE_TABLE_PRODUCT_GROUP);
		db.execSQL(CREATE_TABLE_PRODUCT_KIND);
		db.execSQL(CREATE_TABLE_COMMERCIAL_PRODUCT);
		db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_SHOP_DESCRIPTION);
		db.execSQL(CREATE_TABLE_ADDRESS);
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_BUYS);
		db.execSQL(CREATE_TABLE_UNKNOWN_BARCODE);
		db.execSQL(CREATE_TABLE_CURRENCIES);
		db.execSQL(CREATE_TABLE_JSON_UPDATE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_GROUP);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_PRODUCT_KIND);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_COMMERCIAL_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_SHOP_DESCRIPTION);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_ADDRESS);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_SHOP);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_BUYS);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_UNKNOWN_BARCODE);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_CURRENCIES);
		db.execSQL("DROP TABLE IF EXISTS " +TABLE_JSON_UPDATE);
		//db.execSQL("DROP TABLE IF EXISTS " +TABLE_LIST);
		// Create tables again
		onCreate(db);
	}

}