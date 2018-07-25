package com.sanguine.webbooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbltempledger")
public class clsTempLedgerModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intId")
	private long intId;

	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	@Column(name = "dblCrAmt")
	private double dblCrAmt;

	@Column(name = "dblDrAmt")
	private double dblDrAmt;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strUserCode")
	private String strUserCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = strDebtorCode;
	}

	public double getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(double dblCrAmt) {
		this.dblCrAmt = dblCrAmt;
	}

	public double getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(double dblDrAmt) {
		this.dblDrAmt = dblDrAmt;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

}
