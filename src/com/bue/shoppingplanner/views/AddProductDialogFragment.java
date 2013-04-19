package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddProductDialogFragment extends DialogFragment {
	private DatabaseHandler db;
	
	private EditText productAddDialogEditText;
	private EditText brandAddDialogEditText;
	private EditText priceAddDialogEditText;
	private EditText numberAddDialogEditText;
	
	private ImageButton numberAddAddDialogImageButton;
	private ImageButton numberRemoveAddDialogImageButton;
	
	private Spinner productGroupAddDialogSpinner;
	private Spinner productKindAddDialogSpinner;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		db=new DatabaseHandler(getActivity());
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.add_product_dialog, null);
		//EditTexts
		productAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.priceAddDialogTextView);
		brandAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.brandAddDialogEditText);
		priceAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.productAddDialogEditText);
		numberAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.numberAddDialogEditText);
		
		//Spinners
		ArrayList<CharSequence> productGroupSpinnerList=new ArrayList<CharSequence>();
		for(ProductGroup group:db.getAllProductGroup()){
			productGroupSpinnerList.add(group.getName());
		}
		addSpinnerFromDatabase(getActivity(),dialogMainView, productGroupAddDialogSpinner,R.id.productGroupAddDialogSpinner, productGroupSpinnerList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		ArrayList<CharSequence> productKindSpinnerList=new ArrayList<CharSequence>();
		for(ProductKind kind:db.getAllProductKind()){
			productKindSpinnerList.add(kind.getName());
		}
		addSpinnerFromDatabase(getActivity(),dialogMainView, productKindAddDialogSpinner, R.id.productKindAddDialogSpinner, productKindSpinnerList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		
		
		builder.setTitle("Add Product")
			.setView(dialogMainView)
			.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

		return builder.create();
	}
	
	/**
	 * Method to create Spinner object getting values from the database.
	 * 
	 * @param context
	 * @param view
	 * @param spinner
	 * @param spinnerId
	 * @param spinnerList
	 * @param textViewResourceId
	 * @param resource
	 */
	public void addSpinnerFromDatabase(Context context, View view, Spinner spinner, int spinnerId, ArrayList<CharSequence> spinnerList, int textViewResourceId, int resource){
		spinner=(Spinner) view.findViewById(spinnerId);
		ArrayAdapter<CharSequence> arrayAdapter=new ArrayAdapter<CharSequence>(context,textViewResourceId,spinnerList);
		arrayAdapter.setDropDownViewResource(resource);
		spinner.setAdapter(arrayAdapter);
	}
	

}
