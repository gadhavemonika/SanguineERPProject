package com.sanguine.webbooks.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webbooks.model.clsSundryCreditorOpeningBalMasterModel;

public class clsSundryCreditorBean {
	private long intGId;
	private String strCreditorCode;
	private String strPrefix;
	private String strFirstName;
	private String strMiddleName;
	private String strLastName;
	private String strCategoryCode;
	private String strCategoryName;
	private String strAddressLine1;
	private String strAddressLine2;
	private String strAddressLine3;
	private String strBlocked;
	private String strExpiryReasonCode;
	private String strExpiryReasonName;
	private String strFax;
	private String strLandmark;
	private String strEmail;
	private String strArea;
	private String strAreaName;
	private String strCurrencyType;
	private String strCity;
	private String strCityName;
	private String dblLicenseFee;
	private String strState;
	private String strStateName;
	private String dblAnnualFee;
	private String strRegion;
	private String strRegionName;
	private String strRemarks;
	private String strCountry;
	private String strCountryName;
	private String longZipCode;
	private String strTelNo1;
	private String strTelNo2;
	private String intCreditDays;
	private String longMobileNo;
	private String strAccountCode;
	
	/* Creditor Details */

	/* Billing Details */
	private String strContactPerson1;
	private String strContactPerson2;
	private String strContactDesignation1;
	private String strContactDesignation2;
	private String strContactEmail1;
	private String strContactEmail2;
	private String strContactTelNo1;
	private String strContactTelNo2;
	private String strBillingToCode;
	private String strCreditorFullName;
	private String strConsolidated;
	private String strAccountHolderCode;
	private String strAccountHolderName;
	private String strAMCCycle;
	private String strAMCRemarks;
	private String strOperational;
	/* Billing Details */

	/* ECS Information */
	private String strECSYN;
	private String dblECS;
	private String strAccountNo;
	private String strSaveCurAccount;
	private String strAlternateCode;
	private String strHolderName;
	private String strECSActivate;
	private String strMICRNo;
	/* ECS Information */

	/* Opening Balance */

	/* Opening Balance */

	private String strExpired;
	private String dblOutstanding;
	private String strStatus;
	private String intDays1;
	private String intDays2;
	private String intDays3;
	private String intDays4;
	private String intDays5;
	private String dblCrAmt;
	private String dblDrAmt;
	private String dteLetterProcess;
	private String strReminder1;
	private String strReminder2;
	private String strReminder3;
	private String strReminder4;
	private String strReminder5;
	private String strClientApproval;
	private String strAMCLink;
	private String dteStartDate;
	private String strClientComment;
	private String dblAnnualFeeInCurrency;
	private String dblLicenseFeeInCurrency;
	private String strCreditorStatusCode;
	private String strReminderStatus1;
	private String strReminderStatus2;
	private String strReminderStatus3;
	private String strReminderStatus4;
	private String strReminderStatus5;
	private String strAllInvoiceHeader;

	private String dteRemainderDate1;
	private String dteRemainderDate2;
	private String dteRemainderDate3;
	private String dteRemainderDate4;
	private String dteRemainderDate5;

	/* Require for each Bean */
	private String strUserCreated;
	private String strUserEdited;
	private String dteDateCreated;
	private String dteDateEdited;
	private String strClientCode;
	private String strPropertyCode;
	/* Require for each Bean */

	private List<clsSundryCreditorOpeningBalMasterModel> listSundryCreditorOpenongBalModel = new ArrayList<clsSundryCreditorOpeningBalMasterModel>();

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public String getStrCreditorCode() {
		return strCreditorCode;
	}

	public void setStrCreditorCode(String strCreditorCode) {
		this.strCreditorCode = strCreditorCode;
	}

	public String getStrPrefix() {
		return strPrefix;
	}

	public void setStrPrefix(String strPrefix) {
		this.strPrefix = strPrefix;
	}

	public String getStrFirstName() {
		return strFirstName;
	}

