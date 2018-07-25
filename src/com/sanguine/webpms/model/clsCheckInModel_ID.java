package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsCheckInModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strCheckInNo")
	private String strCheckInNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCheckInModel_ID() {
	}

	public clsCheckInModel_ID(String strCheckInNo, String strClientCode) {
		this.strCheckInNo = strCheckInNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrCheckInNo() {
		return strCheckInNo;
	}

	public void setStrCheckInNo(String strCheckInNo) {
		this.strCheckInNo = strCheckInNo;
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
		clsCheckInModel_ID objModelId = (clsCheckInModel_ID) obj;
		if (this.strCheckInNo.equals(objModelId.getStrCheckInNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCheckInNo.hashCode() + this.strClientCode.hashCode();
	}

}
