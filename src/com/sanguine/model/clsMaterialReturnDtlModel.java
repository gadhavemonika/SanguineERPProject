package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblmaterialreturndtl")
public class clsMaterialReturnDtlModel {
	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strMRetCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strMRetCode;

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strProdCode;

	@Column(name = "strPartNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPartNo;

	@Column(name = "strProdName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strProdName;

	@Column(name = "strRemarks", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strRemarks;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQty;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrMRetCode() {
		return strMRetCode;
	}

	public void setStrMRetCode(String strMRetCode) {
		this.strMRetCode = strMRetCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}
}
