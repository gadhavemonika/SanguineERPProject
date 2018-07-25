package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsProductReOrderLevelModel_ID implements Serializable {
	private String strLocationCode;
	private String strClientCode;
	private String strProdCode;

	public clsProductReOrderLevelModel_ID() {
	}

	public clsProductReOrderLevelModel_ID(String strLocationCode, String strClientCode, String strProdCode) {
		this.strLocationCode = strLocationCode;
		this.strClientCode = strClientCode;
		this.strProdCode = strProdCode;
	}

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsProductReOrderLevelModel_ID cp = (clsProductReOrderLevelModel_ID) obj;
		if (this.strLocationCode.equals(cp.getStrLocationCode()) && this.strClientCode.equals(cp.getStrClientCode()) && this.strProdCode.equals(cp.getStrProdCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strLocationCode.hashCode() + this.strClientCode.hashCode() + this.strProdCode.hashCode();
	}
}
