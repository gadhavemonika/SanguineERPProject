package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubDesignationMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strDesignationCode")
	private String strDesignationCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubDesignationMasterModel_ID() {
	}

	public clsWebClubDesignationMasterModel_ID(String strDesignationCode, String strClientCode) {
		this.strDesignationCode = strDesignationCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrDesignationCode() {
		return strDesignationCode;
	}

	public void setStrDesignationCode(String strDesignationCode) {
		this.strDesignationCode = strDesignationCode;
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
		clsWebClubDesignationMasterModel_ID objModelId = (clsWebClubDesignationMasterModel_ID) obj;
		if (this.strDesignationCode.equals(objModelId.getStrDesignationCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strDesignationCode.hashCode() + this.strClientCode.hashCode();
	}

}
