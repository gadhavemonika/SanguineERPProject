package com.sanguine.webpms.bean;

import java.util.List;

public class clsTaxReportBean {

	// variables
	private String dteFromDate;
	private String dteToDate;

	private String strRegistrationNo;
	private String strGuestName;
	private String dteCheckInDate;
	private String dteCheckOutDate;
	private String strRoomNo;
	private String strRoomType;
	private String strUserCode;
	/*
	 * private String dteBillDate; private String strBillNo; private String
	 * strDocNo; private String strFolioNo;
	 */
	private List<clsTaxDtlBean> listClsTaxDtls;

	// getters and setters
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

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = strRegistrationNo;
	}

	public String getStrGuestName() {
		return strGuestName;
	}

	public void setStrGuestName(String strGuestName) {
		this.strGuestName = strGuestName;
	}

	public String getDteCheckInDate() {
		return dteCheckInDate;
	}

	public void setDteCheckInDate(String dteCheckInDate) {
		this.dteCheckInDate = dteCheckInDate;
	}

	public String getDteCheckOutDate() {
		return dteCheckOutDate;
	}

	public void setDteCheckOutDate(String dteCheckOutDate) {
		this.dteCheckOutDate = dteCheckOutDate;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public String getStrRoomType() {
		return strRoomType;
	}

	public void setStrRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public List<clsTaxDtlBean> getListClsTaxDtls() {
		return listClsTaxDtls;
	}

	public void setListClsTaxDtls(List<clsTaxDtlBean> listClsTaxDtls) {
		this.listClsTaxDtls = listClsTaxDtls;
	}

}
