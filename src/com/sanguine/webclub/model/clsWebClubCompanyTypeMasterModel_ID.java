package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubCompanyTypeMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCompanyTypeCode")
	private String strCompanyTypeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubCompanyTypeMasterModel_ID() {
	}

	public clsWebClubCompanyTypeMasterModel_ID(String strCompanyTypeCode, String strClientCode) {
		this.strCompanyTypeCode = strCompanyTypeCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCompanyTypeCode() {
		return strCompanyTypeCode;
	}

	public void setStrCompanyTypeCode(String strCompanyTypeCode) {
		this.strCompanyTypeCode = strCompanyTypeCode;
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
		clsWebClubCompanyTypeMasterModel_ID objModelId = (clsWebClubCompanyTypeMasterModel_ID) obj;
		if (this.strCompanyTypeCode.equals(objModelId.getStrCompanyTypeCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCompanyTypeCode.hashCode() + this.strClientCode.hashCode();
	}

}
