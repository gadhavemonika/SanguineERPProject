package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsDeliveryScheduleModulehd_ID implements Serializable {

	private String strDSCode;
	private String strClientCode;

	public clsDeliveryScheduleModulehd_ID() {
	}

	public clsDeliveryScheduleModulehd_ID(String strDSCode, String strClientCode) {
		this.strDSCode = strDSCode;
		this.strClientCode = strClientCode;
	}

	public String getStrDSCode() {
		return strDSCode;
	}

	public void setStrDSCode(String strDSCode) {
		this.strDSCode = strDSCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
