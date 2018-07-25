package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class clsMISDtlModel {

	private String strProdCode, strPartNo, strProdName, strRemarks, strReqCode, strUOM, strExpiry;

	private double dblQty, dblUnitPrice, dblTotalPrice, dblStock;

	@Transient
	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	@Transient
	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	@Column(name = "strProdCode")
	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	@Column(name = "strRemarks", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	@Column(name = "dblUnitPrice", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	@Column(name = "dblTotalPrice", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	@Column(name = "strReqCode")
	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	@Transient
	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	@Transient
	public double getDblStock() {
		return dblStock;
	}

	public void setDblStock(double dblStock) {
		this.dblStock = dblStock;
	}

	@Transient
	public String getStrExpiry() {
		return strExpiry;
	}

	public void setStrExpiry(String strExpiry) {
		this.strExpiry = strExpiry;
	}

}
