package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsRoomCancellationModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strReservationNo")
	private String strReservationNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsRoomCancellationModel_ID() {
	}

	public clsRoomCancellationModel_ID(String strReservationNo, String strClientCode) {
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
		clsRoomCancellationModel_ID objModelId = (clsRoomCancellationModel_ID) obj;
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
