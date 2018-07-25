package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsReasonMaster_ID implements Serializable {
	private String strReasonCode;
	private String strClientCode;

	public clsReasonMaster_ID() {
	}

	public clsReasonMaster_ID(String strReasonCode, String strClientCode) {
		this.strReasonCode = strReasonCode;
		this.strClientCode = strClientCode;
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsReasonMaster_ID cp = (clsReasonMaster_ID) obj;
		if (this.strReasonCode.equals(cp.getStrReasonCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReasonCode.hashCode() + this.strClientCode.hashCode();
	}
}
