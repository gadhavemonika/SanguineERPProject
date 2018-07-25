package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubCommitteeMemberRoleMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strRoleCode")
	private String strRoleCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubCommitteeMemberRoleMasterModel_ID() {
	}

	public clsWebClubCommitteeMemberRoleMasterModel_ID(String strRoleCode, String strClientCode) {
		this.strRoleCode = strRoleCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrRoleCode() {
		return strRoleCode;
	}

	public void setStrRoleCode(String strRoleCode) {
		this.strRoleCode = strRoleCode;
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
		clsWebClubCommitteeMemberRoleMasterModel_ID objModelId = (clsWebClubCommitteeMemberRoleMasterModel_ID) obj;
		if (this.strRoleCode.equals(objModelId.getStrRoleCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRoleCode.hashCode() + this.strClientCode.hashCode();
	}

}
