package com.sanguine.crm.bean;

import java.util.List;

import javax.persistence.Transient;

import com.sanguine.crm.model.clsDeliveryNoteDtlModel;

public class clsDeliveryNoteBean {

	// Variable Declaration

	private String strDNCode;

	private Long intId;

	private String strTypeAgainst;

	private String strTypeCode;

	private String dteDNDate;

	private String strDNType;

	private String strGRNCode;

	private String strJACode;

	private String strSCCode;

	private String strFrom;

	private String strLocCode;

	private String strSCAdd1;

	private String strSCAdd2;

	private String strSCCity;

	private String strSCState;

	private String strSCCountry;

	private String strSCPin;

	private String strFAdd1;

	private String strFAdd2;

	private String strFCity;

	private String strFState;

	private String strFCountry;

	private String strFPin;

	private String dteExpDate;

	private String strVehNo;

	private String strNarration;

	private String strMInBy;

	private String strTimeInOut;

	private Double dblTotal;

	private Double dblTotalWt;

	private Double dblTotalAmt;

	private String strExpDet;

	private String strAuthorise;

	private String strUserCreated;

	private String dteCreatedDate;

	private String strUserModified;

	private String dteLastModified;

	private String strClientCode;
	
	private String strTransportType;;
	

	@Transient
	private String strSCName;

	@Transient
	private String strLocName;

	private List<clsDeliveryNoteDtlModel> listDNDtl;

	// Setter-Getter Methods

	public String getStrDNCode() {
		return strDNCode;
	}

	public void setStrDNCode(String strDNCode) {
		this.strDNCode = strDNCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
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

	public Double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(Double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public Double getDblTotalWt() {
		return dblTotalWt;
	}

	public void setDblTotalWt(Double dblTotalWt) {
		this.dblTotalWt = dblTotalWt;
	}

	public Double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(Double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
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

	public List<clsDeliveryNoteDtlModel> getListDNDtl() {
		return listDNDtl;
	}

	public void setListDNDtl(List<clsDeliveryNoteDtlModel> listDNDtl) {
		this.listDNDtl = listDNDtl;
	}

	public String getStrSCName() {
		return strSCName;
	}

	public void setStrSCName(String strSCName) {
		this.strSCName = strSCName;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrTransportType() {
		return strTransportType;
	}

	public void setStrTransportType(String strTransportType) {
		this.strTransportType = strTransportType;
	}

	
}
