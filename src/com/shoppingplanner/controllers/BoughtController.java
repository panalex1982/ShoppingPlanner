package com.shoppingplanner.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.Buys;
import com.bue.shoppingplanner.model.CommercialProduct;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.Product;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;
import com.bue.shoppingplanner.model.handlers.ShopHandler;

public class BoughtController {
	private ArrayList<ShoppingListElementHelper> products;
	private ShopElementHelper shop;
	private Context context;
	private DatabaseHandler db=null;

	public BoughtController(Context context, ArrayList<ShoppingListElementHelper> products, ShopElementHelper shop) {
		super();
		this.products = products;
		this.shop=shop;
		this.context = context;
		db=new DatabaseHandler(context);
	}
	
	

	public BoughtController(Context context) {
		super();
		this.context = context;
		db=new DatabaseHandler(context);
	}



	public BoughtController() {
		super();
	}



	public ArrayList<ShoppingListElementHelper> getProducts() {
		return products;
	}



	public void setProducts(ArrayList<ShoppingListElementHelper> products) {
		this.products = products;
	}
	
	



	public ShopElementHelper getShop() {
		return shop;
	}



	public void setShop(ShopElementHelper shop) {
		this.shop = shop;
	}


	public Context getContext() {
		return context;
	}



	public void setContext(Context context) {
		this.context = context;
	}
	
	public void persistBought() throws NullPointerException{
		if(db==null){
			db=new DatabaseHandler(context);
		}
		
		//Create Shop Model
		Shop shopModel=new Shop();
		shopModel.setName(shop.getName());
		ShopHandler shopHl=new ShopHandler(shopModel);
		int shopId = shopHl.getShopId(db);
		Log.d("SHOP_ID: ", Integer.toString(shopId));
		
		
		Address address=new Address();
		ShopDescription shopDesc=new ShopDescription();
		
		
		//Create Product Model
		Product product=new Product();
		
		ProductKind kind=new ProductKind();
		ProductGroup group=new ProductGroup();
		CommercialProduct comercial=new CommercialProduct();
		
		Buys buys=new Buys();	
		
		
	}
	
	
	
}
