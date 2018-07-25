package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsRateContractHdModel_ID implements Serializable {
	private String strRateContNo;
	private String strClientCode;

	public clsRateContractHdModel_ID() {
	}

	public clsRateContractHdModel_ID(String strRateContNo, String strClientCode) {
		this.strRateContNo = strRateContNo;
		this.strClientCode = strClientCode;
	}

	public String getStrRateContNo() {
		return strRateContNo;
	}

	public void setStrRateContNo(String strRateContNo) {
		this.strRateContNo = strRateContNo;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsRateContractHdModel_ID cp = (clsRateContractHdModel_ID) obj;
		if (this.strRateContNo.equals(cp.getStrRateContNo()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRateContNo.hashCode() + this.strClientCode.hashCode();
	}

}
