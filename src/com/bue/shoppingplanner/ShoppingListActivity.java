package com.bue.shoppingplanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.bue.shoppingplanner.utilities.SPSharedPrefrences;
import com.bue.shoppingplanner.utilities.SerializeObject;
import com.bue.shoppingplanner.views.AddProductDialogFragment;
import com.bue.shoppingplanner.views.AddShopDialogFragment;
import com.bue.shoppingplanner.views.ShoppingListElementArrayAdapter;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

public class ShoppingListActivity extends FragmentActivity implements AddProductDialogFragment.AddProductDialogListener, AddShopDialogFragment.AddShopDialogListener, SPSharedPrefrences{
	
	private ImageButton addProductButton;
	private ImageButton saveProductButton;
	private ImageButton addShopButton;
	private ImageButton persistShoppingListButton;
	
	private TextView shopNameTextView;
	
	private ListView shoppingListView;
	private ShoppingListElementArrayAdapter shoppingListAdapter;
	private ArrayList<ShoppingListElementHelper> shoppingListArrayList;
	private ShopElementHelper shopElement;
	
	private SharedPreferences savedShoppingList;
	
	private final String SAVED_STATE_SL="encodedShoppingList";
	private final String SAVED_STATE_STORE="encodedStore";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		savedShoppingList=getSharedPreferences(PREFS_NAME, 0);
		boolean hasList=savedShoppingList.getBoolean(PREFS_HAS_SAVED_FILE, false);
		shoppingListArrayList=new ArrayList<ShoppingListElementHelper>();
		shopElement=new ShopElementHelper();
		if(savedInstanceState!=null){
			//Get saved shopping list
			ArrayList<String> savedList=savedInstanceState.getStringArrayList(SAVED_STATE_SL);
			for(String element:savedList){
				ShoppingListElementHelper objectElement=new ShoppingListElementHelper();
				objectElement.decode(element);
				shoppingListArrayList.add(objectElement);
			}
			//Get saved shop
			shopElement.decode(savedInstanceState.getString(SAVED_STATE_STORE));
		}else if(hasList){
			try {
				//Get saved shopping list
				shoppingListArrayList=(ArrayList<ShoppingListElementHelper>)SerializeObject.read(this, "savedSP.sl");
				//Get saved shop
				shopElement=(ShopElementHelper) SerializeObject.read(this, "savedShop.sl");
			} catch (IOException e) {
				Log.d("Serializing Exception",e.toString());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				Log.d("Serializing Exception",e.toString());
				e.printStackTrace();
			}
		}
		setContentView(R.layout.activity_shopping_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//Shop Name Initialize
		shopNameTextView=(TextView) findViewById(R.id.shopNameTextView);
		
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
		
		//editProductButton initialize and listener
		saveProductButton=(ImageButton)findViewById(R.id.saveProductButton);
		saveProductButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
//					test for(int i=1;i<30;i++){
//						shoppingListArrayList.add(new ShoppingListElementHelper("product"+i,"brand"+i,i,i,"test","test",true));
//					}
					try {
						//Save shopping list
						SerializeObject.write(v.getContext(), (Object)shoppingListArrayList,"savedSP.sl");
						//Save shop
						SerializeObject.write(v.getContext(), (Object)shopElement, "savedShop.sl");
						//Tell that saved objects exist
						SharedPreferences.Editor editor=savedShoppingList.edit();
						editor.putBoolean(PREFS_HAS_SAVED_FILE, true);
						editor.commit();
					} catch (IOException e) {
						Log.d("Serializing Exception",e.toString());
						e.printStackTrace();
					}
				}
				return false;
			}
		});
		
		//addShopButton initialize and listener
		addShopButton=(ImageButton) findViewById(R.id.addShopButton);
		addShopButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					showAddShopDialog();
					shopNameTextView.setText(shopElement.getName()+"@"+shopElement.getCity());
				}	
				return false;				
			}
		});
		
		//persistShoppingListButton initialize and listener
		persistShoppingListButton=(ImageButton) findViewById(R.id.persistShoppingListButton);
		persistShoppingListButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					BoughtController controller=new BoughtController();
					controller.setContext(v.getContext());
					controller.setShop(shopElement);
					controller.setProducts(shoppingListArrayList);
					//If everything persisted I clear the saved list and the shop
					if(controller.persistBought()!=-1){
						SharedPreferences.Editor editor=savedShoppingList.edit();
						editor.putBoolean(PREFS_HAS_SAVED_FILE, false);
						editor.commit();
						try {
							shoppingListArrayList.clear();
							shopElement=new ShopElementHelper();
							//Save shopping list
							SerializeObject.write(v.getContext(), (Object)shoppingListArrayList,"savedSP.sl");
							//Save shop
							SerializeObject.write(v.getContext(), (Object)shopElement, "savedShop.sl");
							//Tell that saved objects exist
							SharedPreferences.Editor editorClear=savedShoppingList.edit();
							editorClear.putBoolean(PREFS_HAS_SAVED_FILE, true);
							editorClear.commit();
							refreshElements();
						} catch (IOException e) {
							Log.d("Serializing Exception",e.toString());
							e.printStackTrace();
						}
					}
					
				}	
				return false;				
			}
		});
		
		//shoppingListView and shoppingListAdapter initialize
		shoppingListView=(ListView) findViewById(R.id.shoppingListView);
		shoppingListAdapter=new ShoppingListElementArrayAdapter(this,R.layout.shopping_list_element_view, shoppingListArrayList);
		shoppingListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shoppingListView.setAdapter(shoppingListAdapter);
		//addAllElementsOfShoppingListAdapter();	
		
		//Set values to elements
		refreshElements();
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
		outState.putStringArrayList(SAVED_STATE_SL, encodedShoppingList);
		outState.putString(SAVED_STATE_STORE, shopElement.encodeObject());
	}
	
	

	@Override
	protected void onPause() {
		super.onPause();
		shoppingListAdapter=null;
	}
	
	/**
	 * Opens dialog fragment to add new product to the list.
	 */
	public void showAddProductDialog() {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddProductDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddProductDialogFragment");        
    }
	
	/**
	 * Opens dialog fragment to add the shop of the current buy.
	 */
	public void showAddShopDialog() {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddShopDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddShopDialogFragment");        
    }
	
	
	/**
	 * Used to return values from any attached dialog fragment, when the user press OK on the dialog.
	 */
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		
		if(dialog.getClass().getSimpleName().equalsIgnoreCase("AddProductDialogFragment")){
			AddProductDialogFragment addProductDialog=(AddProductDialogFragment) dialog;
			shoppingListArrayList.add(addProductDialog.getListElement());
		}else if(dialog.getClass().getSimpleName().equalsIgnoreCase("AddShopDialogFragment")){
			AddShopDialogFragment addShopDialog=(AddShopDialogFragment)dialog;
			shopElement=addShopDialog.getShopElement();
			shopNameTextView.setText(shopElement.getName()+" @ "+shopElement.getCity());
		}
		refreshElements();
	}
	
	
	/**
	 * Used to return values from any attached dialog fragment, when the user press Cancel on the dialog.
	 */
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Do Nothing		
	}
	
	/**
	 * Adds all the Shopping list elements of the ArrayList to the ArrayAdapter
	 * @deprecated
	 */
	public void addAllElementsOfShoppingListAdapter(){
		for(ShoppingListElementHelper element:shoppingListArrayList){
			shoppingListAdapter.add(element);			
		}
	}
	
	/**
	 * Notify element changes to the ShoppingListArrayAdapter.
	 */
	public void notifyElementCahngesOnShoppingListAdapter(){
		shoppingListAdapter.notifyDataSetChanged();
	}
	
	protected void refreshElements(){
		if(shopElement.getName()!=null){
			shopNameTextView.setText(shopElement.getName()+" @ "+shopElement.getCity());
		}else{
			shopNameTextView.setText(R.string.no_shop);
		}
		notifyElementCahngesOnShoppingListAdapter();
	}

	
	
	
	
	

}
