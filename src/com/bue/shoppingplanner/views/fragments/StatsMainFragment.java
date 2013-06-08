package com.bue.shoppingplanner.views.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.utilities.FilterMode;
import com.bue.shoppingplanner.views.adapters.StatsArrayAdapter;
import com.bue.shoppingplanner.views.dialogs.StatsFilterDialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link StatsMainFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class StatsMainFragment extends Fragment {
	private static final String CHOSEN_TAB = "chosenTab";
	private static final String ARG_PARAM2 = "param2";

	private int chosenTab;
	private String mParam2;

	private String fragmentTag;

	/**
	 * Filter buttons: used to select the statistics that user wants to see date
	 * button: used to set period of statistics.
	 */
	private Button filterButton1, filterButton2, filterButton3, filterButton4,
			fromButton, toButton;

	/**
	 * ArrayLists Used to filter the results on ListView or Plot.
	 */
	private ArrayList<String> filter1, filter2, filter3, filter4;

	/**
	 * List view which shows the amount is paid according to the given filters.
	 * *
	 */
	private ListView sumsListView;

	/**
	 * The array adapter and the array list that contains the data for the
	 * sumsListView.
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
	 * @param chosenTab
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment StatsMainFragment.
	 */
	public static StatsMainFragment newInstance(String param1, String param2) {
		StatsMainFragment fragment = new StatsMainFragment();
		Bundle args = new Bundle();
		args.putInt(CHOSEN_TAB, 1);
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
			chosenTab = getArguments().getInt(CHOSEN_TAB);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		fragmentTag=getTag();
		
		// filter ArrayList
				filter1 = null;
				filter2 = null;
				filter3 = null;
				filter4 = null;

		// Initialize Dates
		dateFormater = new SimpleDateFormat("ddMMyyyyhhmmss");
		editTextDateFormater = new SimpleDateFormat("dd-MM-yyyy");
		fromDate = Calendar.getInstance();
		toDate = Calendar.getInstance();
		fromDate.set(2012, 0, 1);
		bController = new BoughtController(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_stats, container,
				false);
		initializeView(view);
		switchLayout();
		initializeListeners();
		return view;
	}

	private void initializeView(View view) {
		// filter buttons
		filterButton1 = (Button) view.findViewById(R.id.filterButton1);
		filterButton2 = (Button) view.findViewById(R.id.filterButton2);
		filterButton3 = (Button) view.findViewById(R.id.filterButton3);
		filterButton4 = (Button) view.findViewById(R.id.filterButton4);

		// date buttons
		fromButton = (Button) view.findViewById(R.id.fromMainStatsButton);
		toButton = (Button) view.findViewById(R.id.toMainStatsButton);
		fromButton.setText(String.valueOf(editTextDateFormater.format(fromDate
				.getTime())));
		toButton.setText(String.valueOf(editTextDateFormater.format(toDate
				.getTime())));

		// data list view
		sumsListView = (ListView) view.findViewById(R.id.sumsListView);
		sumsArrayList = new ArrayList<String[]>();
		sumsArrayAdapter = new StatsArrayAdapter(getActivity(),
				R.layout.stats_element_view, sumsArrayList);
		sumsListView.setAdapter(sumsArrayAdapter);

	}

	private void initializeListeners() {

		// date buttons
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

	private void productCase() {
		filterButton1.setText(R.string.buyer);
		filterButton2.setText(R.string.category);
		filterButton3.setText(R.string.brand);
		filterButton4.setText(R.string.shop);

		// filter buttons listeners
		filterButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.USER, 1);
			}
		});

		filterButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.KIND, 2);
			}
		});

		filterButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.BRAND, 3);
			}
		});

		filterButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.SHOP, 4);
			}
		});

		sumsArrayList.addAll(bController.getFilteredProductSpending(
				dateFormater.format(fromDate.getTime()),
				dateFormater.format(toDate.getTime()), filter1, filter2,
				filter4, filter3));

	}

	private void buyerCase() {
		filterButton1.setText(R.string.product);
		filterButton2.setText(R.string.category);
		filterButton3.setText(R.string.brand);
		filterButton4.setText(R.string.shop);

		// filter buttons listeners
		filterButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.PRODUCT, 1);
			}
		});

		filterButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.KIND, 2);
			}
		});

		filterButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.BRAND, 3);
			}
		});

		filterButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.SHOP, 4);
			}
		});

		sumsArrayList.addAll(bController.getFilteredUserSpending(
				dateFormater.format(fromDate.getTime()),
				dateFormater.format(toDate.getTime()), filter1, filter2,
				filter4, filter3));
	}

	public void shopCase() {
		filterButton1.setText(R.string.product);
		filterButton2.setText(R.string.category);
		filterButton3.setText(R.string.brand);
		filterButton4.setText(R.string.buyer);

		// filter buttons listeners
		filterButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.PRODUCT, 1);
			}
		});

		filterButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.KIND, 2);
			}
		});

		filterButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.BRAND, 3);
			}
		});

		filterButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.USER, 4);
			}
		});

		sumsArrayList.addAll(bController.getFilteredShopSpending(
				dateFormater.format(fromDate.getTime()),
				dateFormater.format(toDate.getTime()), filter1, filter2,
				filter4, filter3));
	}

	public void kindCase() {
		filterButton1.setText(R.string.product);
		filterButton2.setText(R.string.shop);
		filterButton3.setText(R.string.brand);
		filterButton4.setText(R.string.buyer);

		// filter buttons listeners
		filterButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.PRODUCT, 1);
			}
		});

		filterButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.SHOP, 2);
			}
		});

		filterButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.BRAND, 3);
			}
		});

		filterButton4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openFilterDialog(FilterMode.USER, 4);
			}
		});

		sumsArrayList.addAll(bController.getFilteredKindSpending(
				dateFormater.format(fromDate.getTime()),
				dateFormater.format(toDate.getTime()), filter1, filter2,
				filter4, filter3));
	}

	protected void openFilterDialog(int filterType, int filterListNumber) {
		StatsFilterDialogFragment filterDialog = new StatsFilterDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("filterType", filterType);
		bundle.putString("fragmentTag", fragmentTag);
		bundle.putInt("filterListNumber", filterListNumber);
		switch (filterListNumber) {
		case 1:
			bundle.putStringArrayList("existingFilterList", filter1);
			break;
		case 2:
			bundle.putStringArrayList("existingFilterList", filter2);
			break;
		case 3:
			bundle.putStringArrayList("existingFilterList", filter3);
			break;
		case 4:
			bundle.putStringArrayList("existingFilterList", filter4);
			break;
		}
		filterDialog.setArguments(bundle);
		filterDialog.show(getFragmentManager(), "StatsFilterDialogFragment");

	}

	private void updateResultList() {
		if (!sumsArrayList.isEmpty())
			sumsArrayList.clear();
		switchLayout();
		sumsArrayAdapter.notifyDataSetChanged();
	}

	public void setFilterList1(ArrayList<String> list) {
		if (filter1 == null)
			filter1 = new ArrayList<String>();
		if (list.isEmpty())
			filter1 = null;
		else
			filter1.addAll(list);
		updateResultList();
	}

	public void setFilterList2(ArrayList<String> list) {
		if (filter2 == null)
			filter2 = new ArrayList<String>();
		if (list.isEmpty())
			filter2 = null;
		else
			filter2.addAll(list);
		updateResultList();
	}

	public void setFilterList3(ArrayList<String> list) {
		if (filter3 == null)
			filter3 = new ArrayList<String>();
		if (list.isEmpty())
			filter3 = null;
		else
			filter3.addAll(list);
		updateResultList();
	}

	public void setFilterList4(ArrayList<String> list) {
		if (filter4 == null)
			filter4 = new ArrayList<String>();
		if (list.isEmpty())
			filter4 = null;
		else
			filter4.addAll(list);
		updateResultList();
	}

	private void switchLayout() {
		switch (chosenTab) {
		case 1:
			buyerCase();
			break;
		case 2:
			productCase();
			break;
		case 3:
			shopCase();
			break;
		case 4:
			kindCase();
			break;
		}
	}

}
