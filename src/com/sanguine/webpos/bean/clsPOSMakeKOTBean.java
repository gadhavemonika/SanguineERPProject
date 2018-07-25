package com.sanguine.webpos.bean;

import java.util.List;

import org.json.simple.JSONArray;

public class clsPOSMakeKOTBean {

	private String strKOTNo;

	private String strTableNo;

	private String strWaiter;

	private int intPaxNo;

	private double strDeditCardBalance;

	private double total;

	private JSONArray jsonArrForMenuItemPricing;

	private JSONArray jsonArrForMenuHeads;

	private JSONArray jsonArrForPopularItems;

	private JSONArray jsonArrForTableDtl;

	private JSONArray jsonArrForWaiterDtl;

	private JSONArray jsonArrForButtonList;

	private JSONArray jsonArrForTopModifierButton;

	private JSONArray jsonArrForModifiers;

	private List<clsMakeKotBillItemDtlBean> listOfMakeKOTBillItemDtl;

	public JSONArray getJsonArrForMenuItemPricing() {
		return jsonArrForMenuItemPricing;
	}

	public void setJsonArrForMenuItemPricing(JSONArray jsonArrForMenuItemPricing) {
		this.jsonArrForMenuItemPricing = jsonArrForMenuItemPricing;
	}

	public JSONArray getJsonArrForMenuHeads() {
		return jsonArrForMenuHeads;
	}

	public void setJsonArrForMenuHeads(JSONArray jsonArrForMenuHeads) {
		this.jsonArrForMenuHeads = jsonArrForMenuHeads;
	}

	public JSONArray getJsonArrForPopularItems() {
		return jsonArrForPopularItems;
	}

	public void setJsonArrForPopularItems(JSONArray jsonArrForPopularItems) {
		this.jsonArrForPopularItems = jsonArrForPopularItems;
	}

	public JSONArray getJsonArrForTableDtl() {
		return jsonArrForTableDtl;
	}

	public void setJsonArrForTableDtl(JSONArray jsonArrForTableDtl) {
		this.jsonArrForTableDtl = jsonArrForTableDtl;
	}

	public JSONArray getJsonArrForWaiterDtl() {
		return jsonArrForWaiterDtl;
	}

	public void setJsonArrForWaiterDtl(JSONArray jsonArrForWaiterDtl) {
		this.jsonArrForWaiterDtl = jsonArrForWaiterDtl;
	}

	public String getStrKOTNo() {
		return strKOTNo;
	}

	public void setStrKOTNo(String strKOTNo) {
		this.strKOTNo = strKOTNo;
	}

	public String getStrTableNo() {
		return strTableNo;
	}

	public void setStrTableNo(String strTableNo) {
		this.strTableNo = strTableNo;
	}

	public String getStrWaiter() {
		return strWaiter;
	}

	public void setStrWaiter(String strWaiter) {
		this.strWaiter = strWaiter;
	}

	public int getIntPaxNo() {
		return intPaxNo;
	}

	public void setIntPaxNo(int intPaxNo) {
		this.intPaxNo = intPaxNo;
	}

	public List<clsMakeKotBillItemDtlBean> getListOfMakeKOTBillItemDtl() {
		return listOfMakeKOTBillItemDtl;
	}

	public void setListOfMakeKOTBillItemDtl(List<clsMakeKotBillItemDtlBean> listOfMakeKOTBillItemDtl) {
		this.listOfMakeKOTBillItemDtl = listOfMakeKOTBillItemDtl;
	}

	public double getStrDeditCardBalance() {
		return strDeditCardBalance;
	}

	public void setStrDeditCardBalance(double strDeditCardBalance) {
		this.strDeditCardBalance = strDeditCardBalance;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public JSONArray getJsonArrForButtonList() {
		return jsonArrForButtonList;
	}

	public void setJsonArrForButtonList(JSONArray jsonArrForButtonList) {
		this.jsonArrForButtonList = jsonArrForButtonList;
	}

	public JSONArray getJsonArrForTopModifierButton() {
		return jsonArrForTopModifierButton;
	}

	public void setJsonArrForTopModifierButton(JSONArray jsonArrForTopModifierButton) {
		this.jsonArrForTopModifierButton = jsonArrForTopModifierButton;
	}

	public JSONArray getJsonArrForModifiers() {
		return jsonArrForModifiers;
	}

	public void setJsonArrForModifiers(JSONArray jsonArrForModifiers) {
		this.jsonArrForModifiers = jsonArrForModifiers;
	}

}
