package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsTaxGroupMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strTaxGroupCode")
	private String strTaxGroupCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsTaxGroupMasterModel_ID() {
	}

	public clsTaxGroupMasterModel_ID(String strTaxGroupCode, String strClientCode) {
		this.strTaxGroupCode = strTaxGroupCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrTaxGroupCode() {
		return strTaxGroupCode;
	}

	public void setStrTaxGroupCode(String strTaxGroupCode) {
		this.strTaxGroupCode = strTaxGroupCode;
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
		clsTaxGroupMasterModel_ID objModelId = (clsTaxGroupMasterModel_ID) obj;
		if (this.strTaxGroupCode.equals(objModelId.getStrTaxGroupCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strTaxGroupCode.hashCode() + this.strClientCode.hashCode();
	}

}
