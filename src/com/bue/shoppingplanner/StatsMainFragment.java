package com.bue.shoppingplanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.views.adapters.StatsArrayAdapter;
import com.bue.shoppingplanner.views.adapters.StatsExpandableListAdapter;
import com.bue.shoppingplanner.views.dialogs.StatsFilterDialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link StatsMainFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class StatsMainFragment extends Fragment{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	private int fragmentId;
	
	/**
	 * Filter buttons: used to select the statistics that user wants to see
	 * date button:  used to set period of statistics.
	 */
	private Button filterButton1,
					filterButton2,
					filterButton3,
					filterButton4,
					fromButton,
					toButton;
	
	/**
	 * ArrayLists Used to filter the results on ListView or Plot.
	 */
	private ArrayList<String> filter1,
							filter2,
							filter3,
							filter4;
	
	/**
	 * List view which shows the amount is paid according to 
	 * the given filters.	 * 
	 */
	private ListView sumsListView;
	
	/**
	 * The array adapter and the array list that contains 
	 * the data for the sumsListView.
	 */
	private StatsArrayAdapter sumsArrayAdapter;
	private ArrayList<String[]> sumsArrayList;
	
	/**
	 * Date Buttons helping fields;
	 */
	private SimpleDateFormat dateFormater;

	private Calendar fromDate;
	private Calendar toDate;
	private SimpleDateFormat editTextDateFormater;
	
	/**
	 * Controller used for queries connected with bought. 
	 */
	BoughtController bController;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment StatsMainFragment.
	 */
	public static StatsMainFragment newInstance(String param1, String param2) {
		StatsMainFragment fragment = new StatsMainFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public StatsMainFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
		fragmentId=this.getId();
		
		// Initialize Dates
		dateFormater = new SimpleDateFormat("ddMMyyyyhhmmss");
		editTextDateFormater = new SimpleDateFormat("dd-MM-yyyy");
		fromDate = Calendar.getInstance();
		toDate = Calendar.getInstance();
		fromDate.set(2012, 0, 1);
		bController=new BoughtController(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_stats,
				container, false);
		initializeView(view);
		productCase();
		initializeListeners();
		
		return view;
	}
	
	private void initializeView(View view) {
		//filter buttons
		filterButton1=(Button) view.findViewById(R.id.filterButton1);
		filterButton2=(Button) view.findViewById(R.id.filterButton2);
		filterButton3=(Button) view.findViewById(R.id.filterButton3);
		filterButton4=(Button) view.findViewById(R.id.filterButton4);
		
		//filter ArrayList
		filter1=new ArrayList<String>();
		filter2=new ArrayList<String>();
		filter3=new ArrayList<String>();
		filter4=new ArrayList<String>();
		
		//date buttons
		fromButton=(Button) view.findViewById(R.id.fromMainStatsButton);
		toButton=(Button) view.findViewById(R.id.toMainStatsButton);
		fromButton.setText(String.valueOf(editTextDateFormater
				.format(fromDate.getTime())));
		toButton.setText(String.valueOf(editTextDateFormater
				.format(toDate.getTime())));
		
		//data list view
		sumsListView=(ListView) view.findViewById(R.id.sumsListView);
		sumsArrayList=new ArrayList<String[]>();
		sumsArrayAdapter=new StatsArrayAdapter(getActivity(),
				R.layout.stats_element_view,sumsArrayList);
		sumsListView.setAdapter(sumsArrayAdapter);
		
	}
	
	private void initializeListeners(){
		
		//date buttons
		fromButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dateDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								fromDate.set(year, monthOfYear, dayOfMonth);
								fromButton.setText(editTextDateFormater
										.format(fromDate.getTime()));
								updateResultList();
							}
						}, fromDate.get(Calendar.YEAR), fromDate
								.get(Calendar.MONTH), fromDate
								.get(Calendar.DATE));
				dateDialog.show();
			}
		});

		
		toButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dateDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								toDate.set(year, monthOfYear, dayOfMonth);
								toButton.setText(editTextDateFormater
										.format(toDate.getTime()));
								updateResultList();
							}
						}, toDate.get(Calendar.YEAR), toDate
								.get(Calendar.MONTH), toDate.get(Calendar.DATE));
				dateDialog.show();
			}
		});
	}

	private void productCase(){
		filterButton1.setText(R.string.buyer);
		filterButton2.setText(R.string.category);
		filterButton3.setText(R.string.brand);
		filterButton4.setText(R.string.shop);
		
		filterButton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openFilterDialog("user");
				
			}
		});
		
		sumsArrayList.addAll(bController.getTotalByProduct(dateFormater.format(fromDate.getTime()),
										dateFormater.format(toDate.getTime())));
		
	}
	
	protected void openFilterDialog(String filterType) {
		StatsFilterDialogFragment filterDialog=new StatsFilterDialogFragment();
		Bundle bundle=new Bundle();
		bundle.putString("filterType", filterType);
		bundle.putInt("fragmentId", fragmentId);
		filterDialog.setArguments(bundle);
		filterDialog.show(getFragmentManager(), "StatsFilterDialogFragment");
		
	}

	private void updateResultList() {
		if(!sumsArrayList.isEmpty())
			sumsArrayList.clear();
		productCase();
		sumsArrayAdapter.notifyDataSetChanged();
	}

	public void setFilterList1(ArrayList<String> list){
		filter1.addAll(list);
	}

}
