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
import com.bue.shoppingplanner.model.UnknownBarcode;
import com.bue.shoppingplanner.utilities.Utilities;

public class BoughtController {
	private ArrayList<ShoppingListElementHelper> products;
	private ShopElementHelper shop;
	private Context context;
	private DatabaseHandler db=null;
	private String listName;

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


	/**
	 * @deprecated
	 */
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

	public String getListName() {
		return listName;
	}



	public void setListName(String listName) {
		this.listName = listName;
	}



	public Context getContext() {
		return context;
	}



	public void setContext(Context context) {
		this.context = context;
	}
	
	/**
	 * Adds entry to bought table and also on the foreign keys tables if the records does not exist.
	 * @param persistType if it is 0 then it persist an bought, if it is 1 it persists a list.
	 * @return
	 * @throws NullPointerException
	 */
	public int persistBought(int persistType) throws NullPointerException{
		int shopId;
		if(persistType==0){
			ShopController shopController=new ShopController(context);
			shopController.setElement(shop);
			shopId=shopController.persistShop();
		}else{
			shopId=-2;
		}
		
		if(db==null){
			db=new DatabaseHandler(context);
		}
		
		//Create Product Model and persist shopping list
		int buysId=0;
		for(ShoppingListElementHelper element : products){
			if(element.isChecked()){
				String barcode=element.getBarcode();
				String productName="";
				
				//Set the commercial
				//Does not have Barcode
				if(element.getBarcode().equalsIgnoreCase("unknown")){
					productName=element.getProduct();
					UnknownBarcode unBarcode=new UnknownBarcode(db);
					CommercialProduct commercial=new CommercialProduct(String.valueOf(unBarcode.getBarcode()-1),element.getProduct(),element.getBrand());
					unBarcode.updateUnknownBarcode(db);
					barcode=commercial.addCommercialProduct(db);
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
				Buys buys=null;
				//Convert Price to local currency
				CurrencyController cController=new CurrencyController(context, element.getCurrency());
				double price=cController.getPriceToDefaultCurrency(element.getPrice());
				if(persistType==0){
					SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
					String timestamp = s.format(new Date());
					buys=new Buys(productId, shopId, price, element.getQuantity(), groupId, timestamp,"-1", 1);//Add vat
				}else if(persistType==1){
					buys=new Buys(productId, shopId, price, element.getQuantity(), groupId, "", listName, 1);//Add vat
				}
				buysId=buys.addBuys(db);//TODO: Currently this method returns the records number and not the PK of buys
				//Make a method that checks if buys stored and then count them
					
			}
		}
		
		//Create Toast
		CharSequence text = "ShopId: "+shopId+": ";
		if(shopId==-1){
			text=text+"Shop did't saved!\n";
		}else if(shopId==-2){
			text="Shopping list "+listName+" saved!";
		}else{
			text=text+shop.getType()+" "+shop.getName()+" at "+shop.getAddress()+", "
					+shop.getNumber()+", "+shop.getCity()+" saved!\n";
			text=text+" Total Records persisted: "+buysId;
		}
		
		
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		if(buysId==-1 || shopId==-1){
			return -1;
		}
		return 0;		
	}
	
	/**
	 * Returns an ArrayList with ShoppingListElementHelper
	 * elements, that contained the information of the shopping list
	 * of the parameter listName.
	 * @param listName
	 * @return
	 */
	public ArrayList<ShoppingListElementHelper> getSavedShoppingList(String listName){
		ArrayList<ShoppingListElementHelper> savedShoppingList=new ArrayList<ShoppingListElementHelper>();
		for(Buys item:Buys.getShoppingListItems(db, listName)){
			ShoppingListElementHelper element=new ShoppingListElementHelper();
			element.setChecked(true);
			element.setPrice(item.getUnit_price());
			element.setQuantity(item.getAmount());
			Product product=Product.getProduct(db, item.getProduct());
			element.setProduct(product.getName());
			element.setBarcode(product.getBarcode());
			CommercialProduct cmProduct=CommercialProduct.getCommercialProduct(db, product.getBarcode());
			element.setBrand(cmProduct.getCompanyBrand());
			ProductGroup group=ProductGroup.getProductGroup(db, item.getGroup());
			element.setGroup(group.getName());
			ProductKind kind=ProductKind.getProductKind(db, product.getKind());
			element.setKind(kind.getName());
			savedShoppingList.add(element);			
		}
		return savedShoppingList;
	}
	
	public ArrayList<String> getShoppingListNames(){
		return Buys.getShoppingListNames(db);
	}
	
	public ArrayList<String[]> getTotalByGroup(String fromDate, String toDate){
		return Buys.getTotalGroupByGroup(db, fromDate, toDate);		
	}
	
	public ArrayList<String[]> getTotalByProduct(String fromDate, String toDate){
		return Buys.getTotalGroupByProduct(db, fromDate, toDate);		
	}
	
	public ArrayList<String[]> getTotalByShop(String fromDate, String toDate){
		return Buys.getTotalGroupByShop(db, fromDate, toDate)	;	
	}
	
	public ArrayList<String[]> getTotalByKind(String fromDate, String toDate){
		return Buys.getTotalGroupByKind(db, fromDate, toDate)	;	
	}
	
	public ArrayList<String[]> getGroupSpendingByProduct(String groupName, String fromDate, String toDate){
		return Buys.getGroupSpendingByProduct(db, groupName, fromDate, toDate);
	}
	
	public ArrayList<String[]> getProductSpedingByShop(String productName, String fromDate, String toDate){
		return Buys.getProductSpedingByShop(db, productName, fromDate, toDate);
	}
	
	public ArrayList<String[]> getShopSpedingByProduct(String shopName, String fromDate, String toDate){
		return Buys.getShopSpedingByProduct(db,shopName, fromDate, toDate);
	}
	
	public ArrayList<String[]> getKindSpendingByProduct(String kindName, String fromDate, String toDate){
		return Buys.getKindSpendingByProduct(db, kindName, fromDate, toDate);
	}
	
	public ArrayList<Product> getProductList(){
		return (ArrayList<Product>) Product.getAllProduct(db);
	}
	
	public void deleteShoppingList(String listName){
		Buys.deleteShoppingList(db, listName);
	}
	
	
}
