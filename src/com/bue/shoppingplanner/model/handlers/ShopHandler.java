package com.bue.shoppingplanner.model.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bue.shoppingplanner.model.*;

public class ShopHandler {
	private Shop shop;
	

	public ShopHandler(Shop shop) {
		super();
		this.shop = shop;
	}

	public ShopHandler() {
		super();
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public int getShopId(DatabaseHandler db){
		int shopId=-1;
		SQLiteDatabase readable=db.getReadableDatabase();
		Cursor cursor =readable.query(db.TABLE_SHOP, new String[]{db.SHOP_ID,},db.SHOP_NAME+"=?",
				new String[] {String.valueOf(shop.getName())},null, null, null, null);
		if (cursor != null)
            cursor.moveToFirst();
		shopId=cursor.getInt(0);
		cursor.close();
		readable.close();
		return shopId;
	}

}
