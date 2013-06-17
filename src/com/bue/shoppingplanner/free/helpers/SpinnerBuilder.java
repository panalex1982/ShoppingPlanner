package com.bue.shoppingplanner.free.helpers;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerBuilder {
	
	/**
	 * Method to create Spinner object getting values from the ArrayList<CharSequence>.
	 * 
	 * @param context
	 * @param view
	 * @param spinnerId
	 * @param spinnerList
	 * @param textViewResourceId
	 * @param resource
	 * @return spinner
	 */
	public static Spinner createSpinnerFromArrayList(Context context, View view, int spinnerId, ArrayList<CharSequence> spinnerList, int textViewResourceId, int resource){
		Spinner spinner=(Spinner) view.findViewById(spinnerId);
		ArrayAdapter<CharSequence> arrayAdapter=new ArrayAdapter<CharSequence>(context,textViewResourceId,spinnerList);
		arrayAdapter.setDropDownViewResource(resource);
		spinner.setAdapter(arrayAdapter);
		return spinner;
	}
}
