package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblprodchar")
public class clsProdCharMasterModel {
	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strCharCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strCharCode;

	@Column(name = "strSpecf", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strSpecf;

	@Column(name = "strProcessCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strProcessCode;

	@Column(name = "strGaugeNo", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strGaugeNo;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strClientCode;

	@Column(name = "strTollerance", columnDefinition = "VARCHAR(50) NOT NULL dafault ''")
	private String strTollerance;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Transient
	private String strCharName;

	@Transient
	private String strProcessName;

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrCharCode() {
		return strCharCode;
	}

	public void setStrCharCode(String strCharCode) {
		this.strCharCode = strCharCode;
	}

	public String getStrSpecf() {
		return strSpecf;
	}

	public void setStrSpecf(String strSpecf) {
		this.strSpecf = strSpecf;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrGaugeNo() {
		return strGaugeNo;
	}

	public void setStrGaugeNo(String strGaugeNo) {
		this.strGaugeNo = strGaugeNo;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrTollerance() {
		return strTollerance;
	}

	public void setStrTollerance(String strTollerance) {
		this.strTollerance = strTollerance;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrCharName() {
		return strCharName;
	}

	public void setStrCharName(String strCharName) {
		this.strCharName = strCharName;
	}
}
