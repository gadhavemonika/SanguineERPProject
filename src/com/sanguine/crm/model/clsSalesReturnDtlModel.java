package com.sanguine.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblsalesreturndtl")
public class clsSalesReturnDtlModel {

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strSRCode")
	private String strSRCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "dblPrice")
	private double dblPrice;

	@Column(name = "dblWeight")
	private double dblWeight;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Transient
	private String strProdName;

	// public long getIntId() {
	// return intId;
	// }
	//
	// public void setIntId(long intId) {
	// this.intId = intId;
	// }

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

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = dblPrice;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = strSRCode;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

}
