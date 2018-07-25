package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPurchaseReturnHdModel_ID implements Serializable {
	private String strPRCode;
	private String strClientCode;

	public clsPurchaseReturnHdModel_ID() {
	}

	public clsPurchaseReturnHdModel_ID(String strPRCode, String strClientCode) {
		this.strPRCode = strPRCode;
		this.strClientCode = strClientCode;
	}

	public String getStrPRCode() {
		return strPRCode;
	}

	public void setStrPRCode(String strPRCode) {
		this.strPRCode = strPRCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsPurchaseReturnHdModel_ID cp = (clsPurchaseReturnHdModel_ID) obj;
		if (this.strPRCode.equals(cp.getStrPRCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPRCode.hashCode() + this.strClientCode.hashCode();
	}

}
