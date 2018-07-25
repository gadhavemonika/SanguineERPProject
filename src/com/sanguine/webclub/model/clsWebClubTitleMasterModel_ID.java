package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubTitleMasterModel_ID implements Serializable {

	@Column(name = "strTitleCode")
	private String strTitleCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubTitleMasterModel_ID() {
	}

	public clsWebClubTitleMasterModel_ID(String strTitleCode, String strClientCode) {
		this.strTitleCode = strTitleCode;
		this.strClientCode = strClientCode;
	}

	public String getStrTitleCode() {
		return strTitleCode;
	}

	public void setStrTitleCode(String strTitleCode) {
		this.strTitleCode = strTitleCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
