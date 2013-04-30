package com.bue.shoppingplanner.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
	
	public int persistBought() throws NullPointerException{
		if(db==null){
			db=new DatabaseHandler(context);
		}
		
		//Create Shop Model and Persist shop
		Shop shopModel=new Shop();
		shopModel.setName(shop.getName());
		//Check if shop exist in database
		int shopId = shopModel.getShopId(db);
		//Log.d("SHOP_ID: ", Integer.toString(shopId));
		if(shopId==-1){
			Address address=new Address(shop.getAddress(), shop.getNumber(), shop.getCity(), shop.getArea(),
					shop.getCountry(), shop.getZip());
			ShopDescription shopDesc=new ShopDescription(shop.getType());
			int addressId=address.getAddressId(db);
			int descId=shopDesc.getShopDescriptionId(db);
			if(addressId==-1)
				shopModel.setAddress(address.addAddress(db));
			else
				shopModel.setAddress(addressId);
			if(descId==-1)
				shopModel.setShopDescription(shopDesc.addShopDescription(db));
			else
				shopModel.setShopDescription(descId);
			shopId=shopModel.addShop(db);			
		}
		
		//Create Product Model and persist shopping list
		int buysId=0;
		for(ShoppingListElementHelper element : products){
			if(element.isChecked()){
				String barcode="-999";
				String productName="";
				
				//Set the commercial
				//Does not have Barcode
				if(element.getBarcode().equalsIgnoreCase("-999")){
					productName=element.getProduct()+" "+element.getBrand();
				//Has Barcode
				}else{
					CommercialProduct commercial=new CommercialProduct(element.getBarcode(),element.getProduct(),element.getBrand());
					if(commercial.getCommercialProductId(db).equals("-1"))
						barcode=commercial.addCommercialProduct(db);
					else
						barcode=commercial.getBarcode();
					productName=element.getProduct();					
				}
				//Set the product kind
				ProductKind kind=new ProductKind(element.getKind());
				int productKind=kind.getProductKindId(db);
				
				Product product=new Product(productName,barcode,productKind);
				int productId=product.getProductId(db);
				if(productId==-1){
					productId=product.addProduct(db);
				}
				ProductGroup group=new ProductGroup(element.getGroup());
				int groupId=group.getProductGroupId(db);
				SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
				String timestamp = s.format(new Date());
				Buys buys=new Buys(productId, shopId, element.getPrice(), element.getQuantity(), groupId, timestamp);
				buysId=buys.addBuys(db);//TODO: Currently this method returns the records number and not the PK of buys
				//Make a method that checks if buys stored and then count them
					
			}
		}
		
		//Create Toast
		CharSequence text = "ShopId: "+shopId+": ";
		if(shopId==-1){
			text=text+"Shop did't saved!\n";
		}else{
			text=text+shop.getType()+" "+shop.getName()+" at "+shop.getAddress()+", "
					+shop.getNumber()+", "+shop.getCity()+" saved!\n";
		}
		
		text=text+" Total Records persisted: "+buysId;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		if(buysId==-1 || shopId==-1){
			return -1;
		}
		return 0;		
	}
	
	public ArrayList<String[]> getTotalByGroup(){
		return Buys.getTotalGroupByGroup(db);		
	}
	
	public ArrayList<String[]> getTotalByProduct(){
		return Buys.getTotalGroupByProduct(db);		
	}
	
	public ArrayList<String[]> getTotalByShop(){
		return Buys.getTotalGroupByShop(db)	;	
	}
	
	public ArrayList<String[]> getTotalByKind(){
		return Buys.getTotalGroupByKind(db)	;	
	}
	
	public ArrayList<String[]> getGroupSpendingByProduct(String groupName){
		return Buys.getGroupSpendingByProduct(db, groupName);
	}
	
	
}
