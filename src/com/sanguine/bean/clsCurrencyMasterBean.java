package com.sanguine.bean;

public class clsCurrencyMasterBean {
	// Variable Declaration
	private String strCurrencyCode;

	private long intID;

	private String strCurrencyName;

	private String strShortName;

	private String strBankName;

	private String strSwiftCode;

	private String strIbanNo;

	private String strRouting;

	private String strAccountNo;

	private double dblConvToBaseCurr;

	private String strSubUnit;

	// Setter-Getter Methods
	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = strCurrencyCode;
	}

	public long getIntID() {
		return intID;
	}

	public void setIntID(long intID) {
		this.intID = intID;
	}

	public String getStrCurrencyName() {
		return strCurrencyName;
	}

	public void setStrCurrencyName(String strCurrencyName) {
		this.strCurrencyName = strCurrencyName;
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = strShortName;
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = strBankName;
	}

	public String getStrSwiftCode() {
		return strSwiftCode;
	}

	public void setStrSwiftCode(String strSwiftCode) {
		this.strSwiftCode = strSwiftCode;
	}

	public String getStrIbanNo() {
		return strIbanNo;
	}

	public void setStrIbanNo(String strIbanNo) {
		this.strIbanNo = strIbanNo;
	}

	public String getStrRouting() {
		return strRouting;
	}

	public void setStrRouting(String strRouting) {
		this.strRouting = strRouting;
	}

	public String getStrAccountNo() {
		return strAccountNo;
	}

	public void setStrAccountNo(String strAccountNo) {
		this.strAccountNo = strAccountNo;
	}

	public double getDblConvToBaseCurr() {
		return dblConvToBaseCurr;
	}

	public void setDblConvToBaseCurr(double dblConvToBaseCurr) {
		this.dblConvToBaseCurr = dblConvToBaseCurr;
	}

	public String getStrSubUnit() {
		return strSubUnit;
	}

	public void setStrSubUnit(String strSubUnit) {
		this.strSubUnit = strSubUnit;
	}

}
