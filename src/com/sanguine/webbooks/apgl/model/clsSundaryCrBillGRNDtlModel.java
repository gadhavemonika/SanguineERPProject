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
public class clsSundaryCrBillGRNDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSundaryCrBillGRNDtlModel() {
	}

	private String strGRNCode;

	private String dteGRNDate;

	private double dblGRNAmt;

	private String dteGRNDueDate;

	private String strGRNBIllNo;

	private String strPropertyCode;

	private String dteBillDate;

	@Transient
	private String strSelected;

	@Transient
	private double dblPayedAmt;

	// Setter-Getter Methods

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = (String) setDefaultValue(strGRNCode, "");
	}

	public String getDteGRNDate() {
		return dteGRNDate;
	}

	public void setDteGRNDate(String dteGRNDate) {
		this.dteGRNDate = dteGRNDate;
	}

	public double getDblGRNAmt() {
		return dblGRNAmt;
	}

	public void setDblGRNAmt(double dblGRNAmt) {
		this.dblGRNAmt = (Double) setDefaultValue(dblGRNAmt, "0");
	}

	public String getDteGRNDueDate() {
		return dteGRNDueDate;
	}

	public void setDteGRNDueDate(String dteGRNDueDate) {
		this.dteGRNDueDate = dteGRNDueDate;
	}

	public String getStrGRNBIllNo() {
		return strGRNBIllNo;
	}

	public void setStrGRNBIllNo(String strGRNBIllNo) {
		this.strGRNBIllNo = (String) setDefaultValue(strGRNBIllNo, "");
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

}
