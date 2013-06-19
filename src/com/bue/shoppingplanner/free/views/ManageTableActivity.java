package com.bue.shoppingplanner.free.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.controllers.ShopController;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.bue.shoppingplanner.free.helpers.ManageTableType;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ManageTableActivity extends FragmentActivity {
	private TextView addColumnManageTableLabelTextView,
			columnManageTableListLabelTextView;

	private EditText addColumnEditText;
	private Button addColumnButton, deleteColumnButton;

	/** All Name of the available Buyers */
	private ListView itemsListView;
	private ArrayList<String> itemsList;
	private ArrayAdapter<String> itemsArrayAdapter;

	private BoughtController bController;
	private ShopController sController;

	private int functionality;

	private LinearLayout itemsListLinearLayout;
	
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_buyers);

		// Show the Up button in the action bar.
		setupActionBar();

		// Labels
		addColumnManageTableLabelTextView = (TextView) findViewById(R.id.addColumnManageTableLabelTextView);
		columnManageTableListLabelTextView = (TextView) findViewById(R.id.columnManageTableListLabelTextView);

		addColumnEditText = (EditText) findViewById(R.id.buyerAddUserEditText);
		addColumnButton = (Button) findViewById(R.id.addUserButton);
		deleteColumnButton = (Button) findViewById(R.id.deleteColumnbutton);

		// Create List view of buyers
		itemsListView = (ListView) findViewById(R.id.buyersListView);
		itemsList = new ArrayList<String>();
		itemsArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_activated_1, itemsList);
		itemsListView.setAdapter(itemsArrayAdapter);
		itemsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		itemsListView.setItemsCanFocus(false);

		Bundle manageType = this.getIntent().getExtras();
		if (manageType != null) {
			functionality = manageType.getInt(ManageTableType.TAG);
			switch (functionality) {
			case ManageTableType.USER:
				bController = new BoughtController(this);
				itemsList.addAll(bController.getAllUserNames());
				itemsListView.invalidateViews();
				if(itemsList.size()<2)
					deleteColumnButton.setEnabled(false);				
				break;
			case ManageTableType.PRODUCTCAT:
				addColumnManageTableLabelTextView
						.setText(R.string.product_category);
				columnManageTableListLabelTextView
						.setText(R.string.product_categories);
				bController = new BoughtController(this);
				itemsList.addAll(bController.getAllKinds());
				itemsListView.invalidateViews();
				break;
			case ManageTableType.SHOPCAT:
				addColumnManageTableLabelTextView.setText(R.string.shop_type);
				columnManageTableListLabelTextView.setText(R.string.shop_types);
				sController = new ShopController(this);
				itemsList.addAll(sController.getAllShopDescription());
				itemsListView.invalidateViews();
				break;
			case ManageTableType.INIT_USER:
				bController = new BoughtController(this);
				itemsListView.setEnabled(false);
				itemsListLinearLayout=(LinearLayout) findViewById(R.id.itemsListLinearLayout);
				itemsListLinearLayout.removeAllViews();
				break;

			}
		} else {
			bController = new BoughtController(this);
			functionality = ManageTableType.USER;
			itemsList.addAll(bController.getAllUserNames());
			itemsListView.invalidateViews();
		}

		// Listeners
		addColumnButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!addColumnEditText.getText().toString().equals("")){
					switch (functionality) {
					case ManageTableType.USER:
						persistUser();
						break;
					case ManageTableType.PRODUCTCAT:
						persistProductKind();
						break;
					case ManageTableType.SHOPCAT:
						persistShopType();
						break;
					case ManageTableType.INIT_USER:
						persistInitialUser();
						break;
					}
				}else{
					Toast toast=Toast.makeText(getBaseContext(), getBaseContext().getResources().getString(R.string.provide_data), Toast.LENGTH_LONG);
					toast.show();
				}

			}
		});

		deleteColumnButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SparseBooleanArray sparseArray = itemsListView
						.getCheckedItemPositions();
				String error="";
				String tmp[]=prepareDelete(sparseArray);
				switch (functionality) {
				case ManageTableType.USER:
					if(tmp.length<itemsList.size())
						for(int i=0;i<tmp.length;i++)
							error=(deleteUser(tmp[i])=="")?"":tmp[i]+"\n";
					else{
						Toast toast=Toast.makeText(getBaseContext(), getBaseContext().getResources().getText(R.string.zero_user_warning), Toast.LENGTH_LONG);
						toast.show();
					}
					break;
				case ManageTableType.PRODUCTCAT:
					for(int i=0;i<tmp.length;i++)
							error=(deleteProductKind(tmp[i])=="")?"":tmp[i]+"\n";					
					break;
				case ManageTableType.SHOPCAT:
					for(int i=0;i<tmp.length;i++)
							error=(deleteShopType(tmp[i])=="")?"":tmp[i]+"\n";
					break;
				}
				itemsListView.clearChoices();
				if(!error.equals("")){
					error=getResources().getString(R.string.constraint_error)+"\n"+error;
					Toast toast = Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
		
		AdMobCreator.createAd(this, adView, R.id.manageTableAdMob);
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
	
	private void persistInitialUser() {
		bController.persistUser(addColumnEditText.getText().toString());
		addColumnEditText.setText("");
		finish();
	}

	private void persistUser() {
		bController.persistUser(addColumnEditText.getText().toString());
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(bController.getAllUserNames());
		itemsListView.invalidateViews();
		if(itemsList.size()>1 && !deleteColumnButton.isEnabled())
					deleteColumnButton.setEnabled(true);
	}

	private void persistProductKind() {
		bController.persistProductKind(addColumnEditText.getText().toString());
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(bController.getAllKinds());
		itemsListView.invalidateViews();
	}

	private void persistShopType() {
		sController.persistShopDescription(addColumnEditText.getText()
				.toString());
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(sController.getAllShopDescription());
		itemsListView.invalidateViews();
	}

	private String deleteUser(String user) {
		String error=bController.deleteUser(user);
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(bController.getAllUserNames());
		itemsListView.invalidateViews();
		if(itemsList.size()<2)
			deleteColumnButton.setEnabled(false);
		return error;
	}

	private String deleteProductKind(String productKind) {
		String error=bController.deleteProductKind(productKind);
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(bController.getAllKinds());
		itemsListView.invalidateViews();
		return error;
	}
	

	private String deleteShopType(String shopDescription) {
		String error=sController.deleteShopDescription(shopDescription);
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(sController.getAllShopDescription());
		itemsListView.invalidateViews();
		return error;
	}
	
	private String[] prepareDelete(SparseBooleanArray sparseArray){
		String tmp[]=new String[sparseArray.size()];
		for (int i = 0; i < sparseArray.size(); i++) {
			if (sparseArray.valueAt(i)) {
				tmp[i]=itemsList.get(sparseArray.keyAt(i));
			}
		}
		return tmp;
	}

	@Override
	protected void onDestroy() {
		AdMobCreator.destroyAd(adView);
		super.onDestroy();
	}
	
	
	
	

}
