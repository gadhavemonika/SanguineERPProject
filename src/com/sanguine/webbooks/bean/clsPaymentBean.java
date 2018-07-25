package com.sanguine.webbooks.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webbooks.model.clsPaymentGRNDtlModel;
import com.sanguine.webbooks.model.clsPaymentScBillDtlModel;

public class clsPaymentBean {
	// Variable Declaration
	private String strVouchNo;

	private String strBankCode;

	private String strBankAccDesc;

	private String strNarration;

	private String strSancCode;

	private String strChequeNo;

	private double dblAmt;

	private String strCrDr;

	private String dteVouchDate;

	private String dteChequeDate;

	private long intVouchMonth;

	private long intVouchNum;

	private String strTransMode;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strModuleType;

	private String dteClearence;

	private long intMonth;

	private String strType;

	private String strDrawnOn;

	private String strDrawnDesc;

	private String strBranch;

	private String strClientCode;

	private String strPropertyCode;
	
	private String strCurrency;
	
	private double dblConversion;

	private List<clsPaymentDetailsBean> listPaymentDetailsBean;

	private List<clsPaymentGRNDtlModel> listPaymentGRNDtl = new ArrayList<clsPaymentGRNDtlModel>();

	private List<clsPaymentScBillDtlModel> listPaymentSCDtl = new ArrayList<clsPaymentScBillDtlModel>();

	// Setter-Getter Methods
	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = strVouchNo;
	}

	public String getStrBankCode() {
		return strBankCode;
	}

	public void setStrBankCode(String strBankCode) {
		this.strBankCode = strBankCode;
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

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = strChequeNo;
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = dblAmt;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}

	public String getDteVouchDate() {
		return dteVouchDate;
	}

	public void setDteVouchDate(String dteVouchDate) {
		this.dteVouchDate = dteVouchDate;
	}

	public String getDteChequeDate() {
		return dteChequeDate;
	}

	public void setDteChequeDate(String dteChequeDate) {
		this.dteChequeDate = dteChequeDate;
	}

	public long getIntVouchMonth() {
		return intVouchMonth;
	}

	public void setIntVouchMonth(long intVouchMonth) {
		this.intVouchMonth = intVouchMonth;
	}

	public long getIntVouchNum() {
		return intVouchNum;
	}

	public void setIntVouchNum(long intVouchNum) {
		this.intVouchNum = intVouchNum;
	}

	public String getStrTransMode() {
		return strTransMode;
	}

	public void setStrTransMode(String strTransMode) {
		this.strTransMode = strTransMode;
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

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = strModuleType;
	}

	public String getDteClearence() {
		return dteClearence;
	}

	public void setDteClearence(String dteClearence) {
		this.dteClearence = dteClearence;
	}

	public long getIntMonth() {
		return intMonth;
	}

	public void setIntMonth(long intMonth) {
		this.intMonth = intMonth;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrDrawnOn() {
		return strDrawnOn;
	}

	public void setStrDrawnOn(String strDrawnOn) {
		this.strDrawnOn = strDrawnOn;
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = strBranch;
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

	public List<clsPaymentDetailsBean> getListPaymentDetailsBean() {
		return listPaymentDetailsBean;
	}

	public void setListPaymentDetailsBean(List<clsPaymentDetailsBean> listPaymentDetailsBean) {
		this.listPaymentDetailsBean = listPaymentDetailsBean;
	}

	public String getStrBankAccDesc() {
		return strBankAccDesc;
	}

	public void setStrBankAccDesc(String strBankAccDesc) {
		this.strBankAccDesc = strBankAccDesc;
	}

	public String getStrDrawnDesc() {
		return strDrawnDesc;
	}

	public void setStrDrawnDesc(String strDrawnDesc) {
		this.strDrawnDesc = strDrawnDesc;
	}

	public List<clsPaymentGRNDtlModel> getListPaymentGRNDtl() {
		return listPaymentGRNDtl;
	}

	public void setListPaymentGRNDtl(List<clsPaymentGRNDtlModel> listPaymentGRNDtl) {
		this.listPaymentGRNDtl = listPaymentGRNDtl;
	}

	public List<clsPaymentScBillDtlModel> getListPaymentSCDtl() {
		return listPaymentSCDtl;
	}

	public void setListPaymentSCDtl(List<clsPaymentScBillDtlModel> listPaymentSCDtl) {
		this.listPaymentSCDtl = listPaymentSCDtl;
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
