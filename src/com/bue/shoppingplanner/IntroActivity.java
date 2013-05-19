package com.bue.shoppingplanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.helpers.ExchangesAsyncTask;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.CommercialProduct;
import com.bue.shoppingplanner.model.Currencies;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.JsonUpdate;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;
import com.bue.shoppingplanner.model.UnknownBarcode;
import com.bue.shoppingplanner.views.MainMenuActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

public class IntroActivity extends Activity {
	
	private DatabaseHandler db;
	private JsonUpdate lastUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		db=new DatabaseHandler(this);
		initializeDatabase();
		lastUpdate=JsonUpdate.getJsonUpdate(db);
		if(lastUpdate.getId()!=1){
			//initializeCurrencies();
		}else{
			String updateDateString=lastUpdate.getDate();
			SimpleDateFormat format=new SimpleDateFormat();
			Date updateDate;
			try {
				updateDate = format.parse(updateDateString);		
				if(updateDate.compareTo(new Date())>7){
					ExchangesAsyncTask async=new ExchangesAsyncTask();
					async.execute("http://openexchangerates.org/api/latest.json?app_id=");
					try {
						ArrayList<Currencies> result=async.get();
						for(Currencies item:result){
							Log.i(item.getId(),""+item.getRateToUsd());
							item.updateCurrencies(db);
							lastUpdate=new JsonUpdate();
							lastUpdate.addJsonUpdate(db);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_UP){
			openMainApplication();
		}
		return true;
	}
	
	/**
	 * Opens the Main Menu Activity.
	 */
	private void openMainApplication(){;
		try{
			startActivity(new Intent(IntroActivity.this, MainMenuActivity.class));
		}catch(Exception ex){
			String exs=ex.toString();
			Log.d("Exception", exs);
		}	
	}
	
	/**
	 * Adds default records to the database.
	 */
	
	public void initializeDatabase(){
		try{
			if(ProductKind.getProductKindCount(db)<=0){
				//Insert Groups
				ProductGroup group=new ProductGroup();
				group.setName("Main Need");//1
				group.addProductGroup(db);
				group.setName("Secondary Need");//2
				group.addProductGroup(db);
				group.setName("Other");//3
				group.addProductGroup(db);
				group.setName("Bill");//4
				group.addProductGroup(db);
				group.setName("Tax");//5
				group.addProductGroup(db);
				group.setName("Entertainment");//6
				group.addProductGroup(db);
				group.setName("Maintenance");//7
				group.addProductGroup(db);
				
				//Insert Kinds
				ProductKind kind=new ProductKind();
				kind.setName("Food");//1		
				kind.addProductKind(db);
				kind.setName("Drinks");//2		
				kind.addProductKind(db);
				kind.setName("Health");//3		
				kind.addProductKind(db);
				kind.setName("Telecomunication");//4		
				kind.addProductKind(db);
				kind.setName("Sports");//5		
				kind.addProductKind(db);
				kind.setName("Hobbies");//6		
				kind.addProductKind(db);
				kind.setName("Technology");//7		
				kind.addProductKind(db);
				kind.setName("Work Equipment");//8		
				kind.addProductKind(db);
				kind.setName("Games");//9		
				kind.addProductKind(db);
				kind.setName("Home Equipment");//10		
				kind.addProductKind(db);
				kind.setName("Travel");//11		
				kind.addProductKind(db);
				kind.setName("Household");//12		
				kind.addProductKind(db);
				kind.setName("Beauty/Personal Care");//13		
				kind.addProductKind(db);
				kind.setName("Children Products");//14		
				kind.addProductKind(db);
				kind.setName("Mobile Phone Bills");//15		
				kind.addProductKind(db);
				kind.setName("Phone Bills");//16		
				kind.addProductKind(db);
				kind.setName("Energy Bills");//17		
				kind.addProductKind(db);
				kind.setName("Electronics");//18		
				kind.addProductKind(db);
				kind.setName("Cigarettes");//19		
				kind.addProductKind(db);
				kind.setName("Vehicles & Parts");//20		
				kind.addProductKind(db);
				kind.setName("Clothes");//21		
				kind.addProductKind(db);
				kind.setName("Heyngine");//22		
				kind.addProductKind(db);
				kind.setName("Pet");//23		
				kind.addProductKind(db);
				kind.setName("Home Entertainment");//24		
				kind.addProductKind(db);
				kind.setName("Outside Entertainment");//25		
				kind.addProductKind(db);
				kind.setName("Other");//26		
				kind.addProductKind(db);
				//Insert Unknown/No Specified barcode
				CommercialProduct cp=new CommercialProduct("-1","Unknown", "Unknown");
				cp.addCommercialProduct(db);
				UnknownBarcode ub=new UnknownBarcode(-1);
				ub.addUnknownBarcode(db);				
				//Insert Shop Description
				ShopDescription desc=new ShopDescription();
				desc.setName("Unknown");
				desc.addShopDescription(db);//1
				desc.setName("Local Store");
				desc.addShopDescription(db);//2
				desc.setName("Super Market");
				desc.addShopDescription(db);//3
				desc.setName("Specialized Store");
				desc.addShopDescription(db);//4
				desc.setName("Mini Market");
				desc.addShopDescription(db);//5
				desc.setName("Official Store");
				desc.addShopDescription(db);//6
				desc.setName("Other");
				desc.addShopDescription(db);//7
				//Insert Address
				Address address=new Address();
				address.setStreetName("Unknown");
				address.setNumber("0");
				address.setCity("Unknown");
				address.setArea("Unknown");
				address.setZip("Unknown");
				address.setCountry("Unknown");
				address.addAddress(db);//1
				Shop shop=new Shop();
				shop.setName("Unknown");
				shop.setAddress(1);
				shop.setShopDescription(1);
				shop.addShop(db);//1
				//Currencies
				initializeCurrencies();
			}
		}catch(Exception ex){
			Log.d("Initialize Exception", ex.toString());
		}
		
	}
	
	private void initializeCurrencies(){
		ExchangesAsyncTask async=new ExchangesAsyncTask();
		async.execute("http://openexchangerates.org/api/latest.json?app_id=");
		try {
			ArrayList<Currencies> result=async.get();
			for(Currencies item:result){
				item.addCurrencies(db);
			}
			lastUpdate=new JsonUpdate();
			lastUpdate.addJsonUpdate(db);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
