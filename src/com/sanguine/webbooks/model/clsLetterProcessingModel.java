package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblletterprocesstemp")
public class clsLetterProcessingModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsLetterProcessingModel() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intId", updatable = false, nullable = false)
	private long intId;

	@Column(name = "strLetterCode")
	private String strLetterCode;

	@Column(name = "strLetterName")
	private String strLetterName;

	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	@Column(name = "strDebtorName")
	private String strDebtorName;

	@Column(name = "strCatCode")
	private String strCatCode;

	@Column(name = "strArea")
	private String strArea;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strZip")
	private String strZip;

	@Column(name = "strDebtorAddr1")
	private String strDebtorAddr1;

	@Column(name = "strDebtorAddr2")
	private String strDebtorAddr2;

	@Column(name = "strDebtorAddr3")
	private String strDebtorAddr3;

	@Column(name = "dblOpBal")
	private double dblOpBal;

	@Column(name = "dblOutstanding")
	private double dblOutstanding;

	@Column(name = "strCatName")
	private String strCatName;

	@Column(name = "strLetter")
	private String strLetter;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "0");
	}

	public String getStrLetterCode() {
		return strLetterCode;
	}

	public void setStrLetterCode(String strLetterCode) {
		this.strLetterCode = (String) setDefaultValue(strLetterCode, "NA");
	}

	public String getStrLetterName() {
		return strLetterName;
	}

	public void setStrLetterName(String strLetterName) {
		this.strLetterName = (String) setDefaultValue(strLetterName, "NA");
	}

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

	public String getStrCatCode() {
		return strCatCode;
	}

	public void setStrCatCode(String strCatCode) {
		this.strCatCode = (String) setDefaultValue(strCatCode, "NA");
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = (String) setDefaultValue(strArea, "NA");
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = (String) setDefaultValue(strCity, "NA");
	}

	public String getStrZip() {
		return strZip;
	}

	public void setStrZip(String strZip) {
		this.strZip = (String) setDefaultValue(strZip, "NA");
	}

	public String getStrDebtorAddr1() {
		return strDebtorAddr1;
	}

	public void setStrDebtorAddr1(String strDebtorAddr1) {
		this.strDebtorAddr1 = (String) setDefaultValue(strDebtorAddr1, "NA");
	}

	public String getStrDebtorAddr2() {
		return strDebtorAddr2;
	}

	public void setStrDebtorAddr2(String strDebtorAddr2) {
		this.strDebtorAddr2 = (String) setDefaultValue(strDebtorAddr2, "NA");
	}

	public String getStrDebtorAddr3() {
		return strDebtorAddr3;
	}

	public void setStrDebtorAddr3(String strDebtorAddr3) {
		this.strDebtorAddr3 = (String) setDefaultValue(strDebtorAddr3, "NA");
	}

	public double getDblOpBal() {
		return dblOpBal;
	}

	public void setDblOpBal(double dblOpBal) {
		this.dblOpBal = (Double) setDefaultValue(dblOpBal, "0.0000");
	}

	public double getDblOutstanding() {
		return dblOutstanding;
	}

	public void setDblOutstanding(double dblOutstanding) {
		this.dblOutstanding = (Double) setDefaultValue(dblOutstanding, "0.0000");
	}

	public String getStrCatName() {
		return strCatName;
	}

	public void setStrCatName(String strCatName) {
		this.strCatName = (String) setDefaultValue(strCatName, "NA");
	}

	public String getStrLetter() {
		return strLetter;
	}

	public void setStrLetter(String strLetter) {
		this.strLetter = (String) setDefaultValue(strLetter, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "NA");
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
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
