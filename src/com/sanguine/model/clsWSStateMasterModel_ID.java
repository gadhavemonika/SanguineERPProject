package com.sanguine.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsWSStateMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strStateCode")
	private String strStateCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWSStateMasterModel_ID() {
	}

	public clsWSStateMasterModel_ID(String strStateCode, String strClientCode) {
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
		clsWSStateMasterModel_ID objModelId = (clsWSStateMasterModel_ID) obj;
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
