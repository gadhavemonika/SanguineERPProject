package com.sanguine.webbooks.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsWebBooksAuditDebtorDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebBooksAuditDebtorDtlModel() {
	}

	// Variable Declaration

	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	@Column(name = "strDebtorName")
	private String strDebtorName;

	@Column(name = "strCrDr")
	private String strCrDr;

	@Column(name = "dblAmt")
	private double dblAmt;

	@Column(name = "strBillNo")
	private String strBillNo;

	@Column(name = "strInvoiceNo")
	private String strInvoiceNo;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strGuest")
	private String strGuest;

	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strCreditNo")
	private String strCreditNo;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Column(name = "dteInvoiceDate")
	private String dteInvoiceDate;

	@Column(name = "dteDueDate")
	private String dteDueDate;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strPOSCode")
	private String strPOSCode;

	@Column(name = "strPOSName")
	private String strPOSName;

	@Column(name = "strRegistrationNo")
	private String strRegistrationNo;

	// Setter-Getter Methods
	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = (String) setDefaultValue(strDebtorCode, "NA");
	}

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = (String) setDefaultValue(strDebtorName, "NA");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "NA");
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = (Double) setDefaultValue(dblAmt, "0.0000");
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = (String) setDefaultValue(strBillNo, "NA");
	}

	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}

	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = (String) setDefaultValue(strInvoiceNo, "NA");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "NA");
	}

	public String getStrGuest() {
		return strGuest;
	}

	public void setStrGuest(String strGuest) {
		this.strGuest = (String) setDefaultValue(strGuest, "NA");
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "NA");
	}

	public String getStrCreditNo() {
		return strCreditNo;
	}

	public void setStrCreditNo(String strCreditNo) {
		this.strCreditNo = (String) setDefaultValue(strCreditNo, "NA");
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getDteInvoiceDate() {
		return dteInvoiceDate;
	}

	public void setDteInvoiceDate(String dteInvoiceDate) {
		this.dteInvoiceDate = dteInvoiceDate;
	}

	public String getDteDueDate() {
		return dteDueDate;
	}

	public void setDteDueDate(String dteDueDate) {
		this.dteDueDate = dteDueDate;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = (String) setDefaultValue(strPOSCode, "NA");
	}

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = (String) setDefaultValue(strPOSName, "NA");
	}

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = (String) setDefaultValue(strRegistrationNo, "NA");
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

}
