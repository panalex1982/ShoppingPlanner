package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bue.shoppingplanner.utilities.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * "CREATE TABLE "+ TABLE_BUYS +"("+ BUYS_ID+
 * " INTEGER PRIMARY KEY AUTOINCREMENT ," + BUYS_PRODUCT + " INTEGER NOT NULL,"
 * + BUYS_SHOP + " INTEGER NOT NULL," + BUYS_UNIT_PRICE + " REAL NOT NULL," +
 * BUYS_AMOUNT + " INTEGER NOT NULL," + BUYS_DATE + " TEXT NOT NULL," +
 * BUYS_PRODUCT_GROUP_ID + " INTEGER NOT NULL," + BUYS_VAT + " REAL NOT NULL, "
 * + "FOREIGN KEY("+BUYS_PRODUCT+") REFERENCES "+
 * TABLE_PRODUCT+"("+PRODUCT_ID+"),"+ "FOREIGN KEY("+BUYS_SHOP+") REFERENCES "+
 * TABLE_SHOP+"("+SHOP_ID+")"+ ")";
 * 
 * Buys
 */
public class Buys {
	private final static String SUM_RESULT=" sum(" + Dbh.TABLE_BUYS + "." + Dbh.BUYS_UNIT_PRICE + "* "
			+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_AMOUNT + ") AS sumresult ",
							VAT_SUM_RESULT = " sum(" + Dbh.TABLE_BUYS + "." 
			+ Dbh.BUYS_UNIT_PRICE + " * "+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_AMOUNT +" * "
			+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_VAT + ") AS vatsumresult ";
	
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

	public Buys(int id, int product, int shop, double unitPrice, int amount,
			int user, String date, String listName, double vat) {
		super();
		this.id = id;
		this.product = product;
		this.shop = shop;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.user = user;
		this.date = date;
		this.listName = listName;
		this.vat = vat;
	}

	public Buys(int id, int product, int shop, double unit_price, int amount,
			String date, int user, double vat) {
		super();
		this.product = product;
		this.shop = shop;
		this.unitPrice = unit_price;
		this.amount = amount;
		this.date = date;
		this.id = id;
		this.user = user;
		this.vat = vat;
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
		this.vat = vat;
		this.listName = listName;
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

	public int addBuys(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.BUYS_PRODUCT, getProduct());
		values.put(Dbh.BUYS_SHOP, getShop());
		values.put(Dbh.BUYS_UNIT_PRICE, getUnitPrice());
		values.put(Dbh.BUYS_AMOUNT, getAmount());
		values.put(Dbh.BUYS_DATE, getDate());
		values.put(Dbh.BUYS_USER_ID, getUser());
		values.put(Dbh.BUYS_LIST_NAME, listName);
		values.put(Dbh.BUYS_VAT, vat);

		// Inserting Row
		int tmp = (int) db.insert(Dbh.TABLE_BUYS, null, values);
		db.close(); // Closing database connection
		return tmp;
	}

