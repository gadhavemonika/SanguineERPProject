package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class clsReceiptInvDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsReceiptInvDtlModel() {
	}

	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "dteInvDate")
	private String dteInvDate;

	@Column(name = "dblInvAmt")
	private double dblInvAmt;

	@Column(name = "dteInvDueDate")
	private String dteInvDueDate;

	@Column(name = "strInvBIllNo")
	private String strInvBIllNo;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Transient
	private String strSelected;

	private double dblPayedAmt;

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

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

	public String getDteInvDate() {
		return dteInvDate;
	}

	public void setDteInvDate(String dteInvDate) {
		this.dteInvDate = dteInvDate;
	}

	public double getDblInvAmt() {
		return dblInvAmt;
	}

	public void setDblInvAmt(double dblInvAmt) {
		this.dblInvAmt = dblInvAmt;
	}

	public String getDteInvDueDate() {
		return dteInvDueDate;
	}

	public void setDteInvDueDate(String dteInvDueDate) {
		this.dteInvDueDate = dteInvDueDate;
	}

	public String getStrInvBIllNo() {
		return strInvBIllNo;
	}

	public void setStrInvBIllNo(String strInvBIllNo) {
		this.strInvBIllNo = strInvBIllNo;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrSelected() {
		return strSelected;
	}

	public void setStrSelected(String strSelected) {
		this.strSelected = strSelected;
	}

	public double getDblPayedAmt() {
		return dblPayedAmt;
	}

	public void setDblPayedAmt(double dblPayedAmt) {
		this.dblPayedAmt = dblPayedAmt;
	}

}
