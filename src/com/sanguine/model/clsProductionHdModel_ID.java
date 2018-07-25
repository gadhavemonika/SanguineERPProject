package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsProductionHdModel_ID implements Serializable {
	private String strPDCode;
	private String strClientCode;

	public clsProductionHdModel_ID() {
	}

	public clsProductionHdModel_ID(String strPDCode, String strClientCode) {
		this.strPDCode = strPDCode;
		this.strClientCode = strClientCode;
	}

	public String getStrPDCode() {
		return strPDCode;
	}

	public void setStrPDCode(String strPDCode) {
		this.strPDCode = strPDCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsProductionHdModel_ID cp = (clsProductionHdModel_ID) obj;
		if (this.strPDCode.equals(cp.getStrPDCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPDCode.hashCode() + this.strClientCode.hashCode();
	}

}
