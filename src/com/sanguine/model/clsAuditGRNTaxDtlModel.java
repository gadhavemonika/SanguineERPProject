package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblauditgrntaxdtl")
public class clsAuditGRNTaxDtlModel {

	@Column(name = "strGRNCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strGRNCode;

	@Column(name = "strTaxCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strTaxCode;

	@Column(name = "strTaxDesc", columnDefinition = "VARCHAR(100) NOT NULL default ''")
	private String strTaxDesc;

	@Column(name = "strTaxableAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double strTaxableAmt;

	@Column(name = "strTaxAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double strTaxAmt;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private long intId;

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = (String) setDefaultValue(strTaxCode, "");
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = (String) setDefaultValue(strTaxDesc, "");
	}

	public double getStrTaxableAmt() {
		return strTaxableAmt;
	}

	public void setStrTaxableAmt(double strTaxableAmt) {
		this.strTaxableAmt = (double) setDefaultValue(strTaxableAmt, 0.00);
	}

	public double getStrTaxAmt() {
		return strTaxAmt;
	}

	public void setStrTaxAmt(double strTaxAmt) {
		this.strTaxAmt = (double) setDefaultValue(strTaxAmt, 0.00);
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

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;
		} else {
			return defaultValue;
		}
	}
}
