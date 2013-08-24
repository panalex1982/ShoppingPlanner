package com.bue.shoppingplanner.free.views;

import com.bue.shoppingplanner.free.MainMenuActivity;
import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.R.layout;
import com.bue.shoppingplanner.free.R.menu;
import com.bue.shoppingplanner.free.helpers.AdMobCreator;
import com.google.ads.AdView;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class StatisticsMenuActivity extends Activity {
	private Button sumsButton,
					chartButton;
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_menu);
		setupActionBar();
		sumsButton=(Button)findViewById(R.id.sumsButton);
		chartButton=(Button)findViewById(R.id.chartsButton);
		sumsButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(StatisticsMenuActivity.this,
							StatsActivity.class));
				}
				return false;
			}
		});
		chartButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startActivity(new Intent(StatisticsMenuActivity.this,
							PriceInTimeChartActivity.class));
				}
				return false;
			}
		});
		AdMobCreator.createAd(this, adView, R.id.statistics_main_ad_mob);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics_menu, menu);
		return true;
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
	protected void onDestroy() {
		super.onDestroy();
		AdMobCreator.destroyAd(adView);
	}
	
	

}
