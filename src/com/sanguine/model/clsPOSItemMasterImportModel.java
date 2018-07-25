package com.sanguine.model;

import javax.persistence.Transient;

public class clsPOSItemMasterImportModel {

	@Transient
	private String strPOSItemCode;

	@Transient
	private String strPOSItemName;

	@Transient
	private String strWSItemCode;

	@Transient
	private String strWSItemName;

	@Transient
	private String strProdType;

	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = strPOSItemName;
	}

	public String getStrWSItemCode() {
		return strWSItemCode;
	}

	public void setStrWSItemCode(String strWSItemCode) {
		this.strWSItemCode = strWSItemCode;
	}

	public String getStrWSItemName() {
		return strWSItemName;
	}

	public void setStrWSItemName(String strWSItemName) {
		this.strWSItemName = strWSItemName;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

}
