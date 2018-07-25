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
// @Table(name = "tbljvdtl")
public class clsJVDtlModel {

	// @Id
	// @AttributeOverrides({
	// @AttributeOverride(name="strVouchNo",column=@Column(name="strVouchNo")),
	// @AttributeOverride(name="strClientCode",column=@Column(name="strClientCode"))
	// })

	// Variable Declaration

	// @Column(name="strVouchNo")
	// private String strVouchNo;
	//
	// @Column(name="strClientCode")
	// private String strClientCode;

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

	@Column(name = "strOneLine")
	private String strOneLine;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "NA");
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = (String) setDefaultValue(strAccountName, "NA");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "NA");
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
		this.strNarration = (String) setDefaultValue(strNarration, "NA");
	}

	public String getStrOneLine() {
		return strOneLine;
	}

	public void setStrOneLine(String strOneLine) {
		this.strOneLine = (String) setDefaultValue(strOneLine, "NA");
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
	// public String getStrVouchNo() {
	// return strVouchNo;
	// }
	// public void setStrVouchNo(String strVouchNo) {
	// this.strVouchNo = strVouchNo;
	// }
	// public String getStrClientCode() {
	// return strClientCode;
	// }
	// public void setStrClientCode(String strClientCode) {
	// this.strClientCode = strClientCode;
	// }

}
