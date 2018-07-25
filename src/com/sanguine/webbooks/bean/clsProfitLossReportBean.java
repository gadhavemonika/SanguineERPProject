package com.sanguine.webbooks.bean;

public class clsProfitLossReportBean {

	private String dteFromDate;

	private String dteToDate;

	private String strVouchNo;

	private double dblAmt;

	private String strNarration;

	private String strAccountName;
	
	private String strCurrency;
	
	

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = strVouchNo;
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = dblAmt;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}

}
