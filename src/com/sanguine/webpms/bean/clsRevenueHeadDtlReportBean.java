package com.sanguine.webpms.bean;

public class clsRevenueHeadDtlReportBean {
	// variables
	private double dblBookedAmt;
	private double dblUnBookedAmt;
	private double dblTotalAmt;
	private String strTaxDesc;

	// getters and setters
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

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

}
