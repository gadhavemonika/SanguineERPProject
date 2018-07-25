package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSStockInOutHd {
	private String strStkInCode;
	private String strStkOutCode;
	private String strItemName;
	private String strItemCode;
	private String strExternalCode;
	private String dblQty;
	private String strInvoiceCode;
	private String strPOSCode;
	private String dteStkInDate;
	private String dteStkOutDate;
	private String strReasonCode;

	public String getStrStkOutCode() {
		return strStkOutCode;
	}

	public void setStrStkOutCode(String strStkOutCode) {
		this.strStkOutCode = strStkOutCode;
	}

	public String getDteStkOutDate() {
		return dteStkOutDate;
	}

	public void setDteStkOutDate(String dteStkOutDate) {
		this.dteStkOutDate = dteStkOutDate;
	}

	private String strPurchaseBillNo;
	private String dtePurchaseBillDate;
	private int intShiftCode;
	private String strUserCreated;
	private String strUserEdited;
	private String dteDateCreated;
	private String dteDateEdited;
	private String strClientCode;
	private String strDataPostFlag;
	private String strTotalQty;
	private String strTotalTax;
	private String strTotalAmount;
	List<clsPOSStockInOutDtl> listOfItem;

	public List<clsPOSStockInOutDtl> getListOfItem() {
		return listOfItem;
	}

	public void setListOfItem(List<clsPOSStockInOutDtl> listOfItem) {
		this.listOfItem = listOfItem;
	}

	public String getStrTotalAmount() {
		return strTotalAmount;
	}

	public void setStrTotalAmount(String strTotalAmount) {
		this.strTotalAmount = strTotalAmount;
	}

	public String getStrTotalQty() {
		return strTotalQty;
	}

	public void setStrTotalQty(String strTotalQty) {
		this.strTotalQty = strTotalQty;
	}

	public String getStrTotalTax() {
		return strTotalTax;
	}

	public void setStrTotalTax(String strTotalTax) {
		this.strTotalTax = strTotalTax;
	}

	public String getStrStkInCode() {
		return strStkInCode;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
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

	public void setStrStkInCode(String strStkInCode) {
		this.strStkInCode = strStkInCode;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getDteStkInDate() {
		return dteStkInDate;
	}

	public void setDteStkInDate(String dteStkInDate) {
		this.dteStkInDate = dteStkInDate;
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
	}

	public String getStrPurchaseBillNo() {
		return strPurchaseBillNo;
	}

	public void setStrPurchaseBillNo(String strPurchaseBillNo) {
		this.strPurchaseBillNo = strPurchaseBillNo;
	}

	public String getDtePurchaseBillDate() {
		return dtePurchaseBillDate;
	}

	public void setDtePurchaseBillDate(String dtePurchaseBillDate) {
		this.dtePurchaseBillDate = dtePurchaseBillDate;
	}

	public int getIntShiftCode() {
		return intShiftCode;
	}

	public void setIntShiftCode(int intShiftCode) {
		this.intShiftCode = intShiftCode;
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

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = strDataPostFlag;
	}

	public String getStrInvoiceCode() {
		return strInvoiceCode;
	}

	public void setStrInvoiceCode(String strInvoiceCode) {
		this.strInvoiceCode = strInvoiceCode;
	}
}
