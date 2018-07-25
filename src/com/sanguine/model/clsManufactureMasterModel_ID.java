package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsManufactureMasterModel_ID implements Serializable {
	private String strManufacturerCode;
	private String strClientCode;

	public clsManufactureMasterModel_ID() {
	}

	public clsManufactureMasterModel_ID(String strManufacturerCode, String strClientCode) {
		this.strManufacturerCode = strManufacturerCode;
		this.strClientCode = strClientCode;
	}

	public String getStrManufacturerCode() {
		return strManufacturerCode;
	}

	public void setStrManufacturerCode(String strManufacturerCode) {
		this.strManufacturerCode = strManufacturerCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
