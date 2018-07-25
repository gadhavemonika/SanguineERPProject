package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsSupplierMasterModel_ID implements Serializable {
	private String strPCode;
	private String strClientCode;

	public clsSupplierMasterModel_ID() {
	}

	public clsSupplierMasterModel_ID(String strPCode, String strClientCode) {
		this.strPCode = strPCode;
		this.strClientCode = strClientCode;
	}

	public String getStrPCode() {
		return strPCode;
	}

	public void setStrPCode(String strPCode) {
		this.strPCode = strPCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsSupplierMasterModel_ID cp = (clsSupplierMasterModel_ID) obj;
		if (this.strPCode.equals(cp.getStrPCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPCode.hashCode() + this.strClientCode.hashCode();
	}
}
