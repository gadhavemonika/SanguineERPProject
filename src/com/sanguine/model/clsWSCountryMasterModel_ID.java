package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsWSCountryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCountryCode")
	private String strCountryCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWSCountryMasterModel_ID() {
	}

	public clsWSCountryMasterModel_ID(String strCountryCode, String strClientCode) {
		this.strCountryCode = strCountryCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCountryCode() {
		return strCountryCode;
	}

	public void setStrCountryCode(String strCountryCode) {
		this.strCountryCode = strCountryCode;
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
		clsWSCountryMasterModel_ID objModelId = (clsWSCountryMasterModel_ID) obj;
		if (this.strCountryCode.equals(objModelId.getStrCountryCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCountryCode.hashCode() + this.strClientCode.hashCode();
	}

}
