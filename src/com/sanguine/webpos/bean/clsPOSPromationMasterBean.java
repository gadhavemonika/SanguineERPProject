package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSPromationMasterBean {

	private String strPromoCode;

	private String strPromoName;

	private String strPromotionOn;

	private String strPromoItemCode;

	private String strPromoItemName;

	private String strOperator;

	private double dblBuyQty;

	private double dblGetQty;

	private String dteFromDate;

	private String dteToDate;

	private String strType;

	private String strPromoNote;

	private String strPOSCode;

	private String strGetItemCode;

	private String strGetItemName;

	private String strGetPromoOn;

	private double dblDiscount;

	private String strDiscountType;

	private String strAreaCode;

	public String getStrAreaCode() {
		return strAreaCode;
	}

	public void setStrAreaCode(String strAreaCode) {
		this.strAreaCode = strAreaCode;
	}

	private List<clsPromotionDtlBean> listBuyPromotionDtl;

	private List<clsPromotionDtlBean> listGetPromotionDtl;

	private List<clsPromotionDayTimeDtlBean> listPromotionDayTimeDtl;

	public String getStrPromoCode() {
		return strPromoCode;
	}

	public void setStrPromoCode(String strPromoCode) {
		this.strPromoCode = strPromoCode;
	}

	public String getStrPromoName() {
		return strPromoName;
	}

	public void setStrPromoName(String strPromoName) {
		this.strPromoName = strPromoName;
	}

	public String getStrPromotionOn() {
		return strPromotionOn;
	}

	public void setStrPromotionOn(String strPromotionOn) {
		this.strPromotionOn = strPromotionOn;
	}

	public String getStrPromoItemCode() {
		return strPromoItemCode;
	}

	public void setStrPromoItemCode(String strPromoItemCode) {
		this.strPromoItemCode = strPromoItemCode;
	}

	public String getStrOperator() {
		return strOperator;
	}

	public void setStrOperator(String strOperator) {
		this.strOperator = strOperator;
	}

	public double getDblBuyQty() {
		return dblBuyQty;
	}

	public void setDblBuyQty(double dblBuyQty) {
		this.dblBuyQty = dblBuyQty;
	}

	public double getDblGetQty() {
		return dblGetQty;
	}

	public void setDblGetQty(double dblGetQty) {
		this.dblGetQty = dblGetQty;
	}

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrPromoNote() {
		return strPromoNote;
	}

	public void setStrPromoNote(String strPromoNote) {
		this.strPromoNote = strPromoNote;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getStrGetItemCode() {
		return strGetItemCode;
	}

	public void setStrGetItemCode(String strGetItemCode) {
		this.strGetItemCode = strGetItemCode;
	}

	public String getStrGetPromoOn() {
		return strGetPromoOn;
	}

	public void setStrGetPromoOn(String strGetPromoOn) {
		this.strGetPromoOn = strGetPromoOn;
	}

	public List<clsPromotionDtlBean> getListBuyPromotionDtl() {
		return listBuyPromotionDtl;
	}

	public void setListBuyPromotionDtl(List<clsPromotionDtlBean> listBuyPromotionDtl) {
		this.listBuyPromotionDtl = listBuyPromotionDtl;
	}

	public List<clsPromotionDtlBean> getListGetPromotionDtl() {
		return listGetPromotionDtl;
	}

	public void setListGetPromotionDtl(List<clsPromotionDtlBean> listGetPromotionDtl) {
		this.listGetPromotionDtl = listGetPromotionDtl;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	public String getStrDiscountType() {
		return strDiscountType;
	}

	public void setStrDiscountType(String strDiscountType) {
		this.strDiscountType = strDiscountType;
	}

	public List<clsPromotionDayTimeDtlBean> getListPromotionDayTimeDtl() {
		return listPromotionDayTimeDtl;
	}

	public void setListPromotionDayTimeDtl(List<clsPromotionDayTimeDtlBean> listPromotionDayTimeDtl) {
		this.listPromotionDayTimeDtl = listPromotionDayTimeDtl;
	}

	public String getStrPromoItemName() {
		return strPromoItemName;
	}

	public void setStrPromoItemName(String strPromoItemName) {
		this.strPromoItemName = strPromoItemName;
	}

	public String getStrGetItemName() {
		return strGetItemName;
	}

	public void setStrGetItemName(String strGetItemName) {
		this.strGetItemName = strGetItemName;
	}

}
