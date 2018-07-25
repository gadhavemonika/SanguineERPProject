package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseManualSaleDtlModel;

public class clsExciseManualSaleBean {

	// Variable Declaration

	private Long intId;

	private String strLicenceCode;

	private String strLicenceNo;

	private String dteBillDate;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String strSourceEntry;

	private List<clsExciseManualSaleDtlModel> objSalesDtlList;

	// Setter-Getter Methods

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public String getStrLicenceNo() {
		return strLicenceNo;
	}

	public void setStrLicenceNo(String strLicenceNo) {
		this.strLicenceNo = strLicenceNo;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrSourceEntry() {
		return strSourceEntry;
	}

	public void setStrSourceEntry(String strSourceEntry) {
		this.strSourceEntry = strSourceEntry;
	}

	public List<clsExciseManualSaleDtlModel> getObjSalesDtlList() {
		return objSalesDtlList;
	}

	public void setObjSalesDtlList(List<clsExciseManualSaleDtlModel> objSalesDtlList) {
		this.objSalesDtlList = objSalesDtlList;
	}

}
