package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Embeddable
public class clsPaymentScBillDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsPaymentScBillDtlModel() {
	}

	@Column(name = "strScCode")
	private String strScCode;

	@Column(name = "dteSCBillDate")
	private String dteSCBillDate;

	@Column(name = "dblScBillAmt")
	private double dblScBillAmt;

	@Column(name = "dteScBillDueDate")
	private String dteScBillDueDate;

	@Column(name = "strScBillNo")
	private String strScBillNo;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Transient
	private String strSelected;

	private double dblPayedAmt;

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

	// Setter-Getter Methods
	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrSelected() {
		return strSelected;
	}

	public void setStrSelected(String strSelected) {
		this.strSelected = strSelected;
	}

	public double getDblPayedAmt() {
		return dblPayedAmt;
	}

	public void setDblPayedAmt(double dblPayedAmt) {
		this.dblPayedAmt = dblPayedAmt;
	}

	/*
	 * public String getStrVouchNo() { return strVouchNo; } public void
	 * setStrVouchNo(String strVouchNo) { this.strVouchNo = strVouchNo; } public
	 * String getStrClientCode() { return strClientCode; } public void
	 * setStrClientCode(String strClientCode) { this.strClientCode =
	 * strClientCode; }
	 */

	public String getStrScCode() {
		return strScCode;
	}

	public void setStrScCode(String strScCode) {
		this.strScCode = strScCode;
	}

	public String getDteSCBillDate() {
		return dteSCBillDate;
	}

	public void setDteSCBillDate(String dteSCBillDate) {
		this.dteSCBillDate = dteSCBillDate;
	}

	public double getDblScBillAmt() {
		return dblScBillAmt;
	}

	public void setDblScBillAmt(double dblScBillAmt) {
		this.dblScBillAmt = dblScBillAmt;
	}

	public String getDteScBillDueDate() {
		return dteScBillDueDate;
	}

	public void setDteScBillDueDate(String dteScBillDueDate) {
		this.dteScBillDueDate = dteScBillDueDate;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStrScBillNo() {
		return strScBillNo;
	}

	public void setStrScBillNo(String strScBillNo) {
		this.strScBillNo = strScBillNo;
	}

}
