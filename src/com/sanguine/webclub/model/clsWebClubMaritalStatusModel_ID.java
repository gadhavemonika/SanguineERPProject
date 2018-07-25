package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubMaritalStatusModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strMaritalCode")
	private String strMaritalCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubMaritalStatusModel_ID() {
	}

	public clsWebClubMaritalStatusModel_ID(String strMaritalCode, String strClientCode) {
		this.strMaritalCode = strMaritalCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrMaritalCode() {
		return strMaritalCode;
	}

	public void setStrMaritalCode(String strMaritalCode) {
		this.strMaritalCode = strMaritalCode;
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
		clsWebClubMaritalStatusModel_ID objModelId = (clsWebClubMaritalStatusModel_ID) obj;
		if (this.strMaritalCode.equals(objModelId.getStrMaritalCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strMaritalCode.hashCode() + this.strClientCode.hashCode();
	}

}
