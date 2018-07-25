package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbldeliverynotehd")
@IdClass(clsDeliveryNoteHdModel_ID.class)
public class clsDeliveryNoteHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsDeliveryNoteHdModel() {
	}

	public clsDeliveryNoteHdModel(clsDeliveryNoteHdModel_ID objModelID) {
		strDNCode = objModelID.getStrDNCode();
		strClientCode = objModelID.getStrClientCode();
	}

	// Variable Declaration

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strDNCode", column = @Column(name = "strDNCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strDNCode")
	private String strDNCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strTypeAgainst")
	private String strTypeAgainst;

	@Column(name = "strTypeCode")
	private String strTypeCode;

	@Column(name = "dteDNDate")
	private String dteDNDate;

	@Column(name = "strDNType")
	private String strDNType;

	@Column(name = "strGRNCode")
	private String strGRNCode;

	@Column(name = "strJACode")
	private String strJACode;

	@Column(name = "strSCCode")
	private String strSCCode;

	@Transient
	private String strSCName;

	@Column(name = "strFrom")
	private String strFrom;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Transient
	private String strLocName;

	@Column(name = "strSCAdd1")
	private String strSCAdd1;

	@Column(name = "strSCAdd2")
	private String strSCAdd2;

	@Column(name = "strSCCity")
	private String strSCCity;

	@Column(name = "strSCState")
	private String strSCState;

	@Column(name = "strSCCountry")
	private String strSCCountry;

	@Column(name = "strSCPin")
	private String strSCPin;

	@Column(name = "strFAdd1")
	private String strFAdd1;

	@Column(name = "strFAdd2")
	private String strFAdd2;

	@Column(name = "strFCity")
	private String strFCity;

	@Column(name = "strFState")
	private String strFState;

	@Column(name = "strFCountry")
	private String strFCountry;

	@Column(name = "strFPin")
	private String strFPin;

	@Column(name = "dteExpDate")
	private String dteExpDate;

	@Column(name = "strVehNo")
	private String strVehNo;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strMInBy")
	private String strMInBy;

	@Column(name = "strTimeInOut")
	private String strTimeInOut;

	@Column(name = "dblTotal")
	private double dblTotal;

	@Column(name = "strExpDet")
	private String strExpDet;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	@Column(name = "strTransportType")
	private String strTransportType;

	// Setter-Getter Methods

	public String getStrDNCode() {
		return strDNCode;
	}

	public void setStrDNCode(String strDNCode) {
		this.strDNCode = strDNCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrTypeAgainst() {
		return strTypeAgainst;
	}

	public void setStrTypeAgainst(String strTypeAgainst) {
		this.strTypeAgainst = strTypeAgainst;
	}

	public String getStrTypeCode() {
		return strTypeCode;
	}

	public void setStrTypeCode(String strTypeCode) {
		this.strTypeCode = strTypeCode;
	}

	public String getDteDNDate() {
		return dteDNDate;
	}

	public void setDteDNDate(String dteDNDate) {
		this.dteDNDate = dteDNDate;
	}

	public String getStrDNType() {
		return strDNType;
	}

	public void setStrDNType(String strDNType) {
		this.strDNType = strDNType;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrJACode() {
		return strJACode;
	}

	public void setStrJACode(String strJACode) {
		this.strJACode = strJACode;
	}

	public String getStrSCCode() {
		return strSCCode;
	}

	public void setStrSCCode(String strSCCode) {
		this.strSCCode = strSCCode;
	}

	public String getStrFrom() {
		return strFrom;
	}

	public void setStrFrom(String strFrom) {
		this.strFrom = strFrom;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrSCAdd1() {
		return strSCAdd1;
	}

	public void setStrSCAdd1(String strSCAdd1) {
		this.strSCAdd1 = strSCAdd1;
	}

	public String getStrSCAdd2() {
		return strSCAdd2;
	}

	public void setStrSCAdd2(String strSCAdd2) {
		this.strSCAdd2 = strSCAdd2;
	}

	public String getStrSCCity() {
		return strSCCity;
	}

	public void setStrSCCity(String strSCCity) {
		this.strSCCity = strSCCity;
	}

	public String getStrSCState() {
		return strSCState;
	}

	public void setStrSCState(String strSCState) {
		this.strSCState = strSCState;
	}

	public String getStrSCCountry() {
		return strSCCountry;
	}

	public void setStrSCCountry(String strSCCountry) {
		this.strSCCountry = strSCCountry;
	}

	public String getStrSCPin() {
		return strSCPin;
	}

	public void setStrSCPin(String strSCPin) {
		this.strSCPin = strSCPin;
	}

	public String getStrFAdd1() {
		return strFAdd1;
	}

	public void setStrFAdd1(String strFAdd1) {
		this.strFAdd1 = strFAdd1;
	}

	public String getStrFAdd2() {
		return strFAdd2;
	}

	public void setStrFAdd2(String strFAdd2) {
		this.strFAdd2 = strFAdd2;
	}

	public String getStrFCity() {
		return strFCity;
	}

	public void setStrFCity(String strFCity) {
		this.strFCity = strFCity;
	}

	public String getStrFState() {
		return strFState;
	}

	public void setStrFState(String strFState) {
		this.strFState = strFState;
	}

	public String getStrFCountry() {
		return strFCountry;
	}

	public void setStrFCountry(String strFCountry) {
		this.strFCountry = strFCountry;
	}

	public String getStrFPin() {
		return strFPin;
	}

	public void setStrFPin(String strFPin) {
		this.strFPin = strFPin;
	}

	public String getDteExpDate() {
		return dteExpDate;
	}

	public void setDteExpDate(String dteExpDate) {
		this.dteExpDate = dteExpDate;
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrMInBy() {
		return strMInBy;
	}

	public void setStrMInBy(String strMInBy) {
		this.strMInBy = strMInBy;
	}

	public String getStrTimeInOut() {
		return strTimeInOut;
	}

	public void setStrTimeInOut(String strTimeInOut) {
		this.strTimeInOut = strTimeInOut;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public String getStrExpDet() {
		return strExpDet;
	}

	public void setStrExpDet(String strExpDet) {
		this.strExpDet = strExpDet;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrTransportType() {
		return strTransportType;
	}

	public void setStrTransportType(String strTransportType) {
		this.strTransportType = strTransportType;
	}
}
