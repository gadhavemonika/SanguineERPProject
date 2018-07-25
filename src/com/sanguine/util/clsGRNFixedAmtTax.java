package com.sanguine.util;

public class clsGRNFixedAmtTax {
	private String strGRNCode;

	private String strTaxCode;

	private String strTaxDesc;

	private double dblTaxRate;

	private double dblTaxAmt;

	private double dblTaxableAmt;

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public double getDblTaxRate() {
		return dblTaxRate;
	}

	public void setDblTaxRate(double dblTaxRate) {
		this.dblTaxRate = dblTaxRate;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblTaxableAmt() {
		return dblTaxableAmt;
	}

	public void setDblTaxableAmt(double dblTaxableAmt) {
		this.dblTaxableAmt = dblTaxableAmt;
	}
}
