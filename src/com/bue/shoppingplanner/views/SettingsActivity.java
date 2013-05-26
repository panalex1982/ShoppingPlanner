package com.bue.shoppingplanner.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.CurrencyController;
import com.bue.shoppingplanner.helpers.CurrencyHelper;
import com.bue.shoppingplanner.helpers.VatHelper;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.utilities.SPSharedPreferences;
import com.bue.shoppingplanner.utilities.fileselector.FileSelectorActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class SettingsActivity extends FragmentActivity {
	
	private Spinner currencySettingsSpinner;
	private Button saveSettingsButton,
				saveDBButton;
	private EditText vatStandrdSettingsEditText,
					vatReducedSettingsEditText;
	
	private DatabaseHandler handler;
	private CurrencyController cController;
	private VatHelper vat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();
		handler=new DatabaseHandler(this);
		cController=new CurrencyController(this);
		vat=new VatHelper(this);
		
		currencySettingsSpinner=(Spinner) findViewById(R.id.currencySettingsSpinner);
		currencySettingsSpinner.setSelection(cController.getDefaultCurrencyPosition());
		saveSettingsButton=(Button) findViewById(R.id.saveSettingsButton);
		saveDBButton=(Button) findViewById(R.id.saveDBButton);
		vatStandrdSettingsEditText=(EditText) findViewById(R.id.vatStandrdSettingsEditText);
		vatReducedSettingsEditText=(EditText) findViewById(R.id.vatReducedSettingsEditText);
		vatStandrdSettingsEditText.setText(vat.getStandardRate());
		vatReducedSettingsEditText.setText(vat.getReducedRate());
		
		saveDBButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
					//exportDB();
				startActivity(new Intent(SettingsActivity.this,FileSelectorActivity.class));
			}
		});
		
		saveSettingsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				cController.setDefaultCurrencyFromName(currencySettingsSpinner.getSelectedItem().toString());
				vat.setRates(vatStandrdSettingsEditText.getText().toString(), 
						vatReducedSettingsEditText.getText().toString());
				
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
		getMenuInflater().inflate(R.menu.settings, menu);
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
