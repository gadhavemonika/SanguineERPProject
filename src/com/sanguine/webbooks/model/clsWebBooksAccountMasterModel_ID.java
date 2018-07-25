package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebBooksAccountMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebBooksAccountMasterModel_ID() {
	}

	public clsWebBooksAccountMasterModel_ID(String strAccountCode, String strClientCode) {
		this.strAccountCode = strAccountCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
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
		clsWebBooksAccountMasterModel_ID objModelId = (clsWebBooksAccountMasterModel_ID) obj;
		if (this.strAccountCode.equals(objModelId.getStrAccountCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAccountCode.hashCode() + this.strClientCode.hashCode();
	}

}
