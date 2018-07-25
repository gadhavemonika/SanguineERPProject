package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsParameterSetupModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public clsParameterSetupModel_ID() {
	}

	public clsParameterSetupModel_ID(String strClientCode, String strPropertyCode) {
		this.strClientCode = strClientCode;
		this.strPropertyCode = strPropertyCode;
	}

	// Setter-Getter Methods
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsParameterSetupModel_ID objModelId = (clsParameterSetupModel_ID) obj;
		if (this.strClientCode.equals(objModelId.getStrClientCode()) && this.strPropertyCode.equals(objModelId.getStrPropertyCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strClientCode.hashCode() + this.strPropertyCode.hashCode();
	}

}
