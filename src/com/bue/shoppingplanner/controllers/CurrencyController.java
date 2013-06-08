package com.bue.shoppingplanner.controllers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.helpers.CurrencyHelper;
import com.bue.shoppingplanner.model.Currencies;
import com.bue.shoppingplanner.model.Dbh;
import com.bue.shoppingplanner.utilities.SPSharedPreferences;

public class CurrencyController implements SPSharedPreferences {
	
	private Dbh db=null;
	private SharedPreferences settings;
	private String selectedCurrency;
	private String defaultCurrency;
	private int defaultCurrencyPosition;
	private ArrayList<CurrencyHelper> currencies;
	
	public CurrencyController(Context context) {
		super();
		initialize(context);
	}
	
	public CurrencyController(Context context, String selectedCurrency) {
		super();
		initialize(context);
		this.selectedCurrency=selectedCurrency;
	}
	
	private void initialize(Context context) {
		currencies=new ArrayList<CurrencyHelper>();
		createCurrencyList(context);
		settings=context.getSharedPreferences(SET_NAME,0);
		defaultCurrency=settings.getString(SET_DEF_CURRENCY, "USD");
		defaultCurrencyPosition=getCurrencyListPosition(defaultCurrency);
		selectedCurrency="USD";
		db=new Dbh(context);		
	}

	public String getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(String selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}
	
	public void setDefaultCurrencyFromName(String name){
		Editor edit=settings.edit();	
		CurrencyHelper defaultCurrencyHelper=new CurrencyHelper();
		for(CurrencyHelper helper:currencies){
			if(helper.getName().equals(name)){
				defaultCurrencyHelper.copyCurrencyHelper(helper);
				defaultCurrency=defaultCurrencyHelper.getIso();
				defaultCurrencyPosition=currencies.indexOf(helper);
			}
		}
		edit.putString(SET_DEF_CURRENCY, defaultCurrency);
		edit.commit();
	}
	
	public void setDefaultCurrencyFromLocale(Locale locale){
		Editor edit=settings.edit();	
		Currency currency=Currency.getInstance(locale);
		defaultCurrency=currency.getCurrencyCode();
		edit.putString(SET_DEF_CURRENCY, defaultCurrency);
		edit.commit();
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}
	

	public int getDefaultCurrencyPosition() {
		return defaultCurrencyPosition;
	}
	
	public int getCurrencyListPosition(String iso){
		int position=0;
		for(CurrencyHelper helper:currencies){
			if(helper.getIso().equals(iso)){
				position=currencies.indexOf(helper);
			}
		}
		return position;
	}

	public ArrayList<CurrencyHelper> getCurrencies() {
		return currencies;
	}

	private void createCurrencyList(Context context){
		Resources resources=context.getResources();
		ArrayList<String> listIso=new ArrayList<String>(Arrays.asList(resources.getStringArray(R.array.currencies)));
		ArrayList<String> listNames=new ArrayList<String>(Arrays.asList(resources.getStringArray(R.array.currency_names)));
		ArrayList<String> listSymbols=new ArrayList<String>(Arrays.asList(resources.getStringArray(R.array.currency_symbols)));
		for(int i=0;i<listIso.size();i++){
			CurrencyHelper helper=new CurrencyHelper(listIso.get(i),listNames.get(i),listSymbols.get(i));
			currencies.add(helper);
		}
	}
	
	public boolean isDefaultCurrency(String selectedCurrency){
		return defaultCurrency.equals(selectedCurrency);
	}
	
	public double getPriceToDefaultCurrency(double price){
		if(!isDefaultCurrency(selectedCurrency)){
			Currencies currency=Currencies.getCurrencies(db, selectedCurrency);
			Currencies defaultRate=Currencies.getCurrencies(db, defaultCurrency);
			double rateToUsd=currency.getRateToUsd();
			double rateToDef=defaultRate.getRateToUsd();
			if(defaultCurrency.equals("USD")){
				price = price/rateToUsd;
			}else if(selectedCurrency.equals("USD")){				
				price = price*rateToDef;
			}else{
				price=price/rateToDef;
			}
		}
		return price;
	}
	
	public String getCurrencySymbol(){
        Currency currency=Currency.getInstance(defaultCurrency);
        return currency.getSymbol();
	}
	public static String formatCurrecy(String price, String currencyIso){
    	DecimalFormat format = new DecimalFormat();
		Currency currency=Currency.getInstance(currencyIso);
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		//symbols.setCurrency(currency);
		format.setGroupingUsed(false);
		format.setMaximumFractionDigits(currency.getDefaultFractionDigits());
		format.setMinimumFractionDigits(currency.getDefaultFractionDigits());
		//Log.d("Currency test: ",currency.getCurrencyCode()+" "+currency.getSymbol()+" "+currency.getDefaultFractionDigits());
		format.setCurrency(currency);
		format.setDecimalFormatSymbols(symbols);
		String formatted = format.format(Double.parseDouble(price));
		return formatted;
    }
	
	public String formatCurrecy(String price){
    	DecimalFormat format = new DecimalFormat();
		Currency currency=Currency.getInstance(defaultCurrency);
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
