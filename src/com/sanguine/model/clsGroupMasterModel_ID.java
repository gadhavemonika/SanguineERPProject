package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsGroupMasterModel_ID implements Serializable {

	private String strGCode;

	private String strClientCode;

	public clsGroupMasterModel_ID() {
	}

	public clsGroupMasterModel_ID(String strGCode, String strClientCode) {
		this.strGCode = strGCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsGroupMasterModel_ID cp = (clsGroupMasterModel_ID) obj;
		if (this.strGCode.equals(cp.getStrGCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strGCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
