package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCategoryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCatCode")
	private String strCatCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCategoryMasterModel_ID() {
	}

	public clsCategoryMasterModel_ID(String strCatCode, String strClientCode) {
		this.strCatCode = strCatCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCatCode() {
		return strCatCode;
	}

	public void setStrCatCode(String strCatCode) {
		this.strCatCode = strCatCode;
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
		clsCategoryMasterModel_ID objModelId = (clsCategoryMasterModel_ID) obj;
		if (this.strCatCode.equals(objModelId.getStrCatCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCatCode.hashCode() + this.strClientCode.hashCode();
	}

}
