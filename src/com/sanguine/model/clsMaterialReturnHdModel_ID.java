package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsMaterialReturnHdModel_ID implements Serializable {
	private String strMRetCode;
	private String strClientCode;

	public clsMaterialReturnHdModel_ID() {
	}

	public clsMaterialReturnHdModel_ID(String strMRetCode, String strClientCode) {
		this.strMRetCode = strMRetCode;
		this.strClientCode = strClientCode;
	}

	public String getStrMRetCode() {
		return strMRetCode;
	}

	public void setStrMRetCode(String strMRetCode) {
		this.strMRetCode = strMRetCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsMaterialReturnHdModel_ID cp = (clsMaterialReturnHdModel_ID) obj;
		if (this.strMRetCode.equals(cp.getStrMRetCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strMRetCode.hashCode() + this.strClientCode.hashCode();
	}

}
