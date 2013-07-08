package com.bue.shoppingplanner.free.views;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.R.layout;
import com.bue.shoppingplanner.free.R.menu;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.model.Product;
import com.bue.shoppingplanner.free.utilities.FilterMode;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;

public class PriceInTimeChartActivity extends FragmentActivity {
	
	/**
	 * Chart Variables
	 */

	/** The main dataset that includes all the series that go into a chart. */
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	/** The main renderer that includes all the renderers customizing a chart. */
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	/** The most recently added series. */
	private XYSeries mCurrentSeries;
	/** The most recently created renderer, customizing the current series. */
	private XYSeriesRenderer mCurrentRenderer;
	/** Button for creating a new series of data. */
	private Button mNewSeries;
	/** The chart view that displays the data. */
	private GraphicalView mChartView;
	/**Layout will used for the chart*/
//	private LinearLayout priceChartLayout;
	
	/**Spinner with all products and filtered commercial the second spinner*/
	private Spinner productPriceChartSpinner,
					commercialPriceChartSpinner;
	private ArrayList<String> productArrayList,
					commercialArrayList;
	private ArrayAdapter<String> productArrayAdapter,
								commercialArrayAdapter;
	
	/**Needed Controllers*/
	BoughtController bController;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_in_time_chart);
		// Show the Up button in the action bar.
		setupActionBar();
		//initialize controllers
		bController=new BoughtController(this);
		// filter spinners
		productPriceChartSpinner=(Spinner) findViewById(R.id.productPriceChartSpinner);
		commercialPriceChartSpinner=(Spinner) findViewById(R.id.commercialPriceChartSpinner);
		productArrayList=new ArrayList<String>();
		productArrayList.addAll(bController.getProductNameList());
		commercialArrayList=new ArrayList<String>();
		commercialArrayList.addAll(bController.getCommercialProductNamesByProduct(productArrayList.get(0)));
		productArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,productArrayList);
		commercialArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,commercialArrayList);
		productPriceChartSpinner.setAdapter(productArrayAdapter);
		commercialPriceChartSpinner.setAdapter(commercialArrayAdapter);
		
		productPriceChartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(commercialArrayList.size()>0)
					commercialArrayList.clear();
				commercialArrayList.addAll(bController.getCommercialProductNamesByProduct(productPriceChartSpinner.getSelectedItem().toString()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		commercialPriceChartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				addData(commercialPriceChartSpinner.getSelectedItem().toString());
				String a="1";
				a="2";
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		// Add chart view
		// set some properties on the main renderer
				mRenderer.setApplyBackgroundColor(true);
				mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
				mRenderer.setAxisTitleTextSize(16);
				mRenderer.setChartTitleTextSize(20);
				mRenderer.setLabelsTextSize(15);
				mRenderer.setLegendTextSize(15);
				mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
				mRenderer.setZoomButtonsVisible(true);
				mRenderer.setPointSize(5);
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
		getMenuInflater().inflate(R.menu.price_in_time_chart, menu);
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

	private void addData(String commercialName) {
		ArrayList<String[]> pricesList=bController.getCommercialProductPrices(commercialName, "", ""); 
		// create a new series of data
		XYSeries series = new XYSeries(commercialName+": "+pricesList.get(0)[0]);
		mDataset.addSeries(series);
		mCurrentSeries = series;
		// create a new renderer for the new series
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		// set some renderer properties
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);
		renderer.setDisplayChartValuesDistance(10);
		mCurrentRenderer = renderer;
		// setSeriesWidgetsEnabled(true);
		mChartView.repaint();
		
		//ArrayList<String[]>list=new ArrayList<String[]>();
		for(String[] price:pricesList){
		
			double x = Double.parseDouble(price[1]);
	        double y = Double.parseDouble(price[2]);
	        mCurrentSeries.add(x, y);
		}
        // repaint the chart such as the newly added point to be visible
        mChartView.repaint();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mChartView == null) {
		      LinearLayout priceChartLayout = (LinearLayout) findViewById(R.id.priceChartLayout);
		      mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);
		      // enable the chart click events
		      mRenderer.setClickEnabled(true);
		      mRenderer.setSelectableBuffer(10);
		      mChartView.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View v) {
		          // handle the click event on the chart
		          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		          if (seriesSelection == null) {
		            Toast.makeText(PriceInTimeChartActivity.this, "No chart element", Toast.LENGTH_SHORT).show();
		          } else {
		            // display information of the clicked point
		            Toast.makeText(
		            		PriceInTimeChartActivity.this,
		                "Chart element in series index " + seriesSelection.getSeriesIndex()
		                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
		                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
		                    + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
		          }
		        }
		      });
		      priceChartLayout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
		          LayoutParams.FILL_PARENT));
		      boolean enabled = mDataset.getSeriesCount() > 0;
		      setSeriesWidgetsEnabled(enabled);
		    } else {
		      mChartView.repaint();
		    }
	}
	
	/**
	   * Enable or disable the add data to series widgets
	   * 
	   * @param enabled the enabled state
	   */
	  private void setSeriesWidgetsEnabled(boolean enabled) {
	   // mX.setEnabled(enabled);
	    //mY.setEnabled(enabled);
	    //mAdd.setEnabled(enabled);
	  }

}
