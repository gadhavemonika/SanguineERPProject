package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsAttributeMasterModel_ID implements Serializable {

	private String strAttCode;

	private String strClientCode;

	public clsAttributeMasterModel_ID() {
	}

	public clsAttributeMasterModel_ID(String strAttCode, String strClientCode) {
		this.strAttCode = strAttCode;
		this.strClientCode = strClientCode;
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
		clsAttributeMasterModel_ID cp = (clsAttributeMasterModel_ID) obj;
		if (this.strAttCode.equals(cp.getStrAttCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAttCode.hashCode() + this.strClientCode.hashCode();
	}

}
