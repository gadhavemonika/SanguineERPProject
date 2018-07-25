package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsJOAllocationHdModel_ID implements Serializable {

	// Variable Declaration

	@Column(name = "strJACode")
	private String strJACode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsJOAllocationHdModel_ID() {
	}

	public clsJOAllocationHdModel_ID(String strJACode, String strClientCode) {
		this.strJACode = strJACode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods

	public String getStrJACode() {
		return strJACode;
	}

	public void setStrJACode(String strJACode) {
		this.strJACode = strJACode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
