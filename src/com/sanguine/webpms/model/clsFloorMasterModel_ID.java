package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsFloorMasterModel_ID implements Serializable{
	
	@Column(name = "strFloorCode")
	private String strFloorCode;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	public clsFloorMasterModel_ID() {
	}

	public clsFloorMasterModel_ID(String strFloorCode, String strClientCode) {
		this.strFloorCode = strFloorCode;
		this.strClientCode = strClientCode;
	}

	public String getStrFloorCode() {
		return strFloorCode;
	}

	public void setStrFloorCode(String strFloorCode) {
		this.strFloorCode = strFloorCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
	
//HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsFloorMasterModel_ID objModelId = (clsFloorMasterModel_ID) obj;
		if (this.strFloorCode.equals(objModelId.getStrFloorCode())  && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strFloorCode.hashCode() + this.strClientCode.hashCode();
	}

}
