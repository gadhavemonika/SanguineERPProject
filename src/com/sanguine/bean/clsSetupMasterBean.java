package com.sanguine.bean;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsProcessSetupModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTransactionTimeModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsWorkFlowForSlabBasedAuth;
import com.sanguine.model.clsWorkFlowModel;

public class clsSetupMasterBean {

	private long intId;
	private String strCompanyCode;
	private String strCompanyName;
	private String strFinYear;
	private String dtStart;
	private String dtEnd;
	private String dtLastTransDate;
	private String strDbName;

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
	private String strDivisionAdd;
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
	private int intqtydec;
	private String strListPriceInPO;
	private String strCMSModule;
	private String strBatchMethod;
	private String strTPostingType;
	private String strAudit;
	private String strAutoDC;
	private String strRatePickUpFrom;
	private String strShowReqVal;
	private String strShowStkReq;
	private String strShowValMISSlip;
	private String strChangeUOMTrans;
	private String strShowProdMaster;
	private String strShowProdDoc;
	private String strAllowDateChangeInMIS;
	private String strShowTransAsc_Desc;
	private String strNameChangeProdMast;
	private String strStkAdjReason;
	private int intNotificationTimeinterval;
	private String strMonthEnd;
	private String strShowAllProdToAllLoc;
	private String strLocWiseProductionOrder;
	private String strShowStockInOP;
	private String strShowAvgQtyInOP;
	private String strShowStockInSO;
	private String strShowAvgQtyInSO;
	private String strEffectOfDiscOnPO;
	private String strInvFormat;
	private String strInvNote;
	private String strCurrencyCode;
	private boolean strShowAllPropCustomer;
	private String strEffectOfInvoice;
	private String strEffectOfGRNWebBook;
	private String strMultiCurrency;
	private String strShowAllPartyToAllLoc;
	

	// BAnk Dtl Tab
	private String strBankName;
	private String strBranchName;
	private String strBankAdd1;
	private String strBankAdd2;
	private String strBankCity;
	private String strBankAccountNo;
	private String strSwiftCode;
	// End of BAnk Dtl Tab

	private String strProperty;

	private String strUserCode1;
	private String strUserCode2;
	private String strUserCode3;
	private String strUserCode4;
	private String strUserCode5;

	private String strIndustryType;

	// Purchase Order Tab
	private String strTC1;
	private String strTC2;
	private String strTC3;
	private String strTC4;
	private String strTC5;
	private String strTC6;
	private String strTC7;
	private String strTC8;
	private String strTC9;
	private String strTC10;
	private String strTC11;
	private String strTC12;
	// End of Purchase Order Tab

	// Supplier Performance Tab
	private String strLate;
	private String strRej;
	private String strPChange;
	private String strExDelay;
	// End Supplier Performance Tab

	// SMS setUp Tab Start
	private String strSMSProvider;
	private String strSMSAPI;
	private String strSMSContent;
	// SMS setUp Tab End

	Map<String, String> properties;
	Map<String, String> users;
	
	private String strShowAllTaxesOnTransaction;
	private boolean strSOKOTPrint;
	
	private String strRateHistoryFormat;
	
	private String strPOSlipFormat;

	private String strSRSlipFormat;
	
	public String getStrRateHistoryFormat() {
		return strRateHistoryFormat;
	}

	public void setStrRateHistoryFormat(String strRateHistoryFormat) {
		this.strRateHistoryFormat = strRateHistoryFormat;
	}

	public String getStrUserCode1() {
		return strUserCode1;
	}

	public void setStrUserCode1(String strUserCode1) {
		this.strUserCode1 = strUserCode1;
	}

	public String getStrUserCode2() {
		return strUserCode2;
	}

	public void setStrUserCode2(String strUserCode2) {
		this.strUserCode2 = strUserCode2;
	}

	public String getStrUserCode3() {
		return strUserCode3;
	}

	public void setStrUserCode3(String strUserCode3) {
		this.strUserCode3 = strUserCode3;
	}

	public String getStrUserCode4() {
		return strUserCode4;
	}

	public void setStrUserCode4(String strUserCode4) {
		this.strUserCode4 = strUserCode4;
	}

	public String getStrUserCode5() {
		return strUserCode5;
	}

	public void setStrUserCode5(String strUserCode5) {
		this.strUserCode5 = strUserCode5;
	}

	private List<clsTreeMasterModel> listProcessSetupForm;

