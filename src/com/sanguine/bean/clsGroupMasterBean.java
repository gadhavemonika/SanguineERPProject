package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsGroupMasterBean {
	private String strGCode;
	@NotEmpty
	/* @Length(min=3, max=10) */
	private String strGName;

	private String strGDesc;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String strClientCode;
	private long intGId;

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrGName() {
		return strGName;
	}

	public void setStrGName(String strGName) {
		this.strGName = strGName;
	}

	public String getStrGDesc() {
		return strGDesc;
	}

	public void setStrGDesc(String strGDesc) {
		this.strGDesc = strGDesc;
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

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
