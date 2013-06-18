package com.bue.shoppingplanner.free;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.bue.shoppingplanner.free.asynctascs.ExchangesAsyncTask;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.controllers.CurrencyController;
import com.bue.shoppingplanner.free.controllers.ShopController;
import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.bue.shoppingplanner.free.helpers.ManageTableType;
import com.bue.shoppingplanner.free.model.Address;
import com.bue.shoppingplanner.free.model.CommercialProduct;
import com.bue.shoppingplanner.free.model.Currencies;
import com.bue.shoppingplanner.free.model.Dbh;
import com.bue.shoppingplanner.free.model.JsonUpdate;
import com.bue.shoppingplanner.free.model.ProductKind;
import com.bue.shoppingplanner.free.model.Shop;
import com.bue.shoppingplanner.free.model.UnknownBarcode;
import com.bue.shoppingplanner.free.utilities.Keys;
import com.bue.shoppingplanner.free.views.ManageTableActivity;
import com.bue.shoppingplanner.free.views.SettingsActivity;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.google.ads.*;

public class IntroActivity extends FragmentActivity {

	private Dbh db;
	private JsonUpdate lastUpdate;
	private AdView adView;
	private AdView adView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Test Barcode Scanner
		setContentView(R.layout.activity_intro);
		// Test Plot
		db = new Dbh(this);
		initializeDatabase(this);
		lastUpdate = JsonUpdate.getJsonUpdate(db);
		String updateDateString = lastUpdate.getDate();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmmss");
		Date updateDate;
		try {
			updateDate = format.parse(updateDateString);
			long differnce = getDifferenceFromNow(updateDate);
			Log.i("Days differnece: ", "" + differnce);
			if (differnce > 7) {
				ExchangesAsyncTask async = new ExchangesAsyncTask();
				async.execute("http://openexchangerates.org/api/latest.json?app_id="
						+ Keys.OPEN_RATES);
				try {
					ArrayList<Currencies> result = async.get();
					for (Currencies item : result) {
						// Log.i(item.getId(),""+item.getRateToUsd());
						item.updateCurrencies(db);
						lastUpdate = new JsonUpdate();
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
		AdMobCreator.createAd(this, adView, R.id.intro_ad_mob);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			openMainApplication();
		}
		return true;
	}

	/**
	 * Opens the Main Menu Activity.
	 */
	private void openMainApplication() {
		;
		try {
			Intent main = new Intent(IntroActivity.this, MainMenuActivity.class);
			// main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(main);
			finish();
		} catch (Exception ex) {
			String exs = ex.toString();
			Log.d("Exception", exs);
		}
	}

	/**
	 * Adds default records to the database.
	 */

	public void initializeDatabase(Context context) {
		try {
			if (ProductKind.getProductKindCount(db) <= 0) {
				BoughtController bController = new BoughtController(context);
				ShopController sContreller = new ShopController(context);
				String unknown = context.getResources().getString(
						R.string.unknown);
				// Insert Groups
				// User group=new User();
				// group.setName("Home");//1
				// group.addUser(db);

				// Insert Kinds
				String[] kindArray = context.getResources().getStringArray(
						R.array.product_categories);
				for (String kind : kindArray) {
					bController.persistProductKind(kind);
				}
				// Insert Unknown/No Specified barcode
				CommercialProduct cp = new CommercialProduct("-1", unknown,
						unknown);
				cp.addCommercialProduct(db);
				UnknownBarcode ub = new UnknownBarcode(-1);
				ub.addUnknownBarcode(db);
				// Insert Shop Description
				String[] shopCategoriesArray = context.getResources()
						.getStringArray(R.array.shop_categories);
				for (String shopCategory : shopCategoriesArray) {
					sContreller.persistShopDescription(shopCategory);
				}
				// Insert Address
				Address address = new Address();
				address.setStreetName(unknown);
				address.setNumber("0");
				address.setCity(unknown);
				address.setArea(unknown);
				address.setZip(unknown);
				address.setCountry(unknown);
				address.addAddress(db);// 1
				Shop shop = new Shop();
				shop.setName(unknown);
				shop.setAddress(1);
				shop.setShopDescription(1);
				shop.addShop(db);// 1
				// Currencies
				initializeCurrencies();
				initializeDefaultCurrency();
				Intent initializeSettings = new Intent(IntroActivity.this,
						SettingsActivity.class);
				initializeSettings.putExtra("initSettings", true);
				startActivityForResult(initializeSettings, 0);
			}
		} catch (Exception ex) {
			Log.d("Initialize Exception", ex.toString());
		}

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 0) {
			Intent initializeSettings = new Intent(IntroActivity.this,
					ManageTableActivity.class);
			initializeSettings.putExtra(ManageTableType.TAG,
					ManageTableType.INIT_USER);
			startActivityForResult(initializeSettings, 1);
		}
	}

	private void initializeCurrencies() {
		ExchangesAsyncTask async = new ExchangesAsyncTask();
		async.execute("http://openexchangerates.org/api/latest.json?app_id=");
		try {
			ArrayList<Currencies> result = async.get();
			for (Currencies item : result) {
				item.addCurrencies(db);
			}
			lastUpdate = new JsonUpdate();
			lastUpdate.addJsonUpdate(db);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private long getDifferenceFromNow(Date date1) {
		long days = 0;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		long mils1 = cal1.getTimeInMillis();
		Calendar cal2 = Calendar.getInstance();
		long mils2 = cal2.getTimeInMillis();
		days = (mils2 - mils1) / (1000 * 60 * 60 * 24);
		return days;
	}

	private void initializeDefaultCurrency() {
		Locale locale;
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tm.getSimCountryIso();
		if (countryCode != "")
			locale = new Locale(countryCode, countryCode);
		else
			locale = getResources().getConfiguration().locale;
		CurrencyController currencyController = new CurrencyController(this);
		currencyController.setDefaultCurrencyFromLocale(locale);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.only_about_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_only_about:
			DialogOpener.showAboutDialog(getSupportFragmentManager());
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
//		if (adView != null) {
//			adView.destroy();
//		}
		AdMobCreator.destroyAd(adView);
		super.onDestroy();
	}

}
