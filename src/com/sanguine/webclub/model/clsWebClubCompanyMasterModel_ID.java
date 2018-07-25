package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubCompanyMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCompanyCode")
	private String strCompanyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubCompanyMasterModel_ID() {
	}

	public clsWebClubCompanyMasterModel_ID(String strCompanyCode, String strClientCode) {
		this.strCompanyCode = strCompanyCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
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
		clsWebClubCompanyMasterModel_ID objModelId = (clsWebClubCompanyMasterModel_ID) obj;
		if (this.strCompanyCode.equals(objModelId.getStrCompanyCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCompanyCode.hashCode() + this.strClientCode.hashCode();
	}

}
