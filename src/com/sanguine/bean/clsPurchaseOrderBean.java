package com.sanguine.bean;

import java.util.List;

import javax.persistence.Column;

import com.sanguine.model.clsPOTaxDtlModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.model.clsTCMasterModel;

public class clsPurchaseOrderBean {
	private String strPOCode;
	private String dtPODate;
	private String strSuppCode;
	private String strAgainst;
	private String strSOCode;
	private double dblTotal;
	private String strVAddress1;
	private String strVAddress2;
	private String strVCity;
	private String strVState;
	private String strVCountry;
	private String strVPin;
	private String strSAddress1;
	private String strSAddress2;
	private String strSCity;
	private String strSState;
	private String strSCountry;
	private String strSPin;
	private String strYourRef;
	private String strPerRef;
	private String strEOE;
	private String strCode;
	private String strUserCreated;
	private String dteDateCreated;
	private String strUserModified;
	private String dteLastModified;
	private String strClosePO;
	private String strAuthorise;
	private String dtDelDate;
	private double dblTaxAmt;
	private double dblExtra;
	private double dblFinalAmt;
	private String strExcise;
	private double dblDiscount;
	private String strPayMode;
	private String strCurrency;
	private String strAmedment;
	private String strAmntNO;
	private String stredit;
	private String strUserAmed;
	private String dtPayDate;
	private double dblConversion;
	private double dblFOB;
	private double dblFreight;
	private double dblInsurance;
	private double dblOtherCharges;
	private double dblCIF;
	private double dblClearingAgentCharges;
	private double dblVATClaim;

	private List<clsTCMasterModel> listTCMaster;

	private List<clsPurchaseOrderDtlModel> listPODtlModel;

	private List<clsPOTaxDtlModel> listPOTaxDtl;

	public List<clsPOTaxDtlModel> getListPOTaxDtl() {
		return listPOTaxDtl;
	}

