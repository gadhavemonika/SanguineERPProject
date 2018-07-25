package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseLocationMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsExciseLocationMasterModel_ID() {
	}

	public clsExciseLocationMasterModel_ID(String strLocCode, String strClientCode) {
		this.strLocCode = strLocCode;
		this.strClientCode = strClientCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
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
		clsExciseLocationMasterModel_ID objModelId = (clsExciseLocationMasterModel_ID) obj;
		if (this.strLocCode.equals(objModelId.getStrLocCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strLocCode.hashCode() + this.strClientCode.hashCode();
	}

}
