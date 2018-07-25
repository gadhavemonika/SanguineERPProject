package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsRegionMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strRegionCode")
	private String strRegionCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsRegionMasterModel_ID() {
	}

	public clsRegionMasterModel_ID(String strRegionCode, String strClientCode) {
		this.strRegionCode = strRegionCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrRegionCode() {
		return strRegionCode;
	}

	public void setStrRegionCode(String strRegionCode) {
		this.strRegionCode = strRegionCode;
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
		clsRegionMasterModel_ID objModelId = (clsRegionMasterModel_ID) obj;
		if (this.strRegionCode.equals(objModelId.getStrRegionCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRegionCode.hashCode() + this.strClientCode.hashCode();
	}

}
