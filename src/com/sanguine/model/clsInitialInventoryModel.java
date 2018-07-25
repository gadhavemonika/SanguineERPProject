package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblinitialinventory")
public class clsInitialInventoryModel implements Serializable {
	private static final long serialVersionUID = 784493362462650121L;

	@Id
	@Column(name = "strOpStkCode")
	private String strOpStkCode;

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocCode;

	@Column(name = "dtExpiryDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtExpDate;

	@Column(name = "intId", updatable = false)
	private long intId;

	@Column(name = "strUserCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Column(name = "strClientCode", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "strConversionUOM", columnDefinition = "VARCHAR(25) NOT NULL default ''")
	private String strConversionUOM;

	@Transient
	private String strLocName;

	public String getStrOpStkCode() {
		return strOpStkCode;
	}

	public void setStrOpStkCode(String strOpStkCode) {
		this.strOpStkCode = strOpStkCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
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

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
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

	public String getDtExpDate() {
		return dtExpDate;
	}

	public void setDtExpDate(String dtExpDate) {
		this.dtExpDate = dtExpDate;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrConversionUOM() {
		return strConversionUOM;
	}

	public void setStrConversionUOM(String strConversionUOM) {
		this.strConversionUOM = strConversionUOM;
	}
}
