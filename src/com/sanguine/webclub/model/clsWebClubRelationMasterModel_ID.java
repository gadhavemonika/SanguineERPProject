package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubRelationMasterModel_ID implements Serializable {

	@Column(name = "strRelationCode")
	private String strRelationCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubRelationMasterModel_ID() {
	}

	public clsWebClubRelationMasterModel_ID(String strRelationCode, String strClientCode) {
		this.strRelationCode = strRelationCode;
		this.strClientCode = strClientCode;
	}

	public String getStrRelationCode() {
		return strRelationCode;
	}

	public void setStrRelationCode(String strRelationCode) {
		this.strRelationCode = strRelationCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
