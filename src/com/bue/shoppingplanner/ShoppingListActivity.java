package com.bue.shoppingplanner;

import java.util.ArrayList;
import java.util.Set;

import com.bue.shoppingplanner.views.AddProductDialogFragment;
import com.bue.shoppingplanner.views.ShoppingListElementArrayAdapter;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ShoppingListActivity extends FragmentActivity implements AddProductDialogFragment.AddProductDialogListener{
	
	private ImageButton addProductButton;
	private ListView shoppingListView;
	private ShoppingListElementArrayAdapter shoppingListAdapter;
	//private Set<String> shoppingListSet;
	private ArrayList<ShoppingListElementHelper> shoppingListArrayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		shoppingListArrayList=new ArrayList<ShoppingListElementHelper>();
		if(savedInstanceState!=null){
			ArrayList<String> savedList=savedInstanceState.getStringArrayList("encodedShoppingList");
			for(String element:savedList){
				ShoppingListElementHelper objectElement=new ShoppingListElementHelper();
				objectElement.decode(element);
				shoppingListArrayList.add(objectElement);
			}
		}
		setContentView(R.layout.activity_shopping_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//addProductButton initialize and listener 
		addProductButton=(ImageButton) findViewById(R.id.addProductButton);		
		addProductButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					showAddProductDialog();
				}
				return false;
			}
		});
		
		//shoppingListView and shoppingListAdapter initialize
		shoppingListView=(ListView) findViewById(R.id.shoppingListView);
		shoppingListAdapter=new ShoppingListElementArrayAdapter(this,R.layout.shopping_list_element_view);
		shoppingListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shoppingListView.setAdapter(shoppingListAdapter);
		addAllElementsOfShoppingListAdapter();		
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
		getMenuInflater().inflate(R.menu.shopping_list, menu);
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ArrayList<String> encodedShoppingList=new ArrayList<String>();
		for(ShoppingListElementHelper element:shoppingListArrayList){
			encodedShoppingList.add(element.encodeObject());
		}
		outState.putStringArrayList("encodedShoppingList", encodedShoppingList);
	}

	public void showAddProductDialog() {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddProductDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddProductDialogFragment");        
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		AddProductDialogFragment addProductDialog=(AddProductDialogFragment) dialog;
		shoppingListArrayList.add(addProductDialog.getListElement());
		addNewElementToShoppingListAdapter();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Do Nothing
		
	}
	
	public void addAllElementsOfShoppingListAdapter(){
		for(ShoppingListElementHelper element:shoppingListArrayList){
			//shoppingListAdapter.add(element.getProduct()+" "+element.getPrice()+" x"+element.getQuantity());
			shoppingListAdapter.add(element);
		}
	}
	
	public void addNewElementToShoppingListAdapter(){
		ShoppingListElementHelper element=shoppingListArrayList.get(shoppingListArrayList.size()-1);
		//shoppingListAdapter.add(element.getProduct()+" "+element.getPrice()+" x"+element.getQuantity());
		shoppingListAdapter.add(element);
	}
	
	
	
	

}
