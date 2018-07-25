package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsSubGroupMasterModel_ID implements Serializable {

	private String strSGCode;
	private String strClientCode;

	public clsSubGroupMasterModel_ID(String strSGCode, String strClientCode) {
		this.strSGCode = strSGCode;
		this.strClientCode = strClientCode;

	}

	public clsSubGroupMasterModel_ID() {

	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsSubGroupMasterModel_ID cp = (clsSubGroupMasterModel_ID) obj;
		if (this.strSGCode.equals(cp.getStrSGCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSGCode.hashCode() + this.strClientCode.hashCode();
	}

}
