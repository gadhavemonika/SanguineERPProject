package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sanguine.base.model.clsBaseModel;

@Entity
@Table(name = "tblpropertysetup")
public class clsPropertySetupModel extends clsBaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	private long intId;

	private String strCompanyCode;
	@Id
	@Column(nullable = false, updatable = false)
	private String strPropertyCode;
	private String strIndustryType;

	// private String strCompanyName;
	private String strAdd1;
	private String strAdd2;
	private String strCity;
	private String strState;
	private String strCountry;
	private String strPin;
	private String strBAdd1;
	private String strBAdd2;
	private String strBCity;
	private String strBState;
	private String strBCountry;
	private String strBPin;
	private String strSAdd1;
	private String strSAdd2;
	private String strSCity;
	private String strSState;
	private String strSCountry;
	private String strSPin;
	private String strPhone;
	private String strFax;
	private String strEmail;
	private String strWebsite;
	private String intDueDays;
	private String strCST;
	private String strVAT;
	private String strSerTax;
	private String strPanNo;
	private String strLocCode;
	private String strAsseeCode;
	private String strPurEmail;
	private String strSaleEmail;
	private String strRangeDiv;
	private String strCommi;
	private String strRegNo;
	private String strDivision;
	private String dblBondAmt;
	private String strAcceptanceNo;
	private String strMask;
	private String strRangeAdd;
	private String strClientCode;

	@Column(name = "strDivisionAdd", columnDefinition = "VARCHAR(255) default ''")
	private String strDivisionAdd;

	@Column(name = "strECCNo", columnDefinition = "VARCHAR(255) default ''")
	private String strECCNo;

	// general Tab
	private String strNegStock;
	private String strPOBOM;
	private String strSOBOM;
	private String strTotalWorkhour;
	private String dtFromTime;
	private String dtToTime;
	private String strWorkFlowbasedAuth;
	private int intdec;
	@Column(name = "intqtydec", columnDefinition = "Int(8) default '0'")
	private int intqtydec;
	private String strListPriceInPO;
	private String strCMSModule;
	private String strBatchMethod;
	private String strTPostingType;
	private String strAudit;
	private String strAutoDC;
	@Column(name = "strRatePickUpFrom", columnDefinition = "VARCHAR(50) default 'PurchaseRate'")
	private String strRatePickUpFrom;
	@Column(name = "strShowReqVal", columnDefinition = "CHAR(1) default 'Y'")
	private String strShowReqVal;
	@Column(name = "strShowStkReq", columnDefinition = "CHAR(1) default 'Y'")
	private String strShowStkReq;
	@Column(name = "strShowValMISSlip", columnDefinition = "CHAR(1) default 'Y'")
	private String strShowValMISSlip;
	@Column(name = "strChangeUOMTrans", columnDefinition = "CHAR(1) default 'Y'")
	private String strChangeUOMTrans;
	@Column(name = "strShowProdMaster", columnDefinition = "CHAR(1) default 'Y'")
	private String strShowProdMaster;
	@Column(name = "strShowProdDoc", columnDefinition = "CHAR(1) default 'Y'")
	private String strShowProdDoc;
	@Column(name = "strAllowDateChangeInMIS", columnDefinition = "CHAR(1) default 'Y'")
	private String strAllowDateChangeInMIS;
	@Column(name = "strShowTransAsc_Desc", columnDefinition = "CHAR(5) default 'Asc'")
	private String strShowTransAsc_Desc;
	@Column(name = "strNameChangeProdMast", columnDefinition = "CHAR(1) default 'Y'")
	private String strNameChangeProdMast;
	@Column(name = "strStkAdjReason", columnDefinition = "VARCHAR(50) default ''")
	private String strStkAdjReason;
	@Column(name = "intNotificationTimeinterval", columnDefinition = "Int(8) default '1'")
	private int intNotificationTimeinterval;
	@Column(name = "strMonthEnd", columnDefinition = "CHAR(1) default 'Y'")
	private String strMonthEnd;

	@Column(name = "strShowAllProdToAllLoc", columnDefinition = "CHAR(1) default 'Y'")
	private String strShowAllProdToAllLoc;

	@Column(name = "strLocWiseProductionOrder", columnDefinition = "CHAR(1) default 'Y'")
	private String strLocWiseProductionOrder;

	@Column(name = "strShowStockInOP", columnDefinition = "CHAR(1) default 'N'")
	private String strShowStockInOP;

	@Column(name = "strShowAvgQtyInOP", columnDefinition = "CHAR(1) default 'N'")
	private String strShowAvgQtyInOP;

	@Column(name = "strShowStockInSO", columnDefinition = "CHAR(1) default 'N'")
	private String strShowStockInSO;

	@Column(name = "strShowAvgQtyInSO", columnDefinition = "CHAR(1) default 'N'")
	private String strShowAvgQtyInSO;

	@Column(name = "strEffectOfDiscOnPO", columnDefinition = "VARCHAR(1) default 'N'")
	private String strEffectOfDiscOnPO;

	@Column(name = "strInvFormat", columnDefinition = "VARCHAR(15) default ''")
	private String strInvFormat;

	@Column(name = "strInvNote", columnDefinition = "VARCHAR(15) default ''")
	private String strInvNote;

	@Column(name = "strCurrencyCode", columnDefinition = "VARCHAR(15) default ''")
	private String strCurrencyCode;

	@Column(name = "strShowAllPropCustomer", columnDefinition = "VARCHAR(1) default 'Y'")
	private String strShowAllPropCustomer;

	@Column(name = "strEffectOfInvoice", columnDefinition = "VARCHAR(1) default 'DC'")
	private String strEffectOfInvoice;

	@Column(name = "strEffectOfGRNWebBook", columnDefinition = "VARCHAR(20) default 'Payment'")
	private String strEffectOfGRNWebBook;

	@Column(name = "strMultiCurrency", columnDefinition = "VARCHAR(1) default 'N'")
	private String strMultiCurrency;

	@Column(name = "strShowAllPartyToAllLoc", columnDefinition = "CHAR(1) default 'N'")
	private String strShowAllPartyToAllLoc;

	@Column(name = "strShowAllTaxesOnTransaction", columnDefinition = "VARCHAR(1) default 'Y'")
	private String strShowAllTaxesOnTransaction;
		
	private String strSOKOTPrint;
	
	@Column(name = "strRateHistoryFormat", columnDefinition = "VARCHAR(15) default ''")
	private String strRateHistoryFormat;
	
	@Column(name = "strPOSlipFormat", columnDefinition = "VARCHAR(15) default ''")
	private String strPOSlipFormat;
	
	@Column(name = "strSRSlipFormat", columnDefinition = "VARCHAR(15) default ''")
	private String strSRSlipFormat;
	
	
	
	// BAnk Dtl Tab
	private String strBankName;
	private String strBranchName;
	private String strBankAdd1;
	private String strBankAdd2;
	private String strBankCity;
	private String strBankAccountNo;
	private String strSwiftCode;
	// End of BAnk Dtl Tab

	// Supplier Performance Tab
	private String strLate;
	private String strRej;
	private String strPChange;
	private String strExDelay;
	private String strAuditFrom;
	// End Supplier Performance Tab

	@Column(nullable = false, updatable = false)
	private String strUserCreated;
	@Column(nullable = false, updatable = false)
	private String dtCreatedDate;
	private String strUserModified;
	private String dtLastModified;

	// SMS setUp Tab Start
	@Column(name = "strSMSProvider", columnDefinition = "VARCHAR(50) default ''")
	private String strSMSProvider;
	@Column(name = "strSMSAPI", columnDefinition = "VARCHAR(300) default ''")
	private String strSMSAPI;
	@Column(name = "strSMSContent", columnDefinition = "VARCHAR(500) default ''")
	private String strSMSContent;

	// SMS setUp Tab End

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrLate() {
		return strLate;
	}

	public void setStrLate(String strLate) {
		this.strLate = (String) setDefaultValue(strLate, " ");
	}

	public String getStrRej() {
		return strRej;
	}

	public void setStrRej(String strRej) {
		this.strRej = (String) setDefaultValue(strRej, " ");
	}

	public String getStrPChange() {
		return strPChange;
	}

	public void setStrPChange(String strPChange) {
		this.strPChange = (String) setDefaultValue(strPChange, " ");
	}

	public String getStrExDelay() {
		return strExDelay;
	}

	public void setStrExDelay(String strExDelay) {
		this.strExDelay = (String) setDefaultValue(strExDelay, " ");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = (String) setDefaultValue(strBankName, " ");
	}

	public String getStrBranchName() {
		return strBranchName;
	}

	public void setStrBranchName(String strBranchName) {
		this.strBranchName = (String) setDefaultValue(strBranchName, " ");
	}

	public String getStrBankAdd1() {
		return strBankAdd1;
	}

	public void setStrBankAdd1(String strBankAdd1) {
		this.strBankAdd1 = (String) setDefaultValue(strBankAdd1, " ");
	}

	public String getStrBankAdd2() {
		return strBankAdd2;
	}

	public void setStrBankAdd2(String strBankAdd2) {
		this.strBankAdd2 = (String) setDefaultValue(strBankAdd2, " ");
	}

	public String getStrBankCity() {
		return strBankCity;
	}

	public void setStrBankCity(String strBankCity) {
		this.strBankCity = (String) setDefaultValue(strBankCity, " ");
	}

	public String getStrBankAccountNo() {
		return strBankAccountNo;
	}

	public void setStrBankAccountNo(String strBankAccountNo) {
		this.strBankAccountNo = (String) setDefaultValue(strBankAccountNo, " ");
	}

	public String getStrSwiftCode() {
		return strSwiftCode;
	}

	public void setStrSwiftCode(String strSwiftCode) {
		this.strSwiftCode = (String) setDefaultValue(strSwiftCode, " ");
		;
	}

	public String getStrNegStock() {
		return strNegStock;
	}

	public void setStrNegStock(String strNegStock) {
		this.strNegStock = strNegStock;
	}

	public String getStrPOBOM() {
		return strPOBOM;
	}

	public void setStrPOBOM(String strPOBOM) {
		this.strPOBOM = strPOBOM;
	}

	public String getStrSOBOM() {
		return strSOBOM;
	}

	public void setStrSOBOM(String strSOBOM) {
		this.strSOBOM = strSOBOM;
	}

	public String getStrTotalWorkhour() {
		return strTotalWorkhour;
	}

	public void setStrTotalWorkhour(String strTotalWorkhour) {
		this.strTotalWorkhour = (String) setDefaultValue(strTotalWorkhour, " ");
	}

	public String getDtFromTime() {
		return dtFromTime;
	}

	public void setDtFromTime(String dtFromTime) {
		this.dtFromTime = dtFromTime;
	}

	public String getDtToTime() {
		return dtToTime;
	}

	public void setDtToTime(String dtToTime) {
		this.dtToTime = dtToTime;
	}

	public String getStrWorkFlowbasedAuth() {
		return strWorkFlowbasedAuth;
	}

	public void setStrWorkFlowbasedAuth(String strWorkFlowbasedAuth) {
		this.strWorkFlowbasedAuth = strWorkFlowbasedAuth;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

	public String getStrAdd1() {
		return strAdd1;
	}

	public void setStrAdd1(String strAdd1) {
		this.strAdd1 = (String) setDefaultValue(strAdd1, " ");
	}

	public String getStrAdd2() {
		return strAdd2;
	}

	public void setStrAdd2(String strAdd2) {
		this.strAdd2 = (String) setDefaultValue(strAdd2, " ");
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = (String) setDefaultValue(strCity, " ");
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = (String) setDefaultValue(strState, " ");
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = (String) setDefaultValue(strCountry, " ");
	}

	public String getStrPin() {
		return strPin;
	}

	public void setStrPin(String strPin) {
		this.strPin = (String) setDefaultValue(strPin, " ");
	}

	public String getStrBAdd1() {
		return strBAdd1;
	}

	public void setStrBAdd1(String strBAdd1) {
		this.strBAdd1 = (String) setDefaultValue(strBAdd1, " ");
	}

	public String getStrBAdd2() {
		return strBAdd2;
	}

	public void setStrBAdd2(String strBAdd2) {
		this.strBAdd2 = (String) setDefaultValue(strBAdd2, " ");
	}

	public String getStrBCity() {
		return strBCity;
	}

	public void setStrBCity(String strBCity) {
		this.strBCity = (String) setDefaultValue(strBCity, " ");
	}

	public String getStrBState() {
		return strBState;
	}

	public void setStrBState(String strBState) {
		this.strBState = (String) setDefaultValue(strBState, " ");
	}

	public String getStrBCountry() {
		return strBCountry;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public void setStrBCountry(String strBCountry) {
		this.strBCountry = (String) setDefaultValue(strBCountry, " ");
	}

	public String getStrBPin() {
		return strBPin;
	}

	public void setStrBPin(String strBPin) {
		this.strBPin = (String) setDefaultValue(strBPin, " ");
	}

	public String getStrSAdd1() {
		return strSAdd1;
	}

	public void setStrSAdd1(String strSAdd1) {
		this.strSAdd1 = (String) setDefaultValue(strSAdd1, " ");
	}

	public String getStrSAdd2() {
		return strSAdd2;
	}

	public void setStrSAdd2(String strSAdd2) {
		this.strSAdd2 = (String) setDefaultValue(strSAdd2, " ");
	}

	public String getStrSCity() {
		return strSCity;
	}

	public void setStrSCity(String strSCity) {
		this.strSCity = (String) setDefaultValue(strSCity, " ");
	}

	public String getStrSState() {
		return strSState;
	}

	public void setStrSState(String strSState) {
		this.strSState = (String) setDefaultValue(strSState, " ");
	}

	public String getStrSCountry() {
		return strSCountry;
	}

	public void setStrSCountry(String strSCountry) {
		this.strSCountry = (String) setDefaultValue(strSCountry, " ");
	}

	public String getStrSPin() {
		return strSPin;
	}

	public void setStrSPin(String strSPin) {
		this.strSPin = (String) setDefaultValue(strSPin, " ");
	}

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = (String) setDefaultValue(strPhone, " ");
	}

	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = (String) setDefaultValue(strFax, " ");
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = (String) setDefaultValue(strEmail, " ");
	}

	public String getStrWebsite() {
		return strWebsite;
	}

	public void setStrWebsite(String strWebsite) {
		this.strWebsite = (String) setDefaultValue(strWebsite, " ");
	}

	public String getIntDueDays() {
		return intDueDays;
	}

	public void setIntDueDays(String intDueDays) {
		this.intDueDays = (String) setDefaultValue(intDueDays, "0");
	}

	public String getStrCST() {
		return strCST;
	}

	public void setStrCST(String strCST) {
		this.strCST = (String) setDefaultValue(strCST, " ");
	}

	public String getStrVAT() {
		return strVAT;
	}

	public void setStrVAT(String strVAT) {
		this.strVAT = (String) setDefaultValue(strVAT, " ");
	}

	public String getStrSerTax() {
		return strSerTax;
	}

	public void setStrSerTax(String strSerTax) {
		this.strSerTax = (String) setDefaultValue(strSerTax, " ");
	}

	public String getStrPanNo() {
		return strPanNo;
	}

	public void setStrPanNo(String strPanNo) {
		this.strPanNo = (String) setDefaultValue(strPanNo, " ");
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = (String) setDefaultValue(strLocCode, " ");
	}

	public String getStrAsseeCode() {
		return strAsseeCode;
	}

	public void setStrAsseeCode(String strAsseeCode) {
		this.strAsseeCode = (String) setDefaultValue(strAsseeCode, " ");
	}

	public String getStrPurEmail() {
		return strPurEmail;
	}

	public void setStrPurEmail(String strPurEmail) {
		this.strPurEmail = (String) setDefaultValue(strPurEmail, " ");
	}

	public String getStrSaleEmail() {
		return strSaleEmail;
	}

	public void setStrSaleEmail(String strSaleEmail) {
		this.strSaleEmail = (String) setDefaultValue(strSaleEmail, " ");
	}

	public String getStrRangeDiv() {
		return strRangeDiv;
	}

	public void setStrRangeDiv(String strRangeDiv) {
		this.strRangeDiv = (String) setDefaultValue(strRangeDiv, " ");
	}

	public String getStrCommi() {
		return strCommi;
	}

	public void setStrCommi(String strCommi) {
		this.strCommi = (String) setDefaultValue(strCommi, " ");
	}

	public String getStrRegNo() {
		return strRegNo;
	}

	public void setStrRegNo(String strRegNo) {
		this.strRegNo = (String) setDefaultValue(strRegNo, " ");
	}

	public String getStrDivision() {
		return strDivision;
	}

	public void setStrDivision(String strDivision) {
		this.strDivision = (String) setDefaultValue(strDivision, " ");
	}

	public String getDblBondAmt() {
		return dblBondAmt;
	}

	public void setDblBondAmt(String dblBondAmt) {
		this.dblBondAmt = (String) setDefaultValue(dblBondAmt, "0.00");
	}

	public String getStrAcceptanceNo() {
		return strAcceptanceNo;
	}

	public void setStrAcceptanceNo(String strAcceptanceNo) {
		this.strAcceptanceNo = (String) setDefaultValue(strAcceptanceNo, " ");
	}

	public String getStrMask() {
		return strMask;
	}

	public void setStrMask(String strMask) {
		this.strMask = (String) setDefaultValue(strMask, " ");
	}

	public String getStrRangeAdd() {
		return strRangeAdd;
	}

	public void setStrRangeAdd(String strRangeAdd) {
		this.strRangeAdd = (String) setDefaultValue(strRangeAdd, " ");
	}

	public int getIntdec() {
		return intdec;
	}

	public void setIntdec(int intdec) {
		this.intdec = (int) setDefaultValue(intdec, "0");
	}

	public String getStrListPriceInPO() {
		return strListPriceInPO;
	}

	public void setStrListPriceInPO(String strListPriceInPO) {
		this.strListPriceInPO = (String) setDefaultValue(strListPriceInPO, " ");
	}

	public String getStrCMSModule() {
		return strCMSModule;
	}

	public void setStrCMSModule(String strCMSModule) {
		this.strCMSModule = (String) setDefaultValue(strCMSModule, "N");
	}

	public String getStrBatchMethod() {
		return strBatchMethod;
	}

	public void setStrBatchMethod(String strBatchMethod) {
		this.strBatchMethod = (String) setDefaultValue(strBatchMethod, " ");
	}

	public String getStrTPostingType() {
		return strTPostingType;
	}

	public void setStrTPostingType(String strTPostingType) {
		this.strTPostingType = (String) setDefaultValue(strTPostingType, " ");
	}

	public String getStrAudit() {
		return strAudit;
	}

	public void setStrAudit(String strAudit) {
		this.strAudit = (String) setDefaultValue(strAudit, "N");
	}

	public String getStrAutoDC() {
		return strAutoDC;
	}

	public void setStrAutoDC(String strAutoDC) {
		this.strAutoDC = (String) setDefaultValue(strAutoDC, "N");
	}

	public String getStrIndustryType() {

		return strIndustryType;
	}

	public void setStrIndustryType(String strIndustryType) {
		this.strIndustryType = strIndustryType;
	}

	public String getClientCode() {
		return strClientCode;
	}

	public void setClientCode(String clientCode) {
		this.strClientCode = clientCode;
	}

	public int getIntqtydec() {
		return intqtydec;
	}

	public void setIntqtydec(int intqtydec) {
		this.intqtydec = (int) setDefaultValue(intqtydec, "0");
	}

	public String getStrRatePickUpFrom() {
		return strRatePickUpFrom;
	}

	public void setStrRatePickUpFrom(String strRatePickUpFrom) {
		this.strRatePickUpFrom = strRatePickUpFrom;
	}

	public String getStrShowReqVal() {
		return strShowReqVal;
	}

	public void setStrShowReqVal(String strShowReqVal) {
		this.strShowReqVal = (String) setDefaultValue(strShowReqVal, "N");
	}

	public String getStrShowStkReq() {
		return strShowStkReq;
	}

	public void setStrShowStkReq(String strShowStkReq) {
		this.strShowStkReq = (String) setDefaultValue(strShowStkReq, "N");
	}

	public String getStrShowValMISSlip() {
		return strShowValMISSlip;
	}

	public void setStrShowValMISSlip(String strShowValMISSlip) {
		this.strShowValMISSlip = (String) setDefaultValue(strShowValMISSlip, "N");
	}

	public String getStrChangeUOMTrans() {
		return strChangeUOMTrans;
	}

	public void setStrChangeUOMTrans(String strChangeUOMTrans) {
		this.strChangeUOMTrans = (String) setDefaultValue(strChangeUOMTrans, "N");
	}

	public String getStrShowProdMaster() {
		return strShowProdMaster;
	}

	public void setStrShowProdMaster(String strShowProdMaster) {
		this.strShowProdMaster = (String) setDefaultValue(strShowProdMaster, "N");
	}

	public String getStrAuditFrom() {
		return strAuditFrom;
	}

	public void setStrAuditFrom(String strAuditFrom) {
		this.strAuditFrom = (String) setDefaultValue(strAuditFrom, "N");
	}

	public String getStrShowProdDoc() {
		return strShowProdDoc;
	}

	public void setStrShowProdDoc(String strShowProdDoc) {
		this.strShowProdDoc = (String) setDefaultValue(strShowProdDoc, "N");
	}

	public String getStrAllowDateChangeInMIS() {
		return strAllowDateChangeInMIS;
	}

	public void setStrAllowDateChangeInMIS(String strAllowDateChangeInMIS) {
		this.strAllowDateChangeInMIS = (String) setDefaultValue(strAllowDateChangeInMIS, "N");
	}

	public String getStrShowTransAsc_Desc() {
		return strShowTransAsc_Desc;
	}

	public void setStrShowTransAsc_Desc(String strShowTransAsc_Desc) {
		this.strShowTransAsc_Desc = (String) setDefaultValue(strShowTransAsc_Desc, "Asc");
	}

	public String getStrNameChangeProdMast() {
		return strNameChangeProdMast;
	}

	public void setStrNameChangeProdMast(String strNameChangeProdMast) {
		this.strNameChangeProdMast = (String) setDefaultValue(strNameChangeProdMast, "N");
	}

	public String getStrStkAdjReason() {
		return strStkAdjReason;
	}

	public void setStrStkAdjReason(String strStkAdjReason) {
		this.strStkAdjReason = (String) setDefaultValue(strStkAdjReason, "");
	}

	public int getIntNotificationTimeinterval() {
		return intNotificationTimeinterval;
	}

	public void setIntNotificationTimeinterval(int intNotificationTimeinterval) {
		this.intNotificationTimeinterval = (int) setDefaultValue(intNotificationTimeinterval, 1);
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = (String) setDefaultValue(strMonthEnd, "N");
	}

	public String getStrShowAllProdToAllLoc() {
		return strShowAllProdToAllLoc;
	}

	public void setStrShowAllProdToAllLoc(String strShowAllProdToAllLoc) {
		this.strShowAllProdToAllLoc = strShowAllProdToAllLoc;
	}

	public String getStrLocWiseProductionOrder() {
		return strLocWiseProductionOrder;
	}

	public void setStrLocWiseProductionOrder(String strLocWiseProductionOrder) {
		this.strLocWiseProductionOrder = strLocWiseProductionOrder;
	}

	public String getStrDivisionAdd() {
		return strDivisionAdd;
	}

	public void setStrDivisionAdd(String strDivisionAdd) {
		this.strDivisionAdd = strDivisionAdd;
	}

	public String getStrShowStockInOP() {
		return strShowStockInOP;
	}

	public void setStrShowStockInOP(String strShowStockInOP) {
		this.strShowStockInOP = strShowStockInOP;
	}

	public String getStrShowAvgQtyInOP() {
		return strShowAvgQtyInOP;
	}

	public void setStrShowAvgQtyInOP(String strShowAvgQtyInOP) {
		this.strShowAvgQtyInOP = strShowAvgQtyInOP;
	}

	public String getStrShowStockInSO() {
		return strShowStockInSO;
	}

	public void setStrShowStockInSO(String strShowStockInSO) {
		this.strShowStockInSO = strShowStockInSO;
	}

	public String getStrShowAvgQtyInSO() {
		return strShowAvgQtyInSO;
	}

	public void setStrShowAvgQtyInSO(String strShowAvgQtyInSO) {
		this.strShowAvgQtyInSO = strShowAvgQtyInSO;
	}

	public String getStrEffectOfDiscOnPO() {
		return strEffectOfDiscOnPO;
	}

	public void setStrEffectOfDiscOnPO(String strEffectOfDiscOnPO) {
		this.strEffectOfDiscOnPO = strEffectOfDiscOnPO;
	}

	public String getStrInvFormat() {
		return strInvFormat;
	}

	public void setStrInvFormat(String strInvFormat) {
		this.strInvFormat = strInvFormat;
	}

	public String getStrECCNo() {
		return strECCNo;
	}

	public void setStrECCNo(String strECCNo) {
		this.strECCNo = strECCNo;
	}

	public String getStrSMSProvider() {
		return strSMSProvider;
	}

	public void setStrSMSProvider(String strSMSProvider) {
		this.strSMSProvider = (String) setDefaultValue(strSMSProvider, "");
	}

	public String getStrSMSAPI() {
		return strSMSAPI;
	}

	public void setStrSMSAPI(String strSMSAPI) {
		this.strSMSAPI = (String) setDefaultValue(strSMSAPI, "");
	}

	public String getStrSMSContent() {
		return strSMSContent;
	}

	public void setStrSMSContent(String strSMSContent) {
		this.strSMSContent = (String) setDefaultValue(strSMSContent, "");
	}

	public String getStrInvNote() {
		return strInvNote;
	}

	public void setStrInvNote(String strInvNote) {
		this.strInvNote = (String) setDefaultValue(strInvNote, "");
	}

	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = strCurrencyCode;
	}

	public String getStrShowAllPropCustomer() {
		return strShowAllPropCustomer;
	}

	public void setStrShowAllPropCustomer(String strShowAllPropCustomer) {
		this.strShowAllPropCustomer = strShowAllPropCustomer;
	}

	public String getStrEffectOfInvoice() {
		return strEffectOfInvoice;
	}

	public void setStrEffectOfInvoice(String strEffectOfInvoice) {
		this.strEffectOfInvoice = strEffectOfInvoice;
	}

	public String getStrEffectOfGRNWebBook() {
		return strEffectOfGRNWebBook;
	}

	public void setStrEffectOfGRNWebBook(String strEffectOfGRNWebBook) {
		this.strEffectOfGRNWebBook = strEffectOfGRNWebBook;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrMultiCurrency() {
		return strMultiCurrency;
	}

	public void setStrMultiCurrency(String strMultiCurrency) {
		this.strMultiCurrency = strMultiCurrency;
	}

	public String getStrShowAllPartyToAllLoc() {
		return strShowAllPartyToAllLoc;
	}

	public void setStrShowAllPartyToAllLoc(String strShowAllPartyToAllLoc) {
		this.strShowAllPartyToAllLoc = strShowAllPartyToAllLoc;
	}

	public String getStrShowAllTaxesOnTransaction() {
		return strShowAllTaxesOnTransaction;
	}

	public void setStrShowAllTaxesOnTransaction(String strShowAllTaxesOnTransaction) {
		this.strShowAllTaxesOnTransaction = (String) setDefaultValue(strShowAllTaxesOnTransaction, "Y");
	}

	public String getStrSOKOTPrint()
	{
		return strSOKOTPrint;
	}

	public void setStrSOKOTPrint(String strSOKOTPrint)
	{
		this.strSOKOTPrint = strSOKOTPrint;
	}
	
	public String getStrRateHistoryFormat() {
		return strRateHistoryFormat;
	}

	public void setStrRateHistoryFormat(String strRateHistoryFormat) {
		this.strRateHistoryFormat = strRateHistoryFormat;
	}

	public String getStrPOSlipFormat() {
		return strPOSlipFormat;
	}

	public void setStrPOSlipFormat(String strPOSlipFormat) {
		this.strPOSlipFormat = strPOSlipFormat;
	}

	public String getStrSRSlipFormat() {
		return strSRSlipFormat;
	}

	public void setStrSRSlipFormat(String strSRSlipFormat) {
		this.strSRSlipFormat = strSRSlipFormat;
	}
	
}
