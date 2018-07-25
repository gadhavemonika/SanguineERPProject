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
@Table(name = "tblrateconthd")
@IdClass(clsRateContractHdModel_ID.class)
public class clsRateContractHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsRateContractHdModel() {
	}

	public clsRateContractHdModel(clsRateContractHdModel_ID clsRateContractHdModel_ID) {
		strRateContNo = clsRateContractHdModel_ID.getStrRateContNo();
		strClientCode = clsRateContractHdModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strRateContNo", column = @Column(name = "strRateContNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strRateContNo")
	private String strRateContNo;

	@Column(name = "dtRateContDate")
	private String dtRateContDate;

	@Column(name = "strSuppCode")
	private String strSuppCode;

	@Column(name = "dtFromDate")
	private String dtFromDate;

	@Column(name = "dtToDate")
	private String dtToDate;

	@Column(name = "strDateChg")
	private String strDateChg;

	@Column(name = "strAuthorise", columnDefinition = "CHAR(5) NOT NULL default 'No'")
	private String strAuthorise;

	@Column(name = "strCurrency")
	private String strCurrency;

	@Column(name = "strProdFlag")
	private String strProdFlag;

	@Column(name = "dblConversion")
	private double dblConversion;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strUser")
	private String strUser;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	@Column(name = "strClientCode", nullable = false, updatable = false)
	private String strClientCode;

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrRateContNo() {
		return strRateContNo;
	}

	public void setStrRateContNo(String strRateContNo) {
		this.strRateContNo = strRateContNo;
	}

	public String getDtRateContDate() {
		return dtRateContDate;
	}

	public void setDtRateContDate(String dtRateContDate) {
		this.dtRateContDate = dtRateContDate;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
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

	public String getStrDateChg() {
		return strDateChg;
	}

	public void setStrDateChg(String strDateChg) {
		this.strDateChg = strDateChg;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getStrProdFlag() {
		return strProdFlag;
	}

	public void setStrProdFlag(String strProdFlag) {
		this.strProdFlag = strProdFlag;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
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

	public String getStrUser() {
		return strUser;
	}

	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}
}
