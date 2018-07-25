package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class clsDeliveryScheduleModuledtl implements Serializable {

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "strUOM")
	private String strUOM;

	@Column(name = "dblUnitPrice")
	private double dblUnitPrice;

	@Column(name = "dblTotalPrice")
	private double dblTotalPrice;

	@Column(name = "dblWeight")
	private double dblWeight;

	@Transient
	private String strProdName;

	@Transient
	private String strRemarks;

	@Transient
	private String strLocName;

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

}
