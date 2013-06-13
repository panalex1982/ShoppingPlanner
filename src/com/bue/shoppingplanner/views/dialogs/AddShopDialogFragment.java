package com.bue.shoppingplanner.views.dialogs;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.ShopController;
import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.helpers.SpinnerBuilder;
import com.bue.shoppingplanner.model.Dbh;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class AddShopDialogFragment extends DialogFragment {
	private Dbh db;
	private ShopController cController;
	
	// Use this instance of the interface to deliver action events
    private AddShopDialogListener mListener;
    
	private EditText nameAddShopEditText, 
					addressAddShopEditText, 
					numberAddShopEditText,
					cityAddShopEditText,
					areaAddShopEditText,
					countryAddShopEditText,
					zipAddShopEditText;
	
	private Spinner existingAddShopSpinner, 
					typeAddShopSpinner;
	
	private ArrayList<CharSequence> existingShopsArrayList,
								shopTypeArrayList;
	
	private ShopElementHelper shopElement;
	
	//preview position of the existingAddShopSpinner
	protected int exAddShopPrvPosition;
	//It is used during initialization, because when savedInstanceState exist
	//the OnItemSelectedListener of existingAddShopSpinner is fired on the onResume()
	//and this clears the views
	protected boolean hasInitialized;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		cController=new ShopController(getActivity());
		db=new Dbh(getActivity());
		//Shop tmpShop=new Shop();
		//ShopDescription tmpDesc=new ShopDescription();
		existingShopsArrayList=new ArrayList<CharSequence>();
		shopTypeArrayList=new ArrayList<CharSequence>();
		exAddShopPrvPosition=-1;
		hasInitialized=false;
		
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.add_shop_dialog, null);
		
		//EditTexts Initialize
		nameAddShopEditText=(EditText) dialogMainView.findViewById(R.id.nameAddShopEditText);
		addressAddShopEditText=(EditText) dialogMainView.findViewById(R.id.addressAddShopEditText);
		numberAddShopEditText=(EditText) dialogMainView.findViewById(R.id.numberAddShopEditText);
		cityAddShopEditText=(EditText) dialogMainView.findViewById(R.id.cityAddShopEditText);
		areaAddShopEditText=(EditText) dialogMainView.findViewById(R.id.areaAddShopEditText);
		countryAddShopEditText=(EditText) dialogMainView.findViewById(R.id.countryAddShopEditText);
		zipAddShopEditText=(EditText) dialogMainView.findViewById(R.id.zipAddShopEditText);
		
		//Spinners Initialize
		for(Shop shop:Shop.getAllShop(db)){
			existingShopsArrayList.add(shop.getName());
		}
		existingAddShopSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(), dialogMainView, R.id.existingAddShopSpinner, 
				existingShopsArrayList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		shopTypeArrayList.addAll(cController.getAllShopDescription());
		typeAddShopSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(), dialogMainView, R.id.typeAddShopSpinner,
				shopTypeArrayList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		
		//Spinner existingAddShopSpinner, Adapters Listener
		//ArrayAdapter existingAddShopAdapterView=(ArrayAdapter) existingAddShopSpinner.getAdapter();
		existingAddShopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				if(hasInitialized){
					if(position!=0){
						
						String shopName=((Spinner)parent).getItemAtPosition(position).toString();
						ShopController shop=new ShopController(getActivity());
						
						nameAddShopEditText.setText(shopName);
						shop.selectShopElementHelperByShopName(shopName);
						addressAddShopEditText.setText(shop.getAddress());
						numberAddShopEditText.setText(shop.getNumber());
						cityAddShopEditText.setText(shop.getCity());
						areaAddShopEditText.setText(shop.getArea());
						countryAddShopEditText.setText(shop.getCountry());
						zipAddShopEditText.setText(shop.getZip());					
						typeAddShopSpinner.setSelection(shopTypeArrayList.indexOf(shop.getName()));
					}else if(position==0&&exAddShopPrvPosition!=0){
						nameAddShopEditText.setText("");
						addressAddShopEditText.setText("");
						numberAddShopEditText.setText("");
						cityAddShopEditText.setText("");
						areaAddShopEditText.setText("");
						countryAddShopEditText.setText("");
						zipAddShopEditText.setText("");
				    	typeAddShopSpinner.setSelection(0);
					}
					exAddShopPrvPosition=position;
				}
				hasInitialized=true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		builder.setTitle("Add Shop")
		.setView(dialogMainView)
		.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
            	//TODO: Debug Log.d("listElement.toString(): ",listElement.toString());
            	// Send the positive button event back to the host activity
            	shopElement=new ShopElementHelper(nameAddShopEditText.getText().toString(), addressAddShopEditText.getText().toString(),
            			numberAddShopEditText.getText().toString(),
    					cityAddShopEditText.getText().toString(),areaAddShopEditText.getText().toString(),
    					countryAddShopEditText.getText().toString(), zipAddShopEditText.getText().toString(),
    					typeAddShopSpinner.getSelectedItem().toString());
                mListener.onDialogPositiveClick(AddShopDialogFragment.this);
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	//Send the negative button event back
            	mListener.onDialogNegativeClick(AddShopDialogFragment.this);
            }
        });
		return builder.create();
	}
	
