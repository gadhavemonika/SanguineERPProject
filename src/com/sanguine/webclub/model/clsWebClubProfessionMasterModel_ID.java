package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubProfessionMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strProfessionCode")
	private String strProfessionCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubProfessionMasterModel_ID() {
	}

	public clsWebClubProfessionMasterModel_ID(String strProfessionCode, String strClientCode) {
		this.strProfessionCode = strProfessionCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrProfessionCode() {
		return strProfessionCode;
	}

	public void setStrProfessionCode(String strProfessionCode) {
		this.strProfessionCode = strProfessionCode;
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
		clsWebClubProfessionMasterModel_ID objModelId = (clsWebClubProfessionMasterModel_ID) obj;
		if (this.strProfessionCode.equals(objModelId.getStrProfessionCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strProfessionCode.hashCode() + this.strClientCode.hashCode();
	}

}
