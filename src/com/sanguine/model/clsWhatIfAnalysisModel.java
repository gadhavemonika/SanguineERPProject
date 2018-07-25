package com.sanguine.model;

public class clsWhatIfAnalysisModel {

	private String strProductCode;

	private String strProductName;

	private String strUOM;

	private double dblQtyRequired;

	private double dblCurrentStk;

	private double dblOpenPOQty;

	private double dblOrderQty;

	private String strSuppCode;

	private String strSuppName;

	private double dblLeadTime;

	private double dblRate;

	private String strExpectedDate;

	public String getStrProductCode() {
		return strProductCode;
	}

	public void setStrProductCode(String strProductCode) {
		this.strProductCode = strProductCode;
	}

	public String getStrProductName() {
		return strProductName;
	}

	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblQtyRequired() {
		return dblQtyRequired;
	}

	public void setDblQtyRequired(double dblQtyRequired) {
		this.dblQtyRequired = dblQtyRequired;
	}

	public double getDblCurrentStk() {
		return dblCurrentStk;
	}

	public void setDblCurrentStk(double dblCurrentStk) {
		this.dblCurrentStk = dblCurrentStk;
	}

	public double getDblOpenPOQty() {
		return dblOpenPOQty;
	}

	public void setDblOpenPOQty(double dblOpenPOQty) {
		this.dblOpenPOQty = dblOpenPOQty;
	}

	public double getDblOrderQty() {
		return dblOrderQty;
	}

	public void setDblOrderQty(double dblOrderQty) {
		this.dblOrderQty = dblOrderQty;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public double getDblLeadTime() {
		return dblLeadTime;
	}

	public void setDblLeadTime(double dblLeadTime) {
		this.dblLeadTime = dblLeadTime;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public String getStrExpectedDate() {
		return strExpectedDate;
	}

	public void setStrExpectedDate(String strExpectedDate) {
		this.strExpectedDate = strExpectedDate;
	}
}