	private List<clsTreeMasterModel> listAuditForm;

	private List<clsProcessSetupModel> listProcessSetupModel;

	private List<clsTCMasterModel> listTCForSetup; // Terms and Condition fields

	private List<clsWorkFlowModel> listclsWorkFlowModel; // Authorization

	private List<clsTransactionTimeModel> listclsTransactionTimeModel;

	public List<clsWorkFlowModel> getListclsWorkFlowModel() {
		return listclsWorkFlowModel;
	}

	public void setListclsWorkFlowModel(List<clsWorkFlowModel> listclsWorkFlowModel) {
		this.listclsWorkFlowModel = listclsWorkFlowModel;
	}

	public List<clsTCMasterModel> getListTCForSetup() {
		return listTCForSetup;
	}

	public void setListTCForSetup(List<clsTCMasterModel> listTCForSetup) {
		this.listTCForSetup = listTCForSetup;
	}

	private List<clsWorkFlowForSlabBasedAuth> listclsWorkFlowForSlabBasedAuth;

	public List<clsWorkFlowForSlabBasedAuth> getListclsWorkFlowForSlabBasedAuth() {
		return listclsWorkFlowForSlabBasedAuth;
	}

	public void setListclsWorkFlowForSlabBasedAuth(List<clsWorkFlowForSlabBasedAuth> listclsWorkFlowForSlabBasedAuth) {
		this.listclsWorkFlowForSlabBasedAuth = listclsWorkFlowForSlabBasedAuth;
	}

	public List<clsTreeMasterModel> getListProcessSetupForm() {
		return listProcessSetupForm;
	}

	public void setListProcessSetupForm(List<clsTreeMasterModel> listProcessSetupForm) {
		this.listProcessSetupForm = listProcessSetupForm;
	}

	public List<clsProcessSetupModel> getListProcessSetupModel() {
		return listProcessSetupModel;
	}

	public void setListProcessSetupModel(List<clsProcessSetupModel> listProcessSetupModel) {
		this.listProcessSetupModel = listProcessSetupModel;
	}

	public String getStrIndustryType() {
		return strIndustryType;
	}

	public void setStrIndustryType(String strIndustryType) {
		this.strIndustryType = strIndustryType;
	}

	public String getStrDbName() {
		return strDbName;
	}

	public void setStrDbName(String strDbName) {
		this.strDbName = strDbName;
	}

	public String getStrProperty() {
		return strProperty;
	}

	public void setStrProperty(String strProperty) {
		this.strProperty = strProperty;
	}

	public String getStrBankAccountNo() {
		return strBankAccountNo;
	}

	public void setStrBankAccountNo(String strBankAccountNo) {
		this.strBankAccountNo = strBankAccountNo;
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = strBankName;
	}

	public String getStrBranchName() {
		return strBranchName;
	}

	public void setStrBranchName(String strBranchName) {
		this.strBranchName = strBranchName;
	}

	public String getStrBankAdd1() {
		return strBankAdd1;
	}

	public void setStrBankAdd1(String strBankAdd1) {
		this.strBankAdd1 = strBankAdd1;
	}

	public String getStrBankAdd2() {
		return strBankAdd2;
	}

	public void setStrBankAdd2(String strBankAdd2) {
		this.strBankAdd2 = strBankAdd2;
	}

	public String getStrBankCity() {
		return strBankCity;
	}

	public void setStrBankCity(String strBankCity) {
		this.strBankCity = strBankCity;
	}

	public String getStrSwiftCode() {
		return strSwiftCode;
	}

	public void setStrSwiftCode(String strSwiftCode) {
		this.strSwiftCode = strSwiftCode;
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
		this.strTotalWorkhour = strTotalWorkhour;
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

	public String getStrCompanyName() {
		return strCompanyName;
	}

	public void setStrCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}

	public String getStrFinYear() {
		return strFinYear;
	}

	public void setStrFinYear(String strFinYear) {
		this.strFinYear = strFinYear;
	}

	public String getDtStart() {
		return dtStart;
	}

	public void setDtStart(String dtStart) {
		this.dtStart = dtStart;
	}

	public String getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(String dtEnd) {
		this.dtEnd = dtEnd;
	}

	public String getDtLastTransDate() {
		return dtLastTransDate;
	}

	public void setDtLastTransDate(String dtLastTransDate) {
		this.dtLastTransDate = dtLastTransDate;
	}

