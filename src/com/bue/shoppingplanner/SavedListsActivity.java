package com.bue.shoppingplanner;

import java.util.ArrayList;

import com.bue.shoppingplanner.controllers.BoughtController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class SavedListsActivity extends Activity {
	
	private ListView savedShoppingListView;
	private ArrayAdapter<String> savedListAdapter;
	private ArrayList<String> savedListArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_lists);
		// Show the Up button in the action bar.
		setupActionBar();
		savedShoppingListView=(ListView) findViewById(R.id.savedShoppingListView);
		BoughtController bController=new BoughtController(this);
		savedListArrayList=new ArrayList<String>();
		savedListArrayList=bController.getShoppingListNames();
		savedListAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,savedListArrayList);
		savedShoppingListView.setAdapter(savedListAdapter);
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
		getMenuInflater().inflate(R.menu.saved_lists, menu);
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
