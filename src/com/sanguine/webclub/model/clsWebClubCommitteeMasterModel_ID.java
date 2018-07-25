package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubCommitteeMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCommitteeCode")
	private String strCommitteeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubCommitteeMasterModel_ID() {
	}

	public clsWebClubCommitteeMasterModel_ID(String strCommitteeCode, String strClientCode) {
		this.strCommitteeCode = strCommitteeCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCommitteeCode() {
		return strCommitteeCode;
	}

	public void setStrCommitteeCode(String strCommitteeCode) {
		this.strCommitteeCode = strCommitteeCode;
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
		clsWebClubCommitteeMasterModel_ID objModelId = (clsWebClubCommitteeMasterModel_ID) obj;
		if (this.strCommitteeCode.equals(objModelId.getStrCommitteeCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCommitteeCode.hashCode() + this.strClientCode.hashCode();
	}

}
