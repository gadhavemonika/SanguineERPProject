package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsProdAttMasterModel_ID implements Serializable {
	private String strProdCode;
	private String strAttCode;
	private String strClientCode;

	public clsProdAttMasterModel_ID() {
	}

	public clsProdAttMasterModel_ID(String strProdCode, String strAttCode, String strClientCode) {
		this.strAttCode = strAttCode;
		this.strProdCode = strProdCode;
		this.strClientCode = strClientCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrAttCode() {
		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = strAttCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsProdAttMasterModel_ID cp = (clsProdAttMasterModel_ID) obj;
		if (this.strAttCode.equals(cp.getStrAttCode()) && this.strProdCode.equals(cp.strProdCode) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAttCode.hashCode() + this.strProdCode.hashCode() + this.strClientCode.hashCode();
	}

}
