package com.sanguine.crm.bean;

import java.io.Serializable;
import java.util.List;

import com.sanguine.crm.model.clsSalesRetrunTaxModel;
import com.sanguine.crm.model.clsSalesReturnDtlModel;

public class clsSalesReturnBean implements Serializable {

	private String strSRCode;

	private String dteSRDate;

	private String strAgainst;

	private String strDCCode;

	private String strCustCode;

	private String strLocCode;

	private String dblTotalAmt;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String strLocName;

	private String strCustName;;

	private double dblTaxAmt;
	
	private double dblDiscPer;
	
	private double dblDiscAmt;
	
	private String strSettlementCode;
	
	private String strPropertyName;
	
	private double dblGrandTotal;
	
	private String strCategory;
	
	private String strProductName;
	
	private String strCurrency;
	private double dblConversion;
	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	private List<clsSalesRetrunTaxModel> listSalesRetrunTaxModel;
	//
	// private String strProdCode;
	//
	// private double dblPrice;
	//
	// private double dblWeight;
	//

	private List<clsSalesReturnDtlModel> listSalesReturn;

	
	
	public String getStrProductName() {
		return strProductName;
	}

	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}

	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}

	public double getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(double dblGrandTotal) {
		this.dblGrandTotal = dblGrandTotal;
	}

	public String getStrPropertyName() {
		return strPropertyName;
	}

	public void setStrPropertyName(String strPropertyName) {
		this.strPropertyName = strPropertyName;
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = strSRCode;
	}

	public String getDteSRDate() {
		return dteSRDate;
	}

	public void setDteSRDate(String dteSRDate) {
		this.dteSRDate = dteSRDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrDCCode() {
		return strDCCode;
	}

	public void setStrDCCode(String strDCCode) {
		this.strDCCode = strDCCode;
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(String dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
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

	public List<clsSalesReturnDtlModel> getListSalesReturn() {
		return listSalesReturn;
	}

	public void setListSalesReturn(List<clsSalesReturnDtlModel> listSalesReturn) {
		this.listSalesReturn = listSalesReturn;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrCustName() {
		return strCustName;
	}

	public void setStrCustName(String strCustName) {
		this.strCustName = strCustName;
	}

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public List<clsSalesRetrunTaxModel> getListSalesRetrunTaxModel() {
		return listSalesRetrunTaxModel;
	}

	public void setListSalesRetrunTaxModel(List<clsSalesRetrunTaxModel> listSalesRetrunTaxModel) {
		this.listSalesRetrunTaxModel = listSalesRetrunTaxModel;
	}

	public double getDblDiscPer() {
		return dblDiscPer;
	}

	public void setDblDiscPer(double dblDiscPer) {
		this.dblDiscPer = dblDiscPer;
	}

	public double getDblDiscAmt() {
		return dblDiscAmt;
	}

	public void setDblDiscAmt(double dblDiscAmt) {
		this.dblDiscAmt = dblDiscAmt;
	}

}
