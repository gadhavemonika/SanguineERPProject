package com.sanguine.webpms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblextrabed")
public class clsExtraBedMasterModel {
	@Id
	@Column(name = "strExtraBedTypeCode")
	private String strExtraBedTypeCode;

	@Column(name = "strExtraBedTypeDesc")
	private String strExtraBedTypeDesc;

	@Column(name = "intNoBeds")
	private int intNoBeds;

	@Column(name = "dblChargePerBed")
	private double dblChargePerBed;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strAccountCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strAccountCode;

	public String getStrExtraBedTypeCode() {
		return strExtraBedTypeCode;
	}

	public void setStrExtraBedTypeCode(String strExtraBedTypeCode) {
		this.strExtraBedTypeCode = strExtraBedTypeCode;
	}

	public String getStrExtraBedTypeDesc() {
		return strExtraBedTypeDesc;
	}

	public void setStrExtraBedTypeDesc(String strExtraBedTypeDesc) {
		this.strExtraBedTypeDesc = strExtraBedTypeDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
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
		this.strClientCode = strClientCode;
	}

	public int getIntNoBeds() {
		return intNoBeds;
	}

	public void setIntNoBeds(int intNoBeds) {
		this.intNoBeds = intNoBeds;
	}

	public double getDblChargePerBed() {
		return dblChargePerBed;
	}

	public void setDblChargePerBed(double dblChargePerBed) {
		this.dblChargePerBed = dblChargePerBed;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "NA");
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
