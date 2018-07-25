package com.sanguine.crm.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class clsInvoiceTaxDtlBean {

	private String strInvCode;

	private String strTaxCode;

	private String strTaxDesc;

	private double strTaxableAmt;

	private double strTaxAmt;

	private String strClientCode;

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

	public double getStrTaxableAmt() {
		return strTaxableAmt;
	}

	public void setStrTaxableAmt(double strTaxableAmt) {
		this.strTaxableAmt = strTaxableAmt;
	}

	public double getStrTaxAmt() {
		return strTaxAmt;
	}

	public void setStrTaxAmt(double strTaxAmt) {
		this.strTaxAmt = strTaxAmt;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

}
