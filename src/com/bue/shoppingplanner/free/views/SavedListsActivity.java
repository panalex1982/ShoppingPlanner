package com.bue.shoppingplanner.free.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SavedListsActivity extends FragmentActivity {

	private ListView savedShoppingListView;
	private ArrayAdapter<String> savedListAdapter;
	private ArrayList<String> savedListArrayList;

	//private ImageButton addSavedListImageButton;

	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_lists);
		// Show the Up button in the action bar.
		setupActionBar();
		savedShoppingListView = (ListView) findViewById(R.id.savedShoppingListView);
		BoughtController bController = new BoughtController(this);
		savedListArrayList = new ArrayList<String>();
		savedListArrayList = bController.getShoppingListNames();
		savedListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, savedListArrayList);
		savedShoppingListView.setAdapter(savedListAdapter);
		savedShoppingListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent shoppingListIntent = new Intent(arg1
								.getContext(), ShoppingListActivity.class);
						shoppingListIntent.putExtra(
								"openedListName",
								savedShoppingListView.getItemAtPosition(
										position).toString());
						startActivity(shoppingListIntent);

					}
				});

//		// Add List Image Button
//		addSavedListImageButton = (ImageButton) findViewById(R.id.addSavedListImageButton);
//		addSavedListImageButton.setOnTouchListener(new View.OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//
//				}
//				return false;
//			}
//		});

		AdMobCreator.createAd(this, adView, R.id.savedListsAdMob);
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
