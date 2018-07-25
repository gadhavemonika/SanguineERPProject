package com.sanguine.webclub.model;

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
@IdClass(clsWebClubGroupMasterModel_ID.class)
public class clsWebClubGroupMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsWebClubGroupMasterModel() {
	}

	public clsWebClubGroupMasterModel(clsWebClubGroupMasterModel_ID clsWebClubGroupMasterModel_ID) {
		strGroupCode = clsWebClubGroupMasterModel_ID.getStrGroupCode();
		strClientCode = clsWebClubGroupMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strGroupCode", column = @Column(name = "strGroupCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strGroupCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strGroupCode;

	@Column(name = "strGroupName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strGroupName;

	@Column(name = "strShortName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strShortName;

	@Column(name = "strCategory", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCategory;

	@Column(name = "strCrDr", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCrDr;

	@Column(name = "strUserCreated", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtCreatedDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "strPropertyCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPropertyCode;

	@Column(name = "intGId", nullable = false, updatable = false)
	private long intGId;

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = strGroupCode;
	}

	public String getStrGroupName() {
		return strGroupName;
	}

	public void setStrGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = strShortName;
	}

	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
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

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

}
