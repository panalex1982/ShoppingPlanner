package com.bue.shoppingplanner.free.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.CurrencyController;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.bue.shoppingplanner.free.views.adapters.ExchangeRatesArrayAdapter;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;

public class ExchangeRatesActivity extends FragmentActivity {
	/**Currencies Spinner*/
	private Spinner rateToCurrencySpinner;
	private CharSequence[] currencies;
	private ArrayAdapter<CharSequence> currenciesAdapter;
	/**Exchange Rates List View*/
	private ListView ratesListView;
	private ExchangeRatesArrayAdapter ratesAdapter;
	private ArrayList<String[]> ratesArrayList;
	/**Currency Controller*/
	private CurrencyController cController;
	
	private AdView adView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_rates);
		// Show the Up button in the action bar.
		setupActionBar();
		cController=new CurrencyController(this);
		
		//Spinner
		rateToCurrencySpinner=(Spinner) findViewById(R.id.rateToCurrencySpinner);
		Resources res=getResources();
		currencies=res.getStringArray(R.array.currencies);
		currenciesAdapter=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_list_item_1,currencies);
		rateToCurrencySpinner.setAdapter(currenciesAdapter);
		//List View		
		ratesListView=(ListView) findViewById(R.id.ratesListView);	
		ratesArrayList=new ArrayList<String[]>();
		ratesAdapter=new ExchangeRatesArrayAdapter(this, R.layout.adapter_element_exchange_rate, ratesArrayList);
		ratesListView.setAdapter(ratesAdapter);
		
		//Spinner Listener
		rateToCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				ratesArrayList.clear();
				ratesArrayList.addAll(cController.getRates((String)currencies[pos]));
				ratesListView.invalidateViews();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
		});
		
		//set default currency to spinner
		int position=0;
		int i=0;
		for(CharSequence iso:currencies){
			if(iso.equals(cController.getDefaultCurrency())){
				position=i;
			}
			i++;
		}
		rateToCurrencySpinner.setSelection(position);	
		AdMobCreator.createAd(this, adView, R.id.exchangesAdMob);
	}
	
	

	@Override
	protected void onDestroy() {
		AdMobCreator.destroyAd(adView);
		super.onDestroy();
	}



	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
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

}
