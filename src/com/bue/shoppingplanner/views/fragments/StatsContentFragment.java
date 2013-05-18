package com.bue.shoppingplanner.views.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.views.adapters.StatsExpandableListAdapter;

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
	private TextView fromStatsTextView, toStatsTextView;
	private EditText fromStatsEditText, toStatsEditText;

	private ArrayList<String[]> totalsBy;
	private ArrayList<ArrayList<String[]>> childTotalsBy;

	private String listKey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// View
		// statsContentView=getActivity().findViewById(R.id.statsContentFragmentList);
		Bundle chosenTab = getArguments();
		childTotalsBy = new ArrayList<ArrayList<String[]>>();
		BoughtController boughtController = new BoughtController(getActivity());
		switch (chosenTab.getInt("chosenTab")) {
		case 1:
			totalsBy = boughtController.getTotalByGroup();
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = boughtController
						.getGroupSpendingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		case 2:
			totalsBy = boughtController.getTotalByProduct();
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = boughtController
						.getProductSpedingByShop(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		case 3:
			totalsBy = boughtController.getTotalByShop();
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = new ArrayList<String[]>();
				tmpList = boughtController.getShopSpedingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		case 4:
			totalsBy = boughtController.getTotalByKind();
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = new ArrayList<String[]>();
				tmpList = boughtController.getKindSpendingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		default:
			totalsBy = boughtController.getTotalByGroup();
			for (String[] total : totalsBy) {
				ArrayList<String[]> tmpList = new ArrayList<String[]>();
				tmpList = boughtController.getGroupSpendingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
		}

		// return statsContentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stats_content,
				container, false);
		// Date Buttons
		fromStatsButton = (Button) view.findViewById(R.id.fromStatsButton);
		toStatsButton = (Button) view.findViewById(R.id.toStatsButton);
		fromStatsEditText = (EditText) view
				.findViewById(R.id.fromStatsEditText);
		toStatsEditText = (EditText) view.findViewById(R.id.toStatsEditText);
		final SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");

		final Calendar fromDate = Calendar.getInstance();
		fromDate.set(2012, 0, 1);
		fromStatsEditText.setText(String.valueOf(s.format(fromDate.getTime())));
		fromStatsEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog dateDialog = new DatePickerDialog(
						getActivity(),
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								fromDate.set(year, monthOfYear, dayOfMonth);
								toStatsEditText.setText(s.format(fromDate
										.getTime()));
							}
						}, fromDate.get(Calendar.YEAR), fromDate
								.get(Calendar.MONTH), fromDate.get(Calendar.DATE));
				dateDialog.show();
			}
		});

		final Calendar toDate = Calendar.getInstance();
		toStatsEditText.setText(String.valueOf(s.format(toDate.getTime())));
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
								toStatsEditText.setText(s.format(toDate
										.getTime()));
							}
						}, toDate.get(Calendar.YEAR), toDate
								.get(Calendar.MONTH), toDate.get(Calendar.DATE));
				dateDialog.show();
			}
		});
		// Expandable List
		statsItemsExpandableListView = (ExpandableListView) view
				.findViewById(R.id.statsItemsExpandableListView);
		LinearLayout headerLayout = new LinearLayout(getActivity());
		TextView headerText = new TextView(getActivity());
		headerText.setText(listKey);
		headerLayout.addView(headerText);
		statsItemsExpandableListView.addHeaderView(headerLayout);
		groupStatsListAdapter = new StatsExpandableListAdapter(getActivity(),
				R.layout.stats_element_view, R.layout.stats_element_child_view,
				totalsBy, childTotalsBy);
		statsItemsExpandableListView.setAdapter(groupStatsListAdapter);
		return view;
	}

	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// super.onActivityCreated(savedInstanceState);
	//
	// }

	/*
	 * @Override public void onListItemClick(ListView l, View v, int position,
	 * long id) { super.onListItemClick(l, v, position, id);
	 * Log.d("Item Selected", totalsBy.get(position-1)[0]); Bundle bundle=new
	 * Bundle(); bundle.putInt("chosenTab", listGroup);
	 * bundle.putString("listKey", totalsBy.get(position-1)[0]); FragmentManager
	 * fragmentManager = getFragmentManager(); FragmentTransaction
	 * fragmentTransaction = fragmentManager.beginTransaction();
	 * StatsContentFragment fragment=new StatsContentFragment();
	 * fragment.setArguments(bundle); fragmentTransaction.replace(this.getId(),
	 * fragment); fragmentTransaction.addToBackStack(null);
	 * fragmentTransaction.commit(); //Errors:has problem when I change tab }
	 */

}
