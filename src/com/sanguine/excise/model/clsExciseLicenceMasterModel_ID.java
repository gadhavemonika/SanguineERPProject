package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseLicenceMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strLicenceCode")
	private String strLicenceCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public clsExciseLicenceMasterModel_ID() {
	}

	public clsExciseLicenceMasterModel_ID(String strLicenceCode, String strClientCode, String strPropertyCode) {
		this.strLicenceCode = strLicenceCode;
		this.strClientCode = strClientCode;
		this.strPropertyCode = strPropertyCode;
	}

	// Setter-Getter Methods
	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsExciseLicenceMasterModel_ID objModelId = (clsExciseLicenceMasterModel_ID) obj;
		if (this.strLicenceCode.equals(objModelId.getStrLicenceCode()) && this.strClientCode.equals(objModelId.getStrClientCode()) && this.strPropertyCode.equals(objModelId.getStrPropertyCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strLicenceCode.hashCode() + this.strClientCode.hashCode() + this.strPropertyCode.hashCode();
	}

}
