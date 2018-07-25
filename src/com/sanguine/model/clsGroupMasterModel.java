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
@Table(name = "tblgroupmaster")
@IdClass(clsGroupMasterModel_ID.class)
public class clsGroupMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsGroupMasterModel() {
	}

	public clsGroupMasterModel(clsGroupMasterModel_ID clsGroupMasterModel_ID) {
		strGCode = clsGroupMasterModel_ID.getStrGCode();
		strClientCode = clsGroupMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strGCode", column = @Column(name = "strGCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strGCode")
	private String strGCode;

	@Column(name = "strGName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strGName;

	@Column(name = "strGDesc", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strGDesc;

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

	@Column(name = "intGId", nullable = false, updatable = false)
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
