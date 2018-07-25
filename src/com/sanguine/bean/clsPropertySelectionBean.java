package com.sanguine.bean;

import org.springframework.beans.factory.annotation.Required;

public class clsPropertySelectionBean {

	private String strPropertyCode;

	private String strPropertyName;

	private String strCompanyCode;

	private String strCompanyName;

	private String strLocationCode;

	private String strLocationName;

	private String strFinancialYear;

	private String strClientCode;

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrPropertyName() {
		return strPropertyName;
	}

	public void setStrPropertyName(String strPropertyName) {
		this.strPropertyName = strPropertyName;
	}

	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

	public String getStrCompanyName() {
		return strCompanyName;
	}

	public void setStrCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}

	public String getStrLocationName() {
		return strLocationName;
	}

	@Required
	public void setStrLocationName(String strLocationName) {
		this.strLocationName = strLocationName;
	}

	public String getStrFinancialYear() {
		return strFinancialYear;
	}

	public void setStrFinancialYear(String strFinancialYear) {
		this.strFinancialYear = strFinancialYear;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
