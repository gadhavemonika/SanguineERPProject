package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubLockerMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strLockerCode")
	private String strLockerCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubLockerMasterModel_ID() {
	}

	public clsWebClubLockerMasterModel_ID(String strLockerCode, String strClientCode) {
		this.strLockerCode = strLockerCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrLockerCode() {
		return strLockerCode;
	}

	public void setStrLockerCode(String strLockerCode) {
		this.strLockerCode = strLockerCode;
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
		clsWebClubLockerMasterModel_ID objModelId = (clsWebClubLockerMasterModel_ID) obj;
		if (this.strLockerCode.equals(objModelId.getStrLockerCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strLockerCode.hashCode() + this.strClientCode.hashCode();
	}

}
