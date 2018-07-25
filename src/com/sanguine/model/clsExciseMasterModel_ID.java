package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseMasterModel_ID implements Serializable {

	private String strExciseCode;

	private String strClientCode;

	public clsExciseMasterModel_ID() {
	}

	public clsExciseMasterModel_ID(String strExciseCode, String strClientCode) {
		this.strExciseCode = strExciseCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsExciseMasterModel_ID cp = (clsExciseMasterModel_ID) obj;
		if (this.strExciseCode.equals(cp.getStrExciseCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strExciseCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrExciseCode() {
		return strExciseCode;
	}

	public void setStrExciseCode(String strExciseCode) {
		this.strExciseCode = strExciseCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
