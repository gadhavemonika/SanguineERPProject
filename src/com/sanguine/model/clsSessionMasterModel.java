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
@Table(name = "tblsessionmaster")
@IdClass(clsSessionMasterModel_ID.class)
public class clsSessionMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSessionMasterModel() {
	}

	public clsSessionMasterModel(clsSessionMasterModel_ID clsGroupMasterModel_ID) {
		strSessionCode = clsGroupMasterModel_ID.getStrSessionCode();
		strClientCode = clsGroupMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSessionCode", column = @Column(name = "strSessionCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strSessionCode")
	private String strSessionCode;

	@Column(name = "strSessionName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strSessionName;

	@Column(name = "strSDesc", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strSDesc;

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strClientCode", nullable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "intSId", nullable = false, updatable = false)
	private long intSId;

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

	public String getStrSessionCode() {
		return strSessionCode;
	}

	public void setStrSessionCode(String strSessionCode) {
		this.strSessionCode = strSessionCode;
	}

	public String getStrSessionName() {
		return strSessionName;
	}

	public void setStrSessionName(String strSessionName) {
		this.strSessionName = strSessionName;
	}

	public String getStrSDesc() {
		return strSDesc;
	}

	public void setStrSDesc(String strSDesc) {
		this.strSDesc = strSDesc;
	}

	public long getIntSId() {
		return intSId;
	}

	public void setIntSId(long intSId) {
		this.intSId = intSId;
	}

}
