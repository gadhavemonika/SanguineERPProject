package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsUserMasterModel_ID implements Serializable {
	private String strUserCode;
	private String strClientCode;

	public clsUserMasterModel_ID() {
	}

	public clsUserMasterModel_ID(String strUserCode, String strClientCode) {
		this.strUserCode = strUserCode;
		this.strClientCode = strClientCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsUserMasterModel_ID cp = (clsUserMasterModel_ID) obj;
		if (this.strUserCode.equals(cp.getStrUserCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strUserCode.hashCode() + this.strClientCode.hashCode();
	}

}
