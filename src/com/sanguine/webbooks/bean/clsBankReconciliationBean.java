package com.sanguine.webbooks.bean;

import java.util.ArrayList;
import java.util.List;

public class clsBankReconciliationBean {
	
	private String dteFromDate;

	private String dteToDate;
	
	private String strCurrency;
	
	private String strGLCode;
	
	private String strBankCode;
	
	private String strTransactionType;
	
	private String strVouchNo;
	
	private String strChequeNo;
	
	private String dteChequeDate;
	
	private double dblAmount;
	
	private String strBankName;
	
	private double dblBalAmount;
	
	public double getDblBalAmount() {
		return dblBalAmount;
	}

	public void setDblBalAmount(double dblBalAmount) {
		this.dblBalAmount = dblBalAmount;
	}

	private List<clsBankReconciliationDetailBean> listBankReconciliationDtl=new ArrayList<clsBankReconciliationDetailBean>();

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

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public List<clsBankReconciliationDetailBean> getListBankReconciliationDtl() {
		return listBankReconciliationDtl;
	}

	public void setListBankReconciliationDtl(List<clsBankReconciliationDetailBean> listBankReconciliationDtl) {
		this.listBankReconciliationDtl = listBankReconciliationDtl;
	}

	public String getStrGLCode() {
		return strGLCode;
	}

	public void setStrGLCode(String strGLCode) {
		this.strGLCode = strGLCode;
	}

	public String getStrBankCode() {
		return strBankCode;
	}

	public void setStrBankCode(String strBankCode) {
		this.strBankCode = strBankCode;
	}

	public String getStrTransactionType() {
		return strTransactionType;
	}

	public void setStrTransactionType(String strTransactionType) {
		this.strTransactionType = strTransactionType;
	}

	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = strVouchNo;
	}

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = strChequeNo;
	}

	public String getDteChequeDate() {
		return dteChequeDate;
	}

	public void setDteChequeDate(String dteChequeDate) {
		this.dteChequeDate = dteChequeDate;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = strBankName;
	}
	
	

}
