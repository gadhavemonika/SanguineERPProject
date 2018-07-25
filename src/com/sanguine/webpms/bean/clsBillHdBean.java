package com.sanguine.webpms.bean;

import java.util.List;

public class clsBillHdBean {
	// Variable Declaration
	private String strBillNo;

	private String dteBillDate;

	private String strFolioNo;

	private String strRegistrationNo;

	private String strReservationNo;

	private double dblGrandTotal;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private List<clsBillDtlBean> listBillDtlBean;

	// Setter-Getter Methods

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = strFolioNo;
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

	public double getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(double dblGrandTotal) {
		this.dblGrandTotal = dblGrandTotal;
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

	public List<clsBillDtlBean> getListBillDtlBean() {
		return listBillDtlBean;
	}

	public void setListBillDtlBean(List<clsBillDtlBean> listBillDtlBean) {
		this.listBillDtlBean = listBillDtlBean;
	}
}