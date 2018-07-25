package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubPreMemberProfileModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCustomerCode")
	private String strCustomerCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubPreMemberProfileModel_ID() {
	}

	public clsWebClubPreMemberProfileModel_ID(String strCustomerCode, String strClientCode) {
		this.strCustomerCode = strCustomerCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCustomerCode() {
		return strCustomerCode;
	}

	public void setStrCustomerCode(String strCustomerCode) {
		this.strCustomerCode = strCustomerCode;
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
		clsWebClubPreMemberProfileModel_ID objModelId = (clsWebClubPreMemberProfileModel_ID) obj;
		if (this.strCustomerCode.equals(objModelId.getStrCustomerCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCustomerCode.hashCode() + this.strClientCode.hashCode();
	}

}
