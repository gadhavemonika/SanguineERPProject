package com.sanguine.webpms.bean;

import java.util.List;

import javax.persistence.Column;

public class clsFolioHdBean

{
	// Variable Declaration
	private String strFolioNo;

	private String strRoomNo;

	private String strRegistrationNo;

	private String strReservationNo;

	private String dteArrivalDate;

	private String dteDepartureDate;

	private String strCheckInNo;

	private String tmeArrivalTime;

	private String tmeDepartureTime;

	private String strExtraBedCode;

	private String strGuestCode;

	private String strWalkInNo;

	private List<clsFolioDtlBean> listFolioDtlBean;

	// Setter-Getter Methods
	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = strFolioNo;
	}

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}

	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = strRegistrationNo;
	}

	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = strReservationNo;
	}

	public String getDteArrivalDate() {
		return dteArrivalDate;
	}

	public void setDteArrivalDate(String dteArrivalDate) {
		this.dteArrivalDate = dteArrivalDate;
	}

	public String getDteDepartureDate() {
		return dteDepartureDate;
	}

	public void setDteDepartureDate(String dteDepartureDate) {
		this.dteDepartureDate = dteDepartureDate;
	}

	public String getStrCheckInNo() {
		return strCheckInNo;
	}

	public void setStrCheckInNo(String strCheckInNo) {
		this.strCheckInNo = strCheckInNo;
	}

	public List<clsFolioDtlBean> getListFolioDtlBean() {
		return listFolioDtlBean;
	}

	public void setListFolioDtlBean(List<clsFolioDtlBean> listFolioDtlBean) {
		this.listFolioDtlBean = listFolioDtlBean;
	}

	public String getTmeArrivalTime() {
		return tmeArrivalTime;
	}

	public void setTmeArrivalTime(String tmeArrivalTime) {
		this.tmeArrivalTime = tmeArrivalTime;
	}

	public String getTmeDepartureTime() {
		return tmeDepartureTime;
	}

	public void setTmeDepartureTime(String tmeDepartureTime) {
		this.tmeDepartureTime = tmeDepartureTime;
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public String getStrGuestCode() {
		return strGuestCode;
	}

	public void setStrGuestCode(String strGuestCode) {
		this.strGuestCode = strGuestCode;
	}

	public String getStrWalkInNo() {
		return strWalkInNo;
	}

	public void setStrWalkInNo(String strWalkInNo) {
		this.strWalkInNo = strWalkInNo;
	}

}
