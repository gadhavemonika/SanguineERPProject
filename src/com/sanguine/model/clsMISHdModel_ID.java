package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsMISHdModel_ID implements Serializable {

	private String strMISCode;

	private String strClientCode;

	public clsMISHdModel_ID() {

	}

	public clsMISHdModel_ID(String strMISCode, String strClientCode) {
		this.strMISCode = strMISCode;
		this.strClientCode = strClientCode;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsMISHdModel_ID cp = (clsMISHdModel_ID) obj;
		if (this.strMISCode.equals(cp.getStrMISCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strMISCode.hashCode() + this.strClientCode.hashCode();
	}
}
