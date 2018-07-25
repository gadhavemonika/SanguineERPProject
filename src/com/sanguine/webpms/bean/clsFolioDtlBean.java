package com.sanguine.webpms.bean;

import java.util.List;

public class clsFolioDtlBean {

	// variables declarations
	private String strFolioNo;

	private String dteDocDate;

	private String strDocNo;

	private String strPerticulars;

	private double dblDebitAmt;

	private double dblCreditAmt;

	private double dblBalanceAmt;
	
	private double dblDiscPer;

	private List<clsIncomeHeadMasterBean> listIncomeHeadBeans;

	// getters and setters
	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = strFolioNo;
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

	public List<clsIncomeHeadMasterBean> getListIncomeHeadBeans() {
		return listIncomeHeadBeans;
	}

	public void setListIncomeHeadBeans(List<clsIncomeHeadMasterBean> listIncomeHeadBeans) {
		this.listIncomeHeadBeans = listIncomeHeadBeans;
	}

	public double getDblDiscPer() {
		return dblDiscPer;
	}

	public void setDblDiscPer(double dblDiscPer) {
		this.dblDiscPer = dblDiscPer;
	}

}
