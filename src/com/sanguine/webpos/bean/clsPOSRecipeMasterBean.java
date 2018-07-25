package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSRecipeMasterBean {
	private String strRecipeCode;

	private String strItemCode;

	private String strItemName;

	private String dteFromDate;

	private String dteToDate;

	private List<clsChildMenuItemBean> listChildItemDtl;

	public String getStrRecipeCode() {
		return strRecipeCode;
	}

	public void setStrRecipeCode(String strRecipeCode) {
		this.strRecipeCode = strRecipeCode;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
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

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public List<clsChildMenuItemBean> getListChildItemDtl() {
		return listChildItemDtl;
	}

	public void setListChildItemDtl(List<clsChildMenuItemBean> listChildItemDtl) {
		this.listChildItemDtl = listChildItemDtl;
	}

}
