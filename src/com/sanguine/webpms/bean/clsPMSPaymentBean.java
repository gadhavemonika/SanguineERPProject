package com.sanguine.webpms.bean;

import java.util.List;

import com.sanguine.webpms.model.clsPMSPaymentReceiptDtl;

public class clsPMSPaymentBean {
	// Variable Declaration
	private String strReceiptNo;

	private String strRegistrationNo;

	private String strReservationNo;

	private String strFolioNo;

	private double dblReceiptAmt;

	private double dblPaidAmt;

	private String strAgainst;

	private String strDocNo;

	private String strSettlementCode;

	private String strSettlementDesc;

	private double dblSettlementAmt;

	private String dteExpiryDate;

	private String strRemarks;

	private String strCardNo;

	private String strClientCode;

	private List<clsPMSPaymentReceiptDtlBean> listPaymentRecieptBean;

	private String strFlagOfAdvAmt;

	// Setter-Getter Methods
	public String getStrReceiptNo() {
		return strReceiptNo;
	}

	public void setStrReceiptNo(String strReceiptNo) {
		this.strReceiptNo = strReceiptNo;
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

	public String getStrFolioNo() {
		return strFolioNo;
	}

	public void setStrFolioNo(String strFolioNo) {
		this.strFolioNo = strFolioNo;
	}

	public double getDblReceiptAmt() {
		return dblReceiptAmt;
	}

	public void setDblReceiptAmt(double dblReceiptAmt) {
		this.dblReceiptAmt = dblReceiptAmt;
	}

	public double getDblPaidAmt() {
		return dblPaidAmt;
	}

	public void setDblPaidAmt(double dblPaidAmt) {
		this.dblPaidAmt = dblPaidAmt;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrDocNo() {
		return strDocNo;
	}

	public void setStrDocNo(String strDocNo) {
		this.strDocNo = strDocNo;
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	public String getStrSettlementDesc() {
		return strSettlementDesc;
	}

	public void setStrSettlementDesc(String strSettlementDesc) {
		this.strSettlementDesc = strSettlementDesc;
	}

	public double getDblSettlementAmt() {
		return dblSettlementAmt;
	}

	public void setDblSettlementAmt(double dblSettlementAmt) {
		this.dblSettlementAmt = dblSettlementAmt;
	}

	public String getDteExpiryDate() {
		return dteExpiryDate;
	}

	public void setDteExpiryDate(String dteExpiryDate) {
		this.dteExpiryDate = dteExpiryDate;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrCardNo() {
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}

	public List<clsPMSPaymentReceiptDtlBean> getListPaymentRecieptBean() {
		return listPaymentRecieptBean;
	}

	public void setListPaymentRecieptBean(List<clsPMSPaymentReceiptDtlBean> listPaymentRecieptBean) {
		this.listPaymentRecieptBean = listPaymentRecieptBean;
	}

	public String getStrFlagOfAdvAmt() {
		return strFlagOfAdvAmt;
	}

	public void setStrFlagOfAdvAmt(String strFlagOfAdvAmt) {
		this.strFlagOfAdvAmt = strFlagOfAdvAmt;
	}

}
