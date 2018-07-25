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
@Table(name = "tblpurchaseorderdtl")
public class clsPurchaseOrderDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long intId;
	private String strPOCode;
	private String strProdCode;
	private String strProdChar;
	private String strProcessCode;
	private String strRemarks;
	private String strPICode;
	private String strAmntNO;
	private int intindex;
	private String strUpdate;
	private String dtDelDate;
	private String strClientCode;
	@Column(name = "dblAmount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblAmount;
	@Column(name = "dblOrdQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblOrdQty;
	@Column(name = "dblPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblPrice;
	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblWeight;
	@Column(name = "dblDiscount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0'")
	private double dblDiscount;

	@Column(name = "dblTaxableAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblTaxableAmt;

	@Column(name = "dblTaxAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblTaxAmt;

	@Column(name = "strTaxType", nullable = true, columnDefinition = "VARCHAR(10)  default ' '")
	private String strTaxType;

	@Column(name = "dblTax", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblTax;

	@Transient
	private String strProdName;

	@Transient
	private String strProcessName;

	@Transient
	private String strUOM;

	@Transient
	private String strSuppCode;

	@Transient
	private String strSuppName;

	@Transient
	private double dblTotalWt;
	@Transient
	private double dblTotalPrice;
	@Transient
	private String strExpiry;

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblOrdQty() {
		return dblOrdQty;
	}

	public void setDblOrdQty(double dblOrdQty) {
		this.dblOrdQty = dblOrdQty;
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

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
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

	public String getStrAmntNO() {
		return strAmntNO;
	}

	public void setStrAmntNO(String strAmntNO) {
		this.strAmntNO = strAmntNO;
	}

	public int getIntindex() {
		return intindex;
	}

	public void setIntindex(int intindex) {
		this.intindex = intindex;
	}

	public String getStrUpdate() {
		return strUpdate;
	}

	public void setStrUpdate(String strUpdate) {
		this.strUpdate = strUpdate;
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

	public double getDblTotalWt() {
		return dblTotalWt;
	}

	public void setDblTotalWt(double dblTotalWt) {
		this.dblTotalWt = dblTotalWt;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getStrExpiry() {
		return strExpiry;
	}

	public void setStrExpiry(String strExpiry) {
		this.strExpiry = strExpiry;
	}

	public double getDblTaxableAmt() {
		return dblTaxableAmt;
	}

	public void setDblTaxableAmt(double dblTaxableAmt) {
		this.dblTaxableAmt = dblTaxableAmt;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = strTaxType;
	}

	public double getDblTax() {
		return dblTax;
	}

	public void setDblTax(double dblTax) {
		this.dblTax = dblTax;
	}

	public String getDtDelDate() {
		return dtDelDate;
	}

	public void setDtDelDate(String dtDelDate) {
		this.dtDelDate = dtDelDate;
	}

}
