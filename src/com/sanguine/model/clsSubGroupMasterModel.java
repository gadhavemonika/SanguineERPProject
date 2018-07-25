package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblsubgroupmaster")
@IdClass(clsSubGroupMasterModel_ID.class)
public class clsSubGroupMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSubGroupMasterModel() {
	}

	public clsSubGroupMasterModel(clsSubGroupMasterModel_ID clsSubGroupMasterModel_ID) {
		strSGCode = clsSubGroupMasterModel_ID.getStrSGCode();
		strClientCode = clsSubGroupMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSGCode", column = @Column(name = "strSGCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strSGCode")
	private String strSGCode;

	@Column(name = "strGCode")
	private String strGCode;

	@Column(name = "strSGName")
	private String strSGName;

	@Column(name = "strSGDesc")
	private String strSGDesc;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClientCode", nullable = false, updatable = false)
	private String strClientCode;

	@Column(name = "intSGId", nullable = false, updatable = false)
	private long intSGId;

	@Column(name = "strExciseable", nullable = false, columnDefinition = "VARCHAR(1) NOT NULL default 'N'")
	private String strExciseable;

	@Column(name = "strExciseChapter")
	private String strExciseChapter;

	@Column(name = "intSortingNo")
	private int intSortingNo;

	@Column(name = "strSGDescHeader")
	private String strSGDescHeader;

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
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

	public long getIntSGId() {
		return intSGId;
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
