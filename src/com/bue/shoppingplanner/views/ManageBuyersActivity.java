package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.controllers.BoughtController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ManageBuyersActivity extends Activity {
	private EditText buyerAddUserEditText;
	private Button addUserButton;
	
	/**All Name of the available Buyers*/
	private ListView buyersListView;
	private ArrayList<String> buyersNamesList;
	private ArrayAdapter<String> buyersArrayAdapter;
	
	private BoughtController bController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_buyers);
		bController=new BoughtController(this);
		// Show the Up button in the action bar.
		setupActionBar();
		buyerAddUserEditText=(EditText) findViewById(R.id.buyerAddUserEditText);
		addUserButton=(Button) findViewById(R.id.addUserButton);
		
		//Create List view of buyers
		buyersListView=(ListView) findViewById(R.id.buyersListView);
		buyersNamesList=new ArrayList<String>();
		buyersNamesList.addAll(bController.getAllUserNames());
		buyersArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,buyersNamesList);
		buyersListView.setAdapter(buyersArrayAdapter);
		
		//Listeners
		addUserButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bController.persistUser(buyerAddUserEditText.getText().toString());
				buyerAddUserEditText.setText("");
				buyersNamesList.clear();
				buyersNamesList.addAll(bController.getAllUserNames());
				buyersListView.invalidateViews();
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

}
