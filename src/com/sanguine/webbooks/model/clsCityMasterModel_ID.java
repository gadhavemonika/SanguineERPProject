package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCityMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCityCode")
	private String strCityCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCityMasterModel_ID() {
	}

	public clsCityMasterModel_ID(String strCityCode, String strClientCode) {
		this.strCityCode = strCityCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCityCode() {
		return strCityCode;
	}

	public void setStrCityCode(String strCityCode) {
		this.strCityCode = strCityCode;
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
		clsCityMasterModel_ID objModelId = (clsCityMasterModel_ID) obj;
		if (this.strCityCode.equals(objModelId.getStrCityCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCityCode.hashCode() + this.strClientCode.hashCode();
	}

}
