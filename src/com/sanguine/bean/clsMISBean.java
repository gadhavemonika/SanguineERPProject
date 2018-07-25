package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsMISDtlModel;

public class clsMISBean {

	private String strMISCode;
	private String strAgainst, strReqCode, strLocFrom, strLocTo, strNarration, strAuthorise, strUserModified, strUserCreated, strClientCode;
	private long intId;
	private String dtMISDate, dtCreatedDate, dtLastModified;
	private List<clsMISDtlModel> listMISDtl;
	private double dblTotalAmt;
	private String strCloseReq;

	public List<clsMISDtlModel> getListMISDtl() {
		return listMISDtl;
	}

	public void setListMISDtl(List<clsMISDtlModel> listMISDtl) {
		this.listMISDtl = listMISDtl;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getStrLocFrom() {
		return strLocFrom;
	}

	public void setStrLocFrom(String strLocFrom) {
		this.strLocFrom = strLocFrom;
	}

	public String getStrLocTo() {
		return strLocTo;
	}

	public void setStrLocTo(String strLocTo) {
		this.strLocTo = strLocTo;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
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

	public String getDtMISDate() {
		return dtMISDate;
	}

	public void setDtMISDate(String dtMISDate) {
		this.dtMISDate = dtMISDate;
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

	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrCloseReq() {
		return strCloseReq;
	}

	public void setStrCloseReq(String strCloseReq) {
		this.strCloseReq = strCloseReq;
	}
}
