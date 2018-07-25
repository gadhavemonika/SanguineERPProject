package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubSalutationMasterModel_ID implements Serializable {
	@Column(name = "strSalutationCode")
	private String strSalutationCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubSalutationMasterModel_ID() {
	}

	public clsWebClubSalutationMasterModel_ID(String strSalutationCode, String strClientCode) {
		this.strSalutationCode = strSalutationCode;
		this.strClientCode = strClientCode;
	}

	public String getStrSalutationCode() {
		return strSalutationCode;
	}

	public void setStrSalutationCode(String strSalutationCode) {
		this.strSalutationCode = strSalutationCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
