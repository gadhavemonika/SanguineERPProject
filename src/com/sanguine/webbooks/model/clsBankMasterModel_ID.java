package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsBankMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strBankCode")
	private String strBankCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsBankMasterModel_ID() {
	}

	public clsBankMasterModel_ID(String strBankCode, String strClientCode) {
		this.strBankCode = strBankCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrBankCode() {
		return strBankCode;
	}

	public void setStrBankCode(String strBankCode) {
		this.strBankCode = strBankCode;
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
		clsBankMasterModel_ID objModelId = (clsBankMasterModel_ID) obj;
		if (this.strBankCode.equals(objModelId.getStrBankCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBankCode.hashCode() + this.strClientCode.hashCode();
	}

}
