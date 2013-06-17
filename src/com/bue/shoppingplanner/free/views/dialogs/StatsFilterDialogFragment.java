package com.bue.shoppingplanner.free.views.dialogs;

import java.util.ArrayList;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.BoughtController;
import com.bue.shoppingplanner.free.utilities.FilterMode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StatsFilterDialogFragment extends DialogFragment {

	/**
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface StatsFilterDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);

		public void onDialogNegativeClick(DialogFragment dialog);
	}

	/** The selected items from the filterListView */
	private ArrayList<String> filtersList;

	/**
	 * The List view of the available filters and their list and adapter.
	 */
	private ListView filterListView;
	private ArrayList<String> filterListArrayList;
	private ArrayAdapter<String> filterArrayAdapter;

	/** The listener to communicate with the activity */
	private StatsFilterDialogListener mListener;

	/** Where do the filter applied */
	private int filterType;

	/** In which list corresponds to the called fragment */
	private int filterListNumber;

	/** Fragment Tag of the fragment which called the fragment dialog */
	private String fragmentTag;

	private BoughtController bController;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// Instantiate the StatsFilterDialogListener so we can send events
			// to the host
			mListener = (StatsFilterDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement StatsFilterDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView = inflater.inflate(R.layout.dialog_stats_filter,
				null);

		bController = new BoughtController(getActivity());
		filterListArrayList = new ArrayList<String>();

		Bundle filterBundle = getArguments();
		if (filterBundle != null) {
			filterType = filterBundle.getInt("filterType");
			fragmentTag = filterBundle.getString("fragmentTag");
			filterListNumber = filterBundle.getInt("filterListNumber");
			filtersList = filterBundle.getStringArrayList("existingFilterList");
		}

		if (filtersList == null)
			filtersList = new ArrayList<String>();

		switch (filterType) {
		case FilterMode.USER:
			filterListArrayList.addAll(bController.getAllUsers());
			break;
		case FilterMode.KIND:
			filterListArrayList.addAll(bController.getAllKinds());
			break;
		case FilterMode.BRAND:
			filterListArrayList.addAll(bController.getAllBrands());
			break;
		case FilterMode.SHOP:
			filterListArrayList.addAll(bController.getAllShops());
			break;
		case FilterMode.PRODUCT:
			filterListArrayList.addAll(bController.getAllProducts());
			break;
		}

		filterListView = (ListView) dialogMainView
				.findViewById(R.id.filterListView);
		filterArrayAdapter = new ArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				filterListArrayList);
		filterListView.setAdapter(filterArrayAdapter);
		filterListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		for (String filter : filtersList) {
			filterListView.setItemChecked(filterListArrayList.indexOf(filter),
					true);
		}

		builder.setTitle("Set List Name")
				.setView(dialogMainView)
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						filtersList.clear();
						SparseBooleanArray sparseArray = filterListView
								.getCheckedItemPositions();
						for (int i = 0; i < sparseArray.size(); i++) {
							if (sparseArray.valueAt(i)) {
								filtersList.add(filterListArrayList.get(sparseArray.keyAt(i)));
							}
						}
						mListener
								.onDialogPositiveClick(StatsFilterDialogFragment.this);
					}
				})
				.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Send the negative button event back
								mListener
										.onDialogNegativeClick(StatsFilterDialogFragment.this);
							}
						});
		return builder.create();
	}

	public ArrayList<String> getFiltersList() {
		return filtersList;
	}

	public String getFragmentTag() {
		return fragmentTag;
	}

	public int getFilterMode() {
		return filterType;
	}

	public int getFilterListNumber() {
		return filterListNumber;
	}

}
