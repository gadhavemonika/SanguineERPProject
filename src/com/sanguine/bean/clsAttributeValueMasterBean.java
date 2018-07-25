package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsAttributeValueMasterBean {
	private String strAVCode;
	@NotEmpty
	/* @Length(min=3, max=10) */
	private String strAVName;
	/* @NotEmpty */
	private String strAVDesc;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String strClientCode;
	private long intId;
	private String strAttCode;

	public String getStrAVCode() {
		return strAVCode;
	}

	public void setStrAVCode(String strAVCode) {
		this.strAVCode = strAVCode;
	}

	public String getStrAVName() {
		return strAVName;
	}

	public void setStrAVName(String strAVName) {
		this.strAVName = strAVName;
	}

	public String getStrAVDesc() {
		return strAVDesc;
	}

	public void setStrAVDesc(String strAVDesc) {
		this.strAVDesc = strAVDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrAttCode() {
		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = strAttCode;
	}
}
