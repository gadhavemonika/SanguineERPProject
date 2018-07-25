package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsExciseChallanModel_ID implements Serializable {

	private String strECCode;
	private String strClientCode;

	public clsExciseChallanModel_ID() {
	}

	public clsExciseChallanModel_ID(String strECCode, String strClientCode) {
		this.strECCode = strECCode;
		this.strClientCode = strClientCode;
	}

	public String getStrECCode() {
		return strECCode;
	}

	public void setStrECCode(String strECCode) {
		this.strECCode = strECCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
