package com.sanguine.webpos.bean;

public class clsBillSettlementBean {

	private String strBillNo;

	private String strSettleMode;

	private String strPayMode;

	private String strRemark;

	private double dblPaidAmt;

	private double dblBalance;

	private String dblBillAmt;

	private String strTableNo;

	private String selectedRow;;

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrSettleMode() {
		return strSettleMode;
	}

	public void setStrSettleMode(String strSettleMode) {
		this.strSettleMode = strSettleMode;
	}

	public String getStrPayMode() {
		return strPayMode;
	}

	public void setStrPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public double getDblPaidAmt() {
		return dblPaidAmt;
	}

	public void setDblPaidAmt(double dblPaidAmt) {
		this.dblPaidAmt = dblPaidAmt;
	}

	public double getDblBalance() {
		return dblBalance;
	}

	public void setDblBalance(double dblBalance) {
		this.dblBalance = dblBalance;
	}

	public String getDblBillAmt() {
		return dblBillAmt;
	}

	public void setDblBillAmt(String dblBillAmt) {
		this.dblBillAmt = dblBillAmt;
	}

	public String getStrTableNo() {
		return strTableNo;
	}

	public void setStrTableNo(String strTableNo) {
		this.strTableNo = strTableNo;
	}

	public String getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

}
