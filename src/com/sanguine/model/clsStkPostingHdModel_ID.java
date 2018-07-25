package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsStkPostingHdModel_ID implements Serializable {
	private String strPSCode;
	private String strClientCode;

	public clsStkPostingHdModel_ID() {
	}

	public clsStkPostingHdModel_ID(String strPSCode, String strClientCode) {
		this.strPSCode = strPSCode;
		this.strClientCode = strClientCode;
	}

	public String getStrPSCode() {
		return strPSCode;
	}

	public void setStrPSCode(String strPSCode) {
		this.strPSCode = strPSCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsStkPostingHdModel_ID cp = (clsStkPostingHdModel_ID) obj;
		if (this.strPSCode.equals(cp.getStrPSCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPSCode.hashCode() + this.strClientCode.hashCode();
	}

}
