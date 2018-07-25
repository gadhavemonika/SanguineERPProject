package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblAuditDtl")
public class clsAuditDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "intId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long intId;

	@Column(name = "strTransCode", columnDefinition = "VARCHAR(20) NOT NULL default '' ")
	private String strTransCode;

	@Column(name = "strProdCode", columnDefinition = "VARCHAR(20) NOT NULL default '' ")
	private String strProdCode;

	@Transient
	private String strProdName;

	@Column(name = "strUOM", nullable = true, columnDefinition = "VARCHAR(10) default '' ")
	private String strUOM;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0' ")
	private double dblQty;

	@Column(name = "dblUnitPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0' ")
	private double dblUnitPrice;

	@Column(name = "dblTotalPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0' ")
	private double dblTotalPrice;

	@Column(name = "strRemarks", nullable = true, columnDefinition = "VARCHAR(200)  default '' ")
	private String strRemarks;

	@Column(name = "dtReqDate")
	private String dtReqDate;

	@Column(name = "strAgainst", nullable = true, columnDefinition = "VARCHAR(10) default ' ' ")
	private String strAgainst;

	@Column(name = "strPICode", nullable = true, columnDefinition = "VARCHAR(20)  default ' ' ")
	private String strPICode;

	@Column(name = "dblDiscount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblDiscount;

	@Column(name = "strTaxType", nullable = true, columnDefinition = "VARCHAR(20)  default ' ' ")
	private String strTaxType;

	@Column(name = "dblRejected", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRejected;

	@Column(name = "dblTaxableAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTaxableAmt;

	@Column(name = "dblTax", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTax;

	@Column(name = "dblTaxAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTaxAmt;

	@Column(name = "dblDCQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDCQty;

	@Column(name = "dblDCWt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDCWt;

	@Column(name = "dblQtyRbl", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQtyRbl;

	@Column(name = "dblRework", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRework;

	@Column(name = "dblPackForw", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblPackForw;

	@Column(name = "strCode", nullable = true, columnDefinition = "VARCHAR(255) default ' ' ")
	private String strCode;

	@Column(name = "dblCStock", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblCStock;

	@Column(name = "dblReOrderQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double dblReOrderQty;

	@Column(name = "dblRevLvl", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRevLvl;

	@Column(name = "strLotNo", nullable = true, columnDefinition = "VARCHAR(255) default '' ")
	private String strLotNo;

	@Column(name = "strClientCode", nullable = true, columnDefinition = "VARCHAR(255) default ' ' ")
	private String strClientCode;

	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblWeight;

	@Column(name = "strUpdate", nullable = true, columnDefinition = "VARCHAR(5) default ' ' ")
	private String strUpdate;

	@Column(name = "strProdChar", nullable = true, columnDefinition = "VARCHAR(50) default ' ' ")
	private String strProdChar;

	@Column(name = "strProcessCode", nullable = true, columnDefinition = "VARCHAR(15) default ' ' ")
	private String strProcessCode;

	@Column(name = "dblPOWeight", nullable = true, columnDefinition = "DECIMAL(18,4)  default '0.0000'")
	private double dblPOWeight;

	@Column(name = "dblRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRate;

	@Column(name = "dblValue", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblValue;

	@Column(name = "strType", nullable = true, columnDefinition = "VARCHAR(3) default ' '")
	private String strType;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
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

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
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

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getDtReqDate() {
		return dtReqDate;
	}

	public void setDtReqDate(String dtReqDate) {
		this.dtReqDate = dtReqDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrPICode() {
		return strPICode;
	}

	public void setStrPICode(String strPICode) {
		this.strPICode = strPICode;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = strTaxType;
	}

	public double getDblRejected() {
		return dblRejected;
	}

	public void setDblRejected(double dblRejected) {
		this.dblRejected = dblRejected;
	}

	public double getDblTaxableAmt() {
		return dblTaxableAmt;
	}

	public void setDblTaxableAmt(double dblTaxableAmt) {
		this.dblTaxableAmt = dblTaxableAmt;
	}

	public double getDblTax() {
		return dblTax;
	}

	public void setDblTax(double dblTax) {
		this.dblTax = dblTax;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblDCQty() {
		return dblDCQty;
	}

	public void setDblDCQty(double dblDCQty) {
		this.dblDCQty = dblDCQty;
	}

	public double getDblQtyRbl() {
		return dblQtyRbl;
	}

	public void setDblQtyRbl(double dblQtyRbl) {
		this.dblQtyRbl = dblQtyRbl;
	}

	public double getDblRework() {
		return dblRework;
	}

	public void setDblRework(double dblRework) {
		this.dblRework = dblRework;
	}

	public double getDblPackForw() {
		return dblPackForw;
	}

	public void setDblPackForw(double dblPackForw) {
		this.dblPackForw = dblPackForw;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public double getDblCStock() {
		return dblCStock;
	}

	public void setDblCStock(double dblCStock) {
		this.dblCStock = dblCStock;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblReOrderQty() {
		return dblReOrderQty;
	}

	public void setDblReOrderQty(double dblReOrderQty) {
		this.dblReOrderQty = dblReOrderQty;
	}

	public double getDblRevLvl() {
		return dblRevLvl;
	}

	public void setDblRevLvl(double dblRevLvl) {
		this.dblRevLvl = dblRevLvl;
	}

	public String getStrLotNo() {
		return strLotNo;
	}

	public void setStrLotNo(String strLotNo) {
		this.strLotNo = strLotNo;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public String getStrUpdate() {
		return strUpdate;
	}

	public void setStrUpdate(String strUpdate) {
		this.strUpdate = strUpdate;
	}

	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public double getDblDCWt() {
		return dblDCWt;
	}

	public void setDblDCWt(double dblDCWt) {
		this.dblDCWt = dblDCWt;
	}

	public double getDblPOWeight() {
		return dblPOWeight;
	}

	public void setDblPOWeight(double dblPOWeight) {
		this.dblPOWeight = dblPOWeight;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public double getDblValue() {
		return dblValue;
	}

	public void setDblValue(double dblValue) {
		this.dblValue = dblValue;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

}
