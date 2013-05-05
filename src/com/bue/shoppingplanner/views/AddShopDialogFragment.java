package com.bue.shoppingplanner.views;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.ShopController;
import com.bue.shoppingplanner.helpers.ShopElementHelper;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.helpers.SpinnerBuilder;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;
import com.bue.shoppingplanner.views.AddProductDialogFragment.AddProductDialogListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddShopDialogFragment extends DialogFragment {

	private DatabaseHandler db;
	
	// Use this instance of the interface to deliver action events
    private AddProductDialogListener mListener;
    
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

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		db=new DatabaseHandler(getActivity());
		//Shop tmpShop=new Shop();
		//ShopDescription tmpDesc=new ShopDescription();
		existingShopsArrayList=new ArrayList<CharSequence>();
		shopTypeArrayList=new ArrayList<CharSequence>();
		exAddShopPrvPosition=-1;
		
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
		for(ShopDescription desc:ShopDescription.getAllShopDescription(db)){
			shopTypeArrayList.add(desc.getName());
		}
		typeAddShopSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(), dialogMainView, R.id.typeAddShopSpinner,
				shopTypeArrayList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		
		//Spinner existingAddShopSpinner, Adapters Listener
		//ArrayAdapter existingAddShopAdapterView=(ArrayAdapter) existingAddShopSpinner.getAdapter();
		existingAddShopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				if(position!=0){
					
					String shopName=((Spinner)parent).getItemAtPosition(position).toString();
					ShopController shop=new ShopController();
					
					nameAddShopEditText.setText(shopName);
					shop.selectShopElementHelperByShopName(db, shopName);
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