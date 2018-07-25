package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsPOSSalesDtlModel;

public class clsPOSSalesDtlBean {
	// Variable Declaration
	private String strPOSItemCode;

	private String strPOSItemName;

	private double dblQuantity;

	private double dblRate;

	private String strPOSCode;

	private String dteBillDate;

	private String strClientCode;

	private String strWSItemCode;

	private String strSACode;

	private String strLocCode;

	private String dtePostSADate;

	private List<clsPOSSalesDtlModel> listPOSSalesDtl;

	// Setter-Getter Methods
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

	public double getDblQuantity() {
		return dblQuantity;
	}

	public void setDblQuantity(double dblQuantity) {
		this.dblQuantity = dblQuantity;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrWSItemCode() {
		return strWSItemCode;
	}

	public void setStrWSItemCode(String strWSItemCode) {
		this.strWSItemCode = strWSItemCode;
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public List<clsPOSSalesDtlModel> getListPOSSalesDtl() {
		return listPOSSalesDtl;
	}

	public void setListPOSSalesDtl(List<clsPOSSalesDtlModel> listPOSSalesDtl) {
		this.listPOSSalesDtl = listPOSSalesDtl;
	}

	public String getDtePostSADate() {
		return dtePostSADate;
	}

	public void setDtePostSADate(String dtePostSADate) {
		this.dtePostSADate = dtePostSADate;
	}

}
