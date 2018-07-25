package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPurchaseOrderHdModel_ID implements Serializable {
	private String strPOCode;
	private String strClientCode;

	public clsPurchaseOrderHdModel_ID() {
	}

	public clsPurchaseOrderHdModel_ID(String strPOCode, String strClientCode) {
		this.strPOCode = strPOCode;
		this.strClientCode = strClientCode;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsPurchaseOrderHdModel_ID cp = (clsPurchaseOrderHdModel_ID) obj;
		if (this.strPOCode.equals(cp.getStrPOCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPOCode.hashCode() + this.strClientCode.hashCode();
	}

}
