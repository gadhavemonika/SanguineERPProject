package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsProductMasterModel_ID implements Serializable {

	private String strProdCode;
	private String strClientCode;

	public clsProductMasterModel_ID() {
	}

	public clsProductMasterModel_ID(String strProdCode, String strClientCode) {
		this.strProdCode = strProdCode;
		this.strClientCode = strClientCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsProductMasterModel_ID cp = (clsProductMasterModel_ID) obj;
		if (this.strProdCode.equals(cp.getStrProdCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strProdCode.hashCode() + this.strClientCode.hashCode();
	}

}
