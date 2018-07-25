package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblpurchasereturndtl")
public class clsPurchaseReturnDtlModel {
	private String strPRCode, strProdCode, strProdName, strProdChar, strClientCode;
	private double dblQty, dblDiscount, dblUnitPrice, dblWeight;
	private long intid;
	@Column(name = "strUOM")
	private String strUOM;

	@Column(name = "dblTotalPrice")
	private double dblTotalPrice;

	@Column(name = "dblTotalWt")
	private double dblTotalWt;

	@Column(name = "strPRCode")
	public String getStrPRCode() {
		return strPRCode;
	}

	public void setStrPRCode(String strPRCode) {
		this.strPRCode = strPRCode;
	}

	@Transient
	private String strExpiry;

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

	@Column(name = "strProdChar")
	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	@Column(name = "strClientCode")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "dblQty")
	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	@Column(name = "dblDiscount")
	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	@Column(name = "dblUnitPrice")
	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	@Column(name = "dblWeight")
	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	@GeneratedValue
	@Id
	@Column(name = "intid")
	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public double getDblTotalWt() {
		return dblTotalWt;
	}

	public void setDblTotalWt(double dblTotalWt) {
		this.dblTotalWt = dblTotalWt;
	}

	public String getStrExpiry() {
		return strExpiry;
	}

	public void setStrExpiry(String strExpiry) {
		this.strExpiry = strExpiry;
	}
}
