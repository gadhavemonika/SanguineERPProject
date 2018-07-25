package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class clsPurchaseIndentDtlModel {
	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double dblQty;

	@Column(name = "dblRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double dblRate;

	@Column(name = "dblAmount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double dblAmount;

	@Column(name = "strPurpose")
	private String strPurpose;

	@Column(name = "dtReqDate")
	private String dtReqDate;

	@Column(name = "strInStock", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double strInStock;

	@Column(name = "strAgainst", nullable = true)
	private String strAgainst;

	@Column(name = "intIndex")
	private int intIndex;

	@Column(name = "dblReOrderQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double dblReOrderQty;

	@Transient
	private double dblMinLevel;
	@Transient
	private String strChecked;

	@Transient
	private String strDocCode;

	@Transient
	private String strDocType;

	public String getStrDocType() {
		return strDocType;
	}

	public void setStrDocType(String strDocType) {
		this.strDocType = strDocType;
	}

	public String getStrDocCode() {
		return strDocCode;
	}

	public void setStrDocCode(String strDocCode) {
		this.strDocCode = strDocCode;
	}

	public double getDblMinLevel() {
		return dblMinLevel;
	}

	public void setDblMinLevel(double dblMinLevel) {
		this.dblMinLevel = dblMinLevel;
	}

	@Transient
	private String strProdName;

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrPurpose() {
		return strPurpose;
	}

	public void setStrPurpose(String strPurpose) {
		this.strPurpose = strPurpose;
	}

	public String getDtReqDate() {
		return dtReqDate;
	}

	public void setDtReqDate(String dtReqDate) {
		this.dtReqDate = dtReqDate;
	}

	public double getStrInStock() {
		return strInStock;
	}

	public void setStrInStock(double strInStock) {
		this.strInStock = strInStock;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public double getDblReOrderQty() {
		return dblReOrderQty;
	}

	public void setDblReOrderQty(double dblReOrderQty) {
		this.dblReOrderQty = dblReOrderQty;
	}

	public String getStrChecked() {
		return strChecked;
	}

	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}
}
