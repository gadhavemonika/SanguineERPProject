package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Embeddable
public class clsProFormaInvoiceModelDtl implements Serializable {

	// Variable Declaration

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

	private double dblUnitPrice;

	private double dblAssValue;

	private double dblBillRate;

	private String strSOCode;

	private String strCustCode;

	private String strUOM;

	private double dblUOMConversion;

	private double dblProdDiscAmount;

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
		this.strProdType = (String) setDefaultValue(strProdType, "");
		;
	}

	public String getStrPktNo() {
		return strPktNo;
	}

	public void setStrPktNo(String strPktNo) {
		this.strPktNo = (String) setDefaultValue(strPktNo, "");
		;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "");
		;
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
		this.strInvoiceable = (String) setDefaultValue(strInvoiceable, "N");
		;
	}

	public String getStrSerialNo() {
		return strSerialNo;
	}

	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = (String) setDefaultValue(strSerialNo, "");
		;
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

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = (String) setDefaultValue(strSOCode, "");
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = (String) setDefaultValue(strCustCode, "");
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblUOMConversion() {
		return dblUOMConversion;
	}

	public void setDblUOMConversion(double dblUOMConversion) {
		this.dblUOMConversion = dblUOMConversion;
	}

	public double getDblProdDiscAmount() {
		return dblProdDiscAmount;
	}

	public void setDblProdDiscAmount(double dblProdDiscAmount) {
		this.dblProdDiscAmount = dblProdDiscAmount;
	}

}