	public void setListPOTaxDtl(List<clsPOTaxDtlModel> listPOTaxDtl) {
		this.listPOTaxDtl = listPOTaxDtl;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
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

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public String getStrVAddress1() {
		return strVAddress1;
	}

	public void setStrVAddress1(String strVAddress1) {
		this.strVAddress1 = strVAddress1;
	}

	public String getStrVAddress2() {
		return strVAddress2;
	}

	public void setStrVAddress2(String strVAddress2) {
		this.strVAddress2 = strVAddress2;
	}

	public String getStrVCity() {
		return strVCity;
	}

	public void setStrVCity(String strVCity) {
		this.strVCity = strVCity;
	}

	public String getStrVState() {
		return strVState;
	}

	public void setStrVState(String strVState) {
		this.strVState = strVState;
	}

	public String getStrVCountry() {
		return strVCountry;
	}

	public void setStrVCountry(String strVCountry) {
		this.strVCountry = strVCountry;
	}

	public String getStrVPin() {
		return strVPin;
	}

	public void setStrVPin(String strVPin) {
		this.strVPin = strVPin;
	}

	public String getStrSAddress1() {
		return strSAddress1;
	}

	public void setStrSAddress1(String strSAddress1) {
		this.strSAddress1 = strSAddress1;
	}

	public String getStrSAddress2() {
		return strSAddress2;
	}

	public void setStrSAddress2(String strSAddress2) {
		this.strSAddress2 = strSAddress2;
	}

	public String getStrSCity() {
		return strSCity;
	}

	public void setStrSCity(String strSCity) {
		this.strSCity = strSCity;
	}

	public String getStrSState() {
		return strSState;
	}

	public void setStrSState(String strSState) {
		this.strSState = strSState;
	}

	public String getStrSCountry() {
		return strSCountry;
	}

	public void setStrSCountry(String strSCountry) {
		this.strSCountry = strSCountry;
	}

	public String getStrSPin() {
		return strSPin;
	}

	public void setStrSPin(String strSPin) {
		this.strSPin = strSPin;
	}

	public String getStrYourRef() {
		return strYourRef;
	}

	public void setStrYourRef(String strYourRef) {
		this.strYourRef = strYourRef;
	}

	public String getStrPerRef() {
		return strPerRef;
	}

	public void setStrPerRef(String strPerRef) {
		this.strPerRef = strPerRef;
	}

	public String getStrEOE() {
		return strEOE;
	}

	public void setStrEOE(String strEOE) {
		this.strEOE = strEOE;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
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

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClosePO() {
		return strClosePO;
	}

	public void setStrClosePO(String strClosePO) {
		this.strClosePO = strClosePO;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getDtDelDate() {
		return dtDelDate;
	}

	public void setDteDelDate(String dtDelDate) {
		this.dtDelDate = dtDelDate;
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

	public double getDblFinalAmt() {
		return dblFinalAmt;
	}

	public void setDblFinalAmt(double dblFinalAmt) {
		this.dblFinalAmt = dblFinalAmt;
	}

	public String getStrExcise() {
		return strExcise;
	}

	public void setStrExcise(String strExcise) {
		this.strExcise = strExcise;
	}

	public double getDblDiscount() {
		return dblDiscount;
	}

	public void setDblDiscount(double dblDiscount) {
		this.dblDiscount = dblDiscount;
	}

	public String getStrPayMode() {
		return strPayMode;
	}

	public void setStrPayMode(String strPayMode) {
		this.strPayMode = strPayMode;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getStrAmedment() {
		return strAmedment;
	}

	public void setStrAmedment(String strAmedment) {
		this.strAmedment = strAmedment;
	}

	public String getStrAmntNO() {
		return strAmntNO;
	}

	public void setStrAmntNO(String strAmntNO) {
		this.strAmntNO = strAmntNO;
	}

	public String getStredit() {
		return stredit;
	}

	public void setStredit(String stredit) {
		this.stredit = stredit;
	}

	public String getStrUserAmed() {
		return strUserAmed;
	}

	public void setStrUserAmed(String strUserAmed) {
		this.strUserAmed = strUserAmed;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public List<clsPurchaseOrderDtlModel> getListPODtlModel() {
		return listPODtlModel;
	}

	public void setListPODtlModel(List<clsPurchaseOrderDtlModel> listPODtlModel) {
		this.listPODtlModel = listPODtlModel;
	}

	public List<clsTCMasterModel> getListTCMaster() {
		return listTCMaster;
	}

	public void setListTCMaster(List<clsTCMasterModel> listTCMaster) {
		this.listTCMaster = listTCMaster;
	}

	public void setDtDelDate(String dtDelDate) {
		this.dtDelDate = dtDelDate;
	}

	public String getDtPODate() {
		return dtPODate;
	}

	public void setDtPODate(String dtPODate) {
		this.dtPODate = dtPODate;
	}

	public String getDtPayDate() {
		return dtPayDate;
	}

	public void setDtPayDate(String dtPayDate) {
		this.dtPayDate = dtPayDate;
	}

	public double getDblFOB() {
		return dblFOB;
	}

	public void setDblFOB(double dblFOB) {
		this.dblFOB = dblFOB;
	}

	public double getDblFreight() {
		return dblFreight;
	}

	public void setDblFreight(double dblFreight) {
		this.dblFreight = dblFreight;
	}

	public double getDblInsurance() {
		return dblInsurance;
	}

	public void setDblInsurance(double dblInsurance) {
		this.dblInsurance = dblInsurance;
	}

	public double getDblOtherCharges() {
		return dblOtherCharges;
	}

	public void setDblOtherCharges(double dblOtherCharges) {
		this.dblOtherCharges = dblOtherCharges;
	}

	public double getDblCIF() {
		return dblCIF;
	}

	public void setDblCIF(double dblCIF) {
		this.dblCIF = dblCIF;
	}

	public double getDblClearingAgentCharges() {
		return dblClearingAgentCharges;
	}

	public void setDblClearingAgentCharges(double dblClearingAgentCharges) {
		this.dblClearingAgentCharges = dblClearingAgentCharges;
	}

	public double getDblVATClaim() {
		return dblVATClaim;
	}

	public void setDblVATClaim(double dblVATClaim) {
		this.dblVATClaim = dblVATClaim;
	}
}
