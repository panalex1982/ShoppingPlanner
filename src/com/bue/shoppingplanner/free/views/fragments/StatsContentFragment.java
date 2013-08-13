package com.bue.shoppingplanner.free.views.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.views.adapters.StatsExpandableListAdapter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StatsContentFragment extends Fragment {

	private ExpandableListView statsItemsExpandableListView;
	private StatsExpandableListAdapter groupStatsListAdapter;

	private Button fromStatsButton, toStatsButton;

	private ArrayList<String[]> totalsBy;
	private ArrayList<ArrayList<String[]>> childTotalsBy;

	private int listKey;

	private SimpleDateFormat dateFormater;

	private Calendar fromDate;
	private Calendar toDate;
	private SimpleDateFormat editTextDateFormater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Initialize Dates
		dateFormater = new SimpleDateFormat("ddMMyyyyHHmmss");
		editTextDateFormater = new SimpleDateFormat("dd-MM-yyyy");
		fromDate = Calendar.getInstance();
		toDate = Calendar.getInstance();
		fromDate.set(2012, 0, 1);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stats_content,
				container, false);

		// Expandable List
		statsItemsExpandableListView = (ExpandableListView) view
				.findViewById(R.id.statsItemsExpandableListView);
		// LinearLayout headerLayout = new LinearLayout(getActivity());
		// TextView headerText = new TextView(getActivity());
		// headerText.setText(listKey);
		// headerLayout.addView(headerText);
		// statsItemsExpandableListView.addHeaderView(headerLayout);
		totalsBy = new ArrayList<String[]>();
		childTotalsBy = new ArrayList<ArrayList<String[]>>();
		groupStatsListAdapter = new StatsExpandableListAdapter(getActivity(),
				R.layout.stats_element_view, R.layout.stats_element_child_view,
				totalsBy, childTotalsBy);
		statsItemsExpandableListView.setAdapter(groupStatsListAdapter);
		// Initialize ListView ArrayList
		Bundle chosenTab = getArguments();

		listKey = chosenTab.getInt("chosenTab");
		updateResultList();

		// Date Buttons
		fromStatsButton = (Button) view.findViewById(R.id.fromStatsButton);
		toStatsButton = (Button) view.findViewById(R.id.toStatsButton);
		
		fromStatsButton.setText(String.valueOf(editTextDateFormater
				.format(fromDate.getTime())));
		fromStatsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dateDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								fromDate.set(year, monthOfYear, dayOfMonth);
								fromStatsButton.setText(editTextDateFormater
										.format(fromDate.getTime()));
								updateResultList();
							}
						}, fromDate.get(Calendar.YEAR), fromDate
								.get(Calendar.MONTH), fromDate
								.get(Calendar.DATE));
				dateDialog.show();
			}
		});

		toStatsButton.setText(String.valueOf(editTextDateFormater
				.format(toDate.getTime())));
		toStatsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dateDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								toDate.set(year, monthOfYear, dayOfMonth);
								toStatsButton.setText(editTextDateFormater
										.format(toDate.getTime()));
								updateResultList();
							}
						}, toDate.get(Calendar.YEAR), toDate
								.get(Calendar.MONTH), toDate.get(Calendar.DATE));
				dateDialog.show();
			}
		});
		return view;
	}

	private void updateResultList() {
		BoughtController boughtController = new BoughtController(getActivity());
		 if(!totalsBy.isEmpty())
			 totalsBy.clear();
		 if(!childTotalsBy.isEmpty())
			 childTotalsBy.clear();
		ArrayList<String[]> tmp;
		
		//ArrayList<ArrayList<String[]>> tmp2;
		switch (listKey) {
		case 1:
			totalsBy.addAll(boughtController.getTotalByUser(
					dateFormater.format(fromDate.getTime()),
					dateFormater.format(toDate.getTime())));
			/*tmp = boughtController.getTotalByGroup(
					dateFormater.format(fromDate.getTime()),
					dateFormater.format(toDate.getTime()));
			for(String parent[]:tmp)
				totalsBy.add(parent);*/
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = boughtController
						.getUserSpendingByProduct(total[0],
								dateFormater.format(fromDate.getTime()),
								dateFormater.format(toDate.getTime()));
				childTotalsBy.add(tmpList);
			}
			break;
		case 2:
			tmp = boughtController.getTotalByProduct(
					dateFormater.format(fromDate.getTime()),
					dateFormater.format(toDate.getTime()));
			for(String parent[]:tmp)
				totalsBy.add(parent);
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = boughtController
						.getProductSpedingByShop(total[0],
								dateFormater.format(fromDate.getTime()),
								dateFormater.format(toDate.getTime()));
				childTotalsBy.add(tmpList);
			}
			break;
		case 3:
			tmp = boughtController.getTotalByShop(
					dateFormater.format(fromDate.getTime()),
					dateFormater.format(toDate.getTime()));
			for(String parent[]:tmp)
				totalsBy.add(parent);
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = new ArrayList<String[]>();
				tmpList = boughtController.getShopSpedingByProduct(total[0],
						dateFormater.format(fromDate.getTime()),
						dateFormater.format(toDate.getTime()));
				childTotalsBy.add(tmpList);
			}
			break;
		case 4:
			tmp = boughtController.getTotalByKind(
					dateFormater.format(fromDate.getTime()),
					dateFormater.format(toDate.getTime()));
			for(String parent[]:tmp)
				totalsBy.add(parent);
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = new ArrayList<String[]>();
				tmpList = boughtController.getKindSpendingByProduct(total[0],
						dateFormater.format(fromDate.getTime()),
						dateFormater.format(toDate.getTime()));
				childTotalsBy.add(tmpList);
			}
			break;
		default:
			
			tmp = boughtController.getTotalByUser(
					dateFormater.format(fromDate.getTime()),
					dateFormater.format(toDate.getTime()));
			for(String parent[]:tmp)
				totalsBy.add(parent);
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = new ArrayList<String[]>();
				tmpList = boughtController.getUserSpendingByProduct(total[0],
						dateFormater.format(fromDate.getTime()),
						dateFormater.format(toDate.getTime()));
				childTotalsBy.add(tmpList);
			}
		}
		groupStatsListAdapter.notifyDataSetChanged();
	}

}
