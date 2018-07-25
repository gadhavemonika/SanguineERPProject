package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.util.clsBrandWiseInquiryReportData;

public class clsExciseBrandWiseInquiryBean {

	// Variable Declaration

	private String dteFromDate;

	private String dteToDate;

	private String strBrandCode;

	private String strInPeg;

	private List<clsBrandWiseInquiryReportData> objBrandWiseInquiry;

	// Getter and Setter method

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public List<clsBrandWiseInquiryReportData> getObjBrandWiseInquiry() {
		return objBrandWiseInquiry;
	}

	public void setObjBrandWiseInquiry(List<clsBrandWiseInquiryReportData> objBrandWiseInquiry) {
		this.objBrandWiseInquiry = objBrandWiseInquiry;
	}

	public String getStrInPeg() {
		return strInPeg;
	}

	public void setStrInPeg(String strInPeg) {
		this.strInPeg = strInPeg;
	}

}
