package com.sanguine.webpms.bean;

import javax.persistence.Column;

public class clsWalkinDtlBean {
	private String strRoomType;
	private String strRoomDesc;
	private int intNoOfChild;
	private int intNoOfAdults;
	private String strRoomNo;
	private String strGuestCode;
	private String strGuestFirstName;;
	private String strGuestMiddleName;
	private String strGuestLastName;
	private long lngMobileNo;
	private String strExtraBedCode;
	private String strExtraBedDesc;
	private String strAddress;
	private String strRemark;


	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public String getStrRoomType() {
		return strRoomType;
	}

	public void setStrRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}

	public int getIntNoOfChild() {
		return intNoOfChild;
	}

	public void setIntNoOfChild(int intNoOfChild) {
		this.intNoOfChild = intNoOfChild;
	}

	public int getIntNoOfAdults() {
		return intNoOfAdults;
	}

	public void setIntNoOfAdults(int intNoOfAdults) {
		this.intNoOfAdults = intNoOfAdults;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public String getStrGuestCode() {
		return strGuestCode;
	}

	public void setStrGuestCode(String strGuestCode) {
		this.strGuestCode = strGuestCode;
	}

	public String getStrGuestFirstName() {
		return strGuestFirstName;
	}

	public void setStrGuestFirstName(String strGuestFirstName) {
		this.strGuestFirstName = strGuestFirstName;
	}

	public String getStrGuestMiddleName() {
		return strGuestMiddleName;
	}

	public void setStrGuestMiddleName(String strGuestMiddleName) {
		this.strGuestMiddleName = strGuestMiddleName;
	}

	public String getStrGuestLastName() {
		return strGuestLastName;
	}

	public void setStrGuestLastName(String strGuestLastName) {
		this.strGuestLastName = strGuestLastName;
	}

	public String getStrRoomDesc() {
		return strRoomDesc;
	}

	public void setStrRoomDesc(String strRoomDesc) {
		this.strRoomDesc = strRoomDesc;
	}

	public long getLngMobileNo() {
		return lngMobileNo;
	}

	public void setLngMobileNo(long lngMobileNo) {
		this.lngMobileNo = lngMobileNo;
	}

	public String getStrExtraBedDesc() {
		return strExtraBedDesc;
	}

	public void setStrExtraBedDesc(String strExtraBedDesc) {
		this.strExtraBedDesc = strExtraBedDesc;
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = strAddress;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}
}
