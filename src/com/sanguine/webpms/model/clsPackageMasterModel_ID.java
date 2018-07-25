package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPackageMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPackageCode")
	private String strPackageCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsPackageMasterModel_ID() {
	}

	public clsPackageMasterModel_ID(String strPackageCode, String strClientCode) {
		this.strPackageCode = strPackageCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	
	public String getStrClientCode() {
		return strClientCode;
	}

	public String getStrPackageCode() {
		return strPackageCode;
	}

	public void setStrPackageCode(String strPackageCode) {
		this.strPackageCode = strPackageCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsPackageMasterModel_ID objModelId = (clsPackageMasterModel_ID) obj;
		if (this.strPackageCode.equals(objModelId.getStrPackageCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPackageCode.hashCode() + this.strClientCode.hashCode();
	}

}
