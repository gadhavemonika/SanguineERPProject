package com.sanguine.excise.bean;

import java.util.ArrayList;

import com.sanguine.excise.model.clsFLR3AModelRow;

public class clsFLR3ABean {

	// Variable Declaration

	private String strFromDate;

	private String strToDate;

	private String strInPeg;

	private String strExportType;

	private String strLicenceCode;

	private String strLicenceNo;

	private ArrayList<clsFLR3AModelRow> FLRList;

	// Setter Getter Method

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

	public String getStrInPeg() {
		return strInPeg;
	}

	public void setStrInPeg(String strInPeg) {
		this.strInPeg = strInPeg;
	}

	public ArrayList<clsFLR3AModelRow> getFLRList() {
		return FLRList;
	}

	public void setFLRList(ArrayList<clsFLR3AModelRow> fLRList) {
		FLRList = fLRList;
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
