package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsAccountHolderMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strACHolderCode")
	private String strACHolderCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsAccountHolderMasterModel_ID() {
	}

	public clsAccountHolderMasterModel_ID(String strACHolderCode, String strClientCode) {
		this.strACHolderCode = strACHolderCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrACHolderCode() {
		return strACHolderCode;
	}

	public void setStrACHolderCode(String strACHolderCode) {
		this.strACHolderCode = strACHolderCode;
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
		clsAccountHolderMasterModel_ID objModelId = (clsAccountHolderMasterModel_ID) obj;
		if (this.strACHolderCode.equals(objModelId.getStrACHolderCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strACHolderCode.hashCode() + this.strClientCode.hashCode();
	}

}
