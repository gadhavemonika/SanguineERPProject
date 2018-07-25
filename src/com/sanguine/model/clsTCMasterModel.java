package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbltcmaster")
public class clsTCMasterModel {
	@Id
	@Column(name = "strTCCode")
	private String strTCCode;

	@Column(name = "strTCName")
	private String strTCName;

	@Column(name = "strApplicable")
	private String strApplicable;

	@Column(name = "intMaxLength")
	private int intMaxLength;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dtDateCreated", updatable = false)
	private String dtDateCreated;

	@Column(name = "dtDateEdited")
	private String dtDateEdited;

	@Column(name = "strClientCode", updatable = false)
	private String strClientCode;

	@Column(name = "strDataPostFlag")
	private String strDataPostFlag;

	@Column(name = "intId")
	private long intId;

	@Transient
	private String strTCDesc;

	public String getStrTCCode() {
		return strTCCode;
	}

	public void setStrTCCode(String strTCCode) {
		this.strTCCode = strTCCode;
	}

	public String getStrTCName() {
		return strTCName;
	}

	/*
	 * public void setStrTCName(String strTCName) { this.strTCName = (String)
	 * funSetDefaultValue(strTCName, ""); }
	 * 
	 * public String getStrApplicable() { return strApplicable; }
	 * 
	 * public void setStrApplicable(String strApplicable) { this.strApplicable =
	 * (String) funSetDefaultValue(strApplicable, "N"); }
	 * 
	 * public int getIntMaxLength() { return intMaxLength; }
	 * 
	 * public void setIntMaxLength(int intMaxLength) { this.intMaxLength = (int)
	 * funSetDefaultValue(intMaxLength, "0"); }
	 */

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

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getDtDateEdited() {
		return dtDateEdited;
	}

	public void setDtDateEdited(String dtDateEdited) {
		this.dtDateEdited = dtDateEdited;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = strDataPostFlag;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrApplicable() {
		return strApplicable;
	}

	public void setStrApplicable(String strApplicable) {
		this.strApplicable = strApplicable;
	}

	public int getIntMaxLength() {
		return intMaxLength;
	}

	public void setIntMaxLength(int intMaxLength) {
		this.intMaxLength = intMaxLength;
	}

	public void setStrTCName(String strTCName) {
		this.strTCName = strTCName;
	}

	public String getStrTCDesc() {
		return strTCDesc;
	}

	public void setStrTCDesc(String strTCDesc) {
		this.strTCDesc = strTCDesc;
	}

	/*
	 * public Object funSetDefaultValue(Object value, Object defaultValue) {
	 * if(value !=null && (value instanceof String &&
	 * value.toString().length()>0)){ return value; }else if(value !=null &&
	 * !(value instanceof String)){ return value; }else{ return defaultValue; }
	 * //return value !=null && (value instanceof String &&
	 * value.toString().length()>0) ? value : defaultValue; }
	 */
}
