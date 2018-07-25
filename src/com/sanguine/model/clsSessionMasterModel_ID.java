package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSessionMasterModel_ID implements Serializable {

	private String strSessionCode;

	private String strClientCode;

	public clsSessionMasterModel_ID() {
	}

	public clsSessionMasterModel_ID(String strSessionCode, String strClientCode) {
		this.strSessionCode = strSessionCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsSessionMasterModel_ID cp = (clsSessionMasterModel_ID) obj;
		if (this.strSessionCode.equals(cp.getStrSessionCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSessionCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrSessionCode() {
		return strSessionCode;
	}

	public void setStrSessionCode(String strSessionCode) {
		this.strSessionCode = strSessionCode;
	}

}
