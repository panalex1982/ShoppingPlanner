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
	private static final int DATABASE_VERSION = 2;

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
	public static final String BUYS_PRODUCT_GROUP_ID = "productGroupId";

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
	public static final String BUYS_DATE = "date";

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
				+ "FOREIGN KEY(" + BUYS_PRODUCT + ") REFERENCES "
				+ TABLE_PRODUCT + "(" + PRODUCT_ID + ")," + "FOREIGN KEY("
				+ BUYS_SHOP + ") REFERENCES " + TABLE_SHOP + "(" + SHOP_ID
				+ ")" + "FOREIGN KEY(" + BUYS_PRODUCT_GROUP_ID
				+ ") REFERENCES " + TABLE_PRODUCT_GROUP + "("
				+ PRODUCT_GROUP_ID + ")" + ")";
		Log.d("Query", CREATE_TABLE_BUYS);

		// Create all tables
		db.execSQL(CREATE_TABLE_PRODUCT_GROUP);
		db.execSQL(CREATE_TABLE_PRODUCT_KIND);
		db.execSQL(CREATE_TABLE_COMMERCIAL_PRODUCT);
		db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_SHOP_DESCRIPTION);
		db.execSQL(CREATE_TABLE_ADDRESS);
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_BUYS);
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
		//
		// // Create tables again
		// onCreate(db);
	}
	
//	public SQLiteDatabase getWritableDatabase() {
//		return this.getWritableDatabase();
//	}
//
//	public SQLiteDatabase getReadableDatabase() {
//		return this.getReadableDatabase();
//	}

}

/**
 * Using Example DatabaseHandler db = new DatabaseHandler(this);
 * 
 * 
 * CRUD Operations
 * 
 * // Inserting Contacts Log.d("Insert: ", "Inserting ..");
 * db.addProductKind(new ProductKind("Food",0)); db.addProductKind(new
 * ProductKind("Drink",0)); db.addProductKind(new ProductKind("Clothes",0));
 * db.addProductKind(new ProductKind("Computer Hardware",0));
 * db.addCommercialProduct(new CommercialProduct("-1","Unknown", "Unknown"));
 * db.addCommercialProduct(new CommercialProduct("5201399011201",
 * "Φασόλια Μέτρια 3Α", "3 άλφα")); db.addProduct(new
 * Product("Μέτρια Φασόλια 3Α", "5201399011201", 1)); db.addShopDescription(new
 * ShopDescription("Super Market")); db.addShopDescription(new
 * ShopDescription("Mini Market")); db.addShopDescription(new
 * ShopDescription("Local Store")); db.addShopDescription(new
 * ShopDescription("Specialized Shop")); db.addAddress(new Address("Βόλου",
 * "23", "not specified", "Αλμυρός", "Μαγνησίας", "Greece")); db.addAddress(new
 * Address("Αλμυρά", "12", "not specified", "Νέα Αγχίαλος", "Μαγνησίας",
 * "Greece")); db.addShop(new Shop(1, 2, "Γαλαξίας")); SimpleDateFormat s = new
 * SimpleDateFormat("ddMMyyyyhhmmss"); String format = s.format(new Date());
 * db.addBuys(new Buys(-1, 2, 1, 1.49, 2, format));
 * 
 * 
 * 
 * // Reading all contacts
 * 
 * // Reading all product kinds Log.d("Reading: ",
 * "Reading all product kinds.."); List<ProductKind> productKinds =
 * db.getAllProductKind();
 * 
 * for (ProductKind pkind : productKinds) { String log2 =
 * "Id: "+pkind.getId()+" ,Name: " + pkind.getName() + " ,Group Id: " +
 * pkind.getGroup_id(); // Writing Contacts to log Log.d("Name: ", log2); } //
 * Reading all CommercialProduct Log.d("Reading: ",
 * "Reading all product CommercialProduct.."); List<CommercialProduct>
 * commercialProduct = db.getAllCommercialProduct();
 * 
 * for (CommercialProduct cmp : commercialProduct) { String log3 =
 * "Barcode: "+cmp.getBarcode()+" ,Name: " + cmp.getCommercialName() +
 * " ,Brand: " + cmp.getCompanyBrand(); // Writing Contacts to log
 * Log.d("Name: ", log3); } // Reading all product Log.d("Reading: ",
 * "Reading all product .."); List<Product> product = db.getAllProduct(); for
 * (Product pk : product) { ProductKind kind=db.getProductKind(pk.getKind());
 * String log4 = "Id: "+pk.getId()+" ,Name: " + pk.getName() + " ,Kind: " +
 * kind.getName() + "Barcode"+pk.getBarcode(); // Writing Contacts to log
 * Log.d("Name: ", log4); }
 * 
 * // Reading all Shop Desc Log.d("Reading: ", "Reading all Shop Desc..");
 * List<ShopDescription> shopDescription = db.getAllShopDescription(); for
 * (ShopDescription sdesc : shopDescription) { String log5 =
 * "Id: "+sdesc.getId()+" ,Name: " + sdesc.getName(); // Writing Contacts to log
 * Log.d("Name: ", log5); }
 * 
 * // Reading all address Log.d("Reading: ", "Reading all product address..");
 * List<Address> address = db.getAllAddress(); for (Address add : address) {
 * String log6 = "Id: "+add.getId()+" ,Street Name: " + add.getStreetName() +
 * " ,"+add.getNumber() +
 * ", "+add.getCity()+", "+add.getCounty()+", "+add.getCountry(); // Writing
 * Contacts to log Log.d("Name: ", log6); }
 * 
 * // Reading all Buys Log.d("Reading: ", "Reading all product buys..");
 * List<Buys> buys = db.getAllBuys(); for (Buys buy : buys) { Product
 * prod=db.getProduct(buy.getProduct()); Shop sp=db.getShop(buy.getShop());
 * Address sadd=db.getAddress(sp.getAddress()); String log7 =
 * "Id: "+buy.getId()+" ,Product Name: " + prod.getName() +
 * ", Shop name: "+sp.getName() +
 * ", @ "+sadd.getStreetName()+", "+sadd.getNumber
 * ()+", "+sadd.getCity()+", "+sadd
 * .getCounty()+", "+sadd.getCountry()+"\nTotal Price: "
 * +buy.getUnit_price()*buy.getAmount()+" Euro, Purchace date: "+buy.getDate();
 * // Writing Contacts to log Log.d("Name: ", log7); }
 */
