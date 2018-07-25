package com.sanguine.crm.bean;

import java.util.List;

import com.sanguine.crm.model.clsJOAllocationDtlModel;

public class clsJOAllocationBean {

	// Variable Declaration

	private String strJACode;

	private Long intId;

	private String strJANo;

	private String strSCCode;

	private String strSCName;

	private String dteJADate;

	private String strRef;

	private String dteRefDate;

	private String strDispatchMode;

	private String strPayment;

	private String strTaxes;

	private String strUserCreated;

	private String dteDateCreated;

	private String strUserModified;

	private String dteLastModified;

	private String strAuthorise;

	private Double dbltotQty;

	private String strClientCode;

	private List<clsJOAllocationDtlModel> objJOList;

	// Getter Setter Method

	public String getStrJACode() {
		return strJACode;
	}

	public void setStrJACode(String strJACode) {
		this.strJACode = strJACode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrJANo() {
		return strJANo;
	}

	public void setStrJANo(String strJANo) {
		this.strJANo = strJANo;
	}

	public String getStrSCCode() {
		return strSCCode;
	}

	public void setStrSCCode(String strSCCode) {
		this.strSCCode = strSCCode;
	}

	public String getStrSCName() {
		return strSCName;
	}

	public void setStrSCName(String strSCName) {
		this.strSCName = strSCName;
	}

	public String getDteJADate() {
		return dteJADate;
	}

	public void setDteJADate(String dteJADate) {
		this.dteJADate = dteJADate;
	}

	public String getStrRef() {
		return strRef;
	}

	public void setStrRef(String strRef) {
		this.strRef = strRef;
	}

	public String getDteRefDate() {
		return dteRefDate;
	}

	public void setDteRefDate(String dteRefDate) {
		this.dteRefDate = dteRefDate;
	}

	public String getStrDispatchMode() {
		return strDispatchMode;
	}

	public void setStrDispatchMode(String strDispatchMode) {
		this.strDispatchMode = strDispatchMode;
	}

	public String getStrPayment() {
		return strPayment;
	}

	public void setStrPayment(String strPayment) {
		this.strPayment = strPayment;
	}

	public String getStrTaxes() {
		return strTaxes;
	}

	public void setStrTaxes(String strTaxes) {
		this.strTaxes = strTaxes;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public Double getDbltotQty() {
		return dbltotQty;
	}

	public void setDbltotQty(Double dbltotQty) {
		this.dbltotQty = dbltotQty;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsJOAllocationDtlModel> getObjJOList() {
		return objJOList;
	}

	public void setObjJOList(List<clsJOAllocationDtlModel> objJOList) {
		this.objJOList = objJOList;
	}

}