package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

//@Embeddable
@SuppressWarnings("serial")
public class clsProductStandard_ID implements Serializable {

	private String strProdCode;
	private String strLocCode;
	private String strPropertyCode;
	private String strClientCode;

	public clsProductStandard_ID() {
	}

	public clsProductStandard_ID(String strProdCode, String strLocCode, String strPropertyCode, String strClientCode) {
		this.strProdCode = strProdCode;
		this.strLocCode = strLocCode;
		this.strPropertyCode = strPropertyCode;
		this.strClientCode = strClientCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

}
