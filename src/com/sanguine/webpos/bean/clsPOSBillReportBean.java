package com.sanguine.webpos.bean;

public class clsPOSBillReportBean {

	private String strBillNo;
	private String dteBillDate;
	private String strPosName;
	private String strSettelmentMode;
	private double dblDiscountAmt;
	private double dblTaxAmt;
	private double dblSettlementAmt;
	private String strPosCode;
	private double dblSubTotal;

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrPosName() {
		return strPosName;
	}

	public void setStrPosName(String strPosName) {
		this.strPosName = strPosName;
	}

	public String getStrSettelmentMode() {
		return strSettelmentMode;
	}

	public void setStrSettelmentMode(String strSettelmentMode) {
		this.strSettelmentMode = strSettelmentMode;
	}

	public double getDblDiscountAmt() {
		return dblDiscountAmt;
	}

	public void setDblDiscountAmt(double dblDiscountAmt) {
		this.dblDiscountAmt = dblDiscountAmt;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblSettlementAmt() {
		return dblSettlementAmt;
	}

	public void setDblSettlementAmt(double dblSettlementAmt) {
		this.dblSettlementAmt = dblSettlementAmt;
	}

	public String getStrPosCode() {
		return strPosCode;
	}

	public void setStrPosCode(String strPosCode) {
		this.strPosCode = strPosCode;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

}
