package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbldeliverychallandtl")
public class clsDeliveryChallanModelDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	// Variable Declaration
	@Column(name = "strDCCode")
	private String strDCCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "dblPrice")
	private double dblPrice;

	@Column(name = "dblWeight")
	private double dblWeight;

	@Column(name = "strProdType")
	private String strProdType;

	@Column(name = "strPktNo")
	private String strPktNo;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "intindex")
	private long intindex;

	@Column(name = "strInvoiceable")
	private String strInvoiceable;

	@Column(name = "strSerialNo")
	private String strSerialNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Transient
	private String strProdName;

	// Setter-Getter Methods
	public String getStrDCCode() {
		return strDCCode;
	}

	public void setStrDCCode(String strDCCode) {
		this.strDCCode = (String) setDefaultValue(strDCCode, "");
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = (String) setDefaultValue(strProdCode, "");
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = (Double) setDefaultValue(dblQty, "");
	}

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = (Double) setDefaultValue(dblPrice, "");
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = (Double) setDefaultValue(dblWeight, "");
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = (String) setDefaultValue(strProdType, "");
	}

	public String getStrPktNo() {
		return strPktNo;
	}

	public void setStrPktNo(String strPktNo) {
		this.strPktNo = (String) setDefaultValue(strPktNo, "");
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "");
	}

	public long getIntindex() {
		return intindex;
	}

	public void setIntindex(long intindex) {
		this.intindex = (Long) setDefaultValue(intindex, "");
	}

	public String getStrInvoiceable() {
		return strInvoiceable;
	}

	public void setStrInvoiceable(String strInvoiceable) {
		this.strInvoiceable = (String) setDefaultValue(strInvoiceable, "");
	}

	public String getStrSerialNo() {
		return strSerialNo;
	}

	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = (String) setDefaultValue(strSerialNo, "");
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

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

}
