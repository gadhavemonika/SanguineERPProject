package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsWorkOrderHdModel_ID implements Serializable {

	private String strWOCode;
	private String strClientCode;

	public clsWorkOrderHdModel_ID() {
	}

	public clsWorkOrderHdModel_ID(String strWOCode, String strClientCode) {
		this.strWOCode = strWOCode;
		this.strClientCode = strClientCode;
	}

	public String getStrWOCode() {
		return strWOCode;
	}

	public void setStrWOCode(String strWOCode) {
		this.strWOCode = strWOCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsWorkOrderHdModel_ID cp = (clsWorkOrderHdModel_ID) obj;
		if (this.strWOCode.equals(cp.getStrWOCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strWOCode.hashCode() + this.strClientCode.hashCode();
	}

}
