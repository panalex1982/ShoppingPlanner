package com.bue.shoppingplanner.free.views;

import com.bue.shoppingplanner.free.MainMenuActivity;
import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.R.layout;
import com.bue.shoppingplanner.free.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class StatisticsMenuActivity extends Activity {
	private Button sumsButton,
					chartButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_menu);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics_menu, menu);
		return true;
	}

}
