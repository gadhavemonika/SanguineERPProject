package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class clsMonthEndModel_ID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strMonthEnd;
	private String strLocCode;

	public clsMonthEndModel_ID() {
	}

	public clsMonthEndModel_ID(String strMonthEnd, String strLocCode) {
		this.strMonthEnd = strMonthEnd;
		this.strLocCode = strLocCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsMonthEndModel_ID MonthEndobj = (clsMonthEndModel_ID) obj;
		if (this.strMonthEnd.equals(MonthEndobj.getStrMonthEnd()) && this.strLocCode.equals(MonthEndobj.getStrLocCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strMonthEnd.hashCode() + this.strLocCode.hashCode();
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = strMonthEnd;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

}
