package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubInvitedByMasterModel_ID implements Serializable {
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubInvitedByMasterModel_ID() {
	}

	public clsWebClubInvitedByMasterModel_ID(String strInvCode, String strClientCode) {
		this.strInvCode = strInvCode;
		this.strClientCode = strClientCode;
	}

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