	public String getStrAdd1() {
		return strAdd1;
	}

	public void setStrAdd1(String strAdd1) {
		this.strAdd1 = strAdd1;
	}

	public String getStrAdd2() {
		return strAdd2;
	}

	public void setStrAdd2(String strAdd2) {
		this.strAdd2 = strAdd2;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = strCountry;
	}

	public String getStrPin() {
		return strPin;
	}

	public void setStrPin(String strPin) {
		this.strPin = strPin;
	}

	public String getStrBAdd1() {
		return strBAdd1;
	}

	public void setStrBAdd1(String strBAdd1) {
		this.strBAdd1 = strBAdd1;
	}

	public String getStrBAdd2() {
		return strBAdd2;
	}

	public void setStrBAdd2(String strBAdd2) {
		this.strBAdd2 = strBAdd2;
	}

	public String getStrBCity() {
		return strBCity;
	}

	public void setStrBCity(String strBCity) {
		this.strBCity = strBCity;
	}

	public String getStrBState() {
		return strBState;
	}

	public void setStrBState(String strBState) {
		this.strBState = strBState;
	}

	public String getStrBCountry() {
		return strBCountry;
	}

	public void setStrBCountry(String strBCountry) {
		this.strBCountry = strBCountry;
	}

	public String getStrBPin() {
		return strBPin;
	}

	public void setStrBPin(String strBPin) {
		this.strBPin = strBPin;
	}

	public String getStrSAdd1() {
		return strSAdd1;
	}

	public void setStrSAdd1(String strSAdd1) {
		this.strSAdd1 = strSAdd1;
	}

	public String getStrSAdd2() {
		return strSAdd2;
	}

	public void setStrSAdd2(String strSAdd2) {
		this.strSAdd2 = strSAdd2;
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

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = strFax;
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}

	public String getStrWebsite() {
		return strWebsite;
	}

	public void setStrWebsite(String strWebsite) {
		this.strWebsite = strWebsite;
	}

	public String getIntDueDays() {
		return intDueDays;
	}

	public void setIntDueDays(String intDueDays) {
		this.intDueDays = intDueDays;
	}

	public String getStrCST() {
		return strCST;
	}

	public void setStrCST(String strCST) {
		this.strCST = strCST;
	}

	public String getStrVAT() {
		return strVAT;
	}

	public void setStrVAT(String strVAT) {
		this.strVAT = strVAT;
	}

	public String getStrSerTax() {
		return strSerTax;
	}

	public void setStrSerTax(String strSerTax) {
		this.strSerTax = strSerTax;
	}

	public String getStrPanNo() {
		return strPanNo;
	}

