package com.bue.shoppingplanner.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.Dbh;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;

public class ShopController {
	private ShopElementHelper element;
	private Context context;
	private Dbh db=null;

	public ShopController(ShopElementHelper element) {
		super();
		this.element = element;		
	}

	public ShopController(Context context) {
		super();
		this.element=new ShopElementHelper();
		this.context = context;
		db=new Dbh(context);
	}
	
	public ShopElementHelper getElement() {
		return element;
	}
	
	public void selectShopElementHelperByShopName(String shopName) {
		Shop shopModel=new Shop();
		shopModel.setName(shopName);
		shopModel.getShopByName(db);
		Address address=new Address();
		address=address.getAddress(db, shopModel.getAddress());
		ShopDescription shopDesc=new ShopDescription();
		shopDesc=shopDesc.getShopDescription(db, shopModel.getShopDescription());
		element=new ShopElementHelper(shopModel, address, shopDesc);
	}

	public void setElement(ShopElementHelper element) {
		this.element = element;
	}
	
	public String getName() {
		return element.getName();
	}

	public void setName(String name) {
		element.setName(name);
	}

	public String getAddress() {
		return element.getAddress();
	}

	public void setAddress(String address) {
		element.setAddress(address);
	}

	public String getNumber() {
		return element.getNumber();
	}

	public void setNumber(String number) {
		element.setNumber(number);
	}

	public String getCity() {
		return element.getCity();
	}

	public void setCity(String city) {
		element.setCity(city);
	}

	public String getArea() {
		return element.getArea();
	}

	public void setArea(String area) {
		element.setArea(area);
	}

	public String getCountry() {
		return element.getCountry();
	}

	public void setCountry(String country) {
		element.setCountry(country);
	}

	public String getZip() {
		return element.getZip();
	}

	public void setZip(String zip) {
		element.setZip(zip);
	}

	public String getType() {
		return element.getType();
	}

	public void setType(String type) {
		element.setType(type);
	}
	
	public ArrayList<ShopElementHelper> getAllShops(){
		ArrayList<ShopElementHelper> shopList=new ArrayList<ShopElementHelper>();
		ArrayList<Shop> shops=(ArrayList<Shop>) Shop.getAllShop(db);
		for(Shop shopModel:shops){
			ShopElementHelper shop=new ShopElementHelper();
			shop.setName(shopModel.getName());
			Address address=Address.getAddress(db, shopModel.getAddress());
			shop.setAddress(address.getStreetName());
			shop.setNumber(address.getNumber());
			shop.setCity(address.getCity());
			shop.setAddress(address.getArea());
			shop.setCountry(address.getCountry());
			shop.setZip(address.getZip());
			ShopDescription type=new ShopDescription();
			type=ShopDescription.getShopDescription(db,shopModel.getShopDescription());
			shop.setType(type.getName());
			shopList.add(shop);
		}
		return shopList;
	}
	
	/**
	 * Persist new shop with the given (from ShopElementHeleper element)
	 * address and shop description.
	 * Method returns the shop id that adds to the database. If the shop already
	 * exist it return the shop id of the existing shop.
	 *  
	 * @return
	 */
	public int persistShop(){
		//Create Shop Model and Persist shop
				Shop shopModel=new Shop();
				shopModel.setName(element.getName());
				//Check if shop exist in database
				int shopId = shopModel.getShopId(db);
				//Log.d("SHOP_ID: ", Integer.toString(shopId));
				if(shopId==-1){
					Address address=new Address(element.getAddress(), element.getNumber(), element.getCity(), element.getArea(),
							element.getCountry(), element.getZip());
					ShopDescription shopDesc=new ShopDescription(element.getType());
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
		return shopId;
	}
	
	/**
	 * Returns a list with the names of all shopDescriptions.
	 * @return
	 */
	public ArrayList<String> getAllShopDescription(){
		ArrayList<String> shopTypeArrayList=new ArrayList<String>();
		for(ShopDescription desc:ShopDescription.getAllShopDescription(db)){			
			shopTypeArrayList.add(desc.getName());
		}
		return shopTypeArrayList;
	}
	
	public void persistShopDescription(String shopDescription){
		ShopDescription desc=new ShopDescription(shopDescription);
		desc.addShopDescription(db);
	}
	
	public String deleteShopDescription(String shopDescription){
		return ShopDescription.deleteShopDescription(db, shopDescription);
	}
}
