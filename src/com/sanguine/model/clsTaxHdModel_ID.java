package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsTaxHdModel_ID implements Serializable {

	private String strTaxCode;
	private String strClientCode;

	public clsTaxHdModel_ID() {
	}

	public clsTaxHdModel_ID(String strTaxCode, String strClientCode) {
		this.strTaxCode = strTaxCode;
		this.strClientCode = strClientCode;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsTaxHdModel_ID cp = (clsTaxHdModel_ID) obj;
		if (this.strTaxCode.equals(cp.getStrTaxCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strTaxCode.hashCode() + this.strClientCode.hashCode();
	}
}
