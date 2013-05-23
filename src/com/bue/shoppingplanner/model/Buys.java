package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.List;

import com.bue.shoppingplanner.utilities.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * "CREATE TABLE "+ TABLE_BUYS +"("+ BUYS_ID+
 * " INTEGER PRIMARY KEY AUTOINCREMENT ," + BUYS_PRODUCT +
 * " INTEGER NOT NULL," + BUYS_SHOP + " INTEGER NOT NULL," + BUYS_UNIT_PRICE
 * + " REAL NOT NULL," + BUYS_AMOUNT + " INTEGER NOT NULL," + BUYS_DATE +
 * " TEXT NOT NULL," 
 * + BUYS_PRODUCT_GROUP_ID + " INTEGER NOT NULL,"
 * + BUYS_VAT + " REAL NOT NULL, "
 * + "FOREIGN KEY("+BUYS_PRODUCT+") REFERENCES "+
 * TABLE_PRODUCT+"("+PRODUCT_ID+"),"+
 * "FOREIGN KEY("+BUYS_SHOP+") REFERENCES "+ TABLE_SHOP+"("+SHOP_ID+")"+
 * ")";
 * 
 * Buys
 */
public class Buys {
	private int id;
	private int product, shop;
	private double unitPrice;
	private int amount;
	private double vat;
	private int user;
	private String date; 
	private String listName;
	
	public Buys() {
		super();
	}
	
	
	public Buys(int id, int product, int shop, double unitPrice,
			int amount, int user, String date, String listName, double vat) {
		super();
		this.id = id;
		this.product = product;
		this.shop = shop;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.user = user;
		this.date = date;
		this.listName = listName;
		this.vat=vat;
	}


	public Buys(int id, int product, int shop, double unit_price, int amount, String date, int user, double vat) {
		super();
		this.product = product;
		this.shop = shop;
		this.unitPrice = unit_price;
		this.amount = amount;
		this.date = date;
		this.id=id;
		this.user=user;
		this.vat=vat;
	}
	
	public Buys(int product, int shop, double unitPrice, int amount, int user,
			String date, String listName, double vat) {
		super();
		this.product = product;
		this.shop = shop;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.user = user;
		this.date = date;
		this.vat=vat;
		this.listName=listName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public int getShop() {
		return shop;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}
	
	public String getListName() {
		return listName;
	}


	public void setListName(String listName) {
		this.listName = listName;
	}
	
	public double getVat() {
		return vat;
	}


	public void setVat(double vat) {
		this.vat = vat;
	}


	public int addBuys(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BUYS_PRODUCT, getProduct());
		values.put(DatabaseHandler.BUYS_SHOP, getShop());
		values.put(DatabaseHandler.BUYS_UNIT_PRICE, getUnitPrice());
		values.put(DatabaseHandler.BUYS_AMOUNT, getAmount());
		values.put(DatabaseHandler.BUYS_DATE, getDate());
		values.put(DatabaseHandler.BUYS_USER_ID, getUser());
		values.put(DatabaseHandler.BUYS_LIST_NAME, listName);
		values.put(DatabaseHandler.BUYS_VAT, vat);

		// Inserting Row
		int tmp=(int) db.insert(DatabaseHandler.TABLE_BUYS, null, values);
		db.close(); // Closing database connection
		return tmp;
	}

