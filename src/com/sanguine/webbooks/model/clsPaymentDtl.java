package com.sanguine.webbooks.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "tblpaymentdtl")
public class clsPaymentDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPaymentDtl() {
	}

	// Variable Declaration
	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strAccountName")
	private String strAccountName;

	@Column(name = "strCrDr")
	private String strCrDr;

	@Column(name = "dblDrAmt")
	private double dblDrAmt;

	@Column(name = "dblCrAmt")
	private double dblCrAmt;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	// Setter-Getter Methods
	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "");
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = (String) setDefaultValue(strAccountName, "");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "");
	}

	public double getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(double dblDrAmt) {
		this.dblDrAmt = (Double) setDefaultValue(dblDrAmt, "0.0000");
	}

	public double getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(double dblCrAmt) {
		this.dblCrAmt = (Double) setDefaultValue(dblCrAmt, "0.0000");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "");
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

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = (String) setDefaultValue(strDebtorCode, "");
	}

}
