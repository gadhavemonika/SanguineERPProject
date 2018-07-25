package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubCountryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCountryCode")
	private String strCountryCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubCountryMasterModel_ID() {
	}

	public clsWebClubCountryMasterModel_ID(String strCountryCode, String strClientCode) {
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
		clsWebClubCountryMasterModel_ID objModelId = (clsWebClubCountryMasterModel_ID) obj;
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
