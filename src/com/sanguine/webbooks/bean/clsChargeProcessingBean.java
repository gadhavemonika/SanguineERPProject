package com.sanguine.webbooks.bean;

import java.util.List;

import com.sanguine.webbooks.model.clsChargeProcessingDtlModel;

public class clsChargeProcessingBean {
	// Variable Declaration
	private long intGid;

	private String strMemberCode;

	private String strMemberName;
	
	
	private String strAccountCode;

	private String strAccountName;

	private List<clsChargeProcessingDtlModel> listChargeDtl;

	private String dteFromDate;

	private String dteToDate;

	private String dteGeneratedOn;

	private String strInstantJVYN;

	private String strAnnualChargeProcessYN;

	private String strOtherFunctions;

	private String strClientCode;

	private String strPropertyCode;

	// Setter-Getter Methods

	public long getIntGid() {
		return intGid;
	}

	public void setIntGid(long intGid) {
		this.intGid = intGid;
	}

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = strMemberCode;
	}

	public String getStrMemberName() {
		return strMemberName;
	}

	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}

	public List<clsChargeProcessingDtlModel> getListChargeDtl() {
		return listChargeDtl;
	}

	public void setListChargeDtl(List<clsChargeProcessingDtlModel> listChargeDtl) {
		this.listChargeDtl = listChargeDtl;
	}

	public String getDteFromDate() {
		return dteFromDate;
	}

	public void setDteFromDate(String dteFromDate) {
		this.dteFromDate = dteFromDate;
	}

	public String getDteToDate() {
		return dteToDate;
	}

	public void setDteToDate(String dteToDate) {
		this.dteToDate = dteToDate;
	}

	public String getDteGeneratedOn() {
		return dteGeneratedOn;
	}

	public void setDteGeneratedOn(String dteGeneratedOn) {
		this.dteGeneratedOn = dteGeneratedOn;
	}

	public String getStrInstantJVYN() {
		return strInstantJVYN;
	}

	public void setStrInstantJVYN(String strInstantJVYN) {
		this.strInstantJVYN = strInstantJVYN;
	}

	public String getStrAnnualChargeProcessYN() {
		return strAnnualChargeProcessYN;
	}

	public void setStrAnnualChargeProcessYN(String strAnnualChargeProcessYN) {
		this.strAnnualChargeProcessYN = strAnnualChargeProcessYN;
	}

	public String getStrOtherFunctions() {
		return strOtherFunctions;
	}

	public void setStrOtherFunctions(String strOtherFunctions) {
		this.strOtherFunctions = strOtherFunctions;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrAccountCode()
	{
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode)
	{
		this.strAccountCode = strAccountCode;
	}

	public String getStrAccountName()
	{
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName)
	{
		this.strAccountName = strAccountName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
