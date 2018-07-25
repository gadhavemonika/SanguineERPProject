package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsStkTransferDtlModel;

public class clsStockTransferBean {
	private String strSTCode;

	private String dtSTDate;

	private String strFromLocCode;

	private String strFromLocName;

	private String strToLocCode;

	private String strToLocName;

	private String strNo;

	private String strNarration;

	private String strMaterialIssue;

	private String dblTotalAmt;
	
	private String strReqCode;
	
	private String strAgainst;

	private List<clsStkTransferDtlModel> listStkTransDtl;

	public String getStrSTCode() {
		return strSTCode;
	}

	public void setStrSTCode(String strSTCode) {
		this.strSTCode = strSTCode;
	}

	public String getDtSTDate() {
		return dtSTDate;
	}

	public void setDtSTDate(String dtSTDate) {
		this.dtSTDate = dtSTDate;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrFromLocName() {
		return strFromLocName;
	}

	public void setStrFromLocName(String strFromLocName) {
		this.strFromLocName = strFromLocName;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}

	public String getStrToLocName() {
		return strToLocName;
	}

	public void setStrToLocName(String strToLocName) {
		this.strToLocName = strToLocName;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrMaterialIssue() {
		return strMaterialIssue;
	}

	public void setStrMaterialIssue(String strMaterialIssue) {
		this.strMaterialIssue = strMaterialIssue;
	}

	public List<clsStkTransferDtlModel> getListStkTransDtl() {
		return listStkTransDtl;
	}

	public void setListStkTransDtl(List<clsStkTransferDtlModel> listStkTransDtl) {
		this.listStkTransDtl = listStkTransDtl;
	}

	public String getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(String dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}
}
