package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbldeliverynotedtl")
public class clsDeliveryNoteDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsDeliveryNoteDtlModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strDNCode")
	private String strDNCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strProcessCode")
	private String strProcessCode;

	@Column(name = "dblQty")
	private Double dblQty;

	@Column(name = "dblWeight")
	private Double dblWeight;

	@Column(name = "dblRate")
	private Double dblRate;

	@Column(name = "strProdChar")
	private String strProdChar;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Transient
	private String strProdName;

	@Transient
	private String strProdUOM;

	@Transient
	private String strProcessName;

	@Transient
	private Double dblTotalWt;

	@Transient
	private Double dblTotalPrice;

	// Setter Getter Method .

	public String getStrDNCode() {
		return strDNCode;
	}

	public void setStrDNCode(String strDNCode) {
		this.strDNCode = strDNCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public Double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(Double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public Double getDblRate() {
		return dblRate;
	}

	public void setDblRate(Double dblRate) {
		this.dblRate = dblRate;
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

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrProdUOM() {
		return strProdUOM;
	}

	public void setStrProdUOM(String strProdUOM) {
		this.strProdUOM = strProdUOM;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public Double getDblTotalWt() {
		return dblTotalWt;
	}

	public void setDblTotalWt(Double dblTotalWt) {
		this.dblTotalWt = dblTotalWt;
	}

	public Double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(Double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

}