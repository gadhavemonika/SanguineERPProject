package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPMSTaxMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsPMSTaxMasterModel_ID() {
	}

	public clsPMSTaxMasterModel_ID(String strTaxCode, String strClientCode) {
		this.strTaxCode = strTaxCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
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

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsPMSTaxMasterModel_ID objModelId = (clsPMSTaxMasterModel_ID) obj;
		if (this.strTaxCode.equals(objModelId.getStrTaxCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
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
