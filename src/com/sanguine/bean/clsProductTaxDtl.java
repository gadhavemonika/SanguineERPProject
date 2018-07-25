package com.sanguine.bean;

public class clsProductTaxDtl {

	private String strProductCode;

	private String strExcisable;

	private String strSupplierCode;

	private String strTaxIndicator;

	private String strPickMRPForTaxCal;

	private double dblUnitPrice;

	private double dblMarginPer;

	private double dblDiscountPer;

	private double dblDiscountAmt;

	private double dblMRP;

	private double dblQty;

	public String getStrProductCode() {
		return strProductCode;
	}

	public void setStrProductCode(String strProductCode) {
		this.strProductCode = strProductCode;
	}

	public String getStrTaxIndicator() {
		return strTaxIndicator;
	}

	public void setStrTaxIndicator(String strTaxIndicator) {
		this.strTaxIndicator = strTaxIndicator;
	}

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public String getStrSupplierCode() {
		return strSupplierCode;
	}

	public void setStrSupplierCode(String strSupplierCode) {
		this.strSupplierCode = strSupplierCode;
	}

	public double getDblMRP() {
		return dblMRP;
	}

	public void setDblMRP(double dblMRP) {
		this.dblMRP = dblMRP;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrPickMRPForTaxCal() {
		return strPickMRPForTaxCal;
	}

	public void setStrPickMRPForTaxCal(String strPickMRPForTaxCal) {
		this.strPickMRPForTaxCal = strPickMRPForTaxCal;
	}

	public String getStrExcisable() {
		return strExcisable;
	}

	public void setStrExcisable(String strExcisable) {
		this.strExcisable = strExcisable;
	}

	public double getDblMarginPer() {
		return dblMarginPer;
	}

	public void setDblMarginPer(double dblMarginPer) {
		this.dblMarginPer = dblMarginPer;
	}

	public double getDblDiscountPer() {
		return dblDiscountPer;
	}

	public void setDblDiscountPer(double dblDiscountPer) {
		this.dblDiscountPer = dblDiscountPer;
	}

	public double getDblDiscountAmt() {
		return dblDiscountAmt;
	}

	public void setDblDiscountAmt(double dblDiscountAmt) {
		this.dblDiscountAmt = dblDiscountAmt;
	}

}
