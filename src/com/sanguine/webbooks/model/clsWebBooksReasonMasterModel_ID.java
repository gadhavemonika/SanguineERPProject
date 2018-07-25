package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebBooksReasonMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strReasonCode")
	private String strReasonCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebBooksReasonMasterModel_ID() {
	}

	public clsWebBooksReasonMasterModel_ID(String strReasonCode, String strClientCode) {
		this.strReasonCode = strReasonCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
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
		clsWebBooksReasonMasterModel_ID objModelId = (clsWebBooksReasonMasterModel_ID) obj;
		if (this.strReasonCode.equals(objModelId.getStrReasonCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReasonCode.hashCode() + this.strClientCode.hashCode();
	}

}
