package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblpaymentdebtordtl")
public class clsPaymentDebtorDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPaymentDebtorDtlModel() {
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

	// Setter-Getter Methods
	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = (String) setDefaultValue(strDebtorCode, "");
	}

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = (String) setDefaultValue(strDebtorName, "");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "");
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
		this.strBillNo = (String) setDefaultValue(strBillNo, "");
	}

	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}

	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = (String) setDefaultValue(strInvoiceNo, "");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "");
	}

	public String getStrGuest() {
		return strGuest;
	}

	public void setStrGuest(String strGuest) {
		this.strGuest = (String) setDefaultValue(strGuest, "");
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "");
	}

	public String getStrCreditNo() {
		return strCreditNo;
	}

	public void setStrCreditNo(String strCreditNo) {
		this.strCreditNo = (String) setDefaultValue(strCreditNo, "");
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
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "");
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
