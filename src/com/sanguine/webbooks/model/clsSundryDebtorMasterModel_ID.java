package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSundryDebtorMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSundryDebtorMasterModel_ID() {
	}

	public clsSundryDebtorMasterModel_ID(String strDebtorCode, String strClientCode) {
		this.strDebtorCode = strDebtorCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = strDebtorCode;
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
		clsSundryDebtorMasterModel_ID objModelId = (clsSundryDebtorMasterModel_ID) obj;
		if (this.strDebtorCode.equals(objModelId.getStrDebtorCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strDebtorCode.hashCode() + this.strClientCode.hashCode();
	}

}
