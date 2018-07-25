package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsPurchaseIndentDtlModel;

public class clsPurchaseIndentHdBean {
	private long intId;
	private String strPIcode;
	private String dtPIDate;
	private String strLocCode;
	private String strAuthorise;
	private String strUserCreated;
	private String dtCreatedDate;
	private String strUserModified;
	private String dtLastModified;
	private String strNarration;
	private String strClientCode;
	private double dblTotal;
	private List<clsPurchaseIndentDtlModel> listPurchaseIndentDtlModel;
	private String strClosePI;
	private String strDocCode;
	private String strDocType;

	public String getStrDocCode() {
		return strDocCode;
	}

	public void setStrDocCode(String strDocCode) {
		this.strDocCode = strDocCode;
	}

	public String getStrDocType() {
		return strDocType;
	}

	public void setStrDocType(String strDocType) {
		this.strDocType = strDocType;
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

	public String getStrPIcode() {
		return strPIcode;
	}

	public void setStrPIcode(String strPIcode) {
		this.strPIcode = strPIcode;
	}

	public String getDtPIDate() {
		return dtPIDate;
	}

	public void setDtPIDate(String dtPIDate) {
		this.dtPIDate = dtPIDate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
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

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public List<clsPurchaseIndentDtlModel> getListPurchaseIndentDtlModel() {
		return listPurchaseIndentDtlModel;
	}

	public void setListPurchaseIndentDtlModel(List<clsPurchaseIndentDtlModel> listPurchaseIndentDtlModel) {
		this.listPurchaseIndentDtlModel = listPurchaseIndentDtlModel;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public String getStrClosePI() {
		return strClosePI;
	}

	public void setStrClosePI(String strClosePI) {
		this.strClosePI = strClosePI;
	}
}
