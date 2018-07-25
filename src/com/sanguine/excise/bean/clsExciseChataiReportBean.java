package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseChataiReportColumnModel;
import com.sanguine.excise.model.clsExciseChataiReportModel;

public class clsExciseChataiReportBean {

	// Variable Declaration
	private String strFromDate;

	private String strToDate;

	private String strInPeg;

	private List<clsExciseChataiReportColumnModel> colRow;

	private List<clsExciseChataiReportModel> rowList;

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

	public String getStrInPeg() {
		return strInPeg;
	}

	public void setStrInPeg(String strInPeg) {
		this.strInPeg = strInPeg;
	}

	public List<clsExciseChataiReportColumnModel> getColRow() {
		return colRow;
	}

	public void setColRow(List<clsExciseChataiReportColumnModel> colRow) {
		this.colRow = colRow;
	}

	public List<clsExciseChataiReportModel> getRowList() {
		return rowList;
	}

	public void setRowList(List<clsExciseChataiReportModel> rowList) {
		this.rowList = rowList;
	}

}
