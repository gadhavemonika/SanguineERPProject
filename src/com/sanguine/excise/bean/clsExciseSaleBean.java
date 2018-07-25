package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExcisePOSSaleModel;

public class clsExciseSaleBean {
	// Variable Declaration

	private String strLicenceCode;

	private String strLicenceNo;

	private String strFromDate;

	private String strToDate;

	private List<clsExcisePOSSaleModel> listExcisePOSSale;

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

	public List<clsExcisePOSSaleModel> getListExcisePOSSale() {
		return listExcisePOSSale;
	}

	public void setListExcisePOSSale(List<clsExcisePOSSaleModel> listExcisePOSSale) {
		this.listExcisePOSSale = listExcisePOSSale;
	}

}
