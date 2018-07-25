package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblstockpostingdtl")
public class clsStkPostingDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strPSCode")
	private String strPSCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblCStock", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000' ")
	private double dblCStock;

	@Column(name = "dblPStock", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000' ")
	private double dblPStock;

	@Column(name = "dblPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000' ")
	private double dblPrice;

	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000' ")
	private double dblWeight;

	@Column(name = "strProdChar")
	private String strProdChar;

	@Column(name = "intIndex")
	private int intIndex;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Transient
	private String strProdName;

	@Column(name = "dblVariance", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000' ")
	private double dblVariance;

	@Transient
	private double dblAdjValue;

	@Transient
	private double dblAdjWt;

	@Column(name = "dblLooseQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblLooseQty;

	@Column(name = "strDisplyQty", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strDisplyQty;

	@Column(name = "strDisplyVariance", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strDisplyVariance;

	@Column(name = "dblActualRate", columnDefinition = "DECIMAL(18,2) NOT NULL default '0.00'")
	private double dblActualRate;

	@Transient
	private double dblActualValue;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrPSCode() {
		return strPSCode;
	}

	public void setStrPSCode(String strPSCode) {
		this.strPSCode = strPSCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblCStock() {
		return dblCStock;
	}

	public void setDblCStock(double dblCStock) {
		this.dblCStock = dblCStock;
	}

	public double getDblPStock() {
		return dblPStock;
	}

	public void setDblPStock(double dblPStock) {
		this.dblPStock = dblPStock;
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

	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblVariance() {
		return dblVariance;
	}

	public void setDblVariance(double dblVariance) {
		this.dblVariance = dblVariance;
	}

	public double getDblAdjValue() {
		return dblAdjValue;
	}

	public void setDblAdjValue(double dblAdjValue) {
		this.dblAdjValue = dblAdjValue;
	}

	public double getDblAdjWt() {
		return dblAdjWt;
	}

	public void setDblAdjWt(double dblAdjWt) {
		this.dblAdjWt = dblAdjWt;
	}

	public double getDblLooseQty() {
		return dblLooseQty;
	}

	public void setDblLooseQty(double dblLooseQty) {
		this.dblLooseQty = dblLooseQty;
	}

	public String getStrDisplyQty() {
		return strDisplyQty;
	}

	public void setStrDisplyQty(String strDisplyQty) {
		this.strDisplyQty = strDisplyQty;
	}

	public String getStrDisplyVariance() {
		return strDisplyVariance;
	}

	public void setStrDisplyVariance(String strDisplyVariance) {
		this.strDisplyVariance = strDisplyVariance;
	}

	public double getDblActualRate() {
		return dblActualRate;
	}

	public void setDblActualRate(double dblActualRate) {
		this.dblActualRate = dblActualRate;
	}

	public double getDblActualValue() {
		return dblActualValue;
	}

	public void setDblActualValue(double dblActualValue) {
		this.dblActualValue = dblActualValue;
	}
}
