package com.sanguine.webbooks.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsReceiptDtlModel {

	// Variable Declaration
	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strAccountName")
	private String strAccountName;

	@Column(name = "strCrDr")
	private String strCrDr;

	@Column(name = "dblDrAmt")
	private double dblDrAmt;

	@Column(name = "dblCrAmt")
	private double dblCrAmt;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}

	public double getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(double dblDrAmt) {
		this.dblDrAmt = dblDrAmt;
	}

	public double getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(double dblCrAmt) {
		this.dblCrAmt = dblCrAmt;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}
}
