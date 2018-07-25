package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsAttributeMasterBean {
	private String strAttCode;
	@NotEmpty
	/* @Length(min=3, max=10) */
	private String strAttName;
	/* @NotEmpty */
	private String strAttDesc, strAttType;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String strClientCode;
	private long intId;
	private String strPAttCode;

	public String getStrAttCode() {

		return strAttCode;
	}

	public void setStrAttCode(String strAttCode) {
		this.strAttCode = strAttCode;
	}

	public String getStrAttName() {
		return strAttName;
	}

	public void setStrAttName(String strAttName) {
		this.strAttName = strAttName;
	}

	public String getStrAttDesc() {
		return strAttDesc;
	}

	public void setStrAttDesc(String strAttDesc) {
		this.strAttDesc = strAttDesc;
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

	public String getStrPAttCode() {
		return strPAttCode;
	}

	public void setStrPAttCode(String strPAttCode) {
		this.strPAttCode = strPAttCode;
	}

	public String getStrAttType() {
		return strAttType;
	}

	public void setStrAttType(String strAttType) {
		this.strAttType = strAttType;
	}

}
