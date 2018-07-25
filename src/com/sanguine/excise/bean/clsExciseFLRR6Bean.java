package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsFLR6ModelColumns;
import com.sanguine.excise.model.clsFLR6ModelRow;

public class clsExciseFLRR6Bean {

	private String strFromDate;

	private String strToDate;

	private String strFromBillNo;

	private String strToBillNo;

	private String strInPeg;

	private String strExportType;

	private List<clsFLR6ModelColumns> headerList1;

	private List<clsFLR6ModelRow> rowList1;

	private List<clsFLR6ModelColumns> headerList2;

	private List<clsFLR6ModelRow> rowList2;

	private String strLicenceCode;

	private String strLicenceNo;

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

	public String getStrFromBillNo() {
		return strFromBillNo;
	}

	public void setStrFromBillNo(String strFromBillNo) {
		this.strFromBillNo = strFromBillNo;
	}

	public String getStrToBillNo() {
		return strToBillNo;
	}

	public void setStrToBillNo(String strToBillNo) {
		this.strToBillNo = strToBillNo;
	}

	public String getStrInPeg() {
		return strInPeg;
	}

	public void setStrInPeg(String strInPeg) {
		this.strInPeg = strInPeg;
	}

	public List<clsFLR6ModelColumns> getHeaderList1() {
		return headerList1;
	}

	public void setHeaderList1(List<clsFLR6ModelColumns> headerList1) {
		this.headerList1 = headerList1;
	}

	public List<clsFLR6ModelRow> getRowList1() {
		return rowList1;
	}

	public void setRowList1(List<clsFLR6ModelRow> rowList1) {
		this.rowList1 = rowList1;
	}

	public List<clsFLR6ModelColumns> getHeaderList2() {
		return headerList2;
	}

	public void setHeaderList2(List<clsFLR6ModelColumns> headerList2) {
		this.headerList2 = headerList2;
	}

	public List<clsFLR6ModelRow> getRowList2() {
		return rowList2;
	}

	public void setRowList2(List<clsFLR6ModelRow> rowList2) {
		this.rowList2 = rowList2;
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

	// private List<clsFLR6Summary> summaryList;

}
