package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubSubCategoryMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSCCode")
	private String strSCCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubSubCategoryMasterModel_ID() {
	}

	public clsWebClubSubCategoryMasterModel_ID(String strSCCode, String strClientCode) {
		this.strSCCode = strSCCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSCCode() {
		return strSCCode;
	}

	public void setStrSCCode(String strSCCode) {
		this.strSCCode = strSCCode;
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
		clsWebClubSubCategoryMasterModel_ID objModelId = (clsWebClubSubCategoryMasterModel_ID) obj;
		if (this.strSCCode.equals(objModelId.getStrSCCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSCCode.hashCode() + this.strClientCode.hashCode();
	}

}
