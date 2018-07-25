package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCorporateMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCorporateCode")
	private String strCorporateCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCorporateMasterModel_ID() {
	}

	public clsCorporateMasterModel_ID(String strCorporateCode, String strClientCode) {
		this.strCorporateCode = strCorporateCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCorporateCode() {
		return strCorporateCode;
	}

	public void setStrCorporateCode(String strCorporateCode) {
		this.strCorporateCode = strCorporateCode;
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
		clsCorporateMasterModel_ID objModelId = (clsCorporateMasterModel_ID) obj;
		if (this.strCorporateCode.equals(objModelId.getStrCorporateCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCorporateCode.hashCode() + this.strClientCode.hashCode();
	}

}
