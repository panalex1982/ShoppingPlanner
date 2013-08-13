package com.bue.shoppingplanner.free.views;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.R.layout;
import com.bue.shoppingplanner.free.R.menu;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.controllers.CurrencyController;
import com.bue.shoppingplanner.free.model.Product;
import com.bue.shoppingplanner.free.utilities.FilterMode;
import com.bue.shoppingplanner.free.utilities.Utilities;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
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
import android.content.res.Resources;
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
	/** The most recently added series. 
	/** The most recently created renderer, customizing the current series. */
	private XYSeriesRenderer mCurrentRenderer;
	/** Button for creating a new series of data.
	private Button mNewSeries;
	/** The chart view that displays the data. */
	private GraphicalView mChartView;
	/**Layout will used for the chart*/
//	private LinearLayout priceChartLayout;
	
	/**Spinner with all products and filtered commercial the second spinner*/
	private Spinner productPriceChartSpinner,
					commercialPriceChartSpinner;
	private Button addTimeChartButton;
	private ArrayList<String> productArrayList,
					commercialArrayList;
	private ArrayAdapter<String> productArrayAdapter,
								commercialArrayAdapter;
	
	/**Array list which contains the commercial names of the products in diagrams*/
	private ArrayList<String> seriesNames;
	
	private int colorIndex;
	private int colors[]={Color.YELLOW,Color.GREEN,Color.RED,Color.CYAN,Color.MAGENTA,Color.BLUE};
	private double[] limits;
	private Resources res;
	/**Needed Controllers*/
	BoughtController bController;
	CurrencyController cController;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_in_time_chart);
		// Show the Up button in the action bar.
		setupActionBar();
		//initialize controllers
		bController=new BoughtController(this);
		cController =new CurrencyController(this);
		res=getResources();
		
		productArrayList=new ArrayList<String>();
		productArrayList.addAll(bController.getProductNameList());
		//If size is null there is no data to draw
		if(productArrayList.size()>0){
			if(savedInstanceState!=null){
				seriesNames=savedInstanceState.getStringArrayList("seriesNames");
			}else
				seriesNames=new ArrayList<String>();
			colorIndex=0;
			limits=new double[4];	
			limits[0]=0;
        	limits[1]=0;
        	limits[2]=0;
        	limits[3]=0;
			// filter spinners
			productPriceChartSpinner=(Spinner) findViewById(R.id.productPriceChartSpinner);
			commercialPriceChartSpinner=(Spinner) findViewById(R.id.commercialPriceChartSpinner);
			addTimeChartButton=(Button) findViewById(R.id.addTimeChartButton);
		
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
					commercialArrayAdapter.notifyDataSetChanged();
					commercialPriceChartSpinner.setAdapter(commercialArrayAdapter);
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
					
					
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					
				}
			});
			
			addTimeChartButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String commercialName=commercialPriceChartSpinner.getSelectedItem().toString();
					seriesNames.add(commercialName);
					addData(commercialName);					
				}
			});
			
			// Add chart view
			// set some properties on the main renderer
					mRenderer.setApplyBackgroundColor(true);
					mRenderer.setBackgroundColor(Color.BLACK);
					mRenderer.setAxisTitleTextSize(16);
					mRenderer.setChartTitleTextSize(20);
					mRenderer.setLabelsTextSize(13);
					mRenderer.setLegendTextSize(15);
					mRenderer.setMargins(new int[] { 20, 20, 5, 10 });
					mRenderer.setPointSize(10);
					mRenderer.setZoomEnabled(true);
					mRenderer.setXLabelsPadding(5.0f);
					mRenderer.setYLabelsPadding(8.0f);
					//mRenderer.setFitLegend(true);
					mRenderer.setXTitle(res.getString(R.string.date));
					mRenderer.setYTitle(res.getString(R.string.price));
					mRenderer.setShowGrid(true);
					mRenderer.setAntialiasing(true);
					mRenderer.setGridColor(Color.GRAY);
					mRenderer.setAxesColor(Color.WHITE);
		}
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
		TimeSeries series = new TimeSeries(commercialName+": "+pricesList.get(0)[0]);
		
		// create a new renderer for the new series
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		// set some renderer properties
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(false);
		renderer.setDisplayChartValuesDistance(50);
		renderer.setColor(colors[colorIndex%6]);
		
		// setSeriesWidgetsEnabled(true);
		mChartView.repaint();
		
		//ArrayList<String[]>list=new ArrayList<String[]>();
		boolean firstTime=true;
		for(String[] price:pricesList){
			SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyHHmmss");
			double y = Double.parseDouble(price[1]);
	        Date x;
			try {
				x = s.parse(price[2]);
				Log.i(x.toString()+": ",y+" euro.");
		        series.add(x,y);
		        if(firstTime&&colorIndex==0){
		        	limits[0]=x.getTime();
		        	limits[1]=x.getTime();
		        	limits[2]=0;
		        	limits[3]=y;
		        }
		        checkLimits(x.getTime(),y);
		        firstTime=false;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	        
		}
		setAxlesLimits();
		mDataset.addSeries(series);
		//mCurrentSeries = series;
        // repaint the chart such as the newly added point to be visible
        mChartView.repaint();
        colorIndex++;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mChartView == null) {
		      LinearLayout priceChartLayout = (LinearLayout) findViewById(R.id.priceChartLayout);
		      mChartView = ChartFactory.getTimeChartView(this, mDataset, mRenderer,"Test");
		      // enable the chart click events
		      mRenderer.setClickEnabled(true);
		      mRenderer.setSelectableBuffer(10);
		      mChartView.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View v) {
		          // handle the click event on the chart
		          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		          if (seriesSelection != null) {
		            // display information of the clicked point
		            Toast.makeText(
		            		PriceInTimeChartActivity.this, res.getString(R.string.price)+" "
				                    + seriesSelection.getValue()+cController.getDefaultCurrency()+
		                " @ " + new Date((long) seriesSelection.getXValue()).toString(), Toast.LENGTH_LONG).show();
		          }
		        }		        
