package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsStkAdjustmentHdModel_ID implements Serializable {

	private String strSACode;
	private String strClientCode;

	public clsStkAdjustmentHdModel_ID() {
	}

	public clsStkAdjustmentHdModel_ID(String strSACode, String strClientCode) {
		this.strSACode = strSACode;
		this.strClientCode = strClientCode;
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsStkAdjustmentHdModel_ID cp = (clsStkAdjustmentHdModel_ID) obj;
		if (this.strSACode.equals(cp.getStrSACode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSACode.hashCode() + this.strClientCode.hashCode();
	}

}
