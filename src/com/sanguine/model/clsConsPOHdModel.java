package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblconspohd")
public class clsConsPOHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strConsPOCode")
	private String strConsPOCode;

	@Column(name = "dtConsPODate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtConsPODate;

	@Column(name = "strPOCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPOCode;

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strSupplierCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strSupplierCode;

	@Column(name = "dtFromDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtFromDate;

	@Column(name = "dtToDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtToDate;

	@Column(name = "strRefNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strRefNo;

	@Column(name = "strClientCode", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	public String getStrConsPOCode() {
		return strConsPOCode;
	}

	public void setStrConsPOCode(String strConsPOCode) {
		this.strConsPOCode = strConsPOCode;
	}

	public String getDtConsPODate() {
		return dtConsPODate;
	}

	public void setDtConsPODate(String dtConsPODate) {
		this.dtConsPODate = dtConsPODate;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
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

	public String getStrSupplierCode() {
		return strSupplierCode;
	}

	public void setStrSupplierCode(String strSupplierCode) {
		this.strSupplierCode = strSupplierCode;
	}

	public String getDtFromDate() {
		return dtFromDate;
	}

	public void setDtFromDate(String dtFromDate) {
		this.dtFromDate = dtFromDate;
	}

	public String getDtToDate() {
		return dtToDate;
	}

	public void setDtToDate(String dtToDate) {
		this.dtToDate = dtToDate;
	}

	public String getStrRefNo() {
		return strRefNo;
	}

	public void setStrRefNo(String strRefNo) {
		this.strRefNo = strRefNo;
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
}
