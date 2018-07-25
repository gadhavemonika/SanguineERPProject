package com.sanguine.util;

import java.util.List;

import com.sanguine.webpos.bean.clsWebPOSReportBean;

public class clsPOSDashboardBean {
	private String strPOSPieChart;
	private String strItemPiechart;
	private String strGroupPieChart;
	private String strMenuPiechart;
	private String strSubGroupPieChart;
	private String strFromDate;
	private String strToDate;
	private String strShow;
	private String strPOSName;
	private double dblGrnAmt;
	private double dblInvAmt;
	private String strMonthName;

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = strPOSName;
	}

	private List<clsWebPOSReportBean> arrGraphList;
	private List<clsWebPOSReportBean> arrItemList;
	private List<clsWebPOSReportBean> arrMonthList;

	private List<clsPOSDashboardBean> arrGraphGrnInvList;
	private List<clsPOSDashboardBean> arrItemGrnInvList;
	private List<clsPOSDashboardBean> arrMonthGrnInvList;

	public List<clsWebPOSReportBean> getArrItemList() {
		return arrItemList;
	}

	public List<clsWebPOSReportBean> getArrMonthList() {
		return arrMonthList;
	}

	public void setArrMonthList(List<clsWebPOSReportBean> arrMonthList) {
		this.arrMonthList = arrMonthList;
	}

	public void setArrItemList(List<clsWebPOSReportBean> arrItemList) {
		this.arrItemList = arrItemList;
	}

	public List<clsWebPOSReportBean> getArrGraphList() {
		return arrGraphList;
	}

	public void setArrGraphList(List<clsWebPOSReportBean> arrGraphList) {
		this.arrGraphList = arrGraphList;
	}

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public String getStrShow() {
		return strShow;
	}

	public void setStrShow(String strShow) {
		this.strShow = strShow;
	}

	public String getStrSaleLineChart() {
		return strSaleLineChart;
	}

	public void setStrSaleLineChart(String strSaleLineChart) {
		this.strSaleLineChart = strSaleLineChart;
	}

	private String strSaleLineChart;

	public String getStrPOSPieChart() {
		return strPOSPieChart;
	}

	public void setStrPOSPieChart(String strPOSPieChart) {
		this.strPOSPieChart = strPOSPieChart;
	}

	public String getStrItemPiechart() {
		return strItemPiechart;
	}

	public void setStrItemPiechart(String strItemPiechart) {
		this.strItemPiechart = strItemPiechart;
	}

	public String getStrGroupPieChart() {
		return strGroupPieChart;
	}

	public void setStrGroupPieChart(String strGroupPieChart) {
		this.strGroupPieChart = strGroupPieChart;
	}

	public String getStrMenuPiechart() {
		return strMenuPiechart;
	}

	public void setStrMenuPiechart(String strMenuPiechart) {
		this.strMenuPiechart = strMenuPiechart;
	}

	public String getStrSubGroupPieChart() {
		return strSubGroupPieChart;
	}

	public void setStrSubGroupPieChart(String strSubGroupPieChart) {
		this.strSubGroupPieChart = strSubGroupPieChart;
	}

	public double getDblGrnAmt() {
		return dblGrnAmt;
	}

	public void setDblGrnAmt(double dblGrnAmt) {
		this.dblGrnAmt = dblGrnAmt;
	}

	public double getDblInvAmt() {
		return dblInvAmt;
	}

	public void setDblInvAmt(double dblInvAmt) {
		this.dblInvAmt = dblInvAmt;
	}

	public String getStrMonthName() {
		return strMonthName;
	}

	public void setStrMonthName(String strMonthName) {
		this.strMonthName = strMonthName;
	}

	public List<clsPOSDashboardBean> getArrGraphGrnInvList() {
		return arrGraphGrnInvList;
	}

	public void setArrGraphGrnInvList(List<clsPOSDashboardBean> arrGraphGrnInvList) {
		this.arrGraphGrnInvList = arrGraphGrnInvList;
	}

	public List<clsPOSDashboardBean> getArrItemGrnInvList() {
		return arrItemGrnInvList;
	}

	public void setArrItemGrnInvList(List<clsPOSDashboardBean> arrItemGrnInvList) {
		this.arrItemGrnInvList = arrItemGrnInvList;
	}

	public List<clsPOSDashboardBean> getArrMonthGrnInvList() {
		return arrMonthGrnInvList;
	}

	public void setArrMonthGrnInvList(List<clsPOSDashboardBean> arrMonthGrnInvList) {
		this.arrMonthGrnInvList = arrMonthGrnInvList;
	}

}
