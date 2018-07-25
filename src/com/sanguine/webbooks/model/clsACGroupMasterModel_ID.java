package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsACGroupMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strGroupCode")
	private String strGroupCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsACGroupMasterModel_ID() {
	}

	public clsACGroupMasterModel_ID(String strGroupCode, String strClientCode) {
		this.strGroupCode = strGroupCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = strGroupCode;
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
		clsACGroupMasterModel_ID objModelId = (clsACGroupMasterModel_ID) obj;
		if (this.strGroupCode.equals(objModelId.getStrGroupCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strGroupCode.hashCode() + this.strClientCode.hashCode();
	}

}
