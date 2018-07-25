package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPropertySetupModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsPropertySetupModel_ID() {
	}

	public clsPropertySetupModel_ID(String strPropertyCode, String strClientCode) {
		this.strPropertyCode = strPropertyCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
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
		clsPropertySetupModel_ID objModelId = (clsPropertySetupModel_ID) obj;
		if (this.strPropertyCode.equals(objModelId.getStrPropertyCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPropertyCode.hashCode() + this.strClientCode.hashCode();
	}

}
