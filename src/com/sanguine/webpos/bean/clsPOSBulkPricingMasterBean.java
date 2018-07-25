package com.sanguine.webpos.bean;

import java.util.List;
import java.util.ArrayList;

public class clsPOSBulkPricingMasterBean {

	private String strPOSName;

	private String strArea;

	private String strCostCenter;

	private String strMenuHead;

	private String strSortBy;

	private String strExpiredItem;

	private List<clasPOSBulkItemPricingTableDataBean> listdata;

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = strPOSName;
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = strArea;
	}

	public String getStrCostCenter() {
		return strCostCenter;
	}

	public void setStrCostCenter(String strCostCenter) {
		this.strCostCenter = strCostCenter;
	}

	public String getStrMenuHead() {
		return strMenuHead;
	}

	public void setStrMenuHead(String strMenuHead) {
		this.strMenuHead = strMenuHead;
	}

	public String getStrSortBy() {
		return strSortBy;
	}

	public void setStrSortBy(String strSortBy) {
		this.strSortBy = strSortBy;
	}

	public String getStrExpiredItem() {
		return strExpiredItem;
	}

	public void setStrExpiredItem(String strExpiredItem) {
		this.strExpiredItem = strExpiredItem;
	}

	public List<clasPOSBulkItemPricingTableDataBean> getListdata() {
		return listdata;
	}

	public void setListdata(List<clasPOSBulkItemPricingTableDataBean> listdata) {
		this.listdata = listdata;
	}

}
