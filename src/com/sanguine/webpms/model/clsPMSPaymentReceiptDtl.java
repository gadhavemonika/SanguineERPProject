package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsPMSPaymentReceiptDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPMSPaymentReceiptDtl() {
	}

	// Variable Declaration
	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "strCardNo")
	private String strCardNo;

	@Column(name = "dteExpiryDate")
	private String dteExpiryDate;

	@Column(name = "dblSettlementAmt")
	private double dblSettlementAmt;

	@Column(name = "strRemarks")
	private String strRemarks;

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

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
