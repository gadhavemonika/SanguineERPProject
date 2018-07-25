package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblratecontdtl")
public class clsRateContractDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strRateContNo")
	private String strRateContNo;

	@Column(name = "strProductCode")
	private String strProductCode;

	@Column(name = "strUnit")
	private String strUnit;

	@Column(name = "dblDiscount")
	private double dblDiscount;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "dtExpectedDate")
	private String dtExpectedDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblRate")
	private double dblRate;

	private String strProductName;
	private String strPartNo;

	public String getStrProductName() {
		return strProductName;
	}

	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrRateContNo() {
		return strRateContNo;
	}

	public void setStrRateContNo(String strRateContNo) {
		this.strRateContNo = strRateContNo;
	}

	public String getStrProductCode() {
		return strProductCode;
	}

	public void setStrProductCode(String strProductCode) {
		this.strProductCode = strProductCode;
	}

	public String getStrUnit() {
		return strUnit;
	}

	public void setStrUnit(String strUnit) {
		this.strUnit = strUnit;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getDtExpectedDate() {
		return dtExpectedDate;
	}

	public void setDtExpectedDate(String dtExpectedDate) {
		this.dtExpectedDate = dtExpectedDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}
}
