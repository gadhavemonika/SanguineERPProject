package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCharacteristicsMasterModel_ID implements Serializable {

	private String strCharCode;
	private String strClientCode;

	public clsCharacteristicsMasterModel_ID() {
	}

	public clsCharacteristicsMasterModel_ID(String strCharCode, String strClientCode) {
		this.strCharCode = strCharCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsCharacteristicsMasterModel_ID cp = (clsCharacteristicsMasterModel_ID) obj;
		if (this.strCharCode.equals(cp.getStrCharCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strCharCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrCharCode() {
		return strCharCode;
	}

	public void setStrCharCode(String strCharCode) {
		this.strCharCode = strCharCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
