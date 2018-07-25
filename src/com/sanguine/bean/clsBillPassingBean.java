package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsBillPassDtlModel;
import com.sanguine.model.clsBillPassingTaxDtlModel;

public class clsBillPassingBean {

	private String strBillPassNo;
	private String strBillNo;
	private String dtBillDate;
	private String strSuppCode;
	private String strPVno;
	private double dblBillAmt;
	private String dtPassDate;
	private String strNarration;
	private String strCurrency;
	private String strAgainst;
	private String strAgainstCode;
	private List<clsBillPassDtlModel> listBillPassDtl;
	private List<clsBillPassingTaxDtlModel> listBillPassTaxDtl;
	private long intId;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getDtBillDate() {
		return dtBillDate;
	}

	public void setDtBillDate(String dtBillDate) {
		this.dtBillDate = dtBillDate;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrPVno() {
		return strPVno;
	}

	public void setStrPVno(String strPVno) {
		this.strPVno = strPVno;
	}

	public double getDblBillAmt() {
		return dblBillAmt;
	}

	public void setDblBillAmt(double dblBillAmt) {
		this.dblBillAmt = dblBillAmt;
	}

	public String getDtPassDate() {
		return dtPassDate;
	}

	public void setDtPassDate(String dtPassDate) {
		this.dtPassDate = dtPassDate;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrAgainstCode() {
		return strAgainstCode;
	}

	public void setStrAgainstCode(String strAgainstCode) {
		this.strAgainstCode = strAgainstCode;
	}

	public List<clsBillPassDtlModel> getListBillPassDtl() {
		return listBillPassDtl;
	}

	public void setListBillPassDtl(List<clsBillPassDtlModel> listBillPassDtl) {
		this.listBillPassDtl = listBillPassDtl;
	}

	public String getStrBillPassNo() {
		return strBillPassNo;
	}

	public void setStrBillPassNo(String strBillPassNo) {
		this.strBillPassNo = strBillPassNo;
	}

	public List<clsBillPassingTaxDtlModel> getListBillPassTaxDtl() {
		return listBillPassTaxDtl;
	}

	public void setListBillPassTaxDtl(List<clsBillPassingTaxDtlModel> listBillPassTaxDtl) {
		this.listBillPassTaxDtl = listBillPassTaxDtl;
	}

}
