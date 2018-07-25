package com.sanguine.webbooks.bean;

public class clsPaymentDetailsBean {

	private String strAccountCode;

	private String strDebtorCode;

	private String strDebtorName;

	private String strDescription;

	private String strDC;

	private double dblDebitAmt;

	private double dblCreditAmt;

	private double dblBalance;

	private String strDimension;

	private String strDebtorYN;

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = strDebtorCode;
	}

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = strDebtorName;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public String getStrDC() {
		return strDC;
	}

	public void setStrDC(String strDC) {
		this.strDC = strDC;
	}

	public double getDblDebitAmt() {
		return dblDebitAmt;
	}

	public void setDblDebitAmt(double dblDebitAmt) {
		this.dblDebitAmt = dblDebitAmt;
	}

	public double getDblCreditAmt() {
		return dblCreditAmt;
	}

	public void setDblCreditAmt(double dblCreditAmt) {
		this.dblCreditAmt = dblCreditAmt;
	}

	public String getStrDimension() {
		return strDimension;
	}

	public void setStrDimension(String strDimension) {
		this.strDimension = strDimension;
	}

	public String getStrDebtorYN() {
		return strDebtorYN;
	}

	public void setStrDebtorYN(String strDebtorYN) {
		this.strDebtorYN = strDebtorYN;
	}

	public double getDblBalance() {
		return dblBalance;
	}

	public void setDblBalance(double dblBalance) {
		this.dblBalance = dblBalance;
	}
}
