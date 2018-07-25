package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsWSCityMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCityCode")
	private String strCityCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWSCityMasterModel_ID() {
	}

	public clsWSCityMasterModel_ID(String strCityCode, String strClientCode) {
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
		clsWSCityMasterModel_ID objModelId = (clsWSCityMasterModel_ID) obj;
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
