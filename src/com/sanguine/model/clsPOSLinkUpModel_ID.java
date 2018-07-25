package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPOSLinkUpModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPOSItemCode")
	private String strPOSItemCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsPOSLinkUpModel_ID() {
	}

	public clsPOSLinkUpModel_ID(String strPOSItemCode, String strClientCode) {
		this.strPOSItemCode = strPOSItemCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
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
		clsPOSLinkUpModel_ID objModelId = (clsPOSLinkUpModel_ID) obj;
		if (this.strPOSItemCode.equals(objModelId.getStrPOSItemCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPOSItemCode.hashCode() + this.strClientCode.hashCode();
	}

}
