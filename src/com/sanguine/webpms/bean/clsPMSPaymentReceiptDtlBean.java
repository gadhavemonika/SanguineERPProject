package com.sanguine.webpms.bean;

import javax.persistence.Column;

public class clsPMSPaymentReceiptDtlBean {

	private String strSettlementCode;

	private String strCardNo;

	private String dteExpiryDate;

	private double dblSettlementAmt;

	private String strRemarks;

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	public String getStrCardNo() {
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}

	public String getDteExpiryDate() {
		return dteExpiryDate;
	}

	public void setDteExpiryDate(String dteExpiryDate) {
		this.dteExpiryDate = dteExpiryDate;
	}

	public double getDblSettlementAmt() {
		return dblSettlementAmt;
	}

	public void setDblSettlementAmt(double dblSettlementAmt) {
		this.dblSettlementAmt = dblSettlementAmt;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

}
