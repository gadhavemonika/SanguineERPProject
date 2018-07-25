/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpos.bean;

/**
 *
 * @author jai
 */
public class clsGenericBean {

	private String strCode;
	private String strName;
	private String strPOSCode;
	private String strPOSName;
	private double dblQty;
	private double dblAmt;
	private double dblDiscAmt;
	private double dblDiscPer;
	private double dblSubTotal;
	private String dtePOSDate;
	private double dblUnUsedBalance;

	public clsGenericBean() {
	}

	public String getStrCode() {
		return strCode;
	}

	public String getDtePOSDate() {
		return dtePOSDate;
	}

	public void setDtePOSDate(String dtePOSDate) {
		this.dtePOSDate = dtePOSDate;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = strPOSName;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = dblAmt;
	}

	public double getDblDiscAmt() {
		return dblDiscAmt;
	}

	public void setDblDiscAmt(double dblDiscAmt) {
		this.dblDiscAmt = dblDiscAmt;
	}

	public double getDblDiscPer() {
		return dblDiscPer;
	}

	public void setDblDiscPer(double dblDiscPer) {
		this.dblDiscPer = dblDiscPer;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public double getDblUnUsedBalance() {
		return dblUnUsedBalance;
	}

	public void setDblUnUsedBalance(double dblUnUsedBalance) {
		this.dblUnUsedBalance = dblUnUsedBalance;
	}

}
