package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsBookingTypeModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strBookingTypeCode")
	private String strBookingTypeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsBookingTypeModel_ID() {
	}

	public clsBookingTypeModel_ID(String strBookingTypeCode, String strClientCode) {
		this.strBookingTypeCode = strBookingTypeCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrBookingTypeCode() {
		return strBookingTypeCode;
	}

	public void setStrBookingTypeCode(String strBookingTypeCode) {
		this.strBookingTypeCode = strBookingTypeCode;
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
		clsBookingTypeModel_ID objModelId = (clsBookingTypeModel_ID) obj;
		if (this.strBookingTypeCode.equals(objModelId.getStrBookingTypeCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBookingTypeCode.hashCode() + this.strClientCode.hashCode();
	}

}
