package com.bue.shoppingplanner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.id;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.model.*;
import com.bue.shoppingplanner.views.DatabaseMenuActivity;
import com.bue.shoppingplanner.views.ExchangeRatesActivity;
import com.bue.shoppingplanner.views.SavedListsActivity;
import com.bue.shoppingplanner.views.SettingsActivity;
import com.bue.shoppingplanner.views.ShoppingListActivity;
import com.bue.shoppingplanner.views.ShopsActivity;
import com.bue.shoppingplanner.views.StatsActivity;
import com.bue.shoppingplanner.views.dialogs.AddProductDialogFragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

public class MainMenuActivity extends FragmentActivity {
	private ImageButton shoppingListImageButton;
	private ImageButton statsImageButton;
	private ImageButton shopsImageButton;
	private ImageButton savedListsImageButton;
	private ImageButton dbButton;

	private TextView spendingMainTextView;
	private TextView totalVatTextView;
	private ImageButton settingsImageButton;
	private ImageButton ratesImageButton;

	private boolean startIntro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_menu);
		// Show the Up button in the action bar.
		setupActionBar();
		startIntro = true;
		// Shopping List Button
		shoppingListImageButton = (ImageButton) findViewById(R.id.shoppingListImageButton);
		shoppingListImageButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							ShoppingListActivity.class));
				}
				return true;
			}

		});

		// Stats Button
		statsImageButton = (ImageButton) findViewById(R.id.statsImageButton);
		statsImageButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							StatsActivity.class));
				}
				return false;
			}
		});

		// Shops ImageButton
		shopsImageButton = (ImageButton) findViewById(R.id.shopsImageButton);
		shopsImageButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							ShopsActivity.class));
				}
				return false;
			}
		});

		// Saved Lists ImageButton
		savedListsImageButton = (ImageButton) findViewById(R.id.savedListsImageButton);
		savedListsImageButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							SavedListsActivity.class));
				}
				return false;
			}
		});

		// Settings ImageButton
		settingsImageButton = (ImageButton) findViewById(R.id.settingsImageButton);
		settingsImageButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							SettingsActivity.class));
				}
				return false;
			}
		});

		// DB Button ImageButton
		dbButton = (ImageButton) findViewById(R.id.dbButton);
		dbButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							DatabaseMenuActivity.class));
				}
				return false;
			}
		});

		// Exchange Rates ImagButton
		ratesImageButton = (ImageButton) findViewById(R.id.ratesImageButton);
		ratesImageButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(MainMenuActivity.this,
							ExchangeRatesActivity.class));
				}
				return false;
			}
		});

		// Main menu stats
		BoughtController bController = new BoughtController(this);
		spendingMainTextView = (TextView) findViewById(R.id.spendingMainTextView);
		spendingMainTextView.setText(Double.toString(bController
				.getTotalSpending()));

		totalVatTextView = (TextView) findViewById(R.id.totalVatTextView);
		totalVatTextView.setText(Double.toString(bController
				.getTotalVatPayment()));
		if (startIntro)
			startActivityForResult(new Intent(MainMenuActivity.this,
					IntroActivity.class), 0);

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
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 0)
			startIntro = false;
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
		case R.id.action_exit:
			exit();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Dbh db = new Dbh(this);
		spendingMainTextView
				.setText(Double.toString(Buys.getTotalSpending(db)));
	}

	public void exit() {
		// Intent exitIntent=new Intent(MainMenuActivity.this,
		// IntroActivity.class);
		// exitIntent.putExtra("exit", true);
		// startActivity(exitIntent);
		finish();
	}

}