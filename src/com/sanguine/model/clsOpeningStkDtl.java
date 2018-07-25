package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblinitialinvdtl")
public class clsOpeningStkDtl {
	@Id
	@GeneratedValue
	@Column(name = "intId", updatable = false)
	private long intId;

	@Column(name = "strOpStkCode")
	private String strOpStkCode;

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strProdCode;

	@Column(name = "strUOM", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUOM;

	@Column(name = "strLotNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLotNo;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQty;

	@Column(name = "dblCostPerUnit", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblCostPUnit;

	@Column(name = "dblRevLvl", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRevLvl;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "dblLooseQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblLooseQty;

	@Column(name = "strDisplyQty", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strDisplyQty;

	@Transient
	private String strProdName;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

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

	public String getStrLotNo() {
		return strLotNo;
	}

	public void setStrLotNo(String strLotNo) {
		this.strLotNo = strLotNo;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblCostPUnit() {
		return dblCostPUnit;
	}

	public void setDblCostPUnit(double dblCostPUnit) {
		this.dblCostPUnit = dblCostPUnit;
	}

	public double getDblRevLvl() {
		return dblRevLvl;
	}

	public void setDblRevLvl(double dblRevLvl) {
		this.dblRevLvl = dblRevLvl;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrOpStkCode() {
		return strOpStkCode;
	}

	public void setStrOpStkCode(String strOpStkCode) {
		this.strOpStkCode = strOpStkCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
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
}
