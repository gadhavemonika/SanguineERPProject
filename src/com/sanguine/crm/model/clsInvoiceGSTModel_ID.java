package com.sanguine.crm.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsInvoiceGSTModel_ID implements Serializable {

	private String strInvCode;

	private String strTaxCode;

	private String strProdCode;

	private String strClientCode;

	public clsInvoiceGSTModel_ID() {
	}

	public clsInvoiceGSTModel_ID(String strInvCode, String strProdCode, String strTaxCode, String strClientCode) {
		this.strInvCode = strInvCode;
		this.strTaxCode = strTaxCode;
		this.strProdCode = strProdCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsInvoiceGSTModel_ID cp = (clsInvoiceGSTModel_ID) obj;
		if (this.strInvCode.equals(cp.getStrInvCode()) && this.strTaxCode.equals(cp.getStrTaxCode()) && this.strProdCode.equals(cp.getStrProdCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strInvCode.hashCode() + this.strTaxCode.hashCode() + this.strProdCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

}
