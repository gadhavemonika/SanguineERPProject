package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsDeliveryNoteHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strDNCode")
	private String strDNCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsDeliveryNoteHdModel_ID() {
	}

	public clsDeliveryNoteHdModel_ID(String strDNCode, String strClientCode) {
		this.strDNCode = strDNCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrDNCode() {
		return strDNCode;
	}

	public void setStrDNCode(String strDNCode) {
		this.strDNCode = strDNCode;
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
		clsDeliveryNoteHdModel_ID objModelId = (clsDeliveryNoteHdModel_ID) obj;
		if (this.strDNCode.equals(objModelId.getStrDNCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strDNCode.hashCode() + this.strClientCode.hashCode();
	}

}
