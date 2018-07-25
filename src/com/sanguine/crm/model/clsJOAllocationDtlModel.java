package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Table(name = "tbljoborderallocationdtl")
public class clsJOAllocationDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsJOAllocationDtlModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strJACode")
	private String strJACode;

	@Column(name = "strJOCode")
	private String strJOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Transient
	private String strProdName;

	@Column(name = "strNatureOfProcessing")
	private String strNatureOfProcessing;

	@Column(name = "strProdNo")
	private String strProdNo;

	@Column(name = "dblQty")
	private Double dblQty;

	@Column(name = "dblRate")
	private Double dblRate;

	@Transient
	private Double dblTotalPrice;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "intIndex")
	private Long intIndex;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrJACode() {
		return strJACode;
	}

	public void setStrJACode(String strJACode) {
		this.strJACode = strJACode;
	}

	public String getStrJOCode() {
		return strJOCode;
	}

	public void setStrJOCode(String strJOCode) {
		this.strJOCode = strJOCode;
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

	public String getStrNatureOfProcessing() {
		return strNatureOfProcessing;
	}

	public void setStrNatureOfProcessing(String strNatureOfProcessing) {
		this.strNatureOfProcessing = strNatureOfProcessing;
	}

	public String getStrProdNo() {
		return strProdNo;
	}

	public void setStrProdNo(String strProdNo) {
		this.strProdNo = strProdNo;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public Double getDblRate() {
		return dblRate;
	}

	public void setDblRate(Double dblRate) {
		this.dblRate = dblRate;
	}

	public Double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(Double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public Long getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(Long intIndex) {
		this.intIndex = intIndex;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
