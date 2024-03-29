package com.bue.shoppingplanner.free.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.bue.shoppingplanner.free.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.free.utilities.ScanBarcodeFragmentActivity;
import com.bue.shoppingplanner.free.views.dialogs.AddProductDialogFragment;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ManageProductsActivity extends FragmentActivity implements AddProductDialogFragment.AddProductDialogListener{

	private ImageButton barcodeScanManageProductImageButton,
			addProductManageProductImageButton;

	private ExpandableListView productsManageProductExpandableListView;
	private SimpleExpandableListAdapter productsAdapter;
	
//	private ArrayList<String> kindList;
//	private ArrayList<ArrayList<String>> productList;
	
	private List<Map<String, String>> kindList;
	private List<List<Map<String, String>>>	productList;
	
	private AdView adView;
	

	private BoughtController boughtController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_products);
		// Show the Up button in the action bar.
		setupActionBar();
		
		boughtController=new BoughtController(this);

		// Add Product
		addProductManageProductImageButton = (ImageButton) findViewById(R.id.addProductManageProductImageButton);
		addProductManageProductImageButton
				.setOnTouchListener(new View.OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_UP) {
							DialogOpener
									.showAddProductDialog(getSupportFragmentManager(),true);
						}
						return false;
					}
				});

		// Scan Barcode
		barcodeScanManageProductImageButton = (ImageButton) findViewById(R.id.barcodeScanManageProductImageButton);
		barcodeScanManageProductImageButton
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivityForResult(new Intent(
								ManageProductsActivity.this,
								ScanBarcodeFragmentActivity.class), 1);
					}
				});
		
		kindList=new ArrayList<Map<String, String>>();
		productList=new ArrayList<List<Map<String, String>>>();
		
		//Products ExpandableListView
		productsManageProductExpandableListView=(ExpandableListView) findViewById(R.id.productsManageProductExpandableListView);
		productsAdapter=new SimpleExpandableListAdapter(this, 
				kindList,
				android.R.layout.simple_expandable_list_item_1,
				new String[] { "name", "product_name" },
                new int[] { android.R.id.text1, android.R.id.text2 }, 
                productList,
                android.R.layout.simple_expandable_list_item_1,				
				new String[] { "name", "product_name" },
                new int[] { android.R.id.text1, android.R.id.text2 });
		productsManageProductExpandableListView.setAdapter(productsAdapter);
		
		updateKindAndProductArrayLists();
		AdMobCreator.createAd(this, adView, R.id.manageProductsAdMob);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			Bundle extras = data.getExtras();
			String barcode = (extras.getString("lastBarcodeScan") == null ? "noBarcode"
					: extras.getString("lastBarcodeScan"));
			if (!barcode.equals("noBarcode") && !barcode.equals("") && !barcode.equals("cancel")) {
				DialogOpener.showAddProductDialog(getSupportFragmentManager(),
						barcode, true);
			}
		}
	}
	
	public void updateKindAndProductArrayLists(){
		for (String kindName : boughtController.getAllKindNames()) {
			Map<String, String> kindMap = new HashMap<String, String>();
			kindMap.put("name", kindName);
			kindMap.put("product_name", kindName);
            kindList.add(kindMap);
            
            List<Map<String, String>> productListOfKind = new ArrayList<Map<String, String>>();
            for (String productName:boughtController.getAllProductNamesOfKind(kindName)) {
                Map<String, String> productMap = new HashMap<String, String>();
                productMap.put("name", productName);
                productMap.put("product_name", productName);
    			productListOfKind.add(productMap);
            }
            productList.add(productListOfKind);
        }
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		AddProductDialogFragment addProductDialog=(AddProductDialogFragment) dialog;
		ShoppingListElementHelper element=addProductDialog.getListElement();
		BoughtController bController=new BoughtController(this);
		bController.persistProduct(element);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		AdMobCreator.destroyAd(adView);
		super.onDestroy();
	}
	
	
	
//	public void updateKindAndProductArrayLists(){
//		kindList.addAll(boughtController.getAllKindNames());
//		for (String kindName : kindList) {
//			ArrayList<String> tmpList = boughtController.getProductNamesOfKind(kindName);
//			productList.add(tmpList);
//		}
//	}

}
