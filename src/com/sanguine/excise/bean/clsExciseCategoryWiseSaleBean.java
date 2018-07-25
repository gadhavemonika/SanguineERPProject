package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseFLR4ReportColumnModel;
import com.sanguine.util.clsBrandWiseInquiryReportData;

public class clsExciseCategoryWiseSaleBean {

	// Variable Declaration

	private String dteFromDate;

	private String dteToDate;

	private List<clsExciseFLR4ReportColumnModel> colRow;

	private List<clsBrandWiseInquiryReportData> fullRowData;

	private List<clsBrandWiseInquiryReportData> date;

	// Getter and Setter
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

	public List<clsExciseFLR4ReportColumnModel> getColRow() {
		return colRow;
	}

	public void setColRow(List<clsExciseFLR4ReportColumnModel> colRow) {
		this.colRow = colRow;
	}

	public List<clsBrandWiseInquiryReportData> getDate() {
		return date;
	}

	public void setDate(List<clsBrandWiseInquiryReportData> date) {
		this.date = date;
	}

	public List<clsBrandWiseInquiryReportData> getFullRowData() {
		return fullRowData;
	}

	public void setFullRowData(List<clsBrandWiseInquiryReportData> fullRowData) {
		this.fullRowData = fullRowData;
	}

}
