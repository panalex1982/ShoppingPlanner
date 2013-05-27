package com.bue.shoppingplanner;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.CurrencyController;
import com.bue.shoppingplanner.helpers.ExchangesAsyncTask;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.CommercialProduct;
import com.bue.shoppingplanner.model.Currencies;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.JsonUpdate;
import com.bue.shoppingplanner.model.User;
import com.bue.shoppingplanner.model.ProductKind;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;
import com.bue.shoppingplanner.model.UnknownBarcode;
import com.bue.shoppingplanner.views.MainMenuActivity;
import com.bue.shoppingplanner.views.SettingsActivity;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

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
		String updateDateString=lastUpdate.getDate();
		SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyhhmmss");
		Date updateDate;
		try {
			updateDate = format.parse(updateDateString);
			long differnce=getDifferenceFromNow(updateDate);
			Log.i("Days differnece: ",""+differnce);
			if(differnce>7){
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
				User group=new User();
				group.setName("Home");//1
				group.addUser(db);
								
				//Insert Kinds
				ProductKind kind=new ProductKind();
				kind.setName("Food");//1		
				kind.addProductKind(db);
				kind.setName("Drink");//2		
				kind.addProductKind(db);
				kind.setName("Health");//3		
				kind.addProductKind(db);
				kind.setName("Household");//4		
				kind.addProductKind(db);
				kind.setName("Clothes");//5		
				kind.addProductKind(db);
				kind.setName("Entertainment");//6		
				kind.addProductKind(db);
				kind.setName("Technology");//7		
				kind.addProductKind(db);
				kind.setName("Insurance");//8		
				kind.addProductKind(db);
				kind.setName("Loan");//9		
				kind.addProductKind(db);
				kind.setName("Home Equipment");//10		
				kind.addProductKind(db);
				kind.setName("Travel");//11		
				kind.addProductKind(db);
				kind.setName("Bills");//12		
				kind.addProductKind(db);
				kind.setName("Personal Care");//13		
				kind.addProductKind(db);
				kind.setName("Children Products");//14		
				kind.addProductKind(db);
				kind.setName("Rent");//15		
				kind.addProductKind(db);
				kind.setName("Subscriptions");//16		
				kind.addProductKind(db);
				kind.setName("Taxes");//17		
				kind.addProductKind(db);
				kind.setName("Electronics");//18		
				kind.addProductKind(db);
				kind.setName("Tobaccos");//19		
				kind.addProductKind(db);
				kind.setName("Vehicle");//20		
				kind.addProductKind(db);
				kind.setName("Pet");//21
				kind.addProductKind(db);
				kind.setName("Other");//22		
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
				desc.setName("Mini Market");
				desc.addShopDescription(db);//4
				desc.setName("Speciality Store");
				desc.addShopDescription(db);//5
				desc.setName("Entertainment Center");
				desc.addShopDescription(db);//6
				desc.setName("Medical");
				desc.addShopDescription(db);//7
				desc.setName("Internet Store");
				desc.addShopDescription(db);//8
				desc.setName("Service Station");
				desc.addShopDescription(db);//9
				desc.setName("Official Store");
				desc.addShopDescription(db);//10
				desc.setName("Bill Payment Service");
				desc.addShopDescription(db);//11
				desc.setName("Tax Payment Service");
				desc.addShopDescription(db);//12
				desc.setName("Bank");
				desc.addShopDescription(db);//13
				desc.setName("Other");
				desc.addShopDescription(db);//14
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
				initializeDefaultCurrency();
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
	
	private long getDifferenceFromNow(Date date1){
		long days=0;
		Calendar cal1=Calendar.getInstance();
		cal1.setTime(date1);
		long mils1=cal1.getTimeInMillis();
		Calendar cal2=Calendar.getInstance();
		long mils2=cal2.getTimeInMillis();
		days=(mils2-mils1)/(1000*60*60*24);
		return days;
	}
	
	private void initializeDefaultCurrency(){
		Locale locale;
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tm.getSimCountryIso();
		if(countryCode!="")
			locale=new Locale(countryCode,countryCode);
		else
			locale=getResources().getConfiguration().locale;
		CurrencyController currencyController=new CurrencyController(this);
		currencyController.setDefaultCurrencyFromLocale(locale);
	}

}
