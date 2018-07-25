package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblbillpasshd")
public class clsBillPassHdModel implements Serializable {
	@Id
	@Column(name = "strBillPassNo")
	private String strBillPassNo;

	@Column(name = "intId", updatable = false)
	private long intId;

	@Column(name = "dtBillDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtBillDate;

	@Column(name = "strSuppCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strSuppCode;

	@Column(name = "strPVno", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPVno;

	@Column(name = "dtPassDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtPassDate;

	@Column(name = "dblBillAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblBillAmt;

	@Column(name = "strUserCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtDateCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtDateCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strAuthorise", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAuthorise;

	@Column(name = "strCurrency", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCurrency;

	@Column(name = "dblConversion", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblConversion;

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strNarration;

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAgainst;

	@Column(name = "strAgainstCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strAgainstCode;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getDtBillDate() {
		return dtBillDate;
	}

	public void setDtBillDate(String dtBillDate) {
		this.dtBillDate = dtBillDate;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrPVno() {
		return strPVno;
	}

	public void setStrPVno(String strPVno) {
		this.strPVno = strPVno;
	}

	public String getDtPassDate() {
		return dtPassDate;
	}

	public void setDtPassDate(String dtPassDate) {
		this.dtPassDate = dtPassDate;
	}

	public double getDblBillAmt() {
		return dblBillAmt;
	}

	public void setDblBillAmt(double dblBillAmt) {
		this.dblBillAmt = dblBillAmt;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrAgainstCode() {
		return strAgainstCode;
	}

	public void setStrAgainstCode(String strAgainstCode) {
		this.strAgainstCode = strAgainstCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrBillPassNo() {
		return strBillPassNo;
	}

	public void setStrBillPassNo(String strBillPassNo) {
		this.strBillPassNo = strBillPassNo;
	}

	@Column(name = "intLevel", columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}
}
