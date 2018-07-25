package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsLetterProcessingModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strLetterCode")
	private String strLetterCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsLetterProcessingModel_ID() {
	}

	public clsLetterProcessingModel_ID(String strLetterCode, String strClientCode) {
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
		clsLetterProcessingModel_ID objModelId = (clsLetterProcessingModel_ID) obj;
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
