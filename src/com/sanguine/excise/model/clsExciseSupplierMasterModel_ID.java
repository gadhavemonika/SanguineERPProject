package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseSupplierMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSupplierCode")
	private String strSupplierCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsExciseSupplierMasterModel_ID() {
	}

	public clsExciseSupplierMasterModel_ID(String strSupplierCode, String strClientCode) {
		this.strSupplierCode = strSupplierCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSupplierCode() {
		return strSupplierCode;
	}

	public void setStrSupplierCode(String strSupplierCode) {
		this.strSupplierCode = strSupplierCode;
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
		clsExciseSupplierMasterModel_ID objModelId = (clsExciseSupplierMasterModel_ID) obj;
		if (this.strSupplierCode.equals(objModelId.getStrSupplierCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSupplierCode.hashCode() + this.strClientCode.hashCode();
	}

}
