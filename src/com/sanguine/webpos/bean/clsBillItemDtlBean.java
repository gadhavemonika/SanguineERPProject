/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpos.bean;

/**
 *
 * @author ajjim
 */
public class clsBillItemDtlBean {
	private String strBillNo;
	private String dteBillDate;
	private String strPosName;
	private double dblSubTotal;
	private double dblGrandTotal;
	private String strItemCode;
	private String strItemName;
	private double dblQuantity;
	private double dblAmount;
	private double dblDiscountAmt;
	private double dblDiscountPer;
	private double dblBillDiscPer;
	private String strSettelmentMode;
	private double dblTaxAmt;
	private double dblSettlementAmt;
	private String strDiscValue;
	private String strDiscType;

	public String getStrDiscValue() {
		return strDiscValue;
	}

	public String getStrDiscType() {
		return strDiscType;
	}

	public void setStrDiscType(String strDiscType) {
		this.strDiscType = strDiscType;
	}

	public void setStrDiscValue(String strDiscValue) {
		this.strDiscValue = strDiscValue;
	}

	public clsBillItemDtlBean() {
	}

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

	public String getStrPosName() {
		return strPosName;
	}

	public void setStrPosName(String strPosName) {
		this.strPosName = strPosName;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public double getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(double dblGrandTotal) {
		this.dblGrandTotal = dblGrandTotal;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public double getDblQuantity() {
		return dblQuantity;
	}

	public void setDblQuantity(double dblQuantity) {
		this.dblQuantity = dblQuantity;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public double getDblDiscountAmt() {
		return dblDiscountAmt;
	}

	public void setDblDiscountAmt(double dblDiscountAmt) {
		this.dblDiscountAmt = dblDiscountAmt;
	}

	public double getDblDiscountPer() {
		return dblDiscountPer;
	}

	public void setDblDiscountPer(double dblDiscountPer) {
		this.dblDiscountPer = dblDiscountPer;
	}

	public double getDblBillDiscPer() {
		return dblBillDiscPer;
	}

	public void setDblBillDiscPer(double dblBillDiscPer) {
		this.dblBillDiscPer = dblBillDiscPer;
	}

	public String getStrSettelmentMode() {
		return strSettelmentMode;
	}

	public void setStrSettelmentMode(String strSettelmentMode) {
		this.strSettelmentMode = strSettelmentMode;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblSettlementAmt() {
		return dblSettlementAmt;
	}

	public void setDblSettlementAmt(double dblSettlementAmt) {
		this.dblSettlementAmt = dblSettlementAmt;
	}

}
