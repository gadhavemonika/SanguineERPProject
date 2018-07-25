package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblproductiondtl")
public class clsProductionDtlModel {

	private String strPDCode, strProdCode, strPartNo, strProdName, strProcessCode, strProcessName, strProdChar, strClientCode, strUOM;
	// private String strMacCode,strStaffName;
	private long intid;
	private double dblQtyProd, dblQtyRej, dblWeight, dblPrice, dblActTime;

	@Transient
	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	@Transient
	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	@Column(name = "strPDCode")
	public String getStrPDCode() {
		return strPDCode;
	}

	public void setStrPDCode(String strPDCode) {
		this.strPDCode = strPDCode;
	}

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	@Transient
	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	@Column(name = "strProcessCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	@Transient
	@Column(name = "strProdChar", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	// @Column(name="strMacCode")
	// public String getStrMacCode() {
	// return strMacCode;
	// }
	// public void setStrMacCode(String strMacCode) {
	// this.strMacCode = strMacCode;
	// }
	// @Column(name="strStaffName")
	// public String getStrStaffName() {
	// return strStaffName;
	// }
	// public void setStrStaffName(String strStaffName) {
	// this.strStaffName = strStaffName;
	// }
	@Id
	@Column(name = "intid")
	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	@Column(name = "dblQtyProd", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblQtyProd() {
		return dblQtyProd;
	}

	public void setDblQtyProd(double dblQtyProd) {
		this.dblQtyProd = dblQtyProd;
	}

	@Column(name = "dblQtyRej", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblQtyRej() {
		return dblQtyRej;
	}

	public void setDblQtyRej(double dblQtyRej) {
		this.dblQtyRej = dblQtyRej;
	}

	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	@Column(name = "dblPrice", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = dblPrice;
	}

	@Column(name = "dblActTime", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	public double getDblActTime() {
		return dblActTime;
	}

	public void setDblActTime(double dblActTime) {
		this.dblActTime = dblActTime;
	}

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
