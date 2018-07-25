package com.sanguine.webpms.model;

import javax.persistence.Embeddable;

@Embeddable
public class clsRoomPackageDtl 
{
	public clsRoomPackageDtl() {
	}
	private String strReservationNo;
	private String strIncomeHeadCode;
	private String strIncomeHeadName;
	private double dblIncomeHeadAmt;
	private String strWalkinNo;
	private String strCheckInNo;
	private String strPackageCode;
	private String strPackageName;
	
	
	
	
	
	public String getStrPackageName() {
		return strPackageName;
	}
	public void setStrPackageName(String strPackageName) {
		this.strPackageName = strPackageName;
	}
	public String getStrReservationNo() {
		return strReservationNo;
	}
	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = strReservationNo;
	}
	public String getStrIncomeHeadCode() {
		return strIncomeHeadCode;
	}
	public void setStrIncomeHeadCode(String strIncomeHeadCode) {
		this.strIncomeHeadCode = strIncomeHeadCode;
	}
	public String getStrWalkinNo() {
		return strWalkinNo;
	}
	public void setStrWalkinNo(String strWalkinNo) {
		this.strWalkinNo = strWalkinNo;
	}

	public String getStrCheckInNo() {
		return strCheckInNo;
	}
	public void setStrCheckInNo(String strCheckInNo) {
		this.strCheckInNo = strCheckInNo;
	}
	public String getStrPackageCode() {
		return strPackageCode;
	}
	public void setStrPackageCode(String strPackageCode) {
		this.strPackageCode = strPackageCode;
	}
	
	public String getStrIncomeHeadName() {
		return strIncomeHeadName;
	}
	public void setStrIncomeHeadName(String strIncomeHeadName) {
		this.strIncomeHeadName = strIncomeHeadName;
	}
	public double getDblIncomeHeadAmt() {
		return dblIncomeHeadAmt;
	}
	public void setDblIncomeHeadAmt(double dblIncomeHeadAmt) {
		this.dblIncomeHeadAmt = dblIncomeHeadAmt;
	}
	
	
	
}
