package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsChargeMasterModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strChargeCode")
	private String strChargeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsChargeMasterModel_ID() {
	}

	public clsChargeMasterModel_ID(String strChargeCode, String strClientCode) {
		this.strChargeCode = strChargeCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrChargeCode() {
		return strChargeCode;
	}

	public void setStrChargeCode(String strChargeCode) {
		this.strChargeCode = strChargeCode;
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
		clsChargeMasterModel_ID objModelId = (clsChargeMasterModel_ID) obj;
		if (this.strChargeCode.equals(objModelId.getStrChargeCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strChargeCode.hashCode() + this.strClientCode.hashCode();
	}

}
