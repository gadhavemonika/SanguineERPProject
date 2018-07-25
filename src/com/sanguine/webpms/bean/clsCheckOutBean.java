package com.sanguine.webpms.bean;

import java.util.ArrayList;
import java.util.List;

public class clsCheckOutBean {
	// Variable Declaration

	private String strSearchType;

	private String strSearchTextField;

	private String dteCheckOutDate;

	private String tmeCheckOutTime;

	private String strRegistrationNo;

	private String strPrefix;

	private String strFirstName;

	private String strMiddleName;

	private String strLastName;

	private String strCorporate;

	private String strSegment;

	private String strGroup;

	private String intAdultPAX;

	private String intChildPAX;

	private String strType;

	private String strReservationNo;

	private String strWalkInNo;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String strRoomNo;

	private List<clsCheckOutRoomDtlBean> listCheckOutRoomDtlBeans = new ArrayList<clsCheckOutRoomDtlBean>();

	// Setter-Getter Methods

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public List<clsCheckOutRoomDtlBean> getListCheckOutRoomDtlBeans() {
		return listCheckOutRoomDtlBeans;
	}

	public void setListCheckOutRoomDtlBeans(List<clsCheckOutRoomDtlBean> listCheckOutRoomDtlBeans) {
		this.listCheckOutRoomDtlBeans = listCheckOutRoomDtlBeans;
	}

	public String getStrSearchTextField() {
		return strSearchTextField;
	}

	public void setStrSearchTextField(String strSearchTextField) {
		this.strSearchTextField = strSearchTextField;
	}

	public String getDteCheckOutDate() {
		return dteCheckOutDate;
	}

	public void setDteCheckOutDate(String dteCheckOutDate) {
		this.dteCheckOutDate = dteCheckOutDate;
	}

	public String getTmeCheckOutTime() {
		return tmeCheckOutTime;
	}

	public void setTmeCheckOutTime(String tmeCheckOutTime) {
		this.tmeCheckOutTime = tmeCheckOutTime;
	}

	public String getStrSearchType() {
		return strSearchType;
	}

	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = strRegistrationNo;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = strReservationNo;
	}

	public String getStrWalkInNo() {
		return strWalkInNo;
	}

	public void setStrWalkInNo(String strWalkInNo) {
		this.strWalkInNo = strWalkInNo;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPrefix() {
		return strPrefix;
	}

	public void setStrPrefix(String strPrefix) {
		this.strPrefix = strPrefix;
	}

	public String getStrFirstName() {
		return strFirstName;
	}

	public void setStrFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}

	public String getStrMiddleName() {
		return strMiddleName;
	}

	public void setStrMiddleName(String strMiddleName) {
		this.strMiddleName = strMiddleName;
	}

	public String getStrLastName() {
		return strLastName;
	}

	public void setStrLastName(String strLastName) {
		this.strLastName = strLastName;
	}

	public String getStrCorporate() {
		return strCorporate;
	}

	public void setStrCorporate(String strCorporate) {
		this.strCorporate = strCorporate;
	}

	public String getStrSegment() {
		return strSegment;
	}

	public void setStrSegment(String strSegment) {
		this.strSegment = strSegment;
	}

	public String getStrGroup() {
		return strGroup;
	}

	public void setStrGroup(String strGroup) {
		this.strGroup = strGroup;
	}

	public String getIntAdultPAX() {
		return intAdultPAX;
	}

	public void setIntAdultPAX(String intAdultPAX) {
		this.intAdultPAX = intAdultPAX;
	}

	public String getIntChildPAX() {
		return intChildPAX;
	}

	public void setIntChildPAX(String intChildPAX) {
		this.intChildPAX = intChildPAX;
	}

}
