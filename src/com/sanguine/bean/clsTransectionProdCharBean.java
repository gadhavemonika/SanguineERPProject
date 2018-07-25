package com.sanguine.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

public class clsTransectionProdCharBean {

	private String strProdCode;

	private String strCharCode;

	private String strSpecf;

	private String strProcessCode;

	private String strGaugeNo;

	private String strTollerance;

	private String strCharName;

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

	public String getStrTollerance() {
		return strTollerance;
	}

	public void setStrTollerance(String strTollerance) {
		this.strTollerance = strTollerance;
	}

	public String getStrCharName() {
		return strCharName;
	}

	public void setStrCharName(String strCharName) {
		this.strCharName = strCharName;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

}
