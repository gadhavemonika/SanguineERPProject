package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsGRNMISDtl_ID implements Serializable {

	private String strGRNCode;
	private String strMISCode;

	public clsGRNMISDtl_ID() {
	}

	public clsGRNMISDtl_ID(String strGRNCode, String strMISCode) {
		this.strGRNCode = strGRNCode;
		this.strMISCode = strMISCode;

	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsGRNMISDtl_ID GRNMISDtlobj = (clsGRNMISDtl_ID) obj;
		if (this.strGRNCode.equals(GRNMISDtlobj.getStrGRNCode()) && this.strMISCode.equals(GRNMISDtlobj.getStrMISCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strGRNCode.hashCode() + this.strMISCode.hashCode();
	}

}
