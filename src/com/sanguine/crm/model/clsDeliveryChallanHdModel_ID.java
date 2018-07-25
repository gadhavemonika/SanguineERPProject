package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsDeliveryChallanHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strDCCode")
	private String strDCCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsDeliveryChallanHdModel_ID() {
	}

	public clsDeliveryChallanHdModel_ID(String strDCCode, String strClientCode) {
		this.strDCCode = strDCCode;
		this.strClientCode = strClientCode;
	}

	public String getStrDCCode() {
		return strDCCode;
	}

	public void setStrDCCode(String strDCCode) {
		this.strDCCode = strDCCode;
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
		clsDeliveryChallanHdModel_ID objModelId = (clsDeliveryChallanHdModel_ID) obj;
		if (this.strDCCode.equals(objModelId.getStrDCCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strDCCode.hashCode() + this.strClientCode.hashCode();
	}

}
