package com.sanguine.bean;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsStockFlashModel;

public class clsStockFlashBean {
	private String strPropertyCode;

	private String strLocationCode;

	private String dteFromDate;

	private String dteToDate;

	private String strExportType;

	private String strReportType;

	private List<clsStockFlashModel> listStockFlashModel;

	Map<String, String> group;

	Map<String, String> subGroup;

	private String strGCode;

	private String strSGCode;

	private String strProdType;

	private String strUserCode;

	private String strTransaction;

	private String strManufacturerCode;
	
	private String strCurrency;

	public String getStrTransaction() {
		return strTransaction;
	}

	public void setStrTransaction(String strTransaction) {
		this.strTransaction = strTransaction;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
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

	public String getStrExportType() {
		return strExportType;
	}

	public void setStrExportType(String strExportType) {
		this.strExportType = strExportType;
	}

	public List<clsStockFlashModel> getListStockFlashModel() {
		return listStockFlashModel;
	}

	public void setListStockFlashModel(List<clsStockFlashModel> listStockFlashModel) {
		this.listStockFlashModel = listStockFlashModel;
	}

	public String getStrReportType() {
		return strReportType;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public Map<String, String> getGroup() {
		return group;
	}

	public void setGroup(Map<String, String> group) {
		this.group = group;
	}

	public Map<String, String> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(Map<String, String> subGroup) {
		this.subGroup = subGroup;
	}

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrManufacturerCode() {
		return strManufacturerCode;
	}

	public void setStrManufacturerCode(String strManufacturerCode) {
		this.strManufacturerCode = strManufacturerCode;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}
}