	public Buys getBuys(Dbh handler, int id) {
		SQLiteDatabase db = handler.getReadableDatabase();

		Cursor cursor = db.query(Dbh.TABLE_BUYS,
				new String[] { Dbh.BUYS_PRODUCT, Dbh.BUYS_SHOP,
						Dbh.BUYS_UNIT_PRICE, Dbh.BUYS_AMOUNT, Dbh.BUYS_DATE,
						Dbh.BUYS_USER_ID, Dbh.BUYS_VAT, }, Dbh.BUYS_ID
						+ "=? AND " + Dbh.BUYS_LIST_NAME + " = \"-1\"",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Buys buys = new Buys(id, cursor.getInt(0), cursor.getInt(1),
				cursor.getDouble(2), cursor.getInt(3), cursor.getString(4),
				cursor.getInt(5), cursor.getDouble(6));
		cursor.close();
		db.close();

		return buys;
	}

	public static Buys getLastBought(Dbh handler, int productId) {
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.query(Dbh.TABLE_BUYS,
				new String[] { Dbh.BUYS_USER_ID, Dbh.BUYS_SHOP,
						Dbh.BUYS_UNIT_PRICE, Dbh.BUYS_AMOUNT, Dbh.BUYS_DATE,
						Dbh.BUYS_PRODUCT, Dbh.BUYS_VAT, }, Dbh.BUYS_ID
						+ "=? AND " + Dbh.BUYS_LIST_NAME + " = \"-1\"",
				new String[] { String.valueOf(productId) }, null, null, null,
				null);
		Buys buys = new Buys();
		if (cursor != null)
			if (cursor.moveToLast()) {
				buys = new Buys(cursor.getInt(0), productId, cursor.getInt(1),
						cursor.getDouble(2), cursor.getInt(3),
						cursor.getString(4), cursor.getInt(5),
						cursor.getDouble(6));
			}
		cursor.close();
		db.close();

		return buys;
	}

	/**
	 * Getting All Buys.
	 * 
	 * @param handler
	 * @param itemType
	 *            if equals 0 means that result are items has been bought else
	 *            if equals 1 are items belonging to a shopping list
	 * @return
	 */
	public static List<Buys> getAllBuys(Dbh handler, int itemType) {
		List<Buys> buysList = new ArrayList<Buys>();
		// Select All Query
		String selectQuery = "";
		if (itemType == 0)
			selectQuery = "SELECT  * FROM " + Dbh.TABLE_BUYS + " WHERE "
					+ Dbh.BUYS_LIST_NAME + " = \"-1\"";
		else if (itemType == 1)
			selectQuery = "SELECT  * FROM " + Dbh.TABLE_BUYS + " WHERE "
					+ Dbh.BUYS_LIST_NAME + " != \"-1\"";

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
	public int updateBuys(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Dbh.BUYS_PRODUCT, getProduct());
		values.put(Dbh.BUYS_SHOP, getShop());
		values.put(Dbh.BUYS_UNIT_PRICE, getUnitPrice());
		values.put(Dbh.BUYS_AMOUNT, getAmount());
		values.put(Dbh.BUYS_DATE, getDate());
		values.put(Dbh.BUYS_USER_ID, getUser());
		values.put(Dbh.BUYS_VAT, vat);

		// updating row
		int updateMessage = db.update(Dbh.TABLE_BUYS, values, Dbh.BUYS_ID
				+ " = ?", new String[] { String.valueOf(getId()) });
		db.close();
		return updateMessage;
	}

	// Deleting single Buys
	public void deleteBuys(Dbh handler) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(Dbh.TABLE_BUYS, Dbh.BUYS_ID + " = ?",
				new String[] { String.valueOf(getId()) });
		db.close();
	}

	// Getting Buys count
	public static int getBuysCount(Dbh handler) {
		String countQuery = "SELECT  * FROM " + Dbh.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}

	public static double getTotalSpending(Dbh handler) {
		double spending = 0.0;
		String query = "SELECT sum(" + Dbh.BUYS_UNIT_PRICE + "*"
				+ Dbh.BUYS_AMOUNT + ") FROM " + Dbh.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			if (cursor.moveToFirst())
				spending = cursor.getDouble(0);
		cursor.close();
		db.close();
		return spending;
	}

	public static double getTotalVatPayments(Dbh handler) {
		double spending = 0.0;
		String query = "SELECT sum(" + Dbh.BUYS_UNIT_PRICE + " * "
				+ Dbh.BUYS_AMOUNT + " * " + Dbh.BUYS_VAT + ") FROM "
				+ Dbh.TABLE_BUYS;
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			if (cursor.moveToFirst())
				spending = cursor.getDouble(0);
		cursor.close();
		db.close();
		return spending;
	}

