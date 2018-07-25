package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubCurrencyDetailsMasterModel_ID implements Serializable {

	@Column(name = "strCurrCode")
	private String strCurrCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubCurrencyDetailsMasterModel_ID() {
	}

	public clsWebClubCurrencyDetailsMasterModel_ID(String strCurrCode, String strClientCode) {
		this.strCurrCode = strCurrCode;
		this.strClientCode = strClientCode;
	}

	public String getStrCurrCode() {
		return strCurrCode;
	}

	public void setStrCurrCode(String strCurrCode) {
		this.strCurrCode = strCurrCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
