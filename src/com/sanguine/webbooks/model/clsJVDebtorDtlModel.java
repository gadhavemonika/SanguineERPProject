package com.sanguine.webbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable
// @Entity
// @Table(name = "tbljvdebtordtl")
public class clsJVDebtorDtlModel {

	// @Id
	// @AttributeOverrides({
	// @AttributeOverride(name="strVouchNo",column=@Column(name="strVouchNo")),
	// @AttributeOverride(name="strClientCode",column=@Column(name="strClientCode"))
	// })
	// @Column(name="strVouchNo")
	// private String strVouchNo;
	//
	// @Column(name="strClientCode")
	// private String strClientCode;

	private String strDebtorCode;

	private String strDebtorName;

	private String strCrDr;

	private double dblAmt;

	private String strBillNo;

	private String strInvoiceNo;

	private String strNarration;

	private String strGuest;

	private String strAccountCode;

	private String strCreditNo;

	private String dteBillDate;

	private String dteInvoiceDate;

	private String dteDueDate;

	private String strPropertyCode;

	private String strPOSCode;

	private String strPOSName;

	private String strRegistrationNo;

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = strDebtorCode;
	}

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = strDebtorName;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = dblAmt;
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}

	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = strInvoiceNo;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrGuest() {
		return strGuest;
	}

	public void setStrGuest(String strGuest) {
		this.strGuest = strGuest;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public String getStrCreditNo() {
		return strCreditNo;
	}

	public void setStrCreditNo(String strCreditNo) {
		this.strCreditNo = strCreditNo;
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
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = strPOSName;
	}

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = strRegistrationNo;
	}

	// public String getStrVouchNo() {
	// return strVouchNo;
	// }
	//
	// public void setStrVouchNo(String strVouchNo) {
	// this.strVouchNo = strVouchNo;
	// }
	//
	// public String getStrClientCode() {
	// return strClientCode;
	// }
	//
	// public void setStrClientCode(String strClientCode) {
	// this.strClientCode = strClientCode;
	// }
}
