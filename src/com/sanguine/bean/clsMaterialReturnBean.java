package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsMaterialReturnDtlModel;

public class clsMaterialReturnBean {
	private String strMRetCode;

	private String dtMRetDate;

	private String strAgainst;

	private String strMISCode;

	private String strLocFrom;

	private String strLocTo;

	private String strLocFromName;

	private String strLocToName;

	private String strNarration;

	private String strAuthorise;

	private List<clsMaterialReturnDtlModel> listMaterialRetDtl;

	public String getStrMRetCode() {
		return strMRetCode;
	}

	public void setStrMRetCode(String strMRetCode) {
		this.strMRetCode = strMRetCode;
	}

	public String getDtMRetDate() {
		return dtMRetDate;
	}

	public void setDtMRetDate(String dtMRetDate) {
		this.dtMRetDate = dtMRetDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	public String getStrLocFrom() {
		return strLocFrom;
	}

	public void setStrLocFrom(String strLocFrom) {
		this.strLocFrom = strLocFrom;
	}

	public String getStrLocTo() {
		return strLocTo;
	}

	public void setStrLocTo(String strLocTo) {
		this.strLocTo = strLocTo;
	}

	public String getStrLocFromName() {
		return strLocFromName;
	}

	public void setStrLocFromName(String strLocFromName) {
		this.strLocFromName = strLocFromName;
	}

	public String getStrLocToName() {
		return strLocToName;
	}

	public void setStrLocToName(String strLocToName) {
		this.strLocToName = strLocToName;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public List<clsMaterialReturnDtlModel> getListMaterialRetDtl() {
		return listMaterialRetDtl;
	}

	public void setListMaterialRetDtl(List<clsMaterialReturnDtlModel> listMaterialRetDtl) {
		this.listMaterialRetDtl = listMaterialRetDtl;
	}
}
