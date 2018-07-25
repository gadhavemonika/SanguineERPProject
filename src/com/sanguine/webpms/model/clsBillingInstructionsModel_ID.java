package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsBillingInstructionsModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strBillingInstCode")
	private String strBillingInstCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsBillingInstructionsModel_ID() {
	}

	public clsBillingInstructionsModel_ID(String strBillingInstCode, String strClientCode) {
		this.strBillingInstCode = strBillingInstCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrBillingInstCode() {
		return strBillingInstCode;
	}

	public void setStrBillingInstCode(String strBillingInstCode) {
		this.strBillingInstCode = strBillingInstCode;
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
		clsBillingInstructionsModel_ID objModelId = (clsBillingInstructionsModel_ID) obj;
		if (this.strBillingInstCode.equals(objModelId.getStrBillingInstCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBillingInstCode.hashCode() + this.strClientCode.hashCode();
	}

}
