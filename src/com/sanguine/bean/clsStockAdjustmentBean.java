package com.sanguine.bean;

import java.util.List;
import com.sanguine.model.clsStkAdjustmentDtlModel;

public class clsStockAdjustmentBean {
	private String strSACode;

	private String dtSADate;

	private String strLocCode;

	private String strLocName;

	private String strNarration;

	private String strAuthorise;

	private String strReasonCode;

	private Double dblTotalAmt;

	private String strConversionUOM;

	private String strAdjustmentType;

	private List<clsStkAdjustmentDtlModel> listStkAdjDtl;

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = strSACode;
	}

	public String getDtSADate() {
		return dtSADate;
	}

	public void setDtSADate(String dtSADate) {
		this.dtSADate = dtSADate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public List<clsStkAdjustmentDtlModel> getListStkAdjDtl() {
		return listStkAdjDtl;
	}

	public void setListStkAdjDtl(List<clsStkAdjustmentDtlModel> listStkAdjDtl) {
		this.listStkAdjDtl = listStkAdjDtl;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
	}

	public Double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(Double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrConversionUOM() {
		return strConversionUOM;
	}

	public void setStrConversionUOM(String strConversionUOM) {
		this.strConversionUOM = strConversionUOM;
	}

	public String getStrAdjustmentType() {
		return strAdjustmentType;
	}

	public void setStrAdjustmentType(String strAdjustmentType) {
		this.strAdjustmentType = strAdjustmentType;
	}

	@Override
	public String toString() {
		return "clsStockAdjustmentBean [strSACode=" + strSACode + ", dtSADate="
				+ dtSADate + ", strLocCode=" + strLocCode + ", strLocName="
				+ strLocName + ", strNarration=" + strNarration
				+ ", strAuthorise=" + strAuthorise + ", strReasonCode="
				+ strReasonCode + ", dblTotalAmt=" + dblTotalAmt
				+ ", strConversionUOM=" + strConversionUOM
				+ ", strAdjustmentType=" + strAdjustmentType
				+ ", listStkAdjDtl=" + listStkAdjDtl + "]";
	}
}
