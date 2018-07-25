package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsPurchaseReturnDtlModel;
import com.sanguine.model.clsPurchaseReturnTaxDtlModel;


public class clsPurchaseReturnBean {
	private String strPRCode, strPRNo, strSuppCode, strAgainst, strGRNCode, strNarration, strLocCode, strVehNo, strMInBy, dtPRDate;

	private String strTimeInOut, strPurCode, strUserCreated, strUserModified, strCurrency, dtDateCreated, dtLastModified, strClientCode;
	private char strAuthorise;
	private double dblSubTotal, dblDisRate, dblDisAmt, dblTaxAmt, dblExtra, dblTotal, dblConversion;
	private double intid;
	private List<clsPurchaseReturnDtlModel> listPurchaseReturnDtl;
	private List<clsPurchaseReturnTaxDtlModel> listPurchaseReturnTaxDtl;

	public List<clsPurchaseReturnDtlModel> getListPurchaseReturnDtl() {
		return listPurchaseReturnDtl;
	}

	public void setListPurchaseReturnDtl(List<clsPurchaseReturnDtlModel> listPurchaseReturnDtl) {
		this.listPurchaseReturnDtl = listPurchaseReturnDtl;
	}

	public String getStrPRCode() {
		return strPRCode;
	}

	public void setStrPRCode(String strPRCode) {
		this.strPRCode = strPRCode;
	}

	public String getDtPRDate() {
		return dtPRDate;
	}

	public void setDtPRDate(String dtPRDate) {
		this.dtPRDate = dtPRDate;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}

	public String getStrMInBy() {
		return strMInBy;
	}

	public void setStrMInBy(String strMInBy) {
		this.strMInBy = strMInBy;
	}

	public String getStrTimeInOut() {
		return strTimeInOut;
	}

	public void setStrTimeInOut(String strTimeInOut) {
		this.strTimeInOut = strTimeInOut;
	}

	public String getStrPurCode() {
		return strPurCode;
	}

	public void setStrPurCode(String strPurCode) {
		this.strPurCode = strPurCode;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public char getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(char strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public double getDblDisRate() {
		return dblDisRate;
	}

	public void setDblDisRate(double dblDisRate) {
		this.dblDisRate = dblDisRate;
	}

	public double getDblDisAmt() {
		return dblDisAmt;
	}

	public void setDblDisAmt(double dblDisAmt) {
		this.dblDisAmt = dblDisAmt;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblExtra() {
		return dblExtra;
	}

	public void setDblExtra(double dblExtra) {
		this.dblExtra = dblExtra;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public double getIntid() {
		return intid;
	}

	public void setIntid(double intid) {
		this.intid = intid;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPRNo() {
		return strPRNo;
	}

	public void setStrPRNo(String strPRNo) {
		this.strPRNo = strPRNo;
	}

	public List<clsPurchaseReturnTaxDtlModel> getListPurchaseReturnTaxDtl() {
		return listPurchaseReturnTaxDtl;
	}

	public void setListPurchaseReturnTaxDtl(
			List<clsPurchaseReturnTaxDtlModel> listPurchaseReturnTaxDtl) {
		this.listPurchaseReturnTaxDtl = listPurchaseReturnTaxDtl;
	}
	
	
}
