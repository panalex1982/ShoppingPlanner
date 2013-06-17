package com.bue.shoppingplanner.free.helpers;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bue.shoppingplanner.free.utilities.SPSharedPreferences;

public class VatHelper implements SPSharedPreferences {
	private ArrayList<String> vatRates;
	private SharedPreferences settings;
	
	public VatHelper(Context context) {
		super();
		vatRates=new ArrayList<String>();
		settings=context.getSharedPreferences(SET_NAME,0);
		changeRateList();
	}

	public ArrayList<String> getVatRates() {
		return vatRates;
	}
	
	public void setRates(String standardRate, String reducedRate){
		Editor edit=settings.edit();
		edit.putString(SET_STANDARD_VAT_RATE, standardRate);
		edit.putString(SET_REDUCED_VAT_RATE, reducedRate);
		edit.commit();
		vatRates.clear();
		 changeRateList();
	}
	
	private void changeRateList(){
		vatRates.add(settings.getString(SET_STANDARD_VAT_RATE, "0"));
		vatRates.add(settings.getString(SET_REDUCED_VAT_RATE, "0"));
	}

	public String getStandardRate() {
		return vatRates.get(0);
	}

	public String getReducedRate() {
		return vatRates.get(1);
	}
}
