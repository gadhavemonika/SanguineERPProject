package com.sanguine.util;

public class clsRG1DailyStockAccountReportDtl {

	private String dateInv;
	private String strProdCode;
	private String strProdName;
	private String dblOpeningStk;
	private String dblMfgQTY;
	private String dblTotalQty;
	private String dblInvQty;
	private String dblAssValue;
	private String dblExportQty;
	private String dblExportValue;
	private String dblExportQtyBound;
	private String dblExportBoundValue;
	private String dblExportQtyFactoryBound;
	private String dblExportFactoryBoundValue;
	private String purpose;
	private String dblpurposeQty;
	private String exTaxPer;
	private String dblExTaxAmt;
	private String dblclosingBalance;
	private String dblloseQty;
	private String voucherNo;
	private String dateInvRemark;
	private String chaptorNo;

	public String getDateInv() {
		return dateInv;
	}

	public void setDateInv(String dateInv) {
		this.dateInv = (String) setDefaultValue(dateInv, "");
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = (String) setDefaultValue(strProdCode, "");
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = (String) setDefaultValue(strProdName, "");
	}

	public String getDblOpeningStk() {
		return dblOpeningStk;
	}

	public void setDblOpeningStk(String dblOpeningStk) {
		this.dblOpeningStk = (String) setDefaultValue(dblOpeningStk, "");
	}

	public String getDblMfgQTY() {
		return dblMfgQTY;
	}

	public void setDblMfgQTY(String dblMfgQTY) {
		this.dblMfgQTY = (String) setDefaultValue(dblMfgQTY, "");
	}

	public String getDblTotalQty() {
		return dblTotalQty;
	}

	public void setDblTotalQty(String dblTotalQty) {
		this.dblTotalQty = (String) setDefaultValue(dblTotalQty, "");
	}

	public String getDblInvQty() {
		return dblInvQty;
	}

	public void setDblInvQty(String dblInvQty) {
		this.dblInvQty = (String) setDefaultValue(dblInvQty, "");
	}

	public String getDblAssValue() {
		return dblAssValue;
	}

	public void setDblAssValue(String dblAssValue) {
		this.dblAssValue = (String) setDefaultValue(dblAssValue, "");
	}

	public String getDblExportQty() {
		return dblExportQty;
	}

	public void setDblExportQty(String dblExportQty) {
		this.dblExportQty = (String) setDefaultValue(dblExportQty, "");
	}

	public String getDblExportValue() {
		return dblExportValue;
	}

	public void setDblExportValue(String dblExportValue) {
		this.dblExportValue = (String) setDefaultValue(dblExportValue, "");
	}

	public String getDblExportQtyBound() {
		return dblExportQtyBound;
	}

	public void setDblExportQtyBound(String dblExportQtyBound) {
		this.dblExportQtyBound = (String) setDefaultValue(dblExportQtyBound, "");
	}

	public String getDblExportBoundValue() {
		return dblExportBoundValue;
	}

	public void setDblExportBoundValue(String dblExportBoundValue) {
		this.dblExportBoundValue = (String) setDefaultValue(dblExportBoundValue, "");
	}

	public String getDblExportQtyFactoryBound() {
		return dblExportQtyFactoryBound;
	}

	public void setDblExportQtyFactoryBound(String dblExportQtyFactoryBound) {
		this.dblExportQtyFactoryBound = (String) setDefaultValue(dblExportQtyFactoryBound, "");
	}

	public String getDblExportFactoryBoundValue() {
		return dblExportFactoryBoundValue;
	}

	public void setDblExportFactoryBoundValue(String dblExportFactoryBoundValue) {
		this.dblExportFactoryBoundValue = (String) setDefaultValue(dblExportFactoryBoundValue, "");
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = (String) setDefaultValue(purpose, "");
	}

	public String getDblpurposeQty() {
		return dblpurposeQty;
	}

	public void setDblpurposeQty(String dblpurposeQty) {
		this.dblpurposeQty = (String) setDefaultValue(dblpurposeQty, "");
	}

	public String getExTaxPer() {
		return exTaxPer;
	}

	public void setExTaxPer(String exTaxPer) {
		this.exTaxPer = (String) setDefaultValue(exTaxPer, "");
	}

	public String getDblExTaxAmt() {
		return dblExTaxAmt;
	}

	public void setDblExTaxAmt(String dblExTaxAmt) {
		this.dblExTaxAmt = (String) setDefaultValue(dblExTaxAmt, "");
	}

	public String getDblclosingBalance() {
		return dblclosingBalance;
	}

	public void setDblclosingBalance(String dblclosingBalance) {
		this.dblclosingBalance = (String) setDefaultValue(dblclosingBalance, "");
	}

	public String getDblloseQty() {
		return dblloseQty;
	}

	public void setDblloseQty(String dblloseQty) {
		this.dblloseQty = (String) setDefaultValue(dblloseQty, "");
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = (String) setDefaultValue(voucherNo, "");
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public String getDateInvRemark() {
		return dateInvRemark;
	}

	public void setDateInvRemark(String dateInvRemark) {
		this.dateInvRemark = (String) setDefaultValue(dateInvRemark, "");
	}

	public String getChaptorNo() {
		return chaptorNo;
	}

	public void setChaptorNo(String chaptorNo) {
		this.chaptorNo = chaptorNo;
	}

}
