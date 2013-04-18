package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddProductDialogFragment extends DialogFragment {
	private DatabaseHandler db;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		db=new DatabaseHandler(getActivity());
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.activity_shopping_list, null);
		
		Spinner productGroupAddDialogSpinner=(Spinner) dialogMainView.findViewById(R.id.productGroupAddDialogSpinner);
		ArrayList<CharSequence> groupsName=new ArrayList<CharSequence>();
		for(ProductGroup group:db.getAllProductGroup()){
			groupsName.add(group.getName());
		}
		ArrayAdapter<CharSequence> productGroupAdapter=new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item,groupsName);
		productGroupAdapter.setDropDownViewResource(STYLE_NORMAL);
		productGroupAddDialogSpinner.setAdapter(productGroupAdapter);
		builder.setTitle("Add Product")
			.setView(dialogMainView);
		return builder.create();
	}
	

}