//		        "Chart element in series index " + seriesSelection.getSeriesIndex()
//                + " data point index " + seriesSelection.getPointIndex() + " was clicked"
//                + " closest point value X=" + new Date((long) seriesSelection.getXValue()).toString() + ", Y="
//                + seriesSelection.getValue()
		      });
		      priceChartLayout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
		          LayoutParams.FILL_PARENT));
		      boolean enabled = mDataset.getSeriesCount() > 0;
		      //setSeriesWidgetsEnabled(enabled);
		      if(!seriesNames.isEmpty())
		        	for(String commercialName:seriesNames)
			    		addData(commercialName);
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
	  
	  /**
	   * Check Axles Limits of the time diagram
	   */
	  
	  private void checkLimits(double x, double y){
		  if(limits[0]>x)
			  limits[0]=x;
		  if(limits[1]<x)
			  limits[1]=x;
		  if(limits[3]<y){
			  limits[3]=y;
		  }
		  
	  }
	  
	  /**
	   * Set Axles limits to the 
	   */
	  private void setAxlesLimits(){
		  double timeDif=limits[1]-limits[0];
		  double month=86400000.0*30.0;
		  double fourmonth=86400000.0*120.0;
		  double eightmonth=86400000.0*240.0;
		  if(timeDif<86400000){
			  limits[1]=limits[1]+10000;
		  }else if(timeDif<month){
			  limits[1]=limits[1]+10000000;
		  }else if(timeDif<fourmonth){
			  limits[1]=limits[1]+200000000;
		  }else if(timeDif<eightmonth){
			  limits[1]=limits[1]+400000000;
		  }
		  if(limits[3]<20)
			  limits[3]+=2;
		  else if(limits[3]<50)
			  limits[3]+=5;
		  else if(limits[3]<100)
			  limits[3]+=10;
		  else if(limits[3]<500)
			  limits[3]+=20;
		  else if(limits[3]<2000)
			  limits[3]+=100;
		  else if(limits[3]<30000)
			  limits[3]+=500;
		  else{
			  limits[3]+=3000;
		  }
		  mRenderer.setXAxisMin(limits[0]);
		  mRenderer.setXAxisMax(limits[1]);
		  mRenderer.setYAxisMin(limits[2]);
		  mRenderer.setYAxisMax(limits[3]);
		  mRenderer.setPanLimits(limits);
	  }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList("seriesNames", seriesNames);
	}
	  
	  
	  
	  
	  

}
