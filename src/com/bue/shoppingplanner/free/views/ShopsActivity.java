package com.bue.shoppingplanner.free.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.ShopController;
import com.bue.shoppingplanner.free.helpers.ShopElementHelper;
import com.bue.shoppingplanner.free.views.dialogs.AddShopDialogFragment;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ShopsActivity extends FragmentActivity implements AddShopDialogFragment.AddShopDialogListener{
	private ImageButton addShopSetImageButton;
	private ListView shopsShopsListView;
	
	private ArrayAdapter<String> shopsAdapter;
	private ArrayList<String> shopsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shops);
		// Show the Up button in the action bar.
		setupActionBar();
		
		intitialize();
		
		
		//Add Shop
		addShopSetImageButton=(ImageButton) findViewById(R.id.addShopShopsImageButton);
		addShopSetImageButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					showAddShopDialog();
				}	
				return false;				
			}
		});
		
		//Shops List View
		shopsShopsListView=(ListView) findViewById(R.id.shopsShopsListView);
		ArrayList<ShopElementHelper> shops=new ArrayList<ShopElementHelper>();
		ShopController spController=new ShopController(this);
		shops=spController.getAllShops();
		for(ShopElementHelper element:shops){
			shopsList.add(element.getName()+" @ "+element.getAddress()+" "+element.getNumber()+" "+element.getCity());
		}
		shopsAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shopsList);
		shopsShopsListView.setAdapter(shopsAdapter);
	}

	private void intitialize() {
		shopsList=new ArrayList<String>();
		
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
	
	
	
	/**
	 * Opens dialog fragment to add the shop of the current buy.
	 */
	public void showAddShopDialog() {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddShopDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddShopDialogFragment");        
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		AddShopDialogFragment dialogFragment=(AddShopDialogFragment)dialog;
		ShopElementHelper shop=dialogFragment.getShopElement();
		ShopController shopController=new ShopController(this);
		shopController.setElement(shop);
		int shopId=shopController.persistShop();
		CharSequence text = "ShopId: "+shopId+": ";
		if(shopId==-1){
			text=text+"Shop did't saved!\n";
		}else{
			text=text+shop.getType()+" "+shop.getName()+" at "+shop.getAddress()+", "
					+shop.getNumber()+", "+shop.getCity()+" saved!\n";
			
			shopsList.add(shop.getName()+" @ "+shop.getAddress()+" "+shop.getNumber()+" "+shop.getCity());
			shopsAdapter.notifyDataSetChanged();
		}
		
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(this, text, duration);
		toast.show();	
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

}
