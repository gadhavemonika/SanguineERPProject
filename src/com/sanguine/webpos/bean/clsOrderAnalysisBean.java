/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpos.bean;

public class clsOrderAnalysisBean {
	private String strItemName;

	private String itemCode;

	private String KOTNo;

	private double saleQty;

	private double dblCompQty;

	private double dblNCQty;

	private double voidQty;

	private double compliQty;

	private double voidKOTQty;

	private double itemSaleRate;

	private double itemPurchaseRate;

	private double totalAmt;

	private double totalCostValue;

	private double totalDiscountAmt;

	private double finalItemQty;

	private double per;

	private double costValuePer;

	public double getDblCompQty() {
		return dblCompQty;
	}

	public void setDblCompQty(double dblCompQty) {
		this.dblCompQty = dblCompQty;
	}

	public double getDblNCQty() {
		return dblNCQty;
	}

	public void setDblNCQty(double dblNCQty) {
		this.dblNCQty = dblNCQty;
	}

	public double getPer() {
		return per;
	}

	public void setPer(double per) {
		this.per = per;
	}

	public double getCostValuePer() {
		return costValuePer;
	}

	public void setCostValuePer(double costValuePer) {
		this.costValuePer = costValuePer;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getKOTNo() {
		return KOTNo;
	}

	public void setKOTNo(String KOTNo) {
		this.KOTNo = KOTNo;
	}

	public double getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(double saleQty) {
		this.saleQty = saleQty;
	}

	public double getVoidQty() {
		return voidQty;
	}

	public void setVoidQty(double voidQty) {
		this.voidQty = voidQty;
	}

	public double getVoidKOTQty() {
		return voidKOTQty;
	}

	public void setVoidKOTQty(double voidKOTQty) {
		this.voidKOTQty = voidKOTQty;
	}

	public double getItemSaleRate() {
		return itemSaleRate;
	}

	public void setItemSaleRate(double itemSaleRate) {
		this.itemSaleRate = itemSaleRate;
	}

	public double getItemPurchaseRate() {
		return itemPurchaseRate;
	}

	public void setItemPurchaseRate(double itemPurchaseRate) {
		this.itemPurchaseRate = itemPurchaseRate;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public double getTotalCostValue() {
		return totalCostValue;
	}

	public void setTotalCostValue(double totalCostValue) {
		this.totalCostValue = totalCostValue;
	}

	public double getTotalDiscountAmt() {
		return totalDiscountAmt;
	}

	public void setTotalDiscountAmt(double totalDiscountAmt) {
		this.totalDiscountAmt = totalDiscountAmt;
	}

	public double getFinalItemQty() {
		return finalItemQty;
	}

	public void setFinalItemQty(double finalItemQty) {
		this.finalItemQty = finalItemQty;
	}

	public double getCompliQty() {
		return compliQty;
	}

	public void setCompliQty(double compliQty) {
		this.compliQty = compliQty;
	}

}
