package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsNarrationMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strRemarkCode")
	private String strRemarkCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsNarrationMasterModel_ID() {
	}

	public clsNarrationMasterModel_ID(String strRemarkCode, String strClientCode) {
		this.strRemarkCode = strRemarkCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrRemarkCode() {
		return strRemarkCode;
	}

	public void setStrRemarkCode(String strRemarkCode) {
		this.strRemarkCode = strRemarkCode;
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
		clsNarrationMasterModel_ID objModelId = (clsNarrationMasterModel_ID) obj;
		if (this.strRemarkCode.equals(objModelId.getStrRemarkCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRemarkCode.hashCode() + this.strClientCode.hashCode();
	}

}
