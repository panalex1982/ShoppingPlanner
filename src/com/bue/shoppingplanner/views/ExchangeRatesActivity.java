package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.controllers.CurrencyController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ExchangeRatesActivity extends FragmentActivity {
	private ListView ratesListView;
	private ArrayAdapter ratesAdapter;
	private ArrayList<String> ratesArrayList;
	private CurrencyController cController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_rates);
		// Show the Up button in the action bar.
		setupActionBar();
		cController=new CurrencyController(this);
		ratesListView=(ListView) findViewById(R.id.ratesListView);		
		ratesArrayList=cController.getRates("EUR");
		ratesAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, ratesArrayList);
		ratesListView.setAdapter(ratesAdapter);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exchange_rates, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

}
