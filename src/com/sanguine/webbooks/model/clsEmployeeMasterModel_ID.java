package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsEmployeeMasterModel_ID implements Serializable{
	
	@Column(name = "strEmployeeCode")
	private String strEmployeeCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsEmployeeMasterModel_ID() {
	}

	public clsEmployeeMasterModel_ID(String strEmployeeCode, String strClientCode) {
		this.strEmployeeCode = strEmployeeCode;
		this.strClientCode = strClientCode;
	}

	public String getStrEmployeeCode() {
		return strEmployeeCode;
	}

	public void setStrEmployeeCode(String strEmployeeCode) {
		this.strEmployeeCode = strEmployeeCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}


}
