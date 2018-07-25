package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSItemModifierMasterBean {
	// Variable Declaration
	private String strItemCode;
	private String strItemName;

	private String strModifierCode;

	private String strModifierName;
	private String strModifierGroup;
	private String strModifierDescription;

	private String strChargable;

	private double dblRate;

	private String strApplicable;

	private String strDeselectAll;

	private String strSelectAll;

	private String strMenuCode;
	private String strMenuName;

	private List<clsPOSMenuItemMasterBean> listObjItemBean;

	// Setter-Getter Methods
	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public String getStrModifierCode() {
		return strModifierCode;
	}

	public void setStrModifierCode(String strModifierCode) {
		this.strModifierCode = strModifierCode;
	}

	public String getStrChargable() {
		return strChargable;
	}

	public void setStrChargable(String strChargable) {
		this.strChargable = strChargable;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public String getStrApplicable() {
		return strApplicable;
	}

	public void setStrApplicable(String strApplicable) {
		this.strApplicable = strApplicable;
	}

	public String getStrModifierName() {
		return strModifierName;
	}

	public void setStrModifierName(String strModifierName) {
		this.strModifierName = strModifierName;
	}

	public String getStrModifierGroup() {
		return strModifierGroup;
	}

	public void setStrModifierGroup(String strModifierGroup) {
		this.strModifierGroup = strModifierGroup;
	}

	public String getStrModifierDescription() {
		return strModifierDescription;
	}

	public void setStrModifierDescription(String strModifierDescription) {
		this.strModifierDescription = strModifierDescription;
	}

	public String getStrDeselectAll() {
		return strDeselectAll;
	}

	public void setStrDeselectAll(String strDeselectAll) {
		this.strDeselectAll = strDeselectAll;
	}

	public String getStrSelectAll() {
		return strSelectAll;
	}

	public void setStrSelectAll(String strSelectAll) {
		this.strSelectAll = strSelectAll;
	}

	public String getStrMenuCode() {
		return strMenuCode;
	}

	public void setStrMenuCode(String strMenuCode) {
		this.strMenuCode = strMenuCode;
	}

	public String getStrMenuName() {
		return strMenuName;
	}

	public void setStrMenuName(String strMenuName) {
		this.strMenuName = strMenuName;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public List<clsPOSMenuItemMasterBean> getListObjItemBean() {
		return listObjItemBean;
	}

	public void setListObjItemBean(List<clsPOSMenuItemMasterBean> listObjItemBean) {
		this.listObjItemBean = listObjItemBean;
	}

}
