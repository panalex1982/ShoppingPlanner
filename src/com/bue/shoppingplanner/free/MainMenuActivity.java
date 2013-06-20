package com.bue.shoppingplanner.free;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.controllers.CurrencyController;
import com.bue.shoppingplanner.free.helpers.DialogOpener;
import com.bue.shoppingplanner.free.views.DatabaseMenuActivity;
import com.bue.shoppingplanner.free.views.ExchangeRatesActivity;
import com.bue.shoppingplanner.free.views.SavedListsActivity;
import com.bue.shoppingplanner.free.views.SettingsActivity;
import com.bue.shoppingplanner.free.views.ShoppingListActivity;
import com.bue.shoppingplanner.free.views.ShopsActivity;
import com.bue.shoppingplanner.free.views.StatsActivity;
import com.bue.shoppingplanner.free.views.dialogs.AboutDialogFragment;
import com.bue.shoppingplanner.free.views.dialogs.AddProductDialogFragment;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.support.v4.app.DialogFragment;
//import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

public class MainMenuActivity extends FragmentActivity {
	private ImageButton shoppingListImageButton;
	private ImageButton statsImageButton;
	private ImageButton shopsImageButton;
	private ImageButton savedListsImageButton;
	private ImageButton dbButton;

	//private TextView spendingMainTextView;
	//private TextView totalVatTextView;
	private ImageButton settingsImageButton;
	private ImageButton ratesImageButton;

	private boolean startIntro;

	// Pie Chat
	/** Colors to be used for the pie slices. */
	private static int[] COLORS = new int[] { Color.BLUE, Color.RED,
			Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.DKGRAY };
	/** The main series that will include all the data. */
	private CategorySeries mSeries;
	private CategorySeries mSeries2;
	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer;
	private DefaultRenderer mRenderer2;
	private GraphicalView mChartView;
	private GraphicalView mChartView2;
	private LinearLayout chartLayout;
	private LinearLayout chartLayout2;
	
	private CurrencyController cController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_menu);
		// Show the Up button in the action bar.
		setupActionBar();

		// Initialize Objects
		startIntro = true;
		mSeries = new CategorySeries("");
		mSeries2 = new CategorySeries("");
		mRenderer = new DefaultRenderer();
		mRenderer2 = new DefaultRenderer();
		cController=new CurrencyController(this);
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
		// spendingMainTextView = (TextView)
		// findViewById(R.id.spendingMainTextView);
		// spendingMainTextView.setText(Double.toString(bController
		// .getTotalSpending()));
		//
		// totalVatTextView = (TextView) findViewById(R.id.totalVatTextView);
		// totalVatTextView.setText(Double.toString(bController
		// .getTotalVatPayment()));

		// Chart
		// set the start angle for the first slice in the pie chart
		mRenderer.setStartAngle(90);
		// display values on the pie slices
		mRenderer.setDisplayValues(true);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(12f);
		mRenderer.setChartTitle(getResources().getString(
				R.string.main_chart_one_title));

		// set the start angle for the first slice in the pie chart
		mRenderer2.setStartAngle(180);
		// display values on the pie slices
		mRenderer2.setDisplayValues(true);
		mRenderer2.setLabelsColor(Color.BLACK);
		mRenderer2.setLabelsTextSize(12f);
		mRenderer2.setChartTitle(getResources().getString(
				R.string.main_chart_two_title));
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
		case R.id.action_about:
			DialogOpener.showAboutDialog(getSupportFragmentManager());
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (startIntro)
			startActivityForResult(new Intent(MainMenuActivity.this,
					IntroActivity.class), 0);
		BoughtController bController = new BoughtController(this);
		ArrayList<String[]> chart1Items = new ArrayList<String[]>();
		ArrayList<String[]> chart2Items = new ArrayList<String[]>();
		String[] totalItem = { "Total Spending",
				(bController.getTotalSpending()==0?"1":String.valueOf(bController.getTotalSpending())) };
		chart1Items.add(totalItem);
		String[] vatItem = { "VAT",
				(bController.getTotalVatPayment()==0?"1":String.valueOf(bController.getTotalVatPayment()))};
		chart1Items.add(vatItem);
		ArrayList<String[]> kindArray = bController.getTotalByKind();
		if(kindArray.size()==0){
			String noPurchase[]=new String[2];
			noPurchase[0]=getResources().getString(R.string.no_purchases_yet);
			noPurchase[1]="1";
			chart2Items.add(noPurchase);
		}else{
			for (int i = 0; i < 5; i++) {
				if (kindArray.size() > i)
					chart2Items.add(kindArray.get(i));
			}
		}
		createPieChart1(chart1Items);
		createPieChart2(chart2Items);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		startIntro = savedState.getBoolean("startIntro");
		// mSeries = (CategorySeries)
		// savedState.getSerializable("current_series");
		// mRenderer = (DefaultRenderer)
		// savedState.getSerializable("current_renderer");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("startIntro", startIntro);
		// outState.putSerializable("current_series", mSeries);
		// outState.putSerializable("current_renderer", mRenderer);
	}

	public void exit() {
		finish();
	}

	public void createPieChart1(ArrayList<String[]> chartItems) {

		// Chart
		if (mChartView == null) {
			chartLayout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
			chartLayout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			for (String[] item : chartItems)
				addItemToChart(item[0], Double.parseDouble(item[1]));
			mChartView.repaint();
		} else {
			clearChart1();
			for (String[] item : chartItems)
				addItemToChart(item[0], Double.parseDouble(item[1]));
			mChartView.repaint();
		}
	}

	public void createPieChart2(ArrayList<String[]> chartItems) {

		// Chart
		if (mChartView2 == null) {
			chartLayout2 = (LinearLayout) findViewById(R.id.chart2);
			mChartView2 = ChartFactory.getPieChartView(this, mSeries2,
					mRenderer2);
			chartLayout2.addView(mChartView2, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			for (String[] item : chartItems)
				addItemToChart2(item[0], Double.parseDouble(item[1]));
			mChartView2.repaint();
		} else {
			clearChart2();
			while(mRenderer2.getSeriesRendererCount()>0)
				mRenderer2.removeSeriesRenderer(mRenderer2.getSeriesRendererAt(0));
			for (String[] item : chartItems)
				addItemToChart2(item[0], Double.parseDouble(item[1]));
			mChartView2.repaint();
		}
	}

	public void addItemToChart(String name, double value) {
		value=Double.parseDouble(cController.formatCurrecy(String.valueOf(value)));
		mSeries.add(name, value);
		SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
		renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
		mRenderer.addSeriesRenderer(renderer);
	}
	
	public void clearChart1(){
		mSeries.clear();
	}
	
	public void addItemToChart2(String name, double value) {
		value=Double.parseDouble(cController.formatCurrecy(String.valueOf(value)));
		mSeries2.add(name, value);
		SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
		renderer.setColor(COLORS[(mSeries2.getItemCount() - 1) % COLORS.length]);
		mRenderer2.addSeriesRenderer(renderer);
	}
	
	public void clearChart2(){
		mSeries2.clear();
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

}