//	@Override
//	public void onViewStateRestored(Bundle savedInstanceState) {
//		super.onViewStateRestored(savedInstanceState);
//		// Restore Saved State
//		if(savedInstanceState!=null){
//			existingAddShopSpinner.setSelection(savedInstanceState.getInt("existingAddShopSpinner",0));
//			nameAddShopEditText.setText(savedInstanceState.getString("nameAddShopEditText"));
//			addressAddShopEditText.setText(savedInstanceState.getString("addressAddShopEditText"));
//			numberAddShopEditText.setText(savedInstanceState.getString("numberAddShopEditText"));
//			cityAddShopEditText.setText(savedInstanceState.getString("cityAddShopEditText"));
//			areaAddShopEditText.setText(savedInstanceState.getString("areaAddShopEditText"));
//			zipAddShopEditText.setText(savedInstanceState.getString("zipAddShopEditText"));
//			countryAddShopEditText.setText(savedInstanceState.getString("countryAddShopEditText"));
//			typeAddShopSpinner.setSelection(savedInstanceState.getInt("typeAddShopSpinner",0));
//		}
//	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            // Instantiate the AddProductDialogListener so we can send events to the host
            mListener = (AddShopDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
		throw new ClassCastException(activity.toString()
				+ " must implement AddProductDialogListener");
        }
	}
	
	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		state.putInt("existingAddShopSpinner",existingAddShopSpinner.getSelectedItemPosition());
		state.putString("nameAddShopEditText", nameAddShopEditText.getText().toString());
		state.putString("addressAddShopEditText", addressAddShopEditText.getText().toString());
		state.putString("numberAddShopEditText", numberAddShopEditText.getText().toString());
		state.putString("cityAddShopEditText", cityAddShopEditText.getText().toString());
		state.putString("areaAddShopEditText", areaAddShopEditText.getText().toString());
		state.putString("zipAddShopEditText", zipAddShopEditText.getText().toString());
		state.putString("countryAddShopEditText", countryAddShopEditText.getText().toString());
		state.putInt("typeAddShopSpinner", typeAddShopSpinner.getSelectedItemPosition());
		
	}
	
	

	public ShopElementHelper getShopElement() {
		return shopElement;
	}

	public void setShopElement(ShopElementHelper shopElement) {
		this.shopElement = shopElement;
	}



	/** The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AddShopDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    
    protected void refreshElements(){    	
    	nameAddShopEditText.setText(shopElement.getName()); 
		addressAddShopEditText.setText(shopElement.getName());
		numberAddShopEditText.setText(shopElement.getName());
		cityAddShopEditText.setText(shopElement.getName());
		areaAddShopEditText.setText(shopElement.getName());
		countryAddShopEditText.setText(shopElement.getName());
		zipAddShopEditText.setText(shopElement.getName());
    	typeAddShopSpinner.setSelection(1);
    }
	

}
