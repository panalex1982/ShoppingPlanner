package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.views.dialogs.StatsFilterDialogFragment;
import com.bue.shoppingplanner.views.fragments.StatsMainFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class StatsActivity extends FragmentActivity implements StatsFilterDialogFragment.StatsFilterDialogListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.stats, menu);
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

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		StatsFilterDialogFragment filters=(StatsFilterDialogFragment)dialog;
		ArrayList<String> filterList=filters.getFiltersList();
		FragmentManager manager=getSupportFragmentManager();		
		StatsMainFragment fragment=(StatsMainFragment) manager.findFragmentByTag(filters.getFragmentTag());
		switch(filters.getFilterListNumber()){
			case 1:
				fragment.setFilterList1(filterList);
				break;
			case 2:
				fragment.setFilterList2(filterList);
				break;
			case 3:
				fragment.setFilterList3(filterList);
				break;
			case 4:
				fragment.setFilterList4(filterList);
				break;
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}

}
