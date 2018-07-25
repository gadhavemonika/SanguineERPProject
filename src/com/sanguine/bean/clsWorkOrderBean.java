package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsWorkOrderDtlModel;

public class clsWorkOrderBean {
	private String strWOCode, strSOCode, strParentWOCode, strUserCreated, dtDateCreated, strUserModified, dtLastModified, strAuthorise;

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getStrParentWOCode() {
		return strParentWOCode;
	}

	public void setStrParentWOCode(String strParentWOCode) {
		this.strParentWOCode = strParentWOCode;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	private String dtWODate;

	private String strProdCode;

	private String strProdName;

	private double dblQty;

	private String strStatus;

	private List<clsWorkOrderDtlModel> listclsWorkOrderDtlModel;

	private String strProcessCode;

	private String strProcessName;

	private String strProcessStatus;

	private String strFromLocCode;

	private String strToLocCode;

	private String strAgainst;

	public String getStrWOCode() {
		return strWOCode;
	}

	public List<clsWorkOrderDtlModel> getListclsWorkOrderDtlModel() {
		return listclsWorkOrderDtlModel;
	}

	public void setListclsWorkOrderDtlModel(List<clsWorkOrderDtlModel> listclsWorkOrderDtlModel) {
		this.listclsWorkOrderDtlModel = listclsWorkOrderDtlModel;
	}

	public void setStrWOCode(String strWOCode) {
		this.strWOCode = strWOCode;
	}

	public String getDtWODate() {
		return dtWODate;
	}

	public void setDtWODate(String dtWODate) {
		this.dtWODate = dtWODate;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public String getStrProcessStatus() {
		return strProcessStatus;
	}

	public void setStrProcessStatus(String strProcessStatus) {
		this.strProcessStatus = strProcessStatus;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

}
