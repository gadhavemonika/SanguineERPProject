package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsInvoiceHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsInvoiceHdModel_ID() {
	}

	public clsInvoiceHdModel_ID(String strInvCode, String strClientCode) {
		this.strInvCode = strInvCode;
		this.strClientCode = strClientCode;
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

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsInvoiceHdModel_ID objModelId = (clsInvoiceHdModel_ID) obj;
		if (this.strInvCode.equals(objModelId.getStrInvCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strInvCode.hashCode() + this.strClientCode.hashCode();
	}

}
