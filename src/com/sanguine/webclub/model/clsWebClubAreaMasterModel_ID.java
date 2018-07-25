package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubAreaMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strAreaCode")
	private String strAreaCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubAreaMasterModel_ID() {
	}

	public clsWebClubAreaMasterModel_ID(String strAreaCode, String strClientCode) {
		this.strAreaCode = strAreaCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrAreaCode() {
		return strAreaCode;
	}

	public void setStrAreaCode(String strAreaCode) {
		this.strAreaCode = strAreaCode;
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
		clsWebClubAreaMasterModel_ID objModelId = (clsWebClubAreaMasterModel_ID) obj;
		if (this.strAreaCode.equals(objModelId.getStrAreaCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAreaCode.hashCode() + this.strClientCode.hashCode();
	}

}
