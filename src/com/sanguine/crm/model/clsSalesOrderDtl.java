package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblsalesorderdtl")
public class clsSalesOrderDtl implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsSalesOrderDtl() {
	}

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "dblDiscount")
	private double dblDiscount;

	@Column(name = "strTaxType")
	private String strTaxType;

	@Column(name = "dblTaxableAmt")
	private double dblTaxableAmt;

	@Column(name = "dblTax")
	private double dblTax;

	@Column(name = "dblTaxAmt")
	private double dblTaxAmt;

	@Column(name = "dblUnitPrice")
	private double dblUnitPrice;

	@Column(name = "dblWeight")
	private double dblWeight;

	@Column(name = "strProdChar")
	private String strProdChar;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "intindex")
	private long intindex;

	@Transient
	private String strProdName;

	@Transient
	private String strPartNo;

	@Transient
	private Double dblRequiredQty;

	@Transient
	private String strStatus;

	@Transient
	private Double dblAvalaibleStk;

	@Transient
	private String strProdType;

	@Transient
	private double dblTotalPrice;

	@Transient
	private double dblAvgQty;

	@Column(name = "dblAcceptQty")
	private double dblAcceptQty;

	@Transient
	private String strCustCode;

	@Transient
	private String strCustNmae;

	@Transient
	private String strUOM;

	@Transient
	private double prevUnitPrice;

	@Transient
	private String prevInvCode;
	
	@Transient
	private String strCurrency;
	
	@Transient
	private double dblConversion;;
	
	

	// Setter-Getter Methods

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

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

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntindex() {
		return intindex;
	}

	public void setIntindex(long intindex) {
		this.intindex = intindex;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public Double getDblRequiredQty() {
		return dblRequiredQty;
	}

	public void setDblRequiredQty(Double dblRequiredQty) {
		this.dblRequiredQty = dblRequiredQty;
	}

	public Double getDblAvalaibleStk() {
		return dblAvalaibleStk;
	}

	public void setDblAvalaibleStk(Double dblAvalaibleStk) {
		this.dblAvalaibleStk = dblAvalaibleStk;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public double getDblAvgQty() {
		return dblAvgQty;
	}

	public void setDblAvgQty(double dblAvgQty) {
		this.dblAvgQty = dblAvgQty;
	}

	public double getDblAcceptQty() {
		return dblAcceptQty;
	}

	public void setDblAcceptQty(double dblAcceptQty) {
		this.dblAcceptQty = dblAcceptQty;
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}

	public String getStrCustNmae() {
		return strCustNmae;
	}

	public void setStrCustNmae(String strCustNmae) {
		this.strCustNmae = strCustNmae;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getPrevUnitPrice() {
		return prevUnitPrice;
	}

	public void setPrevUnitPrice(double prevUnitPrice) {
		this.prevUnitPrice = prevUnitPrice;
	}

	public String getPrevInvCode() {
		return prevInvCode;
	}

	public void setPrevInvCode(String prevInvCode) {
		this.prevInvCode = prevInvCode;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = (String) setDefaultValue(strCurrency, "");;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	// Function to Set Default Values
		private Object setDefaultValue(Object value, Object defaultValue) {
			if (value != null && (value instanceof String && value.toString().length() > 0)) {
				return value;
			} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
				return value;
			} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
				return value;
			} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
				return value;
			} else {
				return defaultValue;
			}
		}
}
