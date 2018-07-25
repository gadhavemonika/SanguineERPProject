package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class clsCRMDayEndModel_ID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strDayEnd;
	private String strLocCode;
	private String strClientCode;

	public clsCRMDayEndModel_ID() {
	}

	public clsCRMDayEndModel_ID(String strDayEnd, String strLocCode, String strClientCode) {
		this.strDayEnd = strDayEnd;
		this.strLocCode = strLocCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsCRMDayEndModel_ID DayEndobj = (clsCRMDayEndModel_ID) obj;
		if (this.strDayEnd.equals(DayEndobj.getStrDayEnd()) && this.strLocCode.equals(DayEndobj.getStrLocCode()) && this.strClientCode.equals(DayEndobj.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strDayEnd.hashCode() + this.strLocCode.hashCode();
	}

	public String getStrDayEnd() {
		return strDayEnd;
	}

	public void setStrDayEnd(String strDayEnd) {
		this.strDayEnd = strDayEnd;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
