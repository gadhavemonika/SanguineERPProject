package com.sanguine.webpos.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

public class clsDayEndProcessBean {

	private JSONArray jArrDayEnd = null;;
	private JSONArray jArrDayEndTotal = null;;
	private JSONArray jArrSettlement = null;;
	private JSONArray jArrSettlementTotal = null;
	private JSONArray jArrSalesInProg = null;
	private JSONArray jArrUnSettlebill = null;

	private List<clsDayEndProcessBean> listMailReport;

	private String strReportName;
	private Boolean strReportCheck;

	private String totalpax;
	private String total;
	private String fromDate;
	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<clsDayEndProcessBean> getListMailReport() {
		return listMailReport;
	}

	public void setListMailReport(List<clsDayEndProcessBean> listMailReport) {
		this.listMailReport = listMailReport;
	}

	public String getTotalpax() {
		return totalpax;
	}

	public void setTotalpax(String totalpax) {
		this.totalpax = totalpax;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public JSONArray getjArrDayEnd() {
		return jArrDayEnd;
	}

	public void setjArrDayEnd(JSONArray jArrDayEnd) {
		this.jArrDayEnd = jArrDayEnd;
	}

	public JSONArray getjArrDayEndTotal() {
		return jArrDayEndTotal;
	}

	public void setjArrDayEndTotal(JSONArray jArrDayEndTotal) {
		this.jArrDayEndTotal = jArrDayEndTotal;
	}

	public JSONArray getjArrSettlement() {
		return jArrSettlement;
	}

	public void setjArrSettlement(JSONArray jArrSettlement) {
		this.jArrSettlement = jArrSettlement;
	}

	public JSONArray getjArrSettlementTotal() {
		return jArrSettlementTotal;
	}

	public void setjArrSettlementTotal(JSONArray jArrSettlementTotal) {
		this.jArrSettlementTotal = jArrSettlementTotal;
	}

	public JSONArray getjArrSalesInProg() {
		return jArrSalesInProg;
	}

	public void setjArrSalesInProg(JSONArray jArrSalesInProg) {
		this.jArrSalesInProg = jArrSalesInProg;
	}

	public JSONArray getjArrUnSettlebill() {
		return jArrUnSettlebill;
	}

	public void setjArrUnSettlebill(JSONArray jArrUnSettlebill) {
		this.jArrUnSettlebill = jArrUnSettlebill;
	}

	public String getStrReportName() {
		return strReportName;
	}

	public void setStrReportName(String strReportName) {
		this.strReportName = strReportName;
	}

	public Boolean getStrReportCheck() {
		return strReportCheck;
	}

	public void setStrReportCheck(Boolean strReportCheck) {
		this.strReportCheck = strReportCheck;
	}

	public String getMailReport() {
		return mailReport;
	}

	public void setMailReport(String mailReport) {
		this.mailReport = mailReport;
	}

	private String mailReport;

}
