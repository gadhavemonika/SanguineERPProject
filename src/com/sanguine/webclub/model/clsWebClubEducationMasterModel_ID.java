package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubEducationMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strEducationCode")
	private String strEducationCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubEducationMasterModel_ID() {
	}

	public clsWebClubEducationMasterModel_ID(String strEducationCode, String strClientCode) {
		this.strEducationCode = strEducationCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrEducationCode() {
		return strEducationCode;
	}

	public void setStrEducationCode(String strEducationCode) {
		this.strEducationCode = strEducationCode;
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
		clsWebClubEducationMasterModel_ID objModelId = (clsWebClubEducationMasterModel_ID) obj;
		if (this.strEducationCode.equals(objModelId.getStrEducationCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strEducationCode.hashCode() + this.strClientCode.hashCode();
	}

}