	public void setStrPanNo(String strPanNo) {
		this.strPanNo = strPanNo;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrAsseeCode() {
		return strAsseeCode;
	}

	public void setStrAsseeCode(String strAsseeCode) {
		this.strAsseeCode = strAsseeCode;
	}

	public String getStrPurEmail() {
		return strPurEmail;
	}

	public void setStrPurEmail(String strPurEmail) {
		this.strPurEmail = strPurEmail;
	}

	public String getStrSaleEmail() {
		return strSaleEmail;
	}

	public void setStrSaleEmail(String strSaleEmail) {
		this.strSaleEmail = strSaleEmail;
	}

	public String getStrRangeDiv() {
		return strRangeDiv;
	}

	public void setStrRangeDiv(String strRangeDiv) {
		this.strRangeDiv = strRangeDiv;
	}

	public String getStrCommi() {
		return strCommi;
	}

	public void setStrCommi(String strCommi) {
		this.strCommi = strCommi;
	}

	public String getStrRegNo() {
		return strRegNo;
	}

	public void setStrRegNo(String strRegNo) {
		this.strRegNo = strRegNo;
	}

	public String getStrDivision() {
		return strDivision;
	}

	public void setStrDivision(String strDivision) {
		this.strDivision = strDivision;
	}

	public String getDblBondAmt() {
		return dblBondAmt;
	}

	public void setDblBondAmt(String dblBondAmt) {
		this.dblBondAmt = dblBondAmt;
	}

	public String getStrAcceptanceNo() {
		return strAcceptanceNo;
	}

	public void setStrAcceptanceNo(String strAcceptanceNo) {
		this.strAcceptanceNo = strAcceptanceNo;
	}

	public String getStrLate() {
		return strLate;
	}

	public void setStrLate(String strLate) {
		this.strLate = strLate;
	}

	public String getStrRej() {
		return strRej;
	}

	public void setStrRej(String strRej) {
		this.strRej = strRej;
	}

	public String getStrPChange() {
		return strPChange;
	}

	public void setStrPChange(String strPChange) {
		this.strPChange = strPChange;
	}

	public String getStrExDelay() {
		return strExDelay;
	}

	public void setStrExDelay(String strExDelay) {
		this.strExDelay = strExDelay;
	}

	public String getStrTC1() {
		return strTC1;
	}

	public void setStrTC1(String strTC1) {
		this.strTC1 = strTC1;
	}

	public String getStrTC2() {
		return strTC2;
	}

	public void setStrTC2(String strTC2) {
		this.strTC2 = strTC2;
	}

	public String getStrTC3() {
		return strTC3;
	}

	public void setStrTC3(String strTC3) {
		this.strTC3 = strTC3;
	}

	public String getStrTC4() {
		return strTC4;
	}

	public void setStrTC4(String strTC4) {
		this.strTC4 = strTC4;
	}

	public String getStrTC5() {
		return strTC5;
	}

	public void setStrTC5(String strTC5) {
		this.strTC5 = strTC5;
	}

	public String getStrTC6() {
		return strTC6;
	}

	public void setStrTC6(String strTC6) {
		this.strTC6 = strTC6;
	}

	public String getStrTC7() {
		return strTC7;
	}

	public void setStrTC7(String strTC7) {
		this.strTC7 = strTC7;
	}

	public String getStrTC8() {
		return strTC8;
	}

	public void setStrTC8(String strTC8) {
		this.strTC8 = strTC8;
	}

	public String getStrTC9() {
		return strTC9;
	}

	public void setStrTC9(String strTC9) {
		this.strTC9 = strTC9;
	}

	public String getStrTC10() {
		return strTC10;
	}

	public void setStrTC10(String strTC10) {
		this.strTC10 = strTC10;
	}

	public String getStrTC11() {
		return strTC11;
	}

	public void setStrTC11(String strTC11) {
		this.strTC11 = strTC11;
	}

	public String getStrTC12() {
		return strTC12;
	}

	public void setStrTC12(String strTC12) {
		this.strTC12 = strTC12;
	}

	public String getStrMask() {
		return strMask;
	}

	public void setStrMask(String strMask) {
		this.strMask = strMask;
	}

	public String getStrRangeAdd() {
		return strRangeAdd;
	}

	public void setStrRangeAdd(String strRangeAdd) {
		this.strRangeAdd = strRangeAdd;
	}

	public int getIntdec() {
		return intdec;
	}

	public void setIntdec(int intdec) {
		this.intdec = intdec;
	}

	public String getStrListPriceInPO() {
		return strListPriceInPO;
	}

	public void setStrListPriceInPO(String strListPriceInPO) {
		this.strListPriceInPO = strListPriceInPO;
	}

	public String getStrCMSModule() {
		return strCMSModule;
	}

	public void setStrCMSModule(String strCMSModule) {
		this.strCMSModule = strCMSModule;
	}

	public String getStrBatchMethod() {
		return strBatchMethod;
	}

	public void setStrBatchMethod(String strBatchMethod) {
		this.strBatchMethod = strBatchMethod;
	}

	public String getStrTPostingType() {
		return strTPostingType;
	}

	public void setStrTPostingType(String strTPostingType) {
		this.strTPostingType = strTPostingType;
	}

	public String getStrAudit() {
		return strAudit;
	}

	public void setStrAudit(String strAudit) {
		this.strAudit = strAudit;
	}

	public String getStrAutoDC() {
		return strAutoDC;
	}

	public void setStrAutoDC(String strAutoDC) {
		this.strAutoDC = strAutoDC;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public void setUsers(Map<String, String> users) {
		this.users = users;
	}

	public int getIntqtydec() {
		return intqtydec;
	}

	public void setIntqtydec(int intqtydec) {
		this.intqtydec = intqtydec;
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
		this.strShowReqVal = strShowReqVal;
	}

	public String getStrShowStkReq() {
		return strShowStkReq;
	}

	public void setStrShowStkReq(String strShowStkReq) {
		this.strShowStkReq = strShowStkReq;
	}

	public String getStrShowValMISSlip() {
		return strShowValMISSlip;
	}

	public void setStrShowValMISSlip(String strShowValMISSlip) {
		this.strShowValMISSlip = strShowValMISSlip;
	}

	public String getStrChangeUOMTrans() {
		return strChangeUOMTrans;
	}

	public void setStrChangeUOMTrans(String strChangeUOMTrans) {
		this.strChangeUOMTrans = strChangeUOMTrans;
	}

	public String getStrShowProdMaster() {
		return strShowProdMaster;
	}

	public void setStrShowProdMaster(String strShowProdMaster) {
		this.strShowProdMaster = strShowProdMaster;
	}

	public List<clsTreeMasterModel> getListAuditForm() {
		return listAuditForm;
	}

	public void setListAuditForm(List<clsTreeMasterModel> listAuditForm) {
		this.listAuditForm = listAuditForm;
	}

	public String getStrShowProdDoc() {
		return strShowProdDoc;
	}

	public void setStrShowProdDoc(String strShowProdDoc) {
		this.strShowProdDoc = strShowProdDoc;
	}

	public String getStrAllowDateChangeInMIS() {
		return strAllowDateChangeInMIS;
	}

	public void setStrAllowDateChangeInMIS(String strAllowDateChangeInMIS) {
		this.strAllowDateChangeInMIS = strAllowDateChangeInMIS;
	}

	public String getStrShowTransAsc_Desc() {
		return strShowTransAsc_Desc;
	}

	public void setStrShowTransAsc_Desc(String strShowTransAsc_Desc) {
		this.strShowTransAsc_Desc = strShowTransAsc_Desc;
	}

	public String getStrNameChangeProdMast() {
		return strNameChangeProdMast;
	}

	public void setStrNameChangeProdMast(String strNameChangeProdMast) {
		this.strNameChangeProdMast = strNameChangeProdMast;
	}

	public String getStrStkAdjReason() {
		return strStkAdjReason;
	}

	public void setStrStkAdjReason(String strStkAdjReason) {
		this.strStkAdjReason = strStkAdjReason;
	}

	public int getIntNotificationTimeinterval() {
		return intNotificationTimeinterval;
	}

	public void setIntNotificationTimeinterval(int intNotificationTimeinterval) {
		this.intNotificationTimeinterval = intNotificationTimeinterval;
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = strMonthEnd;
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
		this.strSMSProvider = strSMSProvider;
	}

	public String getStrSMSAPI() {
		return strSMSAPI;
	}

	public void setStrSMSAPI(String strSMSAPI) {
		this.strSMSAPI = strSMSAPI;
	}

	public String getStrSMSContent() {
		return strSMSContent;
	}

	public void setStrSMSContent(String strSMSContent) {
		this.strSMSContent = strSMSContent;
	}

	public String getStrInvNote() {
		return strInvNote;
	}

	public void setStrInvNote(String strInvNote) {
		this.strInvNote = strInvNote;
	}

	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = strCurrencyCode;
	}

	public String getStrEffectOfInvoice() {
		return strEffectOfInvoice;
	}

	public void setStrEffectOfInvoice(String strEffectOfInvoice) {
		this.strEffectOfInvoice = strEffectOfInvoice;
	}

	public boolean isStrShowAllPropCustomer() {
		return strShowAllPropCustomer;
	}

	public void setStrShowAllPropCustomer(boolean strShowAllPropCustomer) {
		this.strShowAllPropCustomer = strShowAllPropCustomer;
	}

	public String getStrEffectOfGRNWebBook() {
		return strEffectOfGRNWebBook;
	}

	public void setStrEffectOfGRNWebBook(String strEffectOfGRNWebBook) {
		this.strEffectOfGRNWebBook = strEffectOfGRNWebBook;
	}

	public List<clsTransactionTimeModel> getListclsTransactionTimeModel() {
		return listclsTransactionTimeModel;
	}

	public void setListclsTransactionTimeModel(List<clsTransactionTimeModel> listclsTransactionTimeModel) {
		this.listclsTransactionTimeModel = listclsTransactionTimeModel;
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
		this.strShowAllTaxesOnTransaction = strShowAllTaxesOnTransaction;
	}

	public boolean isStrSOKOTPrint()
	{
		return strSOKOTPrint;
	}

	public void setStrSOKOTPrint(boolean strSOKOTPrint)
	{
		this.strSOKOTPrint = strSOKOTPrint;
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
