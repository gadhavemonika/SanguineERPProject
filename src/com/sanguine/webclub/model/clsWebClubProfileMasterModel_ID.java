package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubProfileMasterModel_ID implements Serializable {

	@Column(name = "strProfileCode")
	private String strProfileCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubProfileMasterModel_ID() {
	}

	public clsWebClubProfileMasterModel_ID(String strProfileCode, String strClientCode) {
		this.strProfileCode = strProfileCode;
		this.strClientCode = strClientCode;
	}

	public String getStrProfileCode() {
		return strProfileCode;
	}

	public void setStrProfileCode(String strProfileCode) {
		this.strProfileCode = strProfileCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
