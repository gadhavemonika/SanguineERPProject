package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.util.clsBrandWiseInquiryReportData;

public class clsExciseAuditBean {

	private String dtFromDate;

	private String dtToDate;

	private String strReportType;

	private String strTransType;

	private List<clsBrandWiseInquiryReportData> objExciseAuditFlash;

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

	public String getStrReportType() {
		return strReportType;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public List<clsBrandWiseInquiryReportData> getObjExciseAuditFlash() {
		return objExciseAuditFlash;
	}

	public void setObjExciseAuditFlash(List<clsBrandWiseInquiryReportData> objExciseAuditFlash) {
		this.objExciseAuditFlash = objExciseAuditFlash;
	}

}
