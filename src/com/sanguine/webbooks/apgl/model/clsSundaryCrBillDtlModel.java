package com.sanguine.webbooks.apgl.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;

@Embeddable
public class clsSundaryCrBillDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSundaryCrBillDtlModel() {
	}

	// @Column(name="strACCode")
	private String strACCode;

	// @Column(name="strCrDr")
	private String strCrDr;

	// @Column(name="dblCrAmt")
	private double dblCrAmt;

	// @Column(name="dblDrAmt")
	private double dblDrAmt;

	// @Column(name="strNarration")
	private String strNarration;

	// @Column(name="strPropertyCode")
	private String strPropertyCode;

	private String strACName;

	@Transient
	private String strCreditorCode;

	@Transient
	private String strCreditorName;

	// Setter-Getter Methods

	public String getStrACCode() {
		return strACCode;
	}

	public void setStrACCode(String strACCode) {
		this.strACCode = (String) setDefaultValue(strACCode, "NA");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "NA");
	}

	public double getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(double dblCrAmt) {
		this.dblCrAmt = (Double) setDefaultValue(dblCrAmt, "NA");
	}

	public double getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(double dblDrAmt) {
		this.dblDrAmt = (Double) setDefaultValue(dblDrAmt, "NA");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
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

	public String getStrACName() {
		return strACName;
	}

	public void setStrACName(String strACName) {
		this.strACName = strACName;
	}

	public String getStrCreditorCode() {
		return strCreditorCode;
	}

	public void setStrCreditorCode(String strCreditorCode) {
		this.strCreditorCode = strCreditorCode;
	}

	public String getStrCreditorName() {
		return strCreditorName;
	}

	public void setStrCreditorName(String strCreditorName) {
		this.strCreditorName = strCreditorName;
	}

}