	/*
	 * SELECT b.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a,
	 * ProductGroup b WHERE a.productGroupId=b.id GROUP BY b.name ORDER BY
	 * sumresult DESC
	 */
	/**
	 * Returns the total spending in every Group.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByUser(Dbh handler,
			String fromDate, String toDate) {
		ArrayList<String[]> userTotal = new ArrayList<String[]>();

		String query = "SELECT b." + Dbh.USER_NAME + ","
				+SUM_RESULT+ ","+VAT_SUM_RESULT
				+"FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_USER + " b" + " WHERE a." + Dbh.BUYS_USER_ID
				+ "=b." + Dbh.USER_ID + " AND a." + Dbh.BUYS_LIST_NAME
				+ " =\"-1\"" + " AND a." + Dbh.BUYS_DATE + " > \"" + fromDate
				+ "\"" + " AND a." + Dbh.BUYS_DATE + " < \"" + toDate + "\""
				+ " GROUP BY b." + Dbh.USER_NAME + " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = String.valueOf(cursor.getDouble(2));
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				userTotal.add(row);
			}
		cursor.close();
		db.close();
		return userTotal;
	}

	/**
	 * Returns spending of users that filtered by the parameters and a specific
	 * period of time.
	 * 
	 * @param db
	 * @param fromDate
	 * @param toDate
	 * @param products
	 * @param kinds
	 * @param shops
	 * @param brands
	 * @return
	 */
	public static Collection<? extends String[]> getFilteredUserSpending(
			Dbh handler, String fromDate, String toDate,
			ArrayList<String> products, ArrayList<String> kinds,
			ArrayList<String> shops, ArrayList<String> brands) {
		ArrayList<String[]> userTotal = new ArrayList<String[]>();

		String query = "SELECT " + Dbh.TABLE_USER + "." + Dbh.PRODUCT_NAME
				+ ","+SUM_RESULT+ ","+VAT_SUM_RESULT
				+ "FROM " + Dbh.TABLE_BUYS + " ";
		String[] clauses = joinWhereBuilder(null, kinds, products, shops,
				brands, null);
		String joins = clauses[0] + Dbh.JOIN_USER;
		String whereClause = clauses[1];
		whereClause = (whereClause == " WHERE ") ? whereClause : whereClause
				+ " AND ";
		query = query + joins + whereClause + Dbh.TABLE_BUYS + "."
				+ Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND " + Dbh.TABLE_BUYS
				+ "." + Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND "
				+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_DATE + " < \"" + toDate
				+ "\"" + " GROUP BY " + Dbh.TABLE_USER + "."
				+ Dbh.USER_NAME + " ORDER BY sumresult DESC";

		Log.i("Query from builder: ", query);
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = String.valueOf(cursor.getDouble(2));
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				userTotal.add(row);
			}
		cursor.close();
		db.close();
		return userTotal;
	}

	/*
	 * SELECT b.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a,
	 * Product b WHERE a.product=b.id GROUP BY b.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Product.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByProduct(Dbh handler,
			String fromDate, String toDate) {
		ArrayList<String[]> productTotal = new ArrayList<String[]>();

		String query = "SELECT b." + Dbh.PRODUCT_NAME + "," 
				+SUM_RESULT+ ","+VAT_SUM_RESULT
				+"FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_PRODUCT + " b" + " WHERE a." + Dbh.BUYS_PRODUCT
				+ "=b." + Dbh.PRODUCT_ID + " AND a." + Dbh.BUYS_LIST_NAME
				+ " =\"-1\"" + " AND a." + Dbh.BUYS_DATE + " > \"" + fromDate
				+ "\"" + " AND a." + Dbh.BUYS_DATE + " < \"" + toDate + "\""
				+ " GROUP BY b." + Dbh.PRODUCT_NAME
				+ " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = String.valueOf(cursor.getDouble(2));
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				productTotal.add(row);
			}
		cursor.close();
		db.close();
		return productTotal;
	}

	/**
	 * Returns spending of products that filtered by the parameters and a
	 * specific period of time
	 */
	public static ArrayList<String[]> getFilteredProductSpending(Dbh handler,
			String fromDate, String toDate, ArrayList<String> users,
			ArrayList<String> kinds, ArrayList<String> shops,
			ArrayList<String> brands) {

		ArrayList<String[]> productTotal = new ArrayList<String[]>();

		String query = "SELECT " + Dbh.TABLE_PRODUCT + "." + Dbh.PRODUCT_NAME
				+ ","+SUM_RESULT+ ","+VAT_SUM_RESULT
				+ "FROM " + Dbh.TABLE_BUYS + " ";
		String[] clauses = joinWhereBuilder(users, kinds, null, shops, brands,
				null);
		String joins = clauses[0] + Dbh.JOIN_PRODUCT;
		String whereClause = clauses[1];
		whereClause = (whereClause == " WHERE ") ? whereClause : whereClause
				+ " AND ";
		query = query + joins + whereClause + Dbh.TABLE_BUYS + "."
				+ Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND " + Dbh.TABLE_BUYS
				+ "." + Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND "
				+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_DATE + " < \"" + toDate
				+ "\"" + " GROUP BY " + Dbh.TABLE_PRODUCT + "."
				+ Dbh.PRODUCT_NAME + " ORDER BY sumresult DESC";

		Log.i("Query from builder: ", query);
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = String.valueOf(cursor.getDouble(2));
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				productTotal.add(row);
			}
		cursor.close();
		db.close();
		return productTotal;
	}

	/*
	 * SELECT b.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a, Shop b
	 * WHERE a.shopId=b.id GROUP BY b.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Shop.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByShop(Dbh handler,
			String fromDate, String toDate) {
		ArrayList<String[]> shopTotal = new ArrayList<String[]>();

		String query = "SELECT b." + Dbh.SHOP_NAME + ", sum(a."
				+ Dbh.BUYS_UNIT_PRICE + "*a." + Dbh.BUYS_AMOUNT
				+ ") AS sumresult FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_SHOP + " b" + " WHERE a." + Dbh.BUYS_SHOP + "=b."
				+ Dbh.SHOP_ID + " AND a." + Dbh.BUYS_LIST_NAME + " =\"-1\""
				+ " AND a." + Dbh.BUYS_DATE + " > \"" + fromDate + "\""
				+ " AND a." + Dbh.BUYS_DATE + " < \"" + toDate + "\""
				+ " GROUP BY b." + Dbh.SHOP_NAME + " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = "Euro";
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				shopTotal.add(row);
			}
		cursor.close();
		db.close();
		return shopTotal;
	}
	
	public static Collection<? extends String[]> getFilteredShopSpending(
			Dbh handler, String fromDate, String toDate, ArrayList<String> products,
			ArrayList<String> kinds, ArrayList<String> users,
			ArrayList<String> brands) {
		ArrayList<String[]> shopTotal = new ArrayList<String[]>();

		String query = "SELECT " + Dbh.TABLE_SHOP + "." + Dbh.SHOP_NAME
				+ ","+SUM_RESULT+ ","+VAT_SUM_RESULT
				+ "FROM " + Dbh.TABLE_BUYS + " ";
		String[] clauses = joinWhereBuilder(users, kinds, products, null, brands,
				null);
		String joins = clauses[0] + Dbh.JOIN_SHOP;
		String whereClause = clauses[1];
		whereClause = (whereClause == " WHERE ") ? whereClause : whereClause
				+ " AND ";
		query = query + joins + whereClause + Dbh.TABLE_BUYS + "."
				+ Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND " + Dbh.TABLE_BUYS
				+ "." + Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND "
				+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_DATE + " < \"" + toDate
				+ "\"" + " GROUP BY " + Dbh.TABLE_SHOP + "."
				+ Dbh.SHOP_NAME + " ORDER BY sumresult DESC";

		Log.i("Query from builder: ", query);
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = String.valueOf(cursor.getDouble(2));
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				shopTotal.add(row);
			}
		cursor.close();
		db.close();
		return shopTotal;
	}

	/*
	 * SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a,
	 * Product b, ProductKind c WHERE a.productId=b.id AND b.kindId=c.id GROUP
	 * BY c.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending in every Shop.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getTotalGroupByKind(Dbh handler,
			String fromDate, String toDate) {
		ArrayList<String[]> kindTotal = new ArrayList<String[]>();

		String query = "SELECT c." + Dbh.PRODUCT_KIND_NAME + ", sum(a."
				+ Dbh.BUYS_UNIT_PRICE + "*a." + Dbh.BUYS_AMOUNT
				+ ") AS sumresult FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_PRODUCT + " b, " + Dbh.TABLE_PRODUCT_KIND + " c"
				+ " WHERE a." + Dbh.BUYS_PRODUCT + "=b." + Dbh.PRODUCT_ID
				+ " AND c." + Dbh.PRODUCT_KIND_ID + " = b." + Dbh.PRODUCT_KIND
				+ " AND a." + Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND a."
				+ Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND a."
				+ Dbh.BUYS_DATE + " < \"" + toDate + "\"" + " GROUP BY c."
				+ Dbh.PRODUCT_KIND_NAME + " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = "Euro";
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				kindTotal.add(row);
			}
		cursor.close();
		db.close();
		return kindTotal;
	}
	
	public static Collection<? extends String[]> getFilteredKindSpending(
			Dbh handler, String fromDate, String toDate, ArrayList<String> products,
			ArrayList<String> shops, ArrayList<String> users,
			ArrayList<String> brands) {
		ArrayList<String[]> kindTotal = new ArrayList<String[]>();

		String query = "SELECT " + Dbh.TABLE_PRODUCT_KIND + "." + Dbh.PRODUCT_KIND_NAME
				+ ","+SUM_RESULT+ ","+VAT_SUM_RESULT
				+ "FROM " + Dbh.TABLE_BUYS + " ";
		String[] clauses = joinWhereBuilder(users, null, products, shops, brands,
				null);
		String joins = clauses[0] + Dbh.JOIN_PRODUCT_KIND;
		if(products==null)
			joins=joins+Dbh.JOIN_PRODUCT;
		String whereClause = clauses[1];
		whereClause = (whereClause == " WHERE ") ? whereClause : whereClause
				+ " AND ";
		query = query + joins + whereClause + Dbh.TABLE_BUYS + "."
				+ Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND " + Dbh.TABLE_BUYS
				+ "." + Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND "
				+ Dbh.TABLE_BUYS + "." + Dbh.BUYS_DATE + " < \"" + toDate
				+ "\"" + " GROUP BY " + Dbh.TABLE_PRODUCT_KIND + "."
				+ Dbh.PRODUCT_KIND_NAME + " ORDER BY sumresult DESC";

		Log.i("Query from builder: ", query);
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = String.valueOf(cursor.getDouble(2));
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				kindTotal.add(row);
			}
		cursor.close();
		db.close();
		return kindTotal;
	}

	// getTotalGroupByShopDescription

	/*
	 * SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a, User
	 * b, Product c WHERE b.name="groupName" AND a.userId=b.id AND
	 * a.product=c.id GROUP BY c.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the spending to all products of a specific group.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getUserSpendingByProduct(Dbh handler,
			String groupName, String fromDate, String toDate) {
		ArrayList<String[]> total = new ArrayList<String[]>();

		String query = "SELECT c." + Dbh.PRODUCT_NAME + ", sum(a."
				+ Dbh.BUYS_UNIT_PRICE + "*a." + Dbh.BUYS_AMOUNT
				+ ") AS sumresult FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_USER + " b, " + Dbh.TABLE_PRODUCT + " c"
				+ " WHERE a." + Dbh.BUYS_USER_ID + "=b." + Dbh.USER_ID
				+ " AND b." + Dbh.PRODUCT_NAME + " = \"" + groupName
				+ "\" AND a." + Dbh.BUYS_PRODUCT + " = c." + Dbh.PRODUCT_ID
				+ " AND a." + Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND a."
				+ Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND a."
				+ Dbh.BUYS_DATE + " < \"" + toDate + "\"" + " GROUP BY c."
				+ Dbh.PRODUCT_NAME + " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = "";
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				total.add(row);
			}
		cursor.close();
		db.close();
		return total;
	}

	/*
	 * SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a,
	 * Product b, Shop c WHERE a.product=b.id AND a.shop=c.id AND
	 * b.name="productName" GROUP BY c.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending of a Product in the shops that someone bought
	 * it.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getProductSpedingByShop(Dbh handler,
			String productName, String fromDate, String toDate) {
		ArrayList<String[]> total = new ArrayList<String[]>();

		String query = "SELECT c." + Dbh.SHOP_NAME + ", sum(a."
				+ Dbh.BUYS_UNIT_PRICE + "*a." + Dbh.BUYS_AMOUNT
				+ ") AS sumresult FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_PRODUCT + " b, " + Dbh.TABLE_SHOP + " c"
				+ " WHERE a." + Dbh.BUYS_PRODUCT + "=b." + Dbh.PRODUCT_ID
				+ " AND a." + Dbh.BUYS_SHOP + " = c." + Dbh.SHOP_ID + " AND b."
				+ Dbh.PRODUCT_NAME + " = \"" + productName + "\"" + " AND a."
				+ Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND a." + Dbh.BUYS_DATE
				+ " > \"" + fromDate + "\"" + " AND a." + Dbh.BUYS_DATE
				+ " < \"" + toDate + "\"" + " GROUP BY c." + Dbh.SHOP_NAME
				+ " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = "Euro";
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				total.add(row);
			}
		cursor.close();
		db.close();
		return total;
	}

	/*
	 * SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a,
	 * Product b, Shop c WHERE a.product=b.id AND a.shop=c.id AND
	 * c.name="shopName" GROUP BY c.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the total spending of in shop by product he bought.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getShopSpedingByProduct(Dbh handler,
			String shopName, String fromDate, String toDate) {
		ArrayList<String[]> total = new ArrayList<String[]>();

		String query = "SELECT b." + Dbh.PRODUCT_NAME + ", sum(a."
				+ Dbh.BUYS_UNIT_PRICE + "*a." + Dbh.BUYS_AMOUNT
				+ ") AS sumresult FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_PRODUCT + " b, " + Dbh.TABLE_SHOP + " c"
				+ " WHERE a." + Dbh.BUYS_PRODUCT + "=b." + Dbh.PRODUCT_ID
				+ " AND a." + Dbh.BUYS_SHOP + " = c." + Dbh.SHOP_ID + " AND c."
				+ Dbh.SHOP_NAME + " = \"" + shopName + "\"" + " AND a."
				+ Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND a." + Dbh.BUYS_DATE
				+ " > \"" + fromDate + "\"" + " AND a." + Dbh.BUYS_DATE
				+ " < \"" + toDate + "\"" + " GROUP BY b." + Dbh.PRODUCT_NAME
				+ " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = "Euro";
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				total.add(row);
			}
		cursor.close();
		db.close();
		return total;
	}

	/*
	 * SELECT c.name, sum(a.unitPrice*a.amount) AS sumresult FROM Buys a,
	 * ProductKind b, Product c WHERE b.name="kindName" AND c.productKindId=b.id
	 * AND a.product=c.id GROUP BY c.name ORDER BY sumresult DESC
	 */
	/**
	 * Returns the spending to all products of a specific kind.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String[]> getKindSpendingByProduct(Dbh handler,
			String kindName, String fromDate, String toDate) {
		ArrayList<String[]> shopTotal = new ArrayList<String[]>();

		String query = "SELECT c." + Dbh.PRODUCT_NAME + ", sum(a."
				+ Dbh.BUYS_UNIT_PRICE + "*a." + Dbh.BUYS_AMOUNT
				+ ") AS sumresult FROM " + Dbh.TABLE_BUYS + " a, "
				+ Dbh.TABLE_PRODUCT_KIND + " b, " + Dbh.TABLE_PRODUCT + " c"
				+ " WHERE c." + Dbh.PRODUCT_KIND + "=b." + Dbh.PRODUCT_KIND_ID
				+ " AND b." + Dbh.PRODUCT_KIND_NAME + " = \"" + kindName
				+ "\" AND a." + Dbh.BUYS_PRODUCT + " = c." + Dbh.PRODUCT_ID
				+ " AND a." + Dbh.BUYS_LIST_NAME + " =\"-1\"" + " AND a."
				+ Dbh.BUYS_DATE + " > \"" + fromDate + "\"" + " AND a."
				+ Dbh.BUYS_DATE + " < \"" + toDate + "\"" + " GROUP BY c."
				+ Dbh.PRODUCT_NAME + " ORDER BY sumresult DESC";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				String[] row = new String[3];
				row[2] = "Euro";
				row[0] = cursor.getString(0);
				row[1] = String.valueOf(cursor.getDouble(1));
				shopTotal.add(row);
			}
		cursor.close();
		db.close();
		return shopTotal;
	}

	/**
	 * Returns the names of the existing shopping lists.
	 * 
	 * @param handler
	 * @return
	 */
	public static ArrayList<String> getShoppingListNames(Dbh handler) {
		ArrayList<String> listNames = new ArrayList<String>();
		String query = "SELECT DISTINCT " + Dbh.BUYS_LIST_NAME + " FROM "
				+ Dbh.TABLE_BUYS + " WHERE " + Dbh.BUYS_LIST_NAME
				+ " != \"-1\"";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
				listNames.add(cursor.getString(0));
			}
		cursor.close();
		db.close();
		return listNames;
	}

	/**
	 * Returns the shopping list items items given the list name.
	 * 
	 * @param listName
	 * @return
	 */
	public static ArrayList<Buys> getShoppingListItems(Dbh handler,
			String listName) {
		ArrayList<Buys> items = new ArrayList<Buys>();
		String query = "SELECT * FROM " + Dbh.TABLE_BUYS + " WHERE "
				+ Dbh.BUYS_LIST_NAME + " = \"" + listName + "\"";
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null)
			while (cursor.moveToNext()) {
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

	public static void deleteShoppingList(Dbh handler, String listName) {
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(Dbh.TABLE_BUYS, Dbh.BUYS_LIST_NAME + " = ?",
				new String[] { listName });
		db.close();
	}

	/**
	 * Builds in clause of where eg. table.column IN ("1", "2", "3")
	 * 
	 * @param tableField
	 *            the column where the values compared
	 * @param tableName
	 *            the table of the column
	 * @param arrayValues
	 *            values you want to match with column
	 * @return
	 */
	private static String inBuilder(String tableField, String tableName,
			ArrayList<String> arrayValues) {
		String onClause = "";
		onClause = onClause + tableName + "." + tableField + " IN (";
		int i = 0;
		for (String value : arrayValues) {
			if (i == 0) {
				onClause = onClause + "\"" + value + "\"";
				i++;
			} else {
				onClause = onClause + ", " + "\"" + value + "\"";
			}
		}
		onClause = onClause + ") ";
		return onClause;
	}

	/**
	 * Builds where and join clause to be add to the query. If null presented in
	 * any of the parameters then the corresponding column is excluded from the
	 * build.
	 * 
	 * @param users
	 * @param kinds
	 * @param shops
	 * @param brands
	 * @return the [0] is the join clause and the [1] is the where clause
	 */
	private static String[] joinWhereBuilder(ArrayList<String> users,
			ArrayList<String> kinds, ArrayList<String> products,
			ArrayList<String> shops, ArrayList<String> brands,
			ArrayList<String> shopTypes) {
		String[] clauses = new String[2];
		String joins = "";
		String whereClause = " WHERE ";
		if (users != null) {
			joins = joins + Dbh.JOIN_USER;
			String ins = inBuilder(Dbh.USER_NAME, Dbh.TABLE_USER, users);
			if (whereClause.equalsIgnoreCase(" WHERE "))
				whereClause = whereClause + ins;
			else
				whereClause = whereClause + " AND " + ins;

		}
		if (kinds != null) {
			joins = joins + Dbh.JOIN_PRODUCT_KIND;
			String ins = inBuilder(Dbh.PRODUCT_KIND_NAME,
					Dbh.TABLE_PRODUCT_KIND, kinds);
			if (whereClause.equalsIgnoreCase(" WHERE "))
				whereClause = whereClause + ins;
			else
				whereClause = whereClause + " AND " + ins;

		}
		if (shops != null) {
			joins = joins + Dbh.JOIN_SHOP;
			String ins = inBuilder(Dbh.SHOP_NAME, Dbh.TABLE_SHOP, shops);
			if (whereClause.equalsIgnoreCase(" WHERE "))
				whereClause = whereClause + ins;
			else
				whereClause = whereClause + " AND " + ins;

		}
		if (brands != null) {
			joins = joins + Dbh.JOIN_COMMERCIALPRODUCT;
			if(products==null){
				joins=joins+Dbh.JOIN_PRODUCT;
			}
			String ins = inBuilder(Dbh.COMMERCIAL_PRODUCT_COMPANY_BRAND,
					Dbh.TABLE_COMMERCIAL_PRODUCT, brands);
			if (whereClause.equalsIgnoreCase(" WHERE "))
				whereClause = whereClause + ins;
			else
				whereClause = whereClause + " AND " + ins;

		}
		if (products != null) {
			joins = joins + Dbh.JOIN_PRODUCT;
			String ins = inBuilder(Dbh.PRODUCT_NAME, Dbh.TABLE_PRODUCT,
					products);
			if (whereClause.equalsIgnoreCase(" WHERE "))
				whereClause = whereClause + ins;
			else
				whereClause = whereClause + " AND " + ins;

		}
		if (shopTypes != null) {
			joins = joins + Dbh.JOIN_SHOP_DESCRIPTION;
			String ins = inBuilder(Dbh.SHOP_DESCRIPTION_NAME,
					Dbh.TABLE_SHOP_DESCRIPTION, shopTypes);
			if (whereClause.equalsIgnoreCase(" WHERE "))
				whereClause = whereClause + ins;
			else
				whereClause = whereClause + " AND " + ins;

		}
		clauses[0] = joins;
		clauses[1] = whereClause;
		return clauses;
	}
}
