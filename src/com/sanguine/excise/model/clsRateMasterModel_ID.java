package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsRateMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsRateMasterModel_ID() {
	}

	public clsRateMasterModel_ID(String strBrandCode, String strClientCode) {
		this.strBrandCode = strBrandCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
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
		clsRateMasterModel_ID objModelId = (clsRateMasterModel_ID) obj;
		if (this.strBrandCode.equals(objModelId.getStrBrandCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBrandCode.hashCode() + this.strClientCode.hashCode();
	}

}
