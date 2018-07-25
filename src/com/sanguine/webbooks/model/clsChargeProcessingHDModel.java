package com.sanguine.webbooks.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblChargeGenerationTemp")
public class clsChargeProcessingHDModel implements Serializable {
	public clsChargeProcessingHDModel() {

	}

	// Variable Declaration
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intGid")
	private long intGid;

	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strMemberName")
	private String strMemberName;

	@Column(name = "strChargeCode")
	private String strChargeCode;

	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "dblAmount")
	private double dblAmount;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strCrDr")
	private String strCrDr;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "dteFromDate")
	private String dteFromDate;

	@Column(name = "dteToDate")
	private String dteToDate;

	@Column(name = "dteGeneratedOn")
	private String dteGeneratedOn;

	@Column(name = "strInstantJVYN")
	private String strInstantJVYN;

	@Column(name = "strAnnualChargeProcessYN")
	private String strAnnualChargeProcessYN;

	@Column(name = "strOtherFunctions")
	private String strOtherFunctions;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public String getStrChargeCode() {
		return strChargeCode;
	}

	public void setStrChargeCode(String strChargeCode) {
		this.strChargeCode = strChargeCode;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	// Setter-Getter Methods
	public long getIntGid() {
		return intGid;
	}

	public void setIntGid(long intGid) {
		this.intGid = (Long) setDefaultValue(intGid, "0");
	}

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
	}

	public String getStrMemberName() {
		return strMemberName;
	}

	public void setStrMemberName(String strMemberName) {
		this.strMemberName = (String) setDefaultValue(strMemberName, "NA");
	}

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getDteGeneratedOn() {
		return dteGeneratedOn;
	}

	public void setDteGeneratedOn(String dteGeneratedOn) {
		this.dteGeneratedOn = dteGeneratedOn;
	}

	public String getStrInstantJVYN() {
		return strInstantJVYN;
	}

	public void setStrInstantJVYN(String strInstantJVYN) {
		this.strInstantJVYN = (String) setDefaultValue(strInstantJVYN, "N");
	}

	public String getStrAnnualChargeProcessYN() {
		return strAnnualChargeProcessYN;
	}

	public void setStrAnnualChargeProcessYN(String strAnnualChargeProcessYN) {
		this.strAnnualChargeProcessYN = (String) setDefaultValue(strAnnualChargeProcessYN, "N");
	}

	public String getStrOtherFunctions() {
		return strOtherFunctions;
	}

	public void setStrOtherFunctions(String strOtherFunctions) {
		this.strOtherFunctions = (String) setDefaultValue(strOtherFunctions, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
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

}
