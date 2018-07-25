package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsRequisitionHdModel_ID implements Serializable {
	private String strReqCode;
	private String strClientCode;

	public clsRequisitionHdModel_ID() {
	}

	public clsRequisitionHdModel_ID(String strReqCode, String strClientCode) {
		this.strReqCode = strReqCode;
		this.strClientCode = strClientCode;
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsRequisitionHdModel_ID cp = (clsRequisitionHdModel_ID) obj;
		if (this.strReqCode.equals(cp.getStrReqCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReqCode.hashCode() + this.strClientCode.hashCode();
	}
}
