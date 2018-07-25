package com.sanguine.crm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

public class clsInvoiceDtlBean implements Serializable {

	// Variable Declaration
	private String strInvCode;

	private String strProdCode;

	private double dblQty;

	private double dblPrice;

	private double dblWeight;

	private String strProdType;

	private String strPktNo;

	private String strRemarks;

	private long intindex;

	private String strInvoiceable;

	private String strSerialNo;

	private String strClientCode;

	private String strProdName;

	private double dblUnitPrice;

	private double dblAssValue;

	private double dblBillRate;

	private String strCustCode;

	private String strSOCode;

	private double dblAmount;

	private double dblRate;

	private double dblMRP;

	private double dblCostRM;

	private double dblCGSTPer;

	private double dblSGSTPer;

	private double dblCGSTAmt;

	private double dblSGSTAmt;

	private double prevUnitPrice;

	private String prevInvCode;

	private String strProdNamemarthi;

	private String strHSN;

	private double dblDisAmt;

	private String strUOM;

	private double dblUOMConversion;

	private double dblNonGSTTaxPer;

	private double dblNonGSTTaxAmt;

	private String strTaxDesc;
	
	private String strSGSTTaxDesc;
	
	private String strCGSTTaxDesc;

	private String strBarCode;

	private double dblTaxableAmt;

	public double getDblNonGSTTaxPer() {
		return dblNonGSTTaxPer;
	}

	public void setDblNonGSTTaxPer(double dblNonGSTTaxPer) {
		this.dblNonGSTTaxPer = dblNonGSTTaxPer;
	}

	public double getDblNonGSTTaxAmt() {
		return dblNonGSTTaxAmt;
	}

	public void setDblNonGSTTaxAmt(double dblNonGSTTaxAmt) {
		this.dblNonGSTTaxAmt = dblNonGSTTaxAmt;
	}

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
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

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public String getStrPktNo() {
		return strPktNo;
	}

	public void setStrPktNo(String strPktNo) {
		this.strPktNo = strPktNo;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public long getIntindex() {
		return intindex;
	}

	public void setIntindex(long intindex) {
		this.intindex = intindex;
	}

	public String getStrInvoiceable() {
		return strInvoiceable;
	}

	public void setStrInvoiceable(String strInvoiceable) {
		this.strInvoiceable = strInvoiceable;
	}

	public String getStrSerialNo() {
		return strSerialNo;
	}

	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = strSerialNo;
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

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public double getDblAssValue() {
		return dblAssValue;
	}

	public void setDblAssValue(double dblAssValue) {
		this.dblAssValue = dblAssValue;
	}

	public double getDblBillRate() {
		return dblBillRate;
	}

	public void setDblBillRate(double dblBillRate) {
		this.dblBillRate = dblBillRate;
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public double getDblMRP() {
		return dblMRP;
	}

	public void setDblMRP(double dblMRP) {
		this.dblMRP = dblMRP;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblCostRM() {
		return dblCostRM;
	}

	public void setDblCostRM(double dblCostRM) {
		this.dblCostRM = dblCostRM;
	}

	public double getDblCGSTPer() {
		return dblCGSTPer;
	}

	public void setDblCGSTPer(double dblCGSTPer) {
		this.dblCGSTPer = dblCGSTPer;
	}

	public double getDblSGSTPer() {
		return dblSGSTPer;
	}

	public void setDblSGSTPer(double dblSGSTPer) {
		this.dblSGSTPer = dblSGSTPer;
	}

	public double getDblCGSTAmt() {
		return dblCGSTAmt;
	}

	public void setDblCGSTAmt(double dblCGSTAmt) {
		this.dblCGSTAmt = dblCGSTAmt;
	}

	public double getDblSGSTAmt() {
		return dblSGSTAmt;
	}

	public void setDblSGSTAmt(double dblSGSTAmt) {
		this.dblSGSTAmt = dblSGSTAmt;
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

	public String getStrProdNamemarthi() {
		return strProdNamemarthi;
	}

	public void setStrProdNamemarthi(String strProdNamemarthi) {
		this.strProdNamemarthi = strProdNamemarthi;
	}

	public String getStrHSN() {
		return strHSN;
	}

	public void setStrHSN(String strHSN) {
		this.strHSN = strHSN;
	}

	public double getDblDisAmt() {
		return dblDisAmt;
	}

	public void setDblDisAmt(double dblDisAmt) {
		this.dblDisAmt = dblDisAmt;
	}

	public double getDblUOMConversion() {
		return dblUOMConversion;
	}

	public void setDblUOMConversion(double dblUOMConversion) {
		this.dblUOMConversion = dblUOMConversion;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public String getStrBarCode() {
		return strBarCode;
	}

	public void setStrBarCode(String strBarCode) {
		this.strBarCode = strBarCode;
	}

	public double getDblTaxableAmt() {
		return dblTaxableAmt;
	}

	public void setDblTaxableAmt(double dblTaxableAmt) {
		this.dblTaxableAmt = dblTaxableAmt;
	}

	public String getStrSGSTTaxDesc()
	{
		return strSGSTTaxDesc;
	}

	public void setStrSGSTTaxDesc(String strSGSTTaxDesc)
	{
		this.strSGSTTaxDesc = strSGSTTaxDesc;
	}

	public String getStrCGSTTaxDesc()
	{
		return strCGSTTaxDesc;
	}

	public void setStrCGSTTaxDesc(String strCGSTTaxDesc)
	{
		this.strCGSTTaxDesc = strCGSTTaxDesc;
	}
	
	
	

}
