package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.helpers.SpinnerBuilder;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddProductDialogFragment extends DialogFragment {
	private DatabaseHandler db;
	
	// Use this instance of the interface to deliver action events
    private AddProductDialogListener mListener;
	
	private EditText productAddDialogEditText;
	private EditText brandAddDialogEditText;
	private EditText priceAddDialogEditText;
	private EditText numberAddDialogEditText;
	
	private ImageButton numberAddAddDialogImageButton;
	private ImageButton numberRemoveAddDialogImageButton;
	
	private Spinner productGroupAddDialogSpinner;
	private Spinner productKindAddDialogSpinner;
	
	private ShoppingListElementHelper listElement;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		db=new DatabaseHandler(getActivity());
		listElement=new ShoppingListElementHelper();
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.add_product_dialog, null);
		//EditTexts initialize
		productAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.productAddDialogEditText);
		brandAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.brandAddDialogEditText);
		priceAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.priceAddDialogEditText);
		numberAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.numberAddDialogEditText);
		
		//Spinners initialize
		ArrayList<CharSequence> productGroupSpinnerList=new ArrayList<CharSequence>();
		for(ProductGroup group:db.getAllProductGroup()){
			productGroupSpinnerList.add(group.getName());
		}
		productGroupAddDialogSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(),dialogMainView, R.id.productGroupAddDialogSpinner, productGroupSpinnerList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		ArrayList<CharSequence> productKindSpinnerList=new ArrayList<CharSequence>();
		for(ProductKind kind:db.getAllProductKind()){
			productKindSpinnerList.add(kind.getName());
		}
		productKindAddDialogSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(),dialogMainView, R.id.productKindAddDialogSpinner, productKindSpinnerList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		
		//ImageButtons initialize
		numberAddAddDialogImageButton=(ImageButton) dialogMainView.findViewById(R.id.numberAddAddDialogImageButton);
		numberAddAddDialogImageButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					calculateNewQuantity(1);
				}
				return false;
			}
		});
		numberRemoveAddDialogImageButton=(ImageButton) dialogMainView.findViewById(R.id.numberRemoveAddDialogImageButton);
		numberRemoveAddDialogImageButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					calculateNewQuantity(-1);
				}
				return false;
			}
		});
		
		builder.setTitle("Add Product")
			.setView(dialogMainView)
			.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	listElement.setProduct(productAddDialogEditText.getText().toString());
                	listElement.setBrand(brandAddDialogEditText.getText().toString());
                	listElement.setPrice(Double.parseDouble(priceAddDialogEditText.getText().toString()));
                	listElement.setQuantity(Integer.parseInt(numberAddDialogEditText.getText().toString()));
                	
                	listElement.setGroup(productGroupAddDialogSpinner.getSelectedItem().toString());
                	listElement.setKind(productKindAddDialogSpinner.getSelectedItem().toString());
                	listElement.setChecked(true);
                	//TODO: Debug Log.d("listElement.toString(): ",listElement.toString());
                	// Send the positive button event back to the host activity
                    mListener.onDialogPositiveClick(AddProductDialogFragment.this);
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	//Send the negative button event back
                	mListener.onDialogNegativeClick(AddProductDialogFragment.this);
                }
            });
		

		return builder.create();
	}
	
	
	
	protected void calculateNewQuantity(int i) {
		if(numberAddDialogEditText.getText().toString().equalsIgnoreCase("")){
			numberAddDialogEditText.setText(Integer.toString(0));
		}
		int quantity=Integer.parseInt(numberAddDialogEditText.getText().toString())+i;
		if(quantity<0)
			quantity=0;
		numberAddDialogEditText.setText(Integer.toString(quantity));
		
	}



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            // Instantiate the AddProductDialogListener so we can send events to the host
            mListener = (AddProductDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
		throw new ClassCastException(activity.toString()
				+ " must implement AddProductDialogListener");
        }
	}
	
	



	public ShoppingListElementHelper getListElement() {
		return listElement;
	}



	public void setListElement(ShoppingListElementHelper listElement) {
		this.listElement = listElement;
	}



	/** The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AddProductDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	

}
