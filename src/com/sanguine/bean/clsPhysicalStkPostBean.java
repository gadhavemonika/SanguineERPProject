package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsStkPostingDtlModel;

public class clsPhysicalStkPostBean {
	private String strPSCode;

	private String dtPSDate;

	private String strLocCode;

	private String strLocName;

	private String strSACode;

	private String strAuthorise;

	private int intIndex;

	private double dblTotalAmt;

	private String strNarration;

	private String strConversionUOM;

	private String strPhyStkFrom;

	private List<clsStkPostingDtlModel> listStkPostDtl;

	public String getStrPSCode() {
		return strPSCode;
	}

	public void setStrPSCode(String strPSCode) {
		this.strPSCode = strPSCode;
	}

	public String getDtPSDate() {
		return dtPSDate;
	}

	public void setDtPSDate(String dtPSDate) {
		this.dtPSDate = dtPSDate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public int getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(int intIndex) {
		this.intIndex = intIndex;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public List<clsStkPostingDtlModel> getListStkPostDtl() {
		return listStkPostDtl;
	}

	public void setListStkPostDtl(List<clsStkPostingDtlModel> listStkPostDtl) {
		this.listStkPostDtl = listStkPostDtl;
	}

	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrConversionUOM() {
		return strConversionUOM;
	}

	public void setStrConversionUOM(String strConversionUOM) {
		this.strConversionUOM = strConversionUOM;
	}

	public String getStrPhyStkFrom() {
		return strPhyStkFrom;
	}

	public void setStrPhyStkFrom(String strPhyStkFrom) {
		this.strPhyStkFrom = strPhyStkFrom;
	}
}
