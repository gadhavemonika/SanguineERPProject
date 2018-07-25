package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsTransactionTimeModel_ID implements Serializable {

	private String strLocCode;

	private String strClientCode;

	private String strPropertyCode;

	public clsTransactionTimeModel_ID() {
	}

	public clsTransactionTimeModel_ID(String strLocCode, String strClientCode, String strPropertyCode) {
		this.strLocCode = strLocCode;
		this.strClientCode = strClientCode;
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

}
