package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSanctionAutherityMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSanctionCode")
	private String strSanctionCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSanctionAutherityMasterModel_ID() {
	}

	public clsSanctionAutherityMasterModel_ID(String strSanctionCode, String strClientCode) {
		this.strSanctionCode = strSanctionCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSanctionCode() {
		return strSanctionCode;
	}

	public void setStrSanctionCode(String strSanctionCode) {
		this.strSanctionCode = strSanctionCode;
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
		clsSanctionAutherityMasterModel_ID objModelId = (clsSanctionAutherityMasterModel_ID) obj;
		if (this.strSanctionCode.equals(objModelId.getStrSanctionCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSanctionCode.hashCode() + this.strClientCode.hashCode();
	}

}
