package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubMemberProfileModel_ID implements Serializable {

	private String strCustomerCode;

	private String strClientCode;

	public clsWebClubMemberProfileModel_ID() {
	}

	public clsWebClubMemberProfileModel_ID(String strCustomerCode, String strClientCode) {
		this.strCustomerCode = strCustomerCode;
		this.strClientCode = strClientCode;

	}

	@Override
	public boolean equals(Object obj) {
		clsWebClubMemberProfileModel_ID cp = (clsWebClubMemberProfileModel_ID) obj;
		if (this.strCustomerCode.equals(cp.getStrCustomerCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCustomerCode.hashCode() + this.strClientCode.hashCode();
	}

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

}
