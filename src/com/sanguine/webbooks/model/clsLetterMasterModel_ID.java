package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsLetterMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strLetterCode")
	private String strLetterCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsLetterMasterModel_ID() {
	}

	public clsLetterMasterModel_ID(String strLetterCode, String strClientCode) {
		this.strLetterCode = strLetterCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrLetterCode() {
		return strLetterCode;
	}

	public void setStrLetterCode(String strLetterCode) {
		this.strLetterCode = strLetterCode;
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
		clsLetterMasterModel_ID objModelId = (clsLetterMasterModel_ID) obj;
		if (this.strLetterCode.equals(objModelId.getStrLetterCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strLetterCode.hashCode() + this.strClientCode.hashCode();
	}

}
