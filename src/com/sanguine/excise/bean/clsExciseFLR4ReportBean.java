package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseFLR4ReportColumnModel;
import com.sanguine.excise.model.clsExciseFLR4ReportModel;

public class clsExciseFLR4ReportBean {

	// Variable Declaration
	private String strFromDate;

	private String strToDate;

	private String strInPeg;

	private String strExportType;

	private String strLicenceCode;

	private String strLicenceNo;

	private List<clsExciseFLR4ReportColumnModel> colRow;

	private List<clsExciseFLR4ReportModel> rowList;

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

	public List<clsExciseFLR4ReportColumnModel> getColRow() {
		return colRow;
	}

	public void setColRow(List<clsExciseFLR4ReportColumnModel> colRow) {
		this.colRow = colRow;
	}

	public List<clsExciseFLR4ReportModel> getRowList() {
		return rowList;
	}

	public void setRowList(List<clsExciseFLR4ReportModel> rowList) {
		this.rowList = rowList;
	}

	public String getStrInPeg() {
		return strInPeg;
	}

	public void setStrInPeg(String strInPeg) {
		this.strInPeg = strInPeg;
	}

	public String getStrExportType() {
		return strExportType;
	}

	public void setStrExportType(String strExportType) {
		this.strExportType = strExportType;
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
