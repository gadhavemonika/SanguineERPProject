package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubEditOtherInfoModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubEditOtherInfoModel_ID() {
	}

	public clsWebClubEditOtherInfoModel_ID(String strMemberCode, String strClientCode) {
		this.strMemberCode = strMemberCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = strMemberCode;
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
		clsWebClubEditOtherInfoModel_ID objModelId = (clsWebClubEditOtherInfoModel_ID) obj;
		if (this.strMemberCode.equals(objModelId.getStrMemberCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strMemberCode.hashCode() + this.strClientCode.hashCode();
	}

}
