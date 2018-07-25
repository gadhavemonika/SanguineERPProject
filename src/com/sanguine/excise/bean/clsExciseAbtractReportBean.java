package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseAbstractReportColumnModel;
import com.sanguine.excise.model.clsExciseAbstractReportModel;

public class clsExciseAbtractReportBean {

	// Variable Declaration
	private String strFromDate;

	private String strToDate;

	private String strLicenceCode;

	private String strLicenceNo;

	private List<clsExciseAbstractReportColumnModel> colRow;

	private List<clsExciseAbstractReportModel> rowList;

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public List<clsExciseAbstractReportColumnModel> getColRow() {
		return colRow;
	}

	public void setColRow(List<clsExciseAbstractReportColumnModel> colRow) {
		this.colRow = colRow;
	}

	public List<clsExciseAbstractReportModel> getRowList() {
		return rowList;
	}

	public void setRowList(List<clsExciseAbstractReportModel> rowList) {
		this.rowList = rowList;
	}

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public String getStrLicenceNo() {
		return strLicenceNo;
	}

	public void setStrLicenceNo(String strLicenceNo) {
		this.strLicenceNo = strLicenceNo;
	}

}
