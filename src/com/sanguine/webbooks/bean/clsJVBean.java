package com.sanguine.webbooks.bean;

import java.util.List;

import javax.persistence.Column;

import com.sanguine.webbooks.model.clsJVDtlModel;

public class clsJVBean {
	// Variable Declaration
	private String strVouchNo;

	private long intId;

	private String strNarration;

	private String strSancCode;

	private String strType;

	private String dteVouchDate;

	private long intVouchMonth;

	private double dblAmt;

	private String strTransType;

	private String strTransMode;

	private String strModuleType;

	private String strMasterPOS;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String strPropertyCode;
	
	private String strSource;
	
	private String strSourceDocNo;
	
   private String strCurrency;
	
	private double dblConversion;
	

	private List<clsJVDetailsBean> listJVDtlBean;

	public List<clsJVDetailsBean> getListJVDtlBean() {
		return listJVDtlBean;
	}

	public void setListJVDtlBean(List<clsJVDetailsBean> listJVDtlBean) {
		this.listJVDtlBean = listJVDtlBean;
	}

	// Setter-Getter Methods
	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = strVouchNo;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrSancCode() {
		return strSancCode;
	}

	public void setStrSancCode(String strSancCode) {
		this.strSancCode = strSancCode;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getDteVouchDate() {
		return dteVouchDate;
	}

	public void setDteVouchDate(String dteVouchDate) {
		this.dteVouchDate = dteVouchDate;
	}

	public long getIntVouchMonth() {
		return intVouchMonth;
	}

	public void setIntVouchMonth(long intVouchMonth) {
		this.intVouchMonth = intVouchMonth;
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = dblAmt;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public String getStrTransMode() {
		return strTransMode;
	}

	public void setStrTransMode(String strTransMode) {
		this.strTransMode = strTransMode;
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = strModuleType;
	}

	public String getStrMasterPOS() {
		return strMasterPOS;
	}

	public void setStrMasterPOS(String strMasterPOS) {
		this.strMasterPOS = strMasterPOS;
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

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrSource() {
		return strSource;
	}

	public void setStrSource(String strSource) {
		this.strSource = strSource;
	}

	public String getStrSourceDocNo() {
		return strSourceDocNo;
	}

	public void setStrSourceDocNo(String strSourceDocNo) {
		this.strSourceDocNo = strSourceDocNo;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}
}
