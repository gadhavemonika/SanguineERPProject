package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsReservationModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strReservationNo")
	private String strReservationNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsReservationModel_ID() {
	}

	public clsReservationModel_ID(String strReservationNo, String strClientCode) {
		this.strReservationNo = strReservationNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = strReservationNo;
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
		clsReservationModel_ID objModelId = (clsReservationModel_ID) obj;
		if (this.strReservationNo.equals(objModelId.getStrReservationNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReservationNo.hashCode() + this.strClientCode.hashCode();
	}

}
