package com.sanguine.excise.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsExcisePermitMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strPermitCode")
	private String strPermitCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsExcisePermitMasterModel_ID() {
	}

	public clsExcisePermitMasterModel_ID(String strPermitCode, String strClientCode) {
		this.strPermitCode = strPermitCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrPermitCode() {
		return strPermitCode;
	}

	public void setStrPermitCode(String strPermitCode) {
		this.strPermitCode = strPermitCode;
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
		clsExcisePermitMasterModel_ID objModelId = (clsExcisePermitMasterModel_ID) obj;
		if (this.strPermitCode.equals(objModelId.getStrPermitCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPermitCode.hashCode() + this.strClientCode.hashCode();
	}

}
