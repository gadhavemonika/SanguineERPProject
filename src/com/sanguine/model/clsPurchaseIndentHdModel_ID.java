package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsPurchaseIndentHdModel_ID implements Serializable {
	private String strPICode;
	private String strClientCode;

	public clsPurchaseIndentHdModel_ID() {
	}

	public clsPurchaseIndentHdModel_ID(String strPICode, String strClientCode) {
		this.strPICode = strPICode;
		this.strClientCode = strClientCode;
	}

	public String getStrPICode() {
		return strPICode;
	}

	public void setStrPICode(String strPICode) {
		this.strPICode = strPICode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsPurchaseIndentHdModel_ID cp = (clsPurchaseIndentHdModel_ID) obj;
		if (this.strPICode.equals(cp.getStrPICode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPICode.hashCode() + this.strClientCode.hashCode();
	}

}
