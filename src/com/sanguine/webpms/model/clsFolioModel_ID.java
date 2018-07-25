package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsFolioModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strFolioNo")
	private String strFolioNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsFolioModel_ID() {
	}

	public clsFolioModel_ID(String strFolioNo, String strClientCode) {
		this.strFolioNo = strFolioNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = strFolioNo;
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
		clsFolioModel_ID objModelId = (clsFolioModel_ID) obj;
		if (this.strFolioNo.equals(objModelId.getStrFolioNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strFolioNo.hashCode() + this.strClientCode.hashCode();
	}

}
