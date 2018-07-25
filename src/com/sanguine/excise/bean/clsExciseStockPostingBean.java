package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseStkPostingDtlModel;

public class clsExciseStockPostingBean {

	// Variable Declaration

	private String strPSPCode;

	private Long intId;

	private String dtePostingDate;

	private String strLicenceCode;

	private String strLicenceNo;

	private String strUserCreated;

	private String dteDateCreated;

	private String strUserModified;

	private String dteDateEdited;

	private String strClientCode;

	private List<clsExciseStkPostingDtlModel> phyStocklist;

	// Setter-Getter Methods

	public String getStrPSPCode() {
		return strPSPCode;
	}

	public void setStrPSPCode(String strPSPCode) {
		this.strPSPCode = strPSPCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getDtePostingDate() {
		return dtePostingDate;
	}

	public void setDtePostingDate(String dtePostingDate) {
		this.dtePostingDate = dtePostingDate;
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

	public List<clsExciseStkPostingDtlModel> getPhyStocklist() {
		return phyStocklist;
	}

	public void setPhyStocklist(List<clsExciseStkPostingDtlModel> phyStocklist) {
		this.phyStocklist = phyStocklist;
	}

}
