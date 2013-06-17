package com.bue.shoppingplanner.free.views;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.helpers.ManageTableType;
import com.bue.shoppingplanner.free.utilities.ScanBarcodeFragmentActivity;
import com.bue.shoppingplanner.free.views.dialogs.AddProductDialogFragment;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class DatabaseMenuActivity extends FragmentActivity {
	private Button hanldeProductsButton,
					manageBuyersButton,
					manageProductCategoriesButton,
					manageShopCategoriesButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_menu);
		// Show the Up button in the action bar.
		setupActionBar();
		//Manage Product Button
		hanldeProductsButton=(Button) findViewById(R.id.manageProductsButton);
		hanldeProductsButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					startActivity(new Intent(DatabaseMenuActivity.this, ManageProductsActivity.class));
				}
				return false;
			}
		});
		
		//Manage Buyers Button
		manageBuyersButton=(Button) findViewById(R.id.manageBuyersButton);
		manageBuyersButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					Intent intent=new Intent(DatabaseMenuActivity.this, ManageTableActivity.class);
					intent.putExtra(ManageTableType.TAG, ManageTableType.USER);
					startActivity(intent);
				}
				return false;
			}
		});
		//Manage Product Categories Button
		manageProductCategoriesButton=(Button) findViewById(R.id.manageProductCategoriesButton);
		manageProductCategoriesButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					Intent intent=new Intent(DatabaseMenuActivity.this, ManageTableActivity.class);
					intent.putExtra(ManageTableType.TAG, ManageTableType.PRODUCTCAT);
					startActivity(intent);
				}
				return false;
			}
		});
		//Manage Shop Types Button
		manageShopCategoriesButton=(Button) findViewById(R.id.manageShopCategoriesButton);
		manageShopCategoriesButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					Intent intent=new Intent(DatabaseMenuActivity.this, ManageTableActivity.class);
					intent.putExtra(ManageTableType.TAG, ManageTableType.SHOPCAT);
					startActivity(intent);
				}
				return false;
			}
		});
		
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
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
