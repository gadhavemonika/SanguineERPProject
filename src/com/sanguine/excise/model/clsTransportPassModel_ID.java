package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsTransportPassModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strTPCode")
	private String strTPCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsTransportPassModel_ID() {
	}

	public clsTransportPassModel_ID(String strTPCode, String strClientCode) {
		this.strTPCode = strTPCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getstrTPCode() {
		return strTPCode;
	}

	public void setstrTPCode(String strTPCode) {
		this.strTPCode = strTPCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsTransportPassModel_ID objModelId = (clsTransportPassModel_ID) obj;
		if (this.strTPCode.equals(objModelId.getstrTPCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strTPCode.hashCode() + this.strClientCode.hashCode();
	}

}
