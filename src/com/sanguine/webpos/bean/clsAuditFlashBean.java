package com.sanguine.webpos.bean;

public class clsAuditFlashBean {

	private String strBillNo;

	private String strBillDate;

	private String strModifiedDate;

	private String strEntryTime;

	private String strModifyTime;

	private String strItemName;

	private double dblBillAmt;

	private double dblNetAmt;

	private double dblPax;

	private String strUserCreated;

	private String strUserEdited;

	private String strReasonName;

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrBillDate() {
		return strBillDate;
	}

	public void setStrBillDate(String strBillDate) {
		this.strBillDate = strBillDate;
	}

	public String getStrModifiedDate() {
		return strModifiedDate;
	}

	public void setStrModifiedDate(String strModifiedDate) {
		this.strModifiedDate = strModifiedDate;
	}

	public String getStrEntryTime() {
		return strEntryTime;
	}

	public void setStrEntryTime(String strEntryTime) {
		this.strEntryTime = strEntryTime;
	}

	public String getStrModifyTime() {
		return strModifyTime;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String itemName) {
		this.strItemName = itemName;
	}

	public void setStrModifyTime(String strModifyTime) {
		this.strModifyTime = strModifyTime;
	}

	public double getDblBillAmt() {
		return dblBillAmt;
	}

	public void setDblBillAmt(double dblBillAmt) {
		this.dblBillAmt = dblBillAmt;
	}

	public double getDblNetAmt() {
		return dblNetAmt;
	}

	public void setDblNetAmt(double dblNetAmt) {
		this.dblNetAmt = dblNetAmt;
	}

	public double getDblPax() {
		return dblPax;
	}

	public void setDblPax(double dblPax) {
		this.dblPax = dblPax;
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

	public String getStrReasonName() {
		return strReasonName;
	}

	public void setStrReasonName(String strReasonName) {
		this.strReasonName = strReasonName;
	}

}
