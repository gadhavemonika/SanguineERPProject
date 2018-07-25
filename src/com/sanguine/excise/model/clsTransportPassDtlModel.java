package com.sanguine.excise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbltpdtl")
public class clsTransportPassDtlModel {

	@Id
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strTPCode")
	private String strTPCode;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Transient
	private String strBrandName;

	@Transient
	private String strBrandStrength;

	@Transient
	private String strBrandSize;

	@Transient
	private Double dblPegPrice;

	@Transient
	private Double dblBrandMRP;

	@Column(name = "intBottals")
	private Integer intBottals;

	@Column(name = "dblBrandTotal")
	private Double dblBrandTotal;

	@Column(name = "dblBrandTax")
	private Double dblBrandTax;

	@Column(name = "dblBrandFee")
	private Double dblBrandFee;

	@Column(name = "intBrandCases")
	private Integer intBrandCases;

	@Column(name = "strBrandBatchNo")
	private String strBrandBatchNo;

	@Column(name = "monofmfg")
	private String strMonOfMfg;

	@Column(name = "strRemark")
	private String strRemark;

	@Column(name = "strClientCode")
	private String strClientCode;

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrTPCode() {
		return strTPCode;
	}

	public void setStrTPCode(String strTPCode) {
		this.strTPCode = strTPCode;
	}

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public String getStrBrandStrength() {
		return strBrandStrength;
	}

	public void setStrBrandStrength(String strBrandStrength) {
		this.strBrandStrength = strBrandStrength;
	}

	public String getStrBrandSize() {
		return strBrandSize;
	}

	public void setStrBrandSize(String strBrandSize) {
		this.strBrandSize = strBrandSize;
	}

	public Double getDblBrandMRP() {
		return dblBrandMRP;
	}

	public void setDblBrandMRP(Double dblBrandMRP) {
		this.dblBrandMRP = dblBrandMRP;
	}

	public Integer getIntBottals() {
		return intBottals;
	}

	public void setIntBottals(Integer intBottals) {
		this.intBottals = intBottals;
	}

	public Double getDblBrandTotal() {
		return dblBrandTotal;
	}

	public void setDblBrandTotal(Double dblBrandTotal) {
		this.dblBrandTotal = dblBrandTotal;
	}

	public Double getDblBrandTax() {
		return dblBrandTax;
	}

	public void setDblBrandTax(Double dblBrandTax) {
		this.dblBrandTax = dblBrandTax;
	}

	public Double getDblBrandFee() {
		return dblBrandFee;
	}

	public void setDblBrandFee(Double dblBrandFee) {
		this.dblBrandFee = dblBrandFee;
	}

	public Integer getIntBrandCases() {
		return intBrandCases;
	}

	public void setIntBrandCases(Integer intBrandCases) {
		this.intBrandCases = intBrandCases;
	}

	public String getStrBrandBatchNo() {
		return strBrandBatchNo;
	}

	public void setStrBrandBatchNo(String strBrandBatchNo) {
		this.strBrandBatchNo = strBrandBatchNo;
	}

	public String getStrMonOfMfg() {
		return strMonOfMfg;
	}

	public void setStrMonOfMfg(String strMonOfMfg) {
		this.strMonOfMfg = strMonOfMfg;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public Double getDblPegPrice() {
		return dblPegPrice;
	}

	public void setDblPegPrice(Double dblPegPrice) {
		this.dblPegPrice = dblPegPrice;
	}

}
