package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSubContractorGRNModelHd_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSRCode")
	private String strSRCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSubContractorGRNModelHd_ID() {
	}

	public clsSubContractorGRNModelHd_ID(String strSRCode, String strClientCode) {
		this.strSRCode = strSRCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = strSRCode;
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
		clsSubContractorGRNModelHd_ID objModelId = (clsSubContractorGRNModelHd_ID) obj;
		if (this.strSRCode.equals(objModelId.getStrSRCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSRCode.hashCode() + this.strClientCode.hashCode();
	}

}
