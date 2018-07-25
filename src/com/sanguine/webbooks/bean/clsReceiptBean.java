package com.sanguine.webbooks.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webbooks.model.clsReceiptInvDtlModel;

public class clsReceiptBean {

	// Variable Declaration
	private String strVouchNo;

	private String strCFCode;

	private String strCFDesc;

	private String strType;

	private String strDebtorCode;

	private String strDebtorName;

	private String strChequeNo;

	private String strDrawnOn;

	private String strBankName;

	private String strBranch;

	private String strNarration;

	private String strSancCode;

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

	private String strReceiptType;

	private String strClientCode;

	private String strPropertyCode;

	private String strModuleType;

	private String dteClearence;

	private String strReceivedFrom;

	private long intOnHold;

	private String strDebtorAccCode;

	private String strDebtorAccDesc;
	
	private String strCurrency;
	
	private double dblConversion;

	private List<clsReceiptDetailBean> listReceiptBeanDtl;

	private List<clsReceiptInvDtlModel> listReceiptInvDtlModel = new ArrayList<clsReceiptInvDtlModel>();

	// Setter-Getter Methods
	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = strVouchNo;
	}

	public String getStrCFCode() {
		return strCFCode;
	}

	public void setStrCFCode(String strCFCode) {
		this.strCFCode = strCFCode;
	}

	public String getStrCFDesc() {
		return strCFDesc;
	}

	public void setStrCFDesc(String strCFDesc) {
		this.strCFDesc = strCFDesc;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = strDebtorCode;
	}

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = strChequeNo;
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

	public String getStrReceiptType() {
		return strReceiptType;
	}

	public void setStrReceiptType(String strReceiptType) {
		this.strReceiptType = strReceiptType;
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

	public String getStrReceivedFrom() {
		return strReceivedFrom;
	}

	public void setStrReceivedFrom(String strReceivedFrom) {
		this.strReceivedFrom = strReceivedFrom;
	}

	public long getIntOnHold() {
		return intOnHold;
	}

	public void setIntOnHold(long intOnHold) {
		this.intOnHold = intOnHold;
	}

	public List<clsReceiptDetailBean> getListReceiptBeanDtl() {
		return listReceiptBeanDtl;
	}

	public void setListReceiptBeanDtl(List<clsReceiptDetailBean> listReceiptBeanDtl) {
		this.listReceiptBeanDtl = listReceiptBeanDtl;
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = strBankName;
	}

	public String getStrDebtorAccCode() {
		return strDebtorAccCode;
	}

	public void setStrDebtorAccCode(String strDebtorAccCode) {
		this.strDebtorAccCode = strDebtorAccCode;
	}

	public String getStrDebtorAccDesc() {
		return strDebtorAccDesc;
	}

	public void setStrDebtorAccDesc(String strDebtorAccDesc) {
		this.strDebtorAccDesc = strDebtorAccDesc;
	}

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = strDebtorName;
	}

	public List<clsReceiptInvDtlModel> getListReceiptInvDtlModel() {
		return listReceiptInvDtlModel;
	}

	public void setListReceiptInvDtlModel(List<clsReceiptInvDtlModel> listReceiptInvDtlModel) {
		this.listReceiptInvDtlModel = listReceiptInvDtlModel;
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
