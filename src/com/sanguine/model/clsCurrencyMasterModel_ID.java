package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCurrencyMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCurrencyCode")
	private String strCurrencyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCurrencyMasterModel_ID() {
	}

	public clsCurrencyMasterModel_ID(String strCurrencyCode, String strClientCode) {
		this.strCurrencyCode = strCurrencyCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = strCurrencyCode;
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
		clsCurrencyMasterModel_ID objModelId = (clsCurrencyMasterModel_ID) obj;
		if (this.strCurrencyCode.equals(objModelId.getStrCurrencyCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCurrencyCode.hashCode() + this.strClientCode.hashCode();
	}

}
