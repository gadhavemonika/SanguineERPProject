package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsAttributeValueMasterModel_ID implements Serializable {

	private String strAVCode;
	private String strClientCode;

	public clsAttributeValueMasterModel_ID() {
	}

	public clsAttributeValueMasterModel_ID(String strAVCode, String strClientCode) {
		this.strAVCode = strAVCode;
		this.strClientCode = strClientCode;
	}

	public String getStrAVCode() {
		return strAVCode;
	}

	public void setStrAVCode(String strAVCode) {
		this.strAVCode = strAVCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsAttributeValueMasterModel_ID cp = (clsAttributeValueMasterModel_ID) obj;
		if (this.strAVCode.equals(cp.getStrAVCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strAVCode.hashCode() + this.strClientCode.hashCode();
	}
}
