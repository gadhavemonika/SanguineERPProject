package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsStateMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strStateCode")
	private String strStateCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsStateMasterModel_ID() {
	}

	public clsStateMasterModel_ID(String strStateCode, String strClientCode) {
		this.strStateCode = strStateCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrStateCode() {
		return strStateCode;
	}

	public void setStrStateCode(String strStateCode) {
		this.strStateCode = strStateCode;
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
		clsStateMasterModel_ID objModelId = (clsStateMasterModel_ID) obj;
		if (this.strStateCode.equals(objModelId.getStrStateCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strStateCode.hashCode() + this.strClientCode.hashCode();
	}

}
