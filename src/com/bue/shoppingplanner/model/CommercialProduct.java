package com.bue.shoppingplanner.model;

/**
 * Commercial firm and name of the product
 * @author Panagiotis
 *
 */
public class CommercialProduct {
	private String barcode;
	private String commercialName, companyBrand;
	public CommercialProduct() {
		super();
	}
	public CommercialProduct(String barcode, String commercialName,
			String companyBrand) {
		super();
		this.barcode = barcode;
		this.commercialName = commercialName;
		this.companyBrand = companyBrand;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getCommercialName() {
		return commercialName;
	}
	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	public String getCompanyBrand() {
		return companyBrand;
	}
	public void setCompanyBrand(String companyBrand) {
		this.companyBrand = companyBrand;
	}
	
	
}
