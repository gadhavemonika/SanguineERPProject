package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSPhysicalStockPostingBean {
	private String strPSPCode;
	private String strItemCode;
	private String strItemName;
	private String strExternalCode;
	private String dblQty;
	private String strType;
	private String strSaleAmt;
	private String strStockOutAmt;
	private String strReason;
	private String strRemark;
	private String dblStock;
	private String dblPurchaseRate;
	private String dblSaleRate;
	private String strBillNo;
	private String strViewBillNo;
	private String strStockinCode;
	private String strViewStockinCode;
	private String strPOSCode;
	private String strPOSName;

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

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrViewBillNo() {
		return strViewBillNo;
	}

	public void setStrViewBillNo(String strViewBillNo) {
		this.strViewBillNo = strViewBillNo;
	}

	public String getStrStockinCode() {
		return strStockinCode;
	}

	public void setStrStockinCode(String strStockinCode) {
		this.strStockinCode = strStockinCode;
	}

	public String getStrViewStockinCode() {
		return strViewStockinCode;
	}

	public void setStrViewStockinCode(String strViewStockinCode) {
		this.strViewStockinCode = strViewStockinCode;
	}

	public String getDblStock() {
		return dblStock;
	}

	public void setDblStock(String dblStock) {
		this.dblStock = dblStock;
	}

	public String getDblPurchaseRate() {
		return dblPurchaseRate;
	}

	public void setDblPurchaseRate(String dblPurchaseRate) {
		this.dblPurchaseRate = dblPurchaseRate;
	}

	public String getDblSaleRate() {
		return dblSaleRate;
	}

	public void setDblSaleRate(String dblSaleRate) {
		this.dblSaleRate = dblSaleRate;
	}

	public String getStrReason() {
		return strReason;
	}

	public void setStrReason(String strReason) {
		this.strReason = strReason;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	private List<clsPOSPSPDtl> listPSPDtl;

	public String getStrItemCode() {
		return strItemCode;
	}

	public List<clsPOSPSPDtl> getListPSPDtl() {
		return listPSPDtl;
	}

	public void setListPSPDtl(List<clsPOSPSPDtl> listPSPDtl) {
		this.listPSPDtl = listPSPDtl;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public String getStrSaleAmt() {
		return strSaleAmt;
	}

	public void setStrSaleAmt(String strSaleAmt) {
		this.strSaleAmt = strSaleAmt;
	}

	public String getStrStockOutAmt() {
		return strStockOutAmt;
	}

	public void setStrStockOutAmt(String strStockOutAmt) {
		this.strStockOutAmt = strStockOutAmt;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrPSPCode() {
		return strPSPCode;
	}

	public void setStrPSPCode(String strPSPCode) {
		this.strPSPCode = strPSPCode;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = strExternalCode;
	}

	public String getDblQty() {
		return dblQty;
	}

	public void setDblQty(String dblQty) {
		this.dblQty = dblQty;
	}

}
