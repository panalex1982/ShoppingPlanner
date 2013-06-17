package com.bue.shoppingplanner.free.helpers;

public class CurrencyHelper {
	private String iso,
				name,
				symbol;

	public CurrencyHelper(String iso, String name, String symbol) {
		super();
		this.iso = iso;
		this.name = name;
		this.symbol = symbol;
	}

	public CurrencyHelper() {
		super();
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void copyCurrencyHelper(CurrencyHelper helper){
		iso=helper.getIso();
		name=helper.getName();
		symbol=helper.getSymbol();
	}
	

}
