package com.sanguine.crm.bean;

import java.util.List;

import com.sanguine.crm.model.clsJobOrderModel;

public class clsJobOrderBean {

	// Variable Declaration
	private String strJOCode;

	private Long intId;

	private String dteJODate;

	private String strSOCode;

	private String strProdCode;

	private Double dblQty;

	private String strParentJOCode;

	private String strStatus;

	private String strAuthorise;

	private String strUserCreated;

	private String dteDateCreated;

	private String strUserModified;

	private String dteLastModified;

	private String strClientCode;

	private List<clsJobOrderModel> jobOrderList;

	// Setter-Getter Methods

	public String getStrJOCode() {
		return strJOCode;
	}

	public void setStrJOCode(String strJOCode) {
		this.strJOCode = strJOCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getDteJODate() {
		return dteJODate;
	}

	public void setDteJODate(String dteJODate) {
		this.dteJODate = dteJODate;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrParentJOCode() {
		return strParentJOCode;
	}

	public void setStrParentJOCode(String strParentJOCode) {
		this.strParentJOCode = strParentJOCode;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
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

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsJobOrderModel> getJobOrderList() {
		return jobOrderList;
	}

	public void setJobOrderList(List<clsJobOrderModel> jobOrderList) {
		this.jobOrderList = jobOrderList;
	}

}
