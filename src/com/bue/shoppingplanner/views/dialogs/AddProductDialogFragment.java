package com.bue.shoppingplanner.views.dialogs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.controllers.CurrencyController;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.helpers.SpinnerBuilder;
import com.bue.shoppingplanner.helpers.VatHelper;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.Product;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddProductDialogFragment extends DialogFragment {
	private DatabaseHandler db;
	
	// Use this instance of the interface to deliver action events
    private AddProductDialogListener mListener;
	
	private AutoCompleteTextView productAddDialogEditText,
								vatAddDialogAutoCompleteTextView;
	
	private EditText brandAddDialogEditText;
	private EditText priceAddDialogEditText;
	private EditText numberAddDialogEditText;
	
	private ImageButton numberAddAddDialogImageButton;
	private ImageButton numberRemoveAddDialogImageButton;
	
	private Spinner productGroupAddDialogSpinner;
	private Spinner productKindAddDialogSpinner;
	private Spinner currencyAddDialogSpinner;
	
	private ShoppingListElementHelper listElement;
	
	private VatHelper vat;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		db=new DatabaseHandler(getActivity());
		listElement=new ShoppingListElementHelper();
		vat=new VatHelper(getActivity());
		
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.add_product_dialog, null);
		//EditTexts initialize
		productAddDialogEditText=(AutoCompleteTextView) dialogMainView.findViewById(R.id.productAddDialogEditText);
		vatAddDialogAutoCompleteTextView=(AutoCompleteTextView) dialogMainView.findViewById(R.id.vatAddDialogAutoCompleteTextView);
		
		brandAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.brandAddDialogEditText);
		priceAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.priceAddDialogEditText);
		numberAddDialogEditText=(EditText) dialogMainView.findViewById(R.id.numberAddDialogEditText);
			
		
		//Add Adapters to auto-complete text views
		BoughtController bcontroller=new BoughtController(getActivity());
		ArrayList<String> productNames=new ArrayList<String>();
		for(Product product:bcontroller.getProductList()){
			productNames.add(product.getName());
		}
		ArrayAdapter productAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, productNames);
		productAddDialogEditText.setAdapter(productAdapter);
		
		//Vat Adapter
		ArrayAdapter vatAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, vat.getVatRates());
		vatAddDialogAutoCompleteTextView.setAdapter(vatAdapter);
		vatAddDialogAutoCompleteTextView.setSelection(0);
		
		//Spinners initialize
		ArrayList<CharSequence> productGroupSpinnerList=new ArrayList<CharSequence>();
		for(ProductGroup group:ProductGroup.getAllProductGroup(db)){
			productGroupSpinnerList.add(group.getName());
		}
		productGroupAddDialogSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(),dialogMainView, R.id.productGroupAddDialogSpinner, productGroupSpinnerList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		ArrayList<CharSequence> productKindSpinnerList=new ArrayList<CharSequence>();
		for(ProductKind kind:ProductKind.getAllProductKind(db)){
			productKindSpinnerList.add(kind.getName());
		}
		productKindAddDialogSpinner=SpinnerBuilder.createSpinnerFromArrayList(getActivity(),dialogMainView, R.id.productKindAddDialogSpinner, productKindSpinnerList, android.R.layout.simple_spinner_item,android.R.layout.simple_spinner_dropdown_item);
		currencyAddDialogSpinner=(Spinner)dialogMainView.findViewById(R.id.currencyAddDialogSpinner);
		CurrencyController controller=new CurrencyController(getActivity());
		currencyAddDialogSpinner.setSelection(controller.getDefaultCurrencyPosition());
		
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
		
		validationListeners();
		
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
                	listElement.setBarcode("unknown");//TODO: When I add barcodes I will present
                								//the barcode or "unknown" for not known barcodes
                	listElement.setCurrency(currencyAddDialogSpinner.getSelectedItem().toString());
                	listElement.setVat(Double.parseDouble(vatAddDialogAutoCompleteTextView.getText().toString())/100);
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
    
    private void validationListeners(){
    	// priceAddDialogEditText
    	priceAddDialogEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!priceAddDialogEditText.getText().toString().equals("")){
						priceAddDialogEditText.setText(formatCurrecy(priceAddDialogEditText.getText().toString(),currencyAddDialogSpinner.getSelectedItem().toString()));		
				}
			}
		});
    	// currencyAddDialogSpinner
    	currencyAddDialogSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(!priceAddDialogEditText.getText().toString().equals(""))
					priceAddDialogEditText.setText(formatCurrecy(priceAddDialogEditText.getText().toString(),currencyAddDialogSpinner.getSelectedItem().toString()));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    }
    
    private String formatCurrecy(String price, String currencyIso){
    	DecimalFormat format = new DecimalFormat();
		Currency currency=Currency.getInstance(currencyIso);
		//DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		//symbols.setCurrency(currency);
		format.setGroupingUsed(false);
		format.setMaximumFractionDigits(currency.getDefaultFractionDigits());
		format.setMinimumFractionDigits(currency.getDefaultFractionDigits());
		//Log.d("Currency test: ",currency.getCurrencyCode()+" "+currency.getSymbol()+" "+currency.getDefaultFractionDigits());
		format.setCurrency(currency);
		//format.setDecimalFormatSymbols(symbols);
		String formatted = format.format(Double.parseDouble(price));
		return formatted;
    }
	

}
