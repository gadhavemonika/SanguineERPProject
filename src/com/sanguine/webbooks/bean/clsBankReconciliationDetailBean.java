package com.sanguine.webbooks.bean;

public class clsBankReconciliationDetailBean {

	
	private String strTransType;

	private String strVouchNo;
	
	private String strChequeNo;
	
	private String dteCheque;
	
	private String dblAmount;
	
	private double dblCreditAmt;
	
	private String dteClearing;
	
	private boolean chequeCleared;
	
	
	
	public String getStrTransType() {
		return strTransType;
	}
	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
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
	public String getDteCheque() {
		return dteCheque;
	}
	public void setDteCheque(String dteCheque) {
		this.dteCheque = dteCheque;
	}
	public String getDblAmount() {
		return dblAmount;
	}
	public void setDblAmount(String dblAmount) {
		this.dblAmount = dblAmount;
	}
	public double getDblCreditAmt() {
		return dblCreditAmt;
	}
	public void setDblCreditAmt(double dblCreditAmt) {
		this.dblCreditAmt = dblCreditAmt;
	}
	public String getDteClearing() {
		return dteClearing;
	}
	public void setDteClearing(String dteClearing) {
		this.dteClearing = dteClearing;
	}
	public boolean isChequeCleared() {
		return chequeCleared;
	}
	public void setChequeCleared(boolean chequeCleared) {
		this.chequeCleared = chequeCleared;
	}
	
	
}
