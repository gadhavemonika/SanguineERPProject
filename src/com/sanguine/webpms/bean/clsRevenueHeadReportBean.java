package com.sanguine.webpms.bean;

import java.util.List;

public class clsRevenueHeadReportBean {
	// variables
	private String dteFromDate;
	private String dteToDate;
	private String strIncomeHeadType;
	private String strTaxDesc;
	private double dblBookedAmt;
	private double dblUnBookedAmt;
	private double dblTotalAmt;
	private List<clsRevenueHeadDtlReportBean> listIncomeHeadTaxDtl;

	private double dblBookedTotal;
	private double dblUnBookedTotal;
	private double dblTotalTotal;

	private String strName;
	private double dblAmount;

	// getters and setters
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

	public String getStrIncomeHeadType() {
		return strIncomeHeadType;
	}

	public void setStrIncomeHeadType(String strIncomeHeadType) {
		this.strIncomeHeadType = strIncomeHeadType;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public double getDblBookedAmt() {
		return dblBookedAmt;
	}

	public void setDblBookedAmt(double dblBookedAmt) {
		this.dblBookedAmt = dblBookedAmt;
	}

	public double getDblUnBookedAmt() {
		return dblUnBookedAmt;
	}

	public void setDblUnBookedAmt(double dblUnBookedAmt) {
		this.dblUnBookedAmt = dblUnBookedAmt;
	}

	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public List<clsRevenueHeadDtlReportBean> getListIncomeHeadTaxDtl() {
		return listIncomeHeadTaxDtl;
	}

	public void setListIncomeHeadTaxDtl(List<clsRevenueHeadDtlReportBean> listIncomeHeadTaxDtl) {
		this.listIncomeHeadTaxDtl = listIncomeHeadTaxDtl;
	}

	public double getDblBookedTotal() {
		return dblBookedTotal;
	}

	public void setDblBookedTotal(double dblBookedTotal) {
		this.dblBookedTotal = dblBookedTotal;
	}

	public double getDblUnBookedTotal() {
		return dblUnBookedTotal;
	}

	public void setDblUnBookedTotal(double dblUnBookedTotal) {
		this.dblUnBookedTotal = dblUnBookedTotal;
	}

	public double getDblTotalTotal() {
		return dblTotalTotal;
	}

	public void setDblTotalTotal(double dblTotalTotal) {
		this.dblTotalTotal = dblTotalTotal;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

}