	public void setStrFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}

	public String getStrMiddleName() {
		return strMiddleName;
	}

	public void setStrMiddleName(String strMiddleName) {
		this.strMiddleName = strMiddleName;
	}

	public String getStrLastName() {
		return strLastName;
	}

	public void setStrLastName(String strLastName) {
		this.strLastName = strLastName;
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = strCategoryCode;
	}

	public String getStrCategoryName() {
		return strCategoryName;
	}

	public void setStrCategoryName(String strCategoryName) {
		this.strCategoryName = strCategoryName;
	}

	public String getStrAddressLine1() {
		return strAddressLine1;
	}

	public void setStrAddressLine1(String strAddressLine1) {
		this.strAddressLine1 = strAddressLine1;
	}

	public String getStrAddressLine2() {
		return strAddressLine2;
	}

	public void setStrAddressLine2(String strAddressLine2) {
		this.strAddressLine2 = strAddressLine2;
	}

	public String getStrAddressLine3() {
		return strAddressLine3;
	}

	public void setStrAddressLine3(String strAddressLine3) {
		this.strAddressLine3 = strAddressLine3;
	}

	public String getStrBlocked() {
		return strBlocked;
	}

	public void setStrBlocked(String strBlocked) {
		this.strBlocked = strBlocked;
	}

	public String getStrExpiryReasonCode() {
		return strExpiryReasonCode;
	}

	public void setStrExpiryReasonCode(String strExpiryReasonCode) {
		this.strExpiryReasonCode = strExpiryReasonCode;
	}

	public String getStrExpiryReasonName() {
		return strExpiryReasonName;
	}

	public void setStrExpiryReasonName(String strExpiryReasonName) {
		this.strExpiryReasonName = strExpiryReasonName;
	}

	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = strFax;
	}

	public String getStrLandmark() {
		return strLandmark;
	}

	public void setStrLandmark(String strLandmark) {
		this.strLandmark = strLandmark;
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = strArea;
	}

	public String getStrAreaName() {
		return strAreaName;
	}

	public void setStrAreaName(String strAreaName) {
		this.strAreaName = strAreaName;
	}

	public String getStrCurrencyType() {
		return strCurrencyType;
	}

	public void setStrCurrencyType(String strCurrencyType) {
		this.strCurrencyType = strCurrencyType;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getStrCityName() {
		return strCityName;
	}

	public void setStrCityName(String strCityName) {
		this.strCityName = strCityName;
	}

	public String getDblLicenseFee() {
		return dblLicenseFee;
	}

	public void setDblLicenseFee(String dblLicenseFee) {
		this.dblLicenseFee = dblLicenseFee;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	public String getStrStateName() {
		return strStateName;
	}

	public void setStrStateName(String strStateName) {
		this.strStateName = strStateName;
	}

	public String getDblAnnualFee() {
		return dblAnnualFee;
	}

	public void setDblAnnualFee(String dblAnnualFee) {
		this.dblAnnualFee = dblAnnualFee;
	}

	public String getStrRegion() {
		return strRegion;
	}

	public void setStrRegion(String strRegion) {
		this.strRegion = strRegion;
	}

	public String getStrRegionName() {
		return strRegionName;
	}

	public void setStrRegionName(String strRegionName) {
		this.strRegionName = strRegionName;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = strCountry;
	}

	public String getStrCountryName() {
		return strCountryName;
	}

	public void setStrCountryName(String strCountryName) {
		this.strCountryName = strCountryName;
	}

	public String getLongZipCode() {
		return longZipCode;
	}

	public void setLongZipCode(String longZipCode) {
		this.longZipCode = longZipCode;
	}

	public String getStrTelNo1() {
		return strTelNo1;
	}

	public void setStrTelNo1(String strTelNo1) {
		this.strTelNo1 = strTelNo1;
	}

	public String getStrTelNo2() {
		return strTelNo2;
	}

	public void setStrTelNo2(String strTelNo2) {
		this.strTelNo2 = strTelNo2;
	}

	public String getIntCreditDays() {
		return intCreditDays;
	}

	public void setIntCreditDays(String intCreditDays) {
		this.intCreditDays = intCreditDays;
	}

	public String getLongMobileNo() {
		return longMobileNo;
	}

	public void setLongMobileNo(String longMobileNo) {
		this.longMobileNo = longMobileNo;
	}

	public String getStrContactPerson1() {
		return strContactPerson1;
	}

	public void setStrContactPerson1(String strContactPerson1) {
		this.strContactPerson1 = strContactPerson1;
	}

	public String getStrContactPerson2() {
		return strContactPerson2;
	}

	public void setStrContactPerson2(String strContactPerson2) {
		this.strContactPerson2 = strContactPerson2;
	}

	public String getStrContactDesignation1() {
		return strContactDesignation1;
	}

	public void setStrContactDesignation1(String strContactDesignation1) {
		this.strContactDesignation1 = strContactDesignation1;
	}

	public String getStrContactDesignation2() {
		return strContactDesignation2;
	}

	public void setStrContactDesignation2(String strContactDesignation2) {
		this.strContactDesignation2 = strContactDesignation2;
	}

	public String getStrContactEmail1() {
		return strContactEmail1;
	}

	public void setStrContactEmail1(String strContactEmail1) {
		this.strContactEmail1 = strContactEmail1;
	}

	public String getStrContactEmail2() {
		return strContactEmail2;
	}

	public void setStrContactEmail2(String strContactEmail2) {
		this.strContactEmail2 = strContactEmail2;
	}

	public String getStrContactTelNo1() {
		return strContactTelNo1;
	}

	public void setStrContactTelNo1(String strContactTelNo1) {
		this.strContactTelNo1 = strContactTelNo1;
	}

	public String getStrContactTelNo2() {
		return strContactTelNo2;
	}

	public void setStrContactTelNo2(String strContactTelNo2) {
		this.strContactTelNo2 = strContactTelNo2;
	}

	public String getStrBillingToCode() {
		return strBillingToCode;
	}

	public void setStrBillingToCode(String strBillingToCode) {
		this.strBillingToCode = strBillingToCode;
	}

	public String getStrCreditorFullName() {
		return strCreditorFullName;
	}

	public void setStrCreditorFullName(String strCreditorFullName) {
		this.strCreditorFullName = strCreditorFullName;
	}

	public String getStrConsolidated() {
		return strConsolidated;
	}

	public void setStrConsolidated(String strConsolidated) {
		this.strConsolidated = strConsolidated;
	}

	public String getStrAccountHolderCode() {
		return strAccountHolderCode;
	}

	public void setStrAccountHolderCode(String strAccountHolderCode) {
		this.strAccountHolderCode = strAccountHolderCode;
	}

	public String getStrAccountHolderName() {
		return strAccountHolderName;
	}

	public void setStrAccountHolderName(String strAccountHolderName) {
		this.strAccountHolderName = strAccountHolderName;
	}

	public String getStrAMCCycle() {
		return strAMCCycle;
	}

	public void setStrAMCCycle(String strAMCCycle) {
		this.strAMCCycle = strAMCCycle;
	}

	public String getStrAMCRemarks() {
		return strAMCRemarks;
	}

	public void setStrAMCRemarks(String strAMCRemarks) {
		this.strAMCRemarks = strAMCRemarks;
	}

	public String getStrECSYN() {
		return strECSYN;
	}

	public void setStrECSYN(String strECSYN) {
		this.strECSYN = strECSYN;
	}

	public String getDblECS() {
		return dblECS;
	}

	public void setDblECS(String dblECS) {
		this.dblECS = dblECS;
	}

	public String getStrAccountNo() {
		return strAccountNo;
	}

	public void setStrAccountNo(String strAccountNo) {
		this.strAccountNo = strAccountNo;
	}

	public String getStrSaveCurAccount() {
		return strSaveCurAccount;
	}

	public void setStrSaveCurAccount(String strSaveCurAccount) {
		this.strSaveCurAccount = strSaveCurAccount;
	}

	public String getStrAlternateCode() {
		return strAlternateCode;
	}

	public void setStrAlternateCode(String strAlternateCode) {
		this.strAlternateCode = strAlternateCode;
	}

	public String getStrHolderName() {
		return strHolderName;
	}

	public void setStrHolderName(String strHolderName) {
		this.strHolderName = strHolderName;
	}

	public String getStrECSActivate() {
		return strECSActivate;
	}

	public void setStrECSActivate(String strECSActivate) {
		this.strECSActivate = strECSActivate;
	}

	public String getStrMICRNo() {
		return strMICRNo;
	}

	public void setStrMICRNo(String strMICRNo) {
		this.strMICRNo = strMICRNo;
	}

	public String getStrExpired() {
		return strExpired;
	}

	public void setStrExpired(String strExpired) {
		this.strExpired = strExpired;
	}

	public String getDblOutstanding() {
		return dblOutstanding;
	}

	public void setDblOutstanding(String dblOutstanding) {
		this.dblOutstanding = dblOutstanding;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getIntDays1() {
		return intDays1;
	}

	public void setIntDays1(String intDays1) {
		this.intDays1 = intDays1;
	}

	public String getIntDays2() {
		return intDays2;
	}

	public void setIntDays2(String intDays2) {
		this.intDays2 = intDays2;
	}

	public String getIntDays3() {
		return intDays3;
	}

	public void setIntDays3(String intDays3) {
		this.intDays3 = intDays3;
	}

	public String getIntDays4() {
		return intDays4;
	}

	public void setIntDays4(String intDays4) {
		this.intDays4 = intDays4;
	}

	public String getIntDays5() {
		return intDays5;
	}

	public void setIntDays5(String intDays5) {
		this.intDays5 = intDays5;
	}

	public String getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(String dblCrAmt) {
		this.dblCrAmt = dblCrAmt;
	}

	public String getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(String dblDrAmt) {
		this.dblDrAmt = dblDrAmt;
	}

	public String getDteLetterProcess() {
		return dteLetterProcess;
	}

	public void setDteLetterProcess(String dteLetterProcess) {
		this.dteLetterProcess = dteLetterProcess;
	}

	public String getStrReminder1() {
		return strReminder1;
	}

	public void setStrReminder1(String strReminder1) {
		this.strReminder1 = strReminder1;
	}

	public String getStrReminder2() {
		return strReminder2;
	}

	public void setStrReminder2(String strReminder2) {
		this.strReminder2 = strReminder2;
	}

	public String getStrReminder3() {
		return strReminder3;
	}

	public void setStrReminder3(String strReminder3) {
		this.strReminder3 = strReminder3;
	}

	public String getStrReminder4() {
		return strReminder4;
	}

	public void setStrReminder4(String strReminder4) {
		this.strReminder4 = strReminder4;
	}

	public String getStrReminder5() {
		return strReminder5;
	}

	public void setStrReminder5(String strReminder5) {
		this.strReminder5 = strReminder5;
	}

	public String getStrClientApproval() {
		return strClientApproval;
	}

	public void setStrClientApproval(String strClientApproval) {
		this.strClientApproval = strClientApproval;
	}

	public String getStrAMCLink() {
		return strAMCLink;
	}

	public void setStrAMCLink(String strAMCLink) {
		this.strAMCLink = strAMCLink;
	}

	public String getDteStartDate() {
		return dteStartDate;
	}

	public void setDteStartDate(String dteStartDate) {
		this.dteStartDate = dteStartDate;
	}

	public String getStrClientComment() {
		return strClientComment;
	}

	public void setStrClientComment(String strClientComment) {
		this.strClientComment = strClientComment;
	}

	public String getDblAnnualFeeInCurrency() {
		return dblAnnualFeeInCurrency;
	}

	public void setDblAnnualFeeInCurrency(String dblAnnualFeeInCurrency) {
		this.dblAnnualFeeInCurrency = dblAnnualFeeInCurrency;
	}

	public String getDblLicenseFeeInCurrency() {
		return dblLicenseFeeInCurrency;
	}

	public void setDblLicenseFeeInCurrency(String dblLicenseFeeInCurrency) {
		this.dblLicenseFeeInCurrency = dblLicenseFeeInCurrency;
	}

	public String getStrCreditorStatusCode() {
		return strCreditorStatusCode;
	}

	public void setStrCreditorStatusCode(String strCreditorStatusCode) {
		this.strCreditorStatusCode = strCreditorStatusCode;
	}

	public String getStrReminderStatus1() {
		return strReminderStatus1;
	}

	public void setStrReminderStatus1(String strReminderStatus1) {
		this.strReminderStatus1 = strReminderStatus1;
	}

	public String getStrReminderStatus2() {
		return strReminderStatus2;
	}

	public void setStrReminderStatus2(String strReminderStatus2) {
		this.strReminderStatus2 = strReminderStatus2;
	}

	public String getStrReminderStatus3() {
		return strReminderStatus3;
	}

	public void setStrReminderStatus3(String strReminderStatus3) {
		this.strReminderStatus3 = strReminderStatus3;
	}

	public String getStrReminderStatus4() {
		return strReminderStatus4;
	}

	public void setStrReminderStatus4(String strReminderStatus4) {
		this.strReminderStatus4 = strReminderStatus4;
	}

	public String getStrReminderStatus5() {
		return strReminderStatus5;
	}

	public void setStrReminderStatus5(String strReminderStatus5) {
		this.strReminderStatus5 = strReminderStatus5;
	}

	public String getStrAllInvoiceHeader() {
		return strAllInvoiceHeader;
	}

	public void setStrAllInvoiceHeader(String strAllInvoiceHeader) {
		this.strAllInvoiceHeader = strAllInvoiceHeader;
	}

	public String getDteRemainderDate1() {
		return dteRemainderDate1;
	}

	public void setDteRemainderDate1(String dteRemainderDate1) {
		this.dteRemainderDate1 = dteRemainderDate1;
	}

	public String getDteRemainderDate2() {
		return dteRemainderDate2;
	}

	public void setDteRemainderDate2(String dteRemainderDate2) {
		this.dteRemainderDate2 = dteRemainderDate2;
	}

	public String getDteRemainderDate3() {
		return dteRemainderDate3;
	}

	public void setDteRemainderDate3(String dteRemainderDate3) {
		this.dteRemainderDate3 = dteRemainderDate3;
	}

	public String getDteRemainderDate4() {
		return dteRemainderDate4;
	}

	public void setDteRemainderDate4(String dteRemainderDate4) {
		this.dteRemainderDate4 = dteRemainderDate4;
	}

	public String getDteRemainderDate5() {
		return dteRemainderDate5;
	}

	public void setDteRemainderDate5(String dteRemainderDate5) {
		this.dteRemainderDate5 = dteRemainderDate5;
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

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public List<clsSundryCreditorOpeningBalMasterModel> getListSundryCreditorOpenongBalModel() {
		return listSundryCreditorOpenongBalModel;
	}

	public void setListSundryCreditorOpenongBalModel(List<clsSundryCreditorOpeningBalMasterModel> listSundryCreditorOpenongBalModel) {
		this.listSundryCreditorOpenongBalModel = listSundryCreditorOpenongBalModel;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}
	
	
	

}
