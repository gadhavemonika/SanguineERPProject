package com.sanguine.webpms.bean;

import java.util.List;

public class clsPMSDashboardBean {
	private String strFromDate;
	private String strToDate;
	private String strShow;

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

	private List<clsWebPMSReportBean> arrFootCountList;

	public List<clsWebPMSReportBean> getArrFootCountList() {
		return arrFootCountList;
	}

	public void setArrFootCountList(List<clsWebPMSReportBean> arrFootCountList) {
		this.arrFootCountList = arrFootCountList;
	}

}
