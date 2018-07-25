package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsSubGroupMasterBean {
	private String strSGCode;
	@NotEmpty
	private String strSGName;
	private String strSGDesc;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String strClientCode;
	private long intSGId;
	private String strGCode;
	private String strExciseable;
	private String strExciseChapter;
	private int intSortingNo;
	private String strSGDescHeader;

	public long getIntSGId() {
		return intSGId;
	}

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrSGName() {
		return strSGName;
	}

	public void setStrSGName(String strSGName) {
		this.strSGName = strSGName;
	}

	public String getStrSGDesc() {
		return strSGDesc;
	}

	public void setStrSGDesc(String strSGDesc) {
		this.strSGDesc = strSGDesc;
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

	public void setIntSGId(long intSGId) {
		this.intSGId = intSGId;
	}

	public String getStrExciseable() {
		return strExciseable;
	}

	public void setStrExciseable(String strExciseable) {
		this.strExciseable = strExciseable;
	}

	public String getStrExciseChapter() {
		return strExciseChapter;
	}

	public void setStrExciseChapter(String strExciseChapter) {
		this.strExciseChapter = strExciseChapter;
	}

	public int getIntSortingNo() {
		return intSortingNo;
	}

	public void setIntSortingNo(int intSortingNo) {
		this.intSortingNo = intSortingNo;
	}

	public String getStrSGDescHeader() {
		return strSGDescHeader;
	}

	public void setStrSGDescHeader(String strSGDescHeader) {
		this.strSGDescHeader = strSGDescHeader;
	}

}
