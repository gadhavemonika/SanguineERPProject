package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class clsVoidBillDtlModel implements Serializable, Comparable {

	private static final long serialVersionUID = 1L;
	
	public clsVoidBillDtlModel(){
		
	}
	
	private String strFolioNo;

	private String dteDocDate;

	private String strDocNo;

	private String strPerticulars;

	private double dblDebitAmt;

	private double dblCreditAmt;

	private double dblBalanceAmt;

	private String strRevenueType;

	private String strRevenueCode;

	// Setter-Getter Methods
	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = (String) setDefaultValue(strFolioNo, "NA");
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
		this.strDocNo = (String) setDefaultValue(strDocNo, "NA");
	}

	public String getStrPerticulars() {
		return strPerticulars;
	}

	public void setStrPerticulars(String strPerticulars) {
		this.strPerticulars = (String) setDefaultValue(strPerticulars, "NA");
	}

	public double getDblDebitAmt() {
		return dblDebitAmt;
	}

	public void setDblDebitAmt(double dblDebitAmt) {
		this.dblDebitAmt = (Double) setDefaultValue(dblDebitAmt, "0.0000");
	}

	public double getDblCreditAmt() {
		return dblCreditAmt;
	}

	public void setDblCreditAmt(double dblCreditAmt) {
		this.dblCreditAmt = (Double) setDefaultValue(dblCreditAmt, "0.0000");
	}

	public double getDblBalanceAmt() {
		return dblBalanceAmt;
	}

	public void setDblBalanceAmt(double dblBalanceAmt) {
		this.dblBalanceAmt = (Double) setDefaultValue(dblBalanceAmt, "0.0000");
	}

	public String getStrRevenueType() {
		return strRevenueType;
	}

	public void setStrRevenueType(String strRevenueType) {
		this.strRevenueType = strRevenueType;
	}

	public String getStrRevenueCode() {
		return strRevenueCode;
	}

	public void setStrRevenueCode(String strRevenueCode) {
		this.strRevenueCode = strRevenueCode;
	}

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

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
