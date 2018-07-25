package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsStkTransferHdModel_ID implements Serializable {

	private String strSTCode;
	private String strClientCode;

	public clsStkTransferHdModel_ID() {
	}

	public clsStkTransferHdModel_ID(String strSTCode, String strClientCode) {
		this.strSTCode = strSTCode;
		this.strClientCode = strClientCode;
	}

	public String getStrSTCode() {
		return strSTCode;
	}

	public void setStrSTCode(String strSTCode) {
		this.strSTCode = strSTCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsStkTransferHdModel_ID cp = (clsStkTransferHdModel_ID) obj;
		if (this.strSTCode.equals(cp.getStrSTCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSTCode.hashCode() + this.strClientCode.hashCode();
	}

}
