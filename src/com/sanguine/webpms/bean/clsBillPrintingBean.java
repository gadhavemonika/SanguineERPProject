package com.sanguine.webpms.bean;

public class clsBillPrintingBean {

	// variables
	private String strFolioNo;
	private String strBillNo;
	private String dteFromDate;
	private String dteToDate;

	private String dteDocDate;
	private String strDocNo;
	private String strPerticulars;
	private double dblDebitAmt;
	private double dblCreditAmt;
	private double dblBalanceAmt;
	private String strRoomNo;
	private String strRoomName;
	private String strBillIncluded;
	private String strGuestName;
	public String getStrGuestName() {
		return strGuestName;
	}

	public void setStrGuestName(String strGuestName) {
		this.strGuestName = strGuestName;
	}

	public String getStrRoomName() {
		return strRoomName;
	}

	public String getStrBillIncluded() {
		return strBillIncluded;
	}

	public void setStrBillIncluded(String strBillIncluded) {
		this.strBillIncluded = strBillIncluded;
	}

	public void setStrRoomName(String strRoomName) {
		this.strRoomName = strRoomName;
	}

	private double dblRate;

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	// getters and setters
	public String getStrFolioNo() {
		return strFolioNo;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = strFolioNo;
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

	public String getDteDocDate() {
		return dteDocDate;
	}

	public void setDteDocDate(String dteDocDate) {
		this.dteDocDate = dteDocDate;
	}

	public String getStrDocNo() {
		return strDocNo;
	}

	public void setStrDocNo(String strDocNo) {
		this.strDocNo = strDocNo;
	}

	public String getStrPerticulars() {
		return strPerticulars;
	}

	public void setStrPerticulars(String strPerticulars) {
		this.strPerticulars = strPerticulars;
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

	public double getDblBalanceAmt() {
		return dblBalanceAmt;
	}

	public void setDblBalanceAmt(double dblBalanceAmt) {
		this.dblBalanceAmt = dblBalanceAmt;
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

}