	public Buys getBuys(DatabaseHandler handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(DatabaseHandler.TABLE_BUYS, new String[] { DatabaseHandler.BUYS_PRODUCT,
				DatabaseHandler.BUYS_SHOP, DatabaseHandler.BUYS_UNIT_PRICE, DatabaseHandler.BUYS_AMOUNT, 
				DatabaseHandler.BUYS_DATE, DatabaseHandler.BUYS_USER_ID,
				DatabaseHandler.BUYS_VAT, }, DatabaseHandler.BUYS_ID + "=? AND "
				+DatabaseHandler.BUYS_LIST_NAME+" = \"-1\"",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Buys buys = new Buys(id, cursor.getInt(0), cursor.getInt(1),
				cursor.getDouble(2), cursor.getInt(3), cursor.getString(4),
				cursor.getInt(5),cursor.getDouble(6));
		cursor.close();
		db.close();

		return buys;
	}
	
	
	/**
	 * Getting All Buys.
	 * @param handler
	 * @param itemType if equals 0 means that result are items has been bought
	 * 			else if equals 1 are items belonging to a shopping list
	 * @return
	 */
	public static List<Buys> getAllBuys(DatabaseHandler handler, int itemType) {
		List<Buys> buysList = new ArrayList<Buys>();
		// Select All Query
		String selectQuery="";
		if(itemType==0)
			selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_BUYS
				+" WHERE "+DatabaseHandler.BUYS_LIST_NAME+" = \"-1\"";
		else if(itemType==1)
			selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_BUYS
			+" WHERE "+DatabaseHandler.BUYS_LIST_NAME+" != \"-1\"";
			

		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Buys buys = new Buys();
				buys.setId(cursor.getInt(0));
				buys.setProduct(cursor.getInt(1));
				buys.setShop(cursor.getInt(2));
				buys.setUnitPrice(cursor.getDouble(3));
				buys.setAmount(cursor.getInt(4));
				buys.setDate(cursor.getString(5));
				buys.setUser(cursor.getInt(6));
				buys.setListName(cursor.getString(7));
				buys.setVat(cursor.getDouble(8));

				// Adding buy to list
				buysList.add(buys);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return contact list
		return buysList;
	}

	// Updating single Buys
	public int updateBuys(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.BUYS_PRODUCT, getProduct());
		values.put(DatabaseHandler.BUYS_SHOP, getShop());
		values.put(DatabaseHandler.BUYS_UNIT_PRICE, getUnitPrice());
		values.put(DatabaseHandler.BUYS_AMOUNT, getAmount());
		values.put(DatabaseHandler.BUYS_DATE, getDate());
		values.put(DatabaseHandler.BUYS_USER_ID, getUser());
		values.put(DatabaseHandler.BUYS_VAT, vat);

		// updating row
		int updateMessage = db.update(DatabaseHandler.TABLE_BUYS, values, DatabaseHandler.BUYS_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Buys
	public void deleteBuys(DatabaseHandler handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_BUYS, DatabaseHandler.BUYS_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Buys count
	public static int getBuysCount(DatabaseHandler handler) {
		String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	
	public static double getTotalSpending(DatabaseHandler handler){
		double spending=0.0;
		String query="SELECT sum("+DatabaseHandler.BUYS_UNIT_PRICE+"*"+DatabaseHandler.BUYS_AMOUNT+") FROM "+DatabaseHandler.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			if(cursor.moveToFirst())
				spending = cursor.getDouble(0);
		cursor.close();
		db.close();
		return spending;
	}
	
	public static double getTotalVatPayments(DatabaseHandler handler){
		double spending=0.0;
		String query="SELECT sum("+DatabaseHandler.BUYS_UNIT_PRICE+" * "+DatabaseHandler.BUYS_AMOUNT+" * "
		+DatabaseHandler.BUYS_VAT+") FROM "+DatabaseHandler.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			if(cursor.moveToFirst())
				spending = cursor.getDouble(0);
		cursor.close();
		db.close();
		return spending;
	}
	
	/*
    SELECT b.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, ProductGroup b
	WHERE a.productGroupId=b.id
	GROUP BY b.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Group.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByUser(DatabaseHandler handler, String fromDate, String toDate){
		ArrayList<String[]> userTotal=new ArrayList<String[]>();
		
		String query="SELECT b."+DatabaseHandler.USER_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult, "
				+"sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+" * a."
				+DatabaseHandler.BUYS_AMOUNT+" * a."+DatabaseHandler.BUYS_VAT+") AS vatresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_USER+" b"
				+" WHERE a."+DatabaseHandler.BUYS_USER_ID+"=b."+DatabaseHandler.USER_ID
				+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY b."+DatabaseHandler.USER_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]=String.valueOf(cursor.getDouble(2));;
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				userTotal.add(row);
			}
		cursor.close();
		db.close();
		return userTotal;
	}
	
	/*
    SELECT b.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, Product b
	WHERE a.product=b.id
	GROUP BY b.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Product.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByProduct(DatabaseHandler handler, String fromDate, String toDate){
		ArrayList<String[]> productTotal=new ArrayList<String[]>();
		
		String query="SELECT b."+DatabaseHandler.PRODUCT_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_PRODUCT+" b"
				+" WHERE a."+DatabaseHandler.BUYS_PRODUCT+"=b."+DatabaseHandler.PRODUCT_ID
				+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY b."+DatabaseHandler.PRODUCT_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="Euro";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				productTotal.add(row);
			}
		cursor.close();
		db.close();
		return productTotal;
	}
	
	/*
    SELECT b.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, Shop b
	WHERE a.shopId=b.id
	GROUP BY b.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Shop.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByShop(DatabaseHandler handler, String fromDate, String toDate){
		ArrayList<String[]> shopTotal=new ArrayList<String[]>();
		
		String query="SELECT b."+DatabaseHandler.SHOP_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_SHOP+" b"
				+" WHERE a."+DatabaseHandler.BUYS_SHOP+"=b."+DatabaseHandler.SHOP_ID
				+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY b."+DatabaseHandler.SHOP_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="Euro";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				shopTotal.add(row);
			}
		cursor.close();
		db.close();
		return shopTotal;
	}
	
	/*
    SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, Product b, ProductKind c
	WHERE a.productId=b.id AND b.kindId=c.id
	GROUP BY c.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Shop.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByKind(DatabaseHandler handler, String fromDate, String toDate){
		ArrayList<String[]> shopTotal=new ArrayList<String[]>();
		
		String query="SELECT c."+DatabaseHandler.PRODUCT_KIND_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_PRODUCT+" b, "+DatabaseHandler.TABLE_PRODUCT_KIND+" c"
				+" WHERE a."+DatabaseHandler.BUYS_PRODUCT+"=b."+DatabaseHandler.PRODUCT_ID
				+" AND c."+DatabaseHandler.PRODUCT_KIND_ID
				+" = b."+DatabaseHandler.PRODUCT_KIND
				+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY c."+DatabaseHandler.PRODUCT_KIND_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="Euro";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				shopTotal.add(row);
			}
		cursor.close();
		db.close();
		return shopTotal;
	}
	
	//getTotalGroupByShopDescription
	
	/*
    SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, User b, Product c
	WHERE b.name="groupName" AND a.userId=b.id AND a.product=c.id
	GROUP BY c.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the spending to all products of a specific group.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getUserSpendingByProduct(DatabaseHandler handler, String groupName, String fromDate, String toDate){
		ArrayList<String[]> total=new ArrayList<String[]>();
		
		String query="SELECT c."+DatabaseHandler.PRODUCT_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_USER+" b, "+DatabaseHandler.TABLE_PRODUCT+" c"
				+" WHERE a."+DatabaseHandler.BUYS_USER_ID+"=b."+DatabaseHandler.USER_ID
				+" AND b."+DatabaseHandler.PRODUCT_NAME+" = \""+groupName+"\" AND a."
				+DatabaseHandler.BUYS_PRODUCT+" = c."+DatabaseHandler.PRODUCT_ID
				+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY c."+DatabaseHandler.PRODUCT_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				total.add(row);
			}
		cursor.close();
		db.close();
		return total;
	}
	
	/*
    SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, Product b, Shop c
	WHERE a.product=b.id AND a.shop=c.id AND b.name="productName"
	GROUP BY c.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending of a Product in the shops that someone bought it.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getProductSpedingByShop(DatabaseHandler handler, String productName, String fromDate, String toDate){
		ArrayList<String[]> total=new ArrayList<String[]>();
		
		String query="SELECT c."+DatabaseHandler.SHOP_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_PRODUCT+" b, "+DatabaseHandler.TABLE_SHOP+" c"
				+" WHERE a."+DatabaseHandler.BUYS_PRODUCT+"=b."+DatabaseHandler.PRODUCT_ID+" AND a."
				+DatabaseHandler.BUYS_SHOP+" = c."+DatabaseHandler.SHOP_ID+" AND b."+DatabaseHandler.PRODUCT_NAME
				+" = \""+productName+"\""+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY c."+DatabaseHandler.SHOP_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="Euro";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				total.add(row);
			}
		cursor.close();
		db.close();
		return total;
	}
	
	/*
    SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, Product b, Shop c
	WHERE a.product=b.id AND a.shop=c.id AND c.name="shopName"
	GROUP BY c.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending of in shop by product he bought.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getShopSpedingByProduct(DatabaseHandler handler, String shopName, String fromDate, String toDate){
		ArrayList<String[]> total=new ArrayList<String[]>();
		
		String query="SELECT b."+DatabaseHandler.PRODUCT_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_PRODUCT+" b, "+DatabaseHandler.TABLE_SHOP+" c"
				+" WHERE a."+DatabaseHandler.BUYS_PRODUCT+"=b."+DatabaseHandler.PRODUCT_ID+" AND a."
				+DatabaseHandler.BUYS_SHOP+" = c."+DatabaseHandler.SHOP_ID+" AND c."+DatabaseHandler.SHOP_NAME
				+" = \""+shopName+"\""+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY b."+DatabaseHandler.PRODUCT_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="Euro";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				total.add(row);
			}
		cursor.close();
		db.close();
		return total;
	}
	
	/*
    SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult
	FROM Buys a, ProductKind b, Product c
	WHERE b.name="kindName" AND c.productKindId=b.id AND a.product=c.id
	GROUP BY c.name
	ORDER BY sumresult DESC
	 */
	/**
	 * Returns the spending to all products of a specific kind.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getKindSpendingByProduct(DatabaseHandler handler, String kindName, String fromDate, String toDate){
		ArrayList<String[]> shopTotal=new ArrayList<String[]>();
		
		String query="SELECT c."+DatabaseHandler.PRODUCT_NAME+", sum(a."+DatabaseHandler.BUYS_UNIT_PRICE+"*a."
				+DatabaseHandler.BUYS_AMOUNT+") AS sumresult FROM "
				+DatabaseHandler.TABLE_BUYS+" a, "+DatabaseHandler.TABLE_PRODUCT_KIND+" b, "+DatabaseHandler.TABLE_PRODUCT+" c"
				+" WHERE c."+DatabaseHandler.PRODUCT_KIND+"=b."+DatabaseHandler.PRODUCT_KIND_ID
				+" AND b."+DatabaseHandler.PRODUCT_KIND_NAME+" = \""+kindName
				+"\" AND a."+DatabaseHandler.BUYS_PRODUCT+" = c."+DatabaseHandler.PRODUCT_ID
				+" AND a."+DatabaseHandler.BUYS_LIST_NAME+" =\"-1\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" > \""+fromDate+"\""
				+" AND a."+DatabaseHandler.BUYS_DATE+" < \""+toDate+"\""
				+" GROUP BY c."+DatabaseHandler.PRODUCT_NAME
				+" ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				String[] row=new String[3];
				row[2]="Euro";
				row[0] = cursor.getString(0);
				row[1]=String.valueOf(cursor.getDouble(1));
				shopTotal.add(row);
			}
		cursor.close();
		db.close();
		return shopTotal;
	}
	
	/**
	 * Returns the names of the existing shopping lists.
	 * @param handler
	 * @return
	 */
	public static ArrayList<String> getShoppingListNames(DatabaseHandler handler){
		ArrayList<String> listNames=new ArrayList<String>();
		String query="SELECT DISTINCT "+DatabaseHandler.BUYS_LIST_NAME+" FROM "+DatabaseHandler.TABLE_BUYS
				+" WHERE "+DatabaseHandler.BUYS_LIST_NAME+" != \"-1\"";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				listNames.add(cursor.getString(0));
			}
		cursor.close();
		db.close();
		return listNames;
	}
	
	/**
	 * Returns the shopping list items items given the list name.
	 * @param listName
	 * @return
	 */
	public static ArrayList<Buys> getShoppingListItems(DatabaseHandler handler, String listName){
		ArrayList<Buys> items=new ArrayList<Buys>();
		String query="SELECT * FROM "+DatabaseHandler.TABLE_BUYS+" WHERE "
				+DatabaseHandler.BUYS_LIST_NAME+" = \""+listName+"\"";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while(cursor.moveToNext()){
				Buys item = new Buys();
				item.setId(cursor.getInt(0));
				item.setProduct(cursor.getInt(1));
				item.setShop(cursor.getInt(2));
				item.setUnitPrice(cursor.getDouble(3));
				item.setAmount(cursor.getInt(4));
				item.setDate(cursor.getString(5));
				item.setUser(cursor.getInt(6));
				item.setListName(cursor.getString(7));

				// Adding item to list
				items.add(item);
			}
		cursor.close();
		db.close();
		return items;
	}
	
	public static void deleteShoppingList(DatabaseHandler handler, String listName){
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(DatabaseHandler.TABLE_BUYS, DatabaseHandler.BUYS_LIST_NAME + " = ?",
				new String[] { listName });
		db.close();
	}
}
