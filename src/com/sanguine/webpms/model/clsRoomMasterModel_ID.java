package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsRoomMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strRoomCode")
	private String strRoomCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsRoomMasterModel_ID() {
	}

	public clsRoomMasterModel_ID(String strRoomCode, String strClientCode) {
		this.strRoomCode = strRoomCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrRoomCode() {
		return strRoomCode;
	}

	public void setStrRoomCode(String strRoomCode) {
		this.strRoomCode = strRoomCode;
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
		clsRoomMasterModel_ID objModelId = (clsRoomMasterModel_ID) obj;
		if (this.strRoomCode.equals(objModelId.getStrRoomCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strRoomCode.hashCode() + this.strClientCode.hashCode();
	}

}
