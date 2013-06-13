package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.controllers.ShopController;
import com.bue.shoppingplanner.helpers.ManageTableType;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ManageTableActivity extends Activity {
	private TextView addColumnManageTableLabelTextView,
					columnManageTableListLabelTextView;
	
	private EditText addColumnEditText;
	private Button addColumnButton;
	
	/**All Name of the available Buyers*/
	private ListView itemsListView;
	private ArrayList<String> itemsList;
	private ArrayAdapter<String> itemsArrayAdapter;
	
	private BoughtController bController;
	private ShopController sController;
	
	private int functionality;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_buyers);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		//Labels
		addColumnManageTableLabelTextView=(TextView) findViewById(R.id.addColumnManageTableLabelTextView);
		columnManageTableListLabelTextView=(TextView) findViewById(R.id.columnManageTableListLabelTextView);
		
		addColumnEditText=(EditText) findViewById(R.id.buyerAddUserEditText);
		addColumnButton=(Button) findViewById(R.id.addUserButton);
		
		//Create List view of buyers
		itemsListView=(ListView) findViewById(R.id.buyersListView);
		itemsList=new ArrayList<String>();
		itemsArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemsList);
		itemsListView.setAdapter(itemsArrayAdapter);
		Bundle manageType=this.getIntent().getExtras();
		if(manageType!=null){
			functionality=manageType.getInt(ManageTableType.TAG);
			switch(functionality){
				case ManageTableType.USER:
					bController=new BoughtController(this);
					itemsList.addAll(bController.getAllUserNames());
					itemsListView.invalidateViews();
					break;
				case ManageTableType.PRODUCTCAT:
					addColumnManageTableLabelTextView.setText(R.string.product_category);
					columnManageTableListLabelTextView.setText(R.string.product_categories);
					bController=new BoughtController(this);
					itemsList.addAll(bController.getAllKinds());
					itemsListView.invalidateViews();
					break;
				case ManageTableType.SHOPCAT:
					addColumnManageTableLabelTextView.setText(R.string.shop_type);
					columnManageTableListLabelTextView.setText(R.string.shop_types);
					sController=new ShopController(this);
					itemsList.addAll(sController.getAllShopDescription());
					itemsListView.invalidateViews();
					break;
				
			}
		}else{
			bController=new BoughtController(this);
			functionality=ManageTableType.USER;
			itemsList.addAll(bController.getAllUserNames());
			itemsListView.invalidateViews();
		}
		
		//Listeners
		addColumnButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(functionality){
				case ManageTableType.USER:
					persistUser();
					break;
				case ManageTableType.PRODUCTCAT:
					persistProductKind();
					break;
				case ManageTableType.SHOPCAT:
					persistShopType();
					break;
			}
				
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_buyers, menu);
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
	
	private void persistUser(){
		bController.persistUser(addColumnEditText.getText().toString());
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(bController.getAllUserNames());
		itemsListView.invalidateViews();
	}
	
	private void persistProductKind(){
		bController.persistProductKind(addColumnEditText.getText().toString());
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(bController.getAllKinds());
		itemsListView.invalidateViews();
	}
	
	private void persistShopType(){
		sController.persistShopDescription(addColumnEditText.getText().toString());
		addColumnEditText.setText("");
		itemsList.clear();
		itemsList.addAll(sController.getAllShopDescription());
		itemsListView.invalidateViews();
	}

}