package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubStaffMasterModel_ID implements Serializable {
	@Column(name = "strStaffCode")
	private String strStaffCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubStaffMasterModel_ID() {
	}

	public clsWebClubStaffMasterModel_ID(String strStaffCode, String strClientCode) {
		this.strStaffCode = strStaffCode;
		this.strClientCode = strClientCode;
	}

	public String getStrStaffCode() {
		return strStaffCode;
	}

	public void setStrStaffCode(String strStaffCode) {
		this.strStaffCode = strStaffCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
