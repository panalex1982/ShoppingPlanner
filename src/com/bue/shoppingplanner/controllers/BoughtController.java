package com.bue.shoppingplanner.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.Buys;
import com.bue.shoppingplanner.model.CommercialProduct;
import com.bue.shoppingplanner.model.Dbh;
import com.bue.shoppingplanner.model.Product;
import com.bue.shoppingplanner.model.User;
import com.bue.shoppingplanner.model.ProductKind;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;
import com.bue.shoppingplanner.model.UnknownBarcode;
import com.bue.shoppingplanner.utilities.Utilities;

public class BoughtController {
	private ArrayList<ShoppingListElementHelper> products;
	private ShopElementHelper shop;
	private Context context;
	private Dbh db=null;
	private String listName;

	public BoughtController(Context context, ArrayList<ShoppingListElementHelper> products, ShopElementHelper shop) {
		super();
		this.products = products;
		this.shop=shop;
		this.context = context;
		db=new Dbh(context);
	}
	
	

	public BoughtController(Context context) {
		super();
		this.context = context;
		db=new Dbh(context);
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
			db=new Dbh(context);
		}
		
		//Create Product Model and persist shopping list
		int buysId=0;
		for(ShoppingListElementHelper element : products){
			if(element.isChecked()){
				String barcode=element.getBarcode();
				String productName=element.getProduct();
				
				barcode=addCommercialProduct(barcode, productName, element.getBrand());
				
				//Set the product kind
				ProductKind kind=new ProductKind(element.getKind());
				int productKind=kind.getProductKindId(db);
				
				int productId=addProduct(productName, barcode, productKind);
				User user=new User(element.getUser());
				int userId=user.getUserId(db);
				Buys buys=null;
				//Convert Price to local currency
				CurrencyController cController=new CurrencyController(context, element.getCurrency());
				double price=cController.getPriceToDefaultCurrency(element.getPrice());
				if(persistType==0){
					SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
					String timestamp = s.format(new Date());
					buys=new Buys(productId, shopId, price, element.getQuantity(), userId, timestamp,"-1", element.getVat());
				}else if(persistType==1){
					buys=new Buys(productId, shopId, price, element.getQuantity(), userId, "", listName, element.getVat());
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
			element.setPrice(item.getUnitPrice());
			element.setQuantity(item.getAmount());
			Product product=Product.getProduct(db, item.getProduct());
			element.setProduct(product.getName());
			element.setBarcode(product.getBarcode());
			CommercialProduct cmProduct=CommercialProduct.getCommercialProduct(db, product.getBarcode());
			element.setBrand(cmProduct.getCompanyBrand());
			User user=User.getUser(db, item.getUser());
			element.setUser(user.getName());
			ProductKind kind=ProductKind.getProductKind(db, product.getKind());
			element.setKind(kind.getName());
			savedShoppingList.add(element);			
		}
		return savedShoppingList;
	}
	
	public double getTotalSpending(){
		return Buys.getTotalSpending(db);
	}
	
	public double getTotalVatPayment(){
		return Buys.getTotalVatPayments(db);
	}
	
	public ArrayList<String> getShoppingListNames(){
		return Buys.getShoppingListNames(db);
	}
	
	public ArrayList<String[]> getTotalByUser(String fromDate, String toDate){
		return Buys.getTotalGroupByUser(db, fromDate, toDate);		
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
	
	public ArrayList<String[]> getUserSpendingByProduct(String userName, String fromDate, String toDate){
		return Buys.getUserSpendingByProduct(db, userName, fromDate, toDate);
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
	
	public ArrayList<CommercialProduct> getCommercialProductByProduct(String productName){
		return CommercialProduct.getCommercialProductByProduct(db, productName);
	}
	
	public ArrayList<String> getCommercialProductNamesByProduct(String productName){
		ArrayList<String> names=new ArrayList<String>();
		ArrayList<CommercialProduct> cProducts=CommercialProduct.getCommercialProductByProduct(db, productName);
		for(CommercialProduct cProduct:cProducts){
			names.add(cProduct.getCompanyBrand());
		}		
		return names;
	}
	
	public Product getProduct(String productName){
		return Product.getProduct(db, productName);
	}
	
	/**
	 * Returns double array with 2 items: double[0] is the price
	 * 									double[1] is the vat rate
	 * @param productId
	 * @return
	 */
	public String[] getLastPriceAndVat(int productId){
		Buys lastBought=Buys.getLastBought(db, productId);
		String[] price=new String[2];
		price[0]=String.valueOf(lastBought.getUnitPrice());
		price[1]=String.valueOf((int)(lastBought.getVat()*100));
		return price;
	}	
	
	 /**
	  * Returns spending of products that filtered by the parameters 
	  * and a specific period of time
	  * @param fromDate
	  * @param toDate
	  * @param users
	  * @param kinds
	  * @param shops
	  * @param brands
	  * @return
	  */
	public ArrayList<String[]> getFilteredProductSpending(String fromDate, String toDate,
			ArrayList<String> users, ArrayList<String> kinds,ArrayList<String> shops, ArrayList<String> brands){
		return Buys.getFilteredProductSpending(db, fromDate, toDate, users, kinds, shops, brands);
	}
	
	/**
	 * Returns spending of users that filtered by the parameters 
	  * and a specific period of time
	 * @param fromDate
	 * @param toDate
	 * @param products
	 * @param kinds
	 * @param shops
	 * @param brands
	 * @return
	 */
	public Collection<? extends String[]> getFilteredUserSpending(
			String fromDate, String toDate, ArrayList<String> products,
			ArrayList<String> kinds, ArrayList<String> shops,
			ArrayList<String> brands) {
		return Buys.getFilteredUserSpending(db, fromDate, toDate, products, kinds, shops, brands);
	}
	
	public Collection<? extends String[]> getFilteredShopSpending(
			String fromDate, String toDate, ArrayList<String> products,
			ArrayList<String> kinds, ArrayList<String> users,
			ArrayList<String> brands) {
		return Buys.getFilteredShopSpending(db, fromDate, toDate, products, kinds, users, brands);
	}
	
	public Collection<? extends String[]> getFilteredKindSpending(
			String fromDate, String toDate, ArrayList<String> products,
			ArrayList<String> shops, ArrayList<String> users,
			ArrayList<String> brands) {
		return Buys.getFilteredKindSpending(db, fromDate, toDate, products, shops, users, brands);
	}



	public ArrayList<? extends String> getAllUsers() {
		ArrayList<String> userNames=new ArrayList<String>();
		for(User user:User.getAllUser(db)){
			userNames.add(user.getName());
		}
		return userNames;
	}



	public Collection<? extends String> getAllKinds() {
		ArrayList<String> kindNames=new ArrayList<String>();
		for(ProductKind kind:ProductKind.getAllProductKind(db)){
			kindNames.add(kind.getName());
		}
		return kindNames;
	}



	public Collection<? extends String> getAllBrands() {
		ArrayList<String> brands=new ArrayList<String>();
		for(CommercialProduct brand:CommercialProduct.getAllCommercialProduct(db)){
			brands.add(brand.getCompanyBrand());
		}
		return brands;
	}



	public Collection<? extends String> getAllShops() {
		ArrayList<String> shopNames=new ArrayList<String>();
		for(Shop shop:Shop.getAllShop(db)){
			shopNames.add(shop.getName());
		}
		return shopNames;
	}



	public Collection<? extends String> getAllProducts() {
		ArrayList<String> productNames=new ArrayList<String>();
		for(Product product:Product.getAllProduct(db)){
			productNames.add(product.getName());
		}
		return productNames;
	}
	
	/**
	 * Return String array with 3 elements. The 1st is the product description or company name,
	 * the 2nd is the commercial name of product(it is not used for easier product input
	 * and the 3rd is the product which describes the barcode.
	 * 
	 * If the 1st element of the return array is "0" then Commercial Product does not exist.
	 * 	
	 * @param barcode
	 * @return
	 */
	public String[] getCommerialProduct(String barcode){
		String[] barcodedProduct=new String[3];
		CommercialProduct cProduct=CommercialProduct.getCommercialProduct(db, barcode);
		if(!cProduct.isNull()){
			Product product=Product.getProductFromBarcode(db, barcode);
			barcodedProduct[0]=cProduct.getCompanyBrand();
			barcodedProduct[1]=cProduct.getCommercialName();
			barcodedProduct[2]=product.getName();
			return barcodedProduct;
		}else{
			barcodedProduct[0]="0";
			barcodedProduct[1]="0";
			barcodedProduct[2]="0";
			return barcodedProduct;
		}
	}



	public ArrayList<String> getAllKindNames() {
		return (ArrayList<String>) ProductKind.getAllProductKindNames(db);
	}



	public ArrayList<String> getAllProductNamesOfKind(String kindName) {
		return (ArrayList<String>) Product.getAllProductNamesOfKind(db, kindName);
	}
	
	private int addProduct(String productName, String barcode, int productKind){
		Product product=new Product(productName,barcode,productKind);
		int productId=product.getProductId(db);
		if(productId==-1){
			productId=product.addProduct(db);
		}
		return productId;
	}
	
	private String addCommercialProduct(String barcode, String commercialName, String brand){
		//Set the commercial
		//Does not have Barcode
		if(barcode.equalsIgnoreCase("unknown")){
			//productName=element.getProduct();
			UnknownBarcode unBarcode=new UnknownBarcode(db);
			CommercialProduct commercial=new CommercialProduct(String.valueOf(unBarcode.getBarcode()-1),commercialName,brand);
			unBarcode.updateUnknownBarcode(db);
			barcode=commercial.addCommercialProduct(db);
		//Has Barcode
		}else{
			CommercialProduct commercial=new CommercialProduct(barcode,commercialName,brand);
			if(commercial.getCommercialProductId(db).equals("-1"))
				barcode=commercial.addCommercialProduct(db);
			else
				barcode=commercial.getBarcode();
			//productName=element.getProduct();					
		}
		return barcode;
	}
	
	public void persistProduct(ShoppingListElementHelper element){
		String barcode=addCommercialProduct(element.getBarcode(), element.getProduct(), element.getBrand());
		
		//Set the product kind
		ProductKind kind=new ProductKind(element.getKind());
		int productKind=kind.getProductKindId(db);
		
		addProduct(element.getProduct(), barcode, productKind);
	}

}
