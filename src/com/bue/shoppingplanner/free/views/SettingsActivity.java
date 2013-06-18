package com.bue.shoppingplanner.free.views;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.CurrencyController;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.bue.shoppingplanner.free.helpers.VatHelper;
import com.bue.shoppingplanner.free.utilities.SerializeObject;
import com.bue.shoppingplanner.free.utilities.fileselector.FileOperation;
import com.bue.shoppingplanner.free.utilities.fileselector.FileSelector;
import com.bue.shoppingplanner.free.utilities.fileselector.OnHandleFileListener;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

public class SettingsActivity extends FragmentActivity {
	
	private Spinner currencySettingsSpinner;
	private Button saveSettingsButton,
				saveDBButton,
				loadDBButton,				
				unlockCurrecnyButton;
	private EditText vatStandrdSettingsEditText,
					vatReducedSettingsEditText;
	
	private CurrencyController cController;
	private VatHelper vat;
	private Dialog unlockWarning;
	private AdView adView;
	
	/** Sample filters array */
	final String[] mFileFilter = { "*.db"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();
		cController=new CurrencyController(this);
		vat=new VatHelper(this);
		
		currencySettingsSpinner=(Spinner) findViewById(R.id.currencySettingsSpinner);
		currencySettingsSpinner.setSelection(cController.getDefaultCurrencyPosition());
		saveSettingsButton=(Button) findViewById(R.id.saveSettingsButton);
		unlockCurrecnyButton=(Button) findViewById(R.id.unlockCurrecnyButton);
		saveDBButton=(Button) findViewById(R.id.saveDBButton);
		loadDBButton=(Button) findViewById(R.id.loadDBButton);
		vatStandrdSettingsEditText=(EditText) findViewById(R.id.vatStandrdSettingsEditText);
		vatReducedSettingsEditText=(EditText) findViewById(R.id.vatReducedSettingsEditText);
		vatStandrdSettingsEditText.setText(vat.getStandardRate());
		vatReducedSettingsEditText.setText(vat.getReducedRate());
		
		saveDBButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				new FileSelector(SettingsActivity.this, FileOperation.SAVE, mSaveFileListener, mFileFilter).show();
			}
		});
		
		loadDBButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				new FileSelector(SettingsActivity.this, FileOperation.LOAD, mLoadFileListener, mFileFilter).show();
			}
		});
		
		saveSettingsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				cController.setDefaultCurrencyFromName(currencySettingsSpinner.getSelectedItem().toString());
				vat.setRates(vatStandrdSettingsEditText.getText().toString(), 
						vatReducedSettingsEditText.getText().toString());
				finish();
				
			}
		});
		
		unlockWarning=createDialog();
		unlockCurrecnyButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currencySettingsSpinner.setEnabled(true);
				unlockWarning.show();
				unlockCurrecnyButton.setEnabled(false);
				unlockCurrecnyButton.setVisibility(View.INVISIBLE);
				
			}
		});
		Bundle extras=getIntent().getExtras();
		if(extras!=null){
			if(extras.getBoolean("initSettings")){
				unlockCurrecnyButton.setEnabled(false);
				unlockCurrecnyButton.setVisibility(View.INVISIBLE);
				saveDBButton.setEnabled(false);
				saveDBButton.setVisibility(View.INVISIBLE);
				loadDBButton.setEnabled(false);
				loadDBButton.setVisibility(View.INVISIBLE);
			}
			else{
				currencySettingsSpinner.setEnabled(false);
				AdMobCreator.createAd(this, adView, R.id.settingsAdMob);
			}
		}else{
			currencySettingsSpinner.setEnabled(false);
			AdMobCreator.createAd(this, adView, R.id.settingsAdMob);
		}
		
	}
	
	

	@Override
	protected void onDestroy() {
		AdMobCreator.destroyAd(adView);
		super.onDestroy();
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
	
	OnHandleFileListener mLoadFileListener = new OnHandleFileListener() {
		@Override
		public void handleFile(final String filePath) {
			SerializeObject.importDB(filePath, getBaseContext());
			Toast.makeText(SettingsActivity.this, "Load: " + filePath, Toast.LENGTH_SHORT).show();
		}
	};

	OnHandleFileListener mSaveFileListener = new OnHandleFileListener() {
		@Override
		public void handleFile(final String filePath) {
			SerializeObject.exportDB(filePath, getBaseContext());
			Toast.makeText(SettingsActivity.this, "Save: " + filePath, Toast.LENGTH_SHORT).show();
		}
	};
	
	private Dialog createDialog(){
		String unlock=getResources().getString(R.string.unlock);
		String unlock_hint=getApplicationContext().getResources().getString(R.string.unlock_hint);
		Builder builder=new AlertDialog.Builder(this);
		builder.setTitle(unlock);
		builder.setMessage(unlock_hint);
		builder.setIcon(android.R.drawable.stat_notify_error);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog=builder.create();
		return dialog;
	}

}
