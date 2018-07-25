package com.sanguine.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class clsNonStkProductModel implements Serializable {

	private String strReqCode;
	private String strGRNCode;
	private String dtReqDate;
	private String strProdCode;
	private String strProdName;
	private String strFromLocName;
	private String strFromLocCode;
	private String strToLocName;
	private String strToLocCode;
	private String strRemarks;
	private double dblQty;
	private double dblCostRM;
	private double dblPrice;
	private double dblSubTotal;
	private double dblGRNQty;

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getDtReqDate() {
		return dtReqDate;
	}

	public void setDtReqDate(String dtReqDate) {
		this.dtReqDate = dtReqDate;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrFromLocName() {
		return strFromLocName;
	}

	public void setStrFromLocName(String strFromLocName) {
		this.strFromLocName = strFromLocName;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrToLocName() {
		return strToLocName;
	}

	public void setStrToLocName(String strToLocName) {
		this.strToLocName = strToLocName;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public double getDblCostRM() {
		return dblCostRM;
	}

	public void setDblCostRM(double dblCostRM) {
		this.dblCostRM = dblCostRM;
	}

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = dblPrice;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public double getDblGRNQty() {
		return dblGRNQty;
	}

	public void setDblGRNQty(double dblGRNQty) {
		this.dblGRNQty = dblGRNQty;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}
}
