package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblgrndtl")
public class clsGRNDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strGRNCode")
	private String strGRNCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	// @EmbeddedId
	// private clsGRNDtlUtil objGRNId;

	@Column(name = "strTaxType", insertable = false)
	private String strTaxType;

	@Column(name = "strProdChar", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strProdChar;

	@Column(name = "strRemarks", columnDefinition = "VARCHAR(1000) NOT NULL default ''")
	private String strRemarks;

	@Column(name = "strGRNProdChar", insertable = false, columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strGRNProdChar;

	@Column(name = "strCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCode;

	@Column(name = "dblQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQty;

	@Column(name = "dblRejected", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRejected;

	@Column(name = "dblDiscount", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDiscount;

	@Column(name = "dblTaxableAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTaxableAmt;

	@Column(name = "dblTax", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTax;

	@Column(name = "dblTaxAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTaxAmt;

	@Column(name = "dblUnitPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblUnitPrice;

	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblWeight;

	@Column(name = "dblDCQty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDCQty;

	@Column(name = "dblDCWt", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDCWt;

	@Column(name = "dblQtyRbl", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQtyRbl;

	@Column(name = "dblPOWeight", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblPOWeight;

	@Column(name = "dblRework", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRework;

	@Column(name = "dblPackForw", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblPackForw;

	@Column(name = "intIndex")
	private int intIndex;

	@Column(name = "dblRate", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblRate;

	@Column(name = "dblValue", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblValue;

	@Transient
	private String strProdName;

	@Transient
	private String strUOM;

	@Transient
	private double dblTotalWt;

	@Column(name = "dblTotalPrice", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblTotalPrice;

	@Transient
	private String strStkble;

	@Column(name = "strIssueLocation", columnDefinition = "VARCHAR(25) NOT NULL default '' ")
	private String strIssueLocation;

	@Column(name = "strMISCode", columnDefinition = "VARCHAR(25) NOT NULL default '' ")
	private String strMISCode;

	@Transient
	private String strIsueLocName;

	@Transient
	private String strReqCode;

	@Transient
	private String strBinNo;

	@Transient
	private String strExpiry;
	
	@Transient
	private double dblConversionRate;

	@Transient
	private String strCurrency;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = strTaxType;
	}

	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = strProdChar;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrGRNProdChar() {
		return strGRNProdChar;
	}

	public void setStrGRNProdChar(String strGRNProdChar) {
		this.strGRNProdChar = strGRNProdChar;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblRejected() {
		return dblRejected;
	}

	public void setDblRejected(double dblRejected) {
		this.dblRejected = dblRejected;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
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

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public double getDblDCQty() {
		return dblDCQty;
	}

	public void setDblDCQty(double dblDCQty) {
		this.dblDCQty = dblDCQty;
	}

	public double getDblDCWt() {
		return dblDCWt;
	}

	public void setDblDCWt(double dblDCWt) {
		this.dblDCWt = dblDCWt;
	}

	public double getDblQtyRbl() {
		return dblQtyRbl;
	}

	public void setDblQtyRbl(double dblQtyRbl) {
		this.dblQtyRbl = dblQtyRbl;
	}

	public double getDblPOWeight() {
		return dblPOWeight;
	}

	public void setDblPOWeight(double dblPOWeight) {
		this.dblPOWeight = dblPOWeight;
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

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
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

	public String getStrBinNo() {
		return strBinNo;
	}

	public void setStrBinNo(String strBinNo) {
		this.strBinNo = strBinNo;
	}

	public String getStrExpiry() {
		return strExpiry;
	}

	public void setStrExpiry(String strExpiry) {
		this.strExpiry = strExpiry;
	}

	public String getStrStkble() {
		return strStkble;
	}

	public void setStrStkble(String strStkble) {
		this.strStkble = strStkble;
	}

	public String getStrIssueLocation() {
		return strIssueLocation;
	}

	public void setStrIssueLocation(String strIssueLocation) {
		this.strIssueLocation = (String) setDefaultValue(strIssueLocation, "");
	}

	public String getStrIsueLocName() {
		return strIsueLocName;
	}

	public void setStrIsueLocName(String strIsueLocName) {
		this.strIsueLocName = strIsueLocName;
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = (String) setDefaultValue(strMISCode, "");
	}
	

	public double getDblConversionRate() {
		return dblConversionRate;
	}

	public void setDblConversionRate(double dblConversionRate) {
		this.dblConversionRate = dblConversionRate;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}
}
