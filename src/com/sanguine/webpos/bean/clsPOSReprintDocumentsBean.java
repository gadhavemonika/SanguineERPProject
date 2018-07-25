package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSReprintDocumentsBean {
	private List<clsPOSReprintDocumentsBean> listForTableDtl;

	private String strBillNo;
	private String strKOTNo;
	private String dteDateCreated;
	private String strWShortName;
	private String strTableName;
	private long intPaxNo;
	private String strUserEdited;
	private double dblAmount;
	private String dteBillDate;
	private String strPOSCode;
	private String dblGrandTotal;
	private String strPOSName;
	private String strSettleDesc;
	private double dblSettleAmt;
	private String strTaxDesc;
	private double dblTaxableAmount;
	private double dblTaxAmount;
	private String strgroupName;
	private double dblNetTotalPlusTax;

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrKOTNo() {
		return strKOTNo;
	}

	public void setStrKOTNo(String strKOTNo) {
		this.strKOTNo = strKOTNo;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrWShortName() {
		return strWShortName;
	}

	public void setStrWShortName(String strWShortName) {
		this.strWShortName = strWShortName;
	}

	public String getStrTableName() {
		return strTableName;
	}

	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}

	public long getIntPaxNo() {
		return intPaxNo;
	}

	public void setIntPaxNo(long intPaxNo) {
		this.intPaxNo = intPaxNo;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(String dblGrandTotal) {
		this.dblGrandTotal = dblGrandTotal;
	}

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = strPOSName;
	}

	public List<clsPOSReprintDocumentsBean> getListForTableDtl() {
		return listForTableDtl;
	}

	public void setListForTableDtl(List<clsPOSReprintDocumentsBean> listForTableDtl) {
		this.listForTableDtl = listForTableDtl;
	}

	public String getStrSettleDesc() {
		return strSettleDesc;
	}

	public void setStrSettleDesc(String strSettleDesc) {
		this.strSettleDesc = strSettleDesc;
	}

	public double getDblSettleAmt() {
		return dblSettleAmt;
	}

	public void setDblSettleAmt(double dblSettleAmt) {
		this.dblSettleAmt = dblSettleAmt;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public double getDblTaxableAmount() {
		return dblTaxableAmount;
	}

	public void setDblTaxableAmount(double dblTaxableAmount) {
		this.dblTaxableAmount = dblTaxableAmount;
	}

	public double getDblTaxAmount() {
		return dblTaxAmount;
	}

	public void setDblTaxAmount(double dblTaxAmount) {
		this.dblTaxAmount = dblTaxAmount;
	}

	public String getStrgroupName() {
		return strgroupName;
	}

	public void setStrgroupName(String strgroupName) {
		this.strgroupName = strgroupName;
	}

	public double getDblNetTotalPlusTax() {
		return dblNetTotalPlusTax;
	}

	public void setDblNetTotalPlusTax(double dblNetTotalPlusTax) {
		this.dblNetTotalPlusTax = dblNetTotalPlusTax;
	}

}
