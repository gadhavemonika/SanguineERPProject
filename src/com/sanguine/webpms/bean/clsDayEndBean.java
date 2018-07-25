package com.sanguine.webpms.bean;

public class clsDayEndBean {
	// Variable Declaration
	private String strPropertyCode;

	private String dtePMSDate;

	private String strDayEnd;

	private String strStartDay;

	private double dblDayEndAmt;

	private String strClientCode;

	// Setter-Getter Methods
	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getDtePMSDate() {
		return dtePMSDate;
	}

	public void setDtePMSDate(String dtePMSDate) {
		this.dtePMSDate = dtePMSDate;
	}

	public String getStrDayEnd() {
		return strDayEnd;
	}

	public void setStrDayEnd(String strDayEnd) {
		this.strDayEnd = strDayEnd;
	}

	public double getDblDayEndAmt() {
		return dblDayEndAmt;
	}

	public void setDblDayEndAmt(double dblDayEndAmt) {
		this.dblDayEndAmt = dblDayEndAmt;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrStartDay() {
		return strStartDay;
	}

	public void setStrStartDay(String strStartDay) {
		this.strStartDay = strStartDay;
	}
}
