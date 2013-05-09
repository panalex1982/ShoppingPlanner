package com.bue.shoppingplanner;

import java.io.IOException;
import java.util.ArrayList;

import com.bue.shoppingplanner.utilities.SPSharedPrefrences;
import com.bue.shoppingplanner.utilities.SerializeObject;
import com.bue.shoppingplanner.views.AddProductDialogFragment;
import com.bue.shoppingplanner.views.AddShopDialogFragment;
import com.bue.shoppingplanner.views.SetListNameDialogFragment;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

public class ShoppingListActivity extends FragmentActivity implements AddProductDialogFragment.AddProductDialogListener, 
														AddShopDialogFragment.AddShopDialogListener,
														SetListNameDialogFragment.SetListNameDialogListener,
														SPSharedPrefrences{
	
	private ImageButton addProductButton;
	private ImageButton saveProductButton;
	private ImageButton addShopButton;
	private ImageButton saveListImageButton;
	private ImageButton persistShoppingListButton;
	
	
	private TextView shopNameTextView;
	private TextView totalCostTextView;
	
	private ListView shoppingListView;
	private ShoppingListElementArrayAdapter shoppingListAdapter;
	private ArrayList<ShoppingListElementHelper> shoppingListArrayList;
	private ShopElementHelper shopElement;
	
	private SharedPreferences savedShoppingList;
	
	private final String SAVED_STATE_SL="encodedShoppingList";
	private final String SAVED_STATE_STORE="encodedStore";
	private String listName;
	
	//It is true when a saved shopping list 
	//has changed, items deleted, or something changed in the items(price, amount etc...) 
	private boolean editList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		savedShoppingList=getSharedPreferences(PREFS_NAME, 0);
		boolean hasList=savedShoppingList.getBoolean(PREFS_HAS_SAVED_FILE, false);
		editList=false;
		
		shopElement=new ShopElementHelper();
		if(savedInstanceState!=null){
			shoppingListArrayList=new ArrayList<ShoppingListElementHelper>();
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
			shoppingListArrayList=new ArrayList<ShoppingListElementHelper>();
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
		}else{
			Bundle openList=getIntent().getExtras();
			if(openList!=null){
				listName=openList.getString("openedListName")==null?"-1":openList.getString("openedListName");
				if(listName!="-1"){
					BoughtController controller=new BoughtController(this);
					shoppingListArrayList=controller.getSavedShoppingList(listName);
				}
			}else{
				shoppingListArrayList=new ArrayList<ShoppingListElementHelper>();
			}
		}
		setContentView(R.layout.activity_shopping_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//Shop Name Initialize
		shopNameTextView=(TextView) findViewById(R.id.shopNameTextView);
		
		//Total Cost Initialize
		totalCostTextView=(TextView) findViewById(R.id.totalCostTextView);
		
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
					persistItems(v.getContext(),0);
				}	
				return false;				
			}
		});
		
		// saveList ImageButton
		saveListImageButton=(ImageButton) findViewById(R.id.saveListImageButton);
		saveListImageButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					showSetListNameDialog();
					// shopping list is saved on the return function
					// of the dialog
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
	protected void showAddProductDialog() {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddProductDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddProductDialogFragment");        
    }
	
	/**
	 * Opens dialog fragment to add the shop of the current buy.
	 */
	protected void showAddShopDialog() {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddShopDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddShopDialogFragment");        
    }
	
	/**
	 * Opens dialog fragment to set shopping list name.
	 */
	protected void showSetListNameDialog() {
		DialogFragment dialog = new SetListNameDialogFragment();
		Bundle listNameBundle=new Bundle();
		listNameBundle.putString("shoppingListName", listName);
		dialog.setArguments(listNameBundle);
        dialog.show(getSupportFragmentManager(), "SetListNameDialogFragment");   		
	}
	
	
	/**
	 * Used to return values from any attached dialog fragment, when the user press OK on the dialog.
	 */
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		
		if(dialog.getClass().getSimpleName().equalsIgnoreCase("AddProductDialogFragment")){
			AddProductDialogFragment addProductDialog=(AddProductDialogFragment) dialog;
			shoppingListArrayList.add(addProductDialog.getListElement());
			editList=true;
		}else if(dialog.getClass().getSimpleName().equalsIgnoreCase("AddShopDialogFragment")){
			AddShopDialogFragment addShopDialog=(AddShopDialogFragment)dialog;
			shopElement=addShopDialog.getShopElement();
			shopNameTextView.setText(shopElement.getName()+" @ "+shopElement.getCity());
		}else if(dialog.getClass().getSimpleName().equalsIgnoreCase("SetListNameDialogFragment")){
			SetListNameDialogFragment setListNameDialogFragment=(SetListNameDialogFragment)dialog;
			listName=setListNameDialogFragment.getListName();
			persistItems(this,1);
		}
		refreshElements();
	}
	
	
	/**
	 * Used to return values from any attached dialog fragment, when the user press Cancel on the dialog.
	 */
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		if(dialog.getClass().getSimpleName().equalsIgnoreCase("SetListNameDialogFragment")){
			String text="Save shopping list has postponed.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(this, text, duration);
			toast.show();
		}	
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
	 * It also changes the total cost field value.
	 */
	public void notifyElementChangesOnShoppingListAdapter(){
		shoppingListAdapter.notifyDataSetChanged();
		totalCostTextView.setText(String.valueOf(shoppingListAdapter.getTotalCost()));
	}
	
	protected void refreshElements(){
		if(shopElement.getName()!=null){
			shopNameTextView.setText(shopElement.getName()+" @ "+shopElement.getCity());
		}else{
			shopNameTextView.setText(R.string.no_shop);
		}
		notifyElementChangesOnShoppingListAdapter();
	}
	
	protected void persistItems(Context context, int persistType){	
		BoughtController controller=new BoughtController(context);
		controller.setProducts(shoppingListArrayList);
		int persist=-1;
		if(persistType==0){
			if(!shopElement.hasName()){
				shopElement=new ShopElementHelper("Unknown","Unknown","0","Unknown","Unknown","Unknown","Unknown", "Unknown");
			}
			controller.setShop(shopElement);
			persist=controller.persistBought(0);
			//If everything persisted I clear the saved list and the shop
			if(persist!=-1){
				SharedPreferences.Editor editor=savedShoppingList.edit();
				editor.putBoolean(PREFS_HAS_SAVED_FILE, false);
				editor.commit();
				try {
					shoppingListArrayList.clear();
					shopElement=new ShopElementHelper();
					//Save shopping list
					SerializeObject.write(context, (Object)shoppingListArrayList,"savedSP.sl");
					//Save shop
					SerializeObject.write(context, (Object)shopElement, "savedShop.sl");
					refreshElements();
				} catch (IOException e) {
					Log.d("Serializing Exception",e.toString());
					e.printStackTrace();
				}
			}
		}else if(persistType==1){
			if(editList || shoppingListAdapter.isListItemChanged())
				controller.deleteShoppingList(listName);
			controller.setListName(listName);
			persist=controller.persistBought(1);
		}
			
		
	}
}
