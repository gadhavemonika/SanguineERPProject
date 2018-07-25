package com.sanguine.webpms.bean;

import java.util.List;
import java.util.TreeMap;

public class clsRoomStatusDtlBean {

	private String strRoomNo;

	private String strRoomType;

	private String strGuestName;

	private String strReservationNo;

	private String strCheckInNo;

	private String strCheckOutNo;

	private String strRoomStatus;

	private int intDay;

	private String strDay1;

	private String strDay2;

	private String strDay3;

	private String strDay4;

	private String strDay5;

	private String strDay6;

	private String strDay7;

	private String dteArrivalDate;

	private String dteDepartureDate;
	
	
	private TreeMap<Integer, List<clsGuestListReportBean>> mapGuestListPerDay;

	public TreeMap<Integer, List<clsGuestListReportBean>> getMapGuestListPerDay() {
		return mapGuestListPerDay;
	}




	public void setMapGuestListPerDay(TreeMap<Integer, List<clsGuestListReportBean>> mapGuestListPerDay) {
		this.mapGuestListPerDay = mapGuestListPerDay;
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

	public String getStrGuestName() {
		return strGuestName;
	}

	public void setStrGuestName(String strGuestName) {
		this.strGuestName = strGuestName;
	}

	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = strReservationNo;
	}

	public String getStrCheckInNo() {
		return strCheckInNo;
	}

	public void setStrCheckInNo(String strCheckInNo) {
		this.strCheckInNo = strCheckInNo;
	}

	public int getIntDay() {
		return intDay;
	}

	public void setIntDay(int intDay) {
		this.intDay = intDay;
	}

	public String getStrDay1() {
		return strDay1;
	}

	public void setStrDay1(String strDay1) {
		this.strDay1 = strDay1;
	}

	public String getStrDay2() {
		return strDay2;
	}

	public void setStrDay2(String strDay2) {
		this.strDay2 = strDay2;
	}

	public String getStrDay3() {
		return strDay3;
	}

	public void setStrDay3(String strDay3) {
		this.strDay3 = strDay3;
	}

	public String getStrDay4() {
		return strDay4;
	}

	public void setStrDay4(String strDay4) {
		this.strDay4 = strDay4;
	}

	public String getStrDay5() {
		return strDay5;
	}

	public void setStrDay5(String strDay5) {
		this.strDay5 = strDay5;
	}

	public String getStrDay6() {
		return strDay6;
	}

	public void setStrDay6(String strDay6) {
		this.strDay6 = strDay6;
	}

	public String getStrDay7() {
		return strDay7;
	}

	public void setStrDay7(String strDay7) {
		this.strDay7 = strDay7;
	}

	public String getStrRoomStatus() {
		return strRoomStatus;
	}

	public void setStrRoomStatus(String strRoomStatus) {
		this.strRoomStatus = strRoomStatus;
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

	public String getStrCheckOutNo() {
		return strCheckOutNo;
	}

	public void setStrCheckOutNo(String strCheckOutNo) {
		this.strCheckOutNo = strCheckOutNo;
	}

}
