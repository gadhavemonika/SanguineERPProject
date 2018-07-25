package com.sanguine.webbooks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.webpms.model.clsBillDtlModel;

@Entity
@Table(name = "tblsundarycreditormaster")
@IdClass(clsSundryCreditorMasterModel_ID.class)
public class clsSundaryCreditorMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSundaryCreditorMasterModel() {
	}

	public clsSundaryCreditorMasterModel(clsSundryCreditorMasterModel_ID objModelID) {
		strCreditorCode = objModelID.getStrCreditorCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblsundarycreditoropeningbalance", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strCreditorCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCreditorCode", column = @Column(name = "strCreditorCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	List<clsSundryCreditorOpeningBalMasterModel> listSundryCreditorOpenongBalModel = new ArrayList<clsSundryCreditorOpeningBalMasterModel>();

	// Variable Declaration
	@Column(name = "intGId", updatable = false, nullable = false)
	private long intGId;

	@Column(name = "strCreditorCode")
	private String strCreditorCode;

	@Column(name = "strPrefix")
	private String strPrefix;

	@Column(name = "strFirstName")
	private String strFirstName;

	@Column(name = "strMiddleName")
	private String strMiddleName;

	@Column(name = "strLastName")
	private String strLastName;

	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	@Column(name = "strAddressLine1")
	private String strAddressLine1;

	@Column(name = "strAddressLine2")
	private String strAddressLine2;

	@Column(name = "strAddressLine3")
	private String strAddressLine3;

	@Column(name = "strBlocked")
	private String strBlocked;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strTelNo1")
	private String strTelNo1;

	@Column(name = "strTelNo2")
	private String strTelNo2;

	@Column(name = "strFax")
	private String strFax;

	@Column(name = "strArea")
	private String strArea;

	@Column(name = "strEmail")
	private String strEmail;

	@Column(name = "strContactPerson1")
	private String strContactPerson1;

	@Column(name = "strContactDesignation1")
	private String strContactDesignation1;

	@Column(name = "strContactEmail1")
	private String strContactEmail1;

	@Column(name = "strContactTelNo1")
	private String strContactTelNo1;

	@Column(name = "strContactPerson2")
	private String strContactPerson2;

	@Column(name = "strContactDesignation2")
	private String strContactDesignation2;

	@Column(name = "strContactEmail2")
	private String strContactEmail2;

	@Column(name = "strContactTelNo2")
	private String strContactTelNo2;

	@Column(name = "strLandmark")
	private String strLandmark;

	@Column(name = "strCreditorFullName")
	private String strCreditorFullName;

	@Column(name = "strExpired")
	private String strExpired;

	@Column(name = "strExpiryReasonCode")
	private String strExpiryReasonCode;

	@Column(name = "strECSYN")
	private String strECSYN;

	@Column(name = "strAccountNo")
	private String strAccountNo;

	@Column(name = "strHolderName")
	private String strHolderName;

	@Column(name = "strMICRNo")
	private String strMICRNo;

	@Column(name = "dblECS")
	private String dblECS;

	@Column(name = "strSaveCurAccount")
	private String strSaveCurAccount;

	@Column(name = "strAlternateCode")
	private String strAlternateCode;

	@Column(name = "dblOutstanding")
	private String dblOutstanding;

	@Column(name = "strStatus")
	private String strStatus;

	@Column(name = "intDays1")
	private String intDays1;

	@Column(name = "intDays2")
	private String intDays2;

	@Column(name = "intDays3")
	private String intDays3;

	@Column(name = "intDays4")
	private String intDays4;

	@Column(name = "intDays5")
	private String intDays5;

	@Column(name = "dblCrAmt")
	private String dblCrAmt;

	@Column(name = "dblDrAmt")
	private String dblDrAmt;

	@Column(name = "dteLetterProcess")
	private String dteLetterProcess;

	@Column(name = "strReminder1")
	private String strReminder1;

	@Column(name = "strReminder2")
	private String strReminder2;

	@Column(name = "strReminder3")
	private String strReminder3;

	@Column(name = "strReminder4")
	private String strReminder4;

	@Column(name = "strReminder5")
	private String strReminder5;

	@Column(name = "dblLicenseFee")
	private String dblLicenseFee;

	@Column(name = "dblAnnualFee")
	private String dblAnnualFee;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strClientApproval")
	private String strClientApproval;

	@Column(name = "strAMCLink")
	private String strAMCLink;

	@Column(name = "strCurrencyType")
	private String strCurrencyType;

	@Column(name = "strAccountHolderCode")
	private String strAccountHolderCode;

	@Column(name = "strAccountHolderName")
	private String strAccountHolderName;

	@Column(name = "strAMCCycle")
	private String strAMCCycle;

	@Column(name = "dteStartDate")
	private String dteStartDate;

	@Column(name = "strAMCRemarks")
	private String strAMCRemarks;

	@Column(name = "strClientComment")
	private String strClientComment;

	@Column(name = "strBillingToCode")
	private String strBillingToCode;

	@Column(name = "dblAnnualFeeInCurrency")
	private String dblAnnualFeeInCurrency;

	@Column(name = "dblLicenseFeeInCurrency")
	private String dblLicenseFeeInCurrency;

	@Column(name = "strState")
	private String strState;

	@Column(name = "strRegion")
	private String strRegion;

	@Column(name = "strCountry")
	private String strCountry;

	@Column(name = "strConsolidated")
	private String strConsolidated;

	@Column(name = "intCreditDays")
	private String intCreditDays;

	@Column(name = "strCreditorStatusCode")
	private String strCreditorStatusCode;

	@Column(name = "strECSActivate")
	private String strECSActivate;

	@Column(name = "strReminderStatus1")
	private String strReminderStatus1;

	@Column(name = "strReminderStatus2")
	private String strReminderStatus2;

	@Column(name = "strReminderStatus3")
	private String strReminderStatus3;

	@Column(name = "strReminderStatus4")
	private String strReminderStatus4;

	@Column(name = "strReminderStatus5")
	private String strReminderStatus5;

	@Column(name = "strAllInvoiceHeader")
	private String strAllInvoiceHeader;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dteRemainderDate1")
	private String dteRemainderDate1;

	@Column(name = "dteRemainderDate2")
	private String dteRemainderDate2;

	@Column(name = "dteRemainderDate3")
	private String dteRemainderDate3;

	@Column(name = "dteRemainderDate4")
	private String dteRemainderDate4;

	@Column(name = "dteRemainderDate5")
	private String dteRemainderDate5;

	@Column(name = "longZipCode")
	private String longZipCode;

	@Column(name = "longMobileNo")
	private String longMobileNo;

	@Transient
	private String strAreaName;

	@Transient
	private String strCityName;

	@Transient
	private String strStateName;

	@Transient
	private String strRegionName;

	@Transient
	private String strCountryName;

	@Transient
	private String strCategoryName;

	@Transient
	private String strExpiryReasonName;
	
	@Column(name = "strAccountCode")
	private String strAccountCode;
	
	@Transient
	private String strAccountName;
	
	@Column(name = "dblConversion", nullable = false,columnDefinition = "double(18,2) NOT NULL default '1'")
	private double dblConversion;
	
	@Column(name = "strOperational", columnDefinition = "VARCHAR(5) NOT NULL")
    private String strOperational;

	// Setter-Getter Methods
	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "0");
	}

	public String getStrCreditorCode() {
		return strCreditorCode;
	}

	public void setStrCreditorCode(String strCreditorCode) {
		this.strCreditorCode = (String) setDefaultValue(strCreditorCode, "");
	}

	public String getStrPrefix() {
		return strPrefix;
	}

	public void setStrPrefix(String strPrefix) {
		this.strPrefix = (String) setDefaultValue(strPrefix, "Mr.");
	}

	public String getStrFirstName() {
		return strFirstName;
	}

	public void setStrFirstName(String strFirstName) {
		this.strFirstName = (String) setDefaultValue(strFirstName, "");
	}

	public String getStrMiddleName() {
		return strMiddleName;
	}

	public void setStrMiddleName(String strMiddleName) {
		this.strMiddleName = (String) setDefaultValue(strMiddleName, "");
	}

	public String getStrLastName() {
		return strLastName;
	}

	public void setStrLastName(String strLastName) {
		this.strLastName = (String) setDefaultValue(strLastName, "");
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = (String) setDefaultValue(strCategoryCode, "");
	}

	public String getStrAddressLine1() {
		return strAddressLine1;
	}

	public void setStrAddressLine1(String strAddressLine1) {
		this.strAddressLine1 = (String) setDefaultValue(strAddressLine1, "");
	}

	public String getStrAddressLine2() {
		return strAddressLine2;
	}

	public void setStrAddressLine2(String strAddressLine2) {
		this.strAddressLine2 = (String) setDefaultValue(strAddressLine2, "");
	}

	public String getStrAddressLine3() {
		return strAddressLine3;
	}

	public void setStrAddressLine3(String strAddressLine3) {
		this.strAddressLine3 = (String) setDefaultValue(strAddressLine3, "");
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = (String) setDefaultValue(strCity, "");
	}

	public String getStrTelNo1() {
		return strTelNo1;
	}

	public void setStrTelNo1(String strTelNo1) {
		this.strTelNo1 = (String) setDefaultValue(strTelNo1, "");
	}

	public String getStrTelNo2() {
		return strTelNo2;
	}

	public void setStrTelNo2(String strTelNo2) {
		this.strTelNo2 = (String) setDefaultValue(strTelNo2, "");
	}

	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = (String) setDefaultValue(strFax, "");
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = (String) setDefaultValue(strArea, "");
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = (String) setDefaultValue(strEmail, "");
	}

	public String getStrContactPerson1() {
		return strContactPerson1;
	}

	public void setStrContactPerson1(String strContactPerson1) {
		this.strContactPerson1 = (String) setDefaultValue(strContactPerson1, "");
	}

	public String getStrContactDesignation1() {
		return strContactDesignation1;
	}

	public void setStrContactDesignation1(String strContactDesignation1) {
		this.strContactDesignation1 = (String) setDefaultValue(strContactDesignation1, "");
	}

	public String getStrContactEmail1() {
		return strContactEmail1;
	}

	public void setStrContactEmail1(String strContactEmail1) {
		this.strContactEmail1 = (String) setDefaultValue(strContactEmail1, "");
	}

	public String getStrContactTelNo1() {
		return strContactTelNo1;
	}

	public void setStrContactTelNo1(String strContactTelNo1) {
		this.strContactTelNo1 = (String) setDefaultValue(strContactTelNo1, "");
	}

	public String getStrContactPerson2() {
		return strContactPerson2;
	}

	public void setStrContactPerson2(String strContactPerson2) {
		this.strContactPerson2 = (String) setDefaultValue(strContactPerson2, "");
	}

	public String getStrContactDesignation2() {
		return strContactDesignation2;
	}

	public void setStrContactDesignation2(String strContactDesignation2) {
		this.strContactDesignation2 = (String) setDefaultValue(strContactDesignation2, "");
	}

	public String getStrContactEmail2() {
		return strContactEmail2;
	}

	public void setStrContactEmail2(String strContactEmail2) {
		this.strContactEmail2 = (String) setDefaultValue(strContactEmail2, "");
	}

	public String getStrContactTelNo2() {
		return strContactTelNo2;
	}

	public void setStrContactTelNo2(String strContactTelNo2) {
		this.strContactTelNo2 = (String) setDefaultValue(strContactTelNo2, "");
	}

	public String getStrLandmark() {
		return strLandmark;
	}

	public void setStrLandmark(String strLandmark) {
		this.strLandmark = (String) setDefaultValue(strLandmark, "");
	}

	public String getStrCreditorFullName() {
		return strCreditorFullName;
	}

	public void setStrCreditorFullName(String strCreditorFullName) {
		this.strCreditorFullName = (String) setDefaultValue(strCreditorFullName, "");
	}

	public String getStrExpired() {
		return strExpired;
	}

	public void setStrExpired(String strExpired) {
		this.strExpired = (String) setDefaultValue(strExpired, "");
	}

	public String getStrExpiryReasonCode() {
		return strExpiryReasonCode;
	}

	public void setStrExpiryReasonCode(String strExpiryReasonCode) {
		this.strExpiryReasonCode = (String) setDefaultValue(strExpiryReasonCode, "");
	}

	public String getStrECSYN() {
		return strECSYN;
	}

	public void setStrECSYN(String strECSYN) {
		this.strECSYN = (String) setDefaultValue(strECSYN, "");
	}

	public String getStrAccountNo() {
		return strAccountNo;
	}

	public void setStrAccountNo(String strAccountNo) {
		this.strAccountNo = (String) setDefaultValue(strAccountNo, "");
	}

	public String getStrHolderName() {
		return strHolderName;
	}

	public void setStrHolderName(String strHolderName) {
		this.strHolderName = (String) setDefaultValue(strHolderName, "");
	}

	public String getStrMICRNo() {
		return strMICRNo;
	}

	public void setStrMICRNo(String strMICRNo) {
		this.strMICRNo = (String) setDefaultValue(strMICRNo, "");
	}

	public String getDblECS() {
		return dblECS;
	}

	public void setDblECS(String dblECS) {
		this.dblECS = (String) setDefaultValue(dblECS, "0");
	}

	public String getStrSaveCurAccount() {
		return strSaveCurAccount;
	}

	public void setStrSaveCurAccount(String strSaveCurAccount) {
		this.strSaveCurAccount = (String) setDefaultValue(strSaveCurAccount, "");
	}

	public String getStrAlternateCode() {
		return strAlternateCode;
	}

	public void setStrAlternateCode(String strAlternateCode) {
		this.strAlternateCode = (String) setDefaultValue(strAlternateCode, "");
	}

	public String getDblOutstanding() {
		return dblOutstanding;
	}

	public void setDblOutstanding(String dblOutstanding) {
		this.dblOutstanding = (String) setDefaultValue(dblOutstanding, "0");
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = (String) setDefaultValue(strStatus, "");
	}

	public String getIntDays1() {
		return intDays1;
	}

	public void setIntDays1(String intDays1) {
		this.intDays1 = (String) setDefaultValue(intDays1, "0");
	}

	public String getIntDays2() {
		return intDays2;
	}

	public void setIntDays2(String intDays2) {
		this.intDays2 = (String) setDefaultValue(intDays2, "0");
	}

	public String getIntDays3() {
		return intDays3;
	}

	public void setIntDays3(String intDays3) {
		this.intDays3 = (String) setDefaultValue(intDays3, "0");
	}

	public String getIntDays4() {
		return intDays4;
	}

	public void setIntDays4(String intDays4) {
		this.intDays4 = (String) setDefaultValue(intDays4, "0");
	}

	public String getIntDays5() {
		return intDays5;
	}

	public void setIntDays5(String intDays5) {
		this.intDays5 = (String) setDefaultValue(intDays5, "0");
	}

	public String getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(String dblCrAmt) {
		this.dblCrAmt = (String) setDefaultValue(dblCrAmt, "0");
	}

	public String getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(String dblDrAmt) {
		this.dblDrAmt = (String) setDefaultValue(dblDrAmt, "0");
	}

	public String getDteLetterProcess() {
		return dteLetterProcess;
	}

	public void setDteLetterProcess(String dteLetterProcess) {
		this.dteLetterProcess = (String) setDefaultValue(dteLetterProcess, "1900-01-01 00:00:00");
	}

	public String getStrReminder1() {
		return strReminder1;
	}

	public void setStrReminder1(String strReminder1) {
		this.strReminder1 = (String) setDefaultValue(strReminder1, "");
	}

	public String getStrReminder2() {
		return strReminder2;
	}

	public void setStrReminder2(String strReminder2) {
		this.strReminder2 = (String) setDefaultValue(strReminder2, "");
	}

	public String getStrReminder3() {
		return strReminder3;
	}

	public void setStrReminder3(String strReminder3) {
		this.strReminder3 = (String) setDefaultValue(strReminder3, "");
	}

	public String getStrReminder4() {
		return strReminder4;
	}

	public void setStrReminder4(String strReminder4) {
		this.strReminder4 = (String) setDefaultValue(strReminder4, "");
	}

	public String getStrReminder5() {
		return strReminder5;
	}

	public void setStrReminder5(String strReminder5) {
		this.strReminder5 = (String) setDefaultValue(strReminder5, "");
	}

	public String getDblLicenseFee() {
		return dblLicenseFee;
	}

	public void setDblLicenseFee(String dblLicenseFee) {
		this.dblLicenseFee = (String) setDefaultValue(dblLicenseFee, "0");
	}

	public String getDblAnnualFee() {
		return dblAnnualFee;
	}

	public void setDblAnnualFee(String dblAnnualFee) {
		this.dblAnnualFee = (String) setDefaultValue(dblAnnualFee, "0");
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "");
	}

	public String getStrClientApproval() {
		return strClientApproval;
	}

	public void setStrClientApproval(String strClientApproval) {
		this.strClientApproval = (String) setDefaultValue(strClientApproval, "");
	}

	public String getStrAMCLink() {
		return strAMCLink;
	}

	public void setStrAMCLink(String strAMCLink) {
		this.strAMCLink = (String) setDefaultValue(strAMCLink, "");
	}

	public String getStrCurrencyType() {
		return strCurrencyType;
	}

	public void setStrCurrencyType(String strCurrencyType) {
		this.strCurrencyType = (String) setDefaultValue(strCurrencyType, "");
	}

	public String getStrAccountHolderCode() {
		return strAccountHolderCode;
	}

	public void setStrAccountHolderCode(String strAccountHolderCode) {
		this.strAccountHolderCode = (String) setDefaultValue(strAccountHolderCode, "");
	}

	public String getStrAccountHolderName() {
		return strAccountHolderName;
	}

	public void setStrAccountHolderName(String strAccountHolderName) {
		this.strAccountHolderName = (String) setDefaultValue(strAccountHolderName, "");
	}

	public String getStrAMCCycle() {
		return strAMCCycle;
	}

	public void setStrAMCCycle(String strAMCCycle) {
		this.strAMCCycle = (String) setDefaultValue(strAMCCycle, "");
	}

	public String getDteStartDate() {
		return dteStartDate;
	}

	public void setDteStartDate(String dteStartDate) {
		this.dteStartDate = (String) setDefaultValue(dteStartDate, "1900-01-01 00:00:00");
	}

	public String getStrAMCRemarks() {
		return strAMCRemarks;
	}

	public void setStrAMCRemarks(String strAMCRemarks) {
		this.strAMCRemarks = (String) setDefaultValue(strAMCRemarks, "");
	}

	public String getStrClientComment() {
		return strClientComment;
	}

	public void setStrClientComment(String strClientComment) {
		this.strClientComment = (String) setDefaultValue(strClientComment, "");
	}

	public String getStrBillingToCode() {
		return strBillingToCode;
	}

	public void setStrBillingToCode(String strBillingToCode) {
		this.strBillingToCode = (String) setDefaultValue(strBillingToCode, "");
	}

	public String getDblAnnualFeeInCurrency() {
		return dblAnnualFeeInCurrency;
	}

	public void setDblAnnualFeeInCurrency(String dblAnnualFeeInCurrency) {
		this.dblAnnualFeeInCurrency = (String) setDefaultValue(dblAnnualFeeInCurrency, "0");
	}

	public String getDblLicenseFeeInCurrency() {
		return dblLicenseFeeInCurrency;
	}

	public void setDblLicenseFeeInCurrency(String dblLicenseFeeInCurrency) {
		this.dblLicenseFeeInCurrency = (String) setDefaultValue(dblLicenseFeeInCurrency, "0");
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = (String) setDefaultValue(strState, "");
	}

	public String getStrRegion() {
		return strRegion;
	}

	public void setStrRegion(String strRegion) {
		this.strRegion = (String) setDefaultValue(strRegion, "");
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = (String) setDefaultValue(strCountry, "");
	}

	public String getStrConsolidated() {
		return strConsolidated;
	}

	public void setStrConsolidated(String strConsolidated) {
		this.strConsolidated = (String) setDefaultValue(strConsolidated, "NO");
	}

	public String getIntCreditDays() {
		return intCreditDays;
	}

	public void setIntCreditDays(String intCreditDays) {
		this.intCreditDays = (String) setDefaultValue(intCreditDays, "0");
	}

	public String getStrCreditorStatusCode() {
		return strCreditorStatusCode;
	}

	public void setStrCreditorStatusCode(String strCreditorStatusCode) {
		this.strCreditorStatusCode = (String) setDefaultValue(strCreditorStatusCode, "");
	}

	public String getStrECSActivate() {
		return strECSActivate;
	}

	public void setStrECSActivate(String strECSActivate) {
		this.strECSActivate = (String) setDefaultValue(strECSActivate, "");
	}

	public String getStrReminderStatus1() {
		return strReminderStatus1;
	}

	public void setStrReminderStatus1(String strReminderStatus1) {
		this.strReminderStatus1 = (String) setDefaultValue(strReminderStatus1, "");
	}

	public String getStrReminderStatus2() {
		return strReminderStatus2;
	}

	public void setStrReminderStatus2(String strReminderStatus2) {
		this.strReminderStatus2 = (String) setDefaultValue(strReminderStatus2, "");
	}

	public String getStrReminderStatus3() {
		return strReminderStatus3;
	}

	public void setStrReminderStatus3(String strReminderStatus3) {
		this.strReminderStatus3 = (String) setDefaultValue(strReminderStatus3, "");
	}

	public String getStrReminderStatus4() {
		return strReminderStatus4;
	}

	public void setStrReminderStatus4(String strReminderStatus4) {
		this.strReminderStatus4 = (String) setDefaultValue(strReminderStatus4, "");
	}

	public String getStrReminderStatus5() {
		return strReminderStatus5;
	}

	public void setStrReminderStatus5(String strReminderStatus5) {
		this.strReminderStatus5 = (String) setDefaultValue(strReminderStatus5, "");
	}

	public String getStrAllInvoiceHeader() {
		return strAllInvoiceHeader;
	}

	public void setStrAllInvoiceHeader(String strAllInvoiceHeader) {
		this.strAllInvoiceHeader = (String) setDefaultValue(strAllInvoiceHeader, "");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "");
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
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "");
	}

	public String getStrAreaName() {
		return strAreaName;
	}

	public void setStrAreaName(String strAreaName) {
		this.strAreaName = (String) setDefaultValue(strAreaName, "");
	}

	public String getStrCityName() {
		return strCityName;
	}

	public void setStrCityName(String strCityName) {
		this.strCityName = (String) setDefaultValue(strCityName, "");
	}

	public String getStrStateName() {
		return strStateName;
	}

	public void setStrStateName(String strStateName) {
		this.strStateName = (String) setDefaultValue(strStateName, "");
	}

	public String getStrRegionName() {
		return strRegionName;
	}

	public void setStrRegionName(String strRegionName) {
		this.strRegionName = (String) setDefaultValue(strRegionName, "");
	}

	public String getStrCountryName() {
		return strCountryName;
	}

	public void setStrCountryName(String strCountryName) {
		this.strCountryName = (String) setDefaultValue(strCountryName, "");
	}

	public String getStrBlocked() {
		return strBlocked;
	}

	public void setStrBlocked(String strBlocked) {
		this.strBlocked = (String) setDefaultValue(strBlocked, "No");
	}

	public String getStrCategoryName() {
		return strCategoryName;
	}

	public void setStrCategoryName(String strCategoryName) {
		this.strCategoryName = (String) setDefaultValue(strCategoryName, "");
	}

	public String getStrExpiryReasonName() {
		return strExpiryReasonName;
	}

	public void setStrExpiryReasonName(String strExpiryReasonName) {
		this.strExpiryReasonName = (String) setDefaultValue(strExpiryReasonName, "");
	}

	public String getLongZipCode() {
		return longZipCode;
	}

	public void setLongZipCode(String longZipCode) {
		this.longZipCode = (String) setDefaultValue(longZipCode, " ");
	}

	public String getLongMobileNo() {
		return longMobileNo;
	}

	public void setLongMobileNo(String longMobileNo) {
		this.longMobileNo = (String) setDefaultValue(longMobileNo, " ");
	}

	public String getDteRemainderDate1() {
		return dteRemainderDate1;
	}

	public void setDteRemainderDate1(String dteRemainderDate1) {
		this.dteRemainderDate1 = (String) setDefaultValue(dteRemainderDate1, "1900-01-01 00:00:00");
	}

	public String getDteRemainderDate2() {
		return dteRemainderDate2;
	}

	public void setDteRemainderDate2(String dteRemainderDate2) {
		this.dteRemainderDate2 = (String) setDefaultValue(dteRemainderDate2, "1900-01-01 00:00:00");
	}

	public String getDteRemainderDate3() {
		return dteRemainderDate3;
	}

	public void setDteRemainderDate3(String dteRemainderDate3) {
		this.dteRemainderDate3 = (String) setDefaultValue(dteRemainderDate3, "1900-01-01 00:00:00");
	}

	public String getDteRemainderDate4() {
		return dteRemainderDate4;
	}

	public void setDteRemainderDate4(String dteRemainderDate4) {
		this.dteRemainderDate4 = (String) setDefaultValue(dteRemainderDate4, "1900-01-01 00:00:00");
	}

	public String getDteRemainderDate5() {
		return dteRemainderDate5;
	}

	public void setDteRemainderDate5(String dteRemainderDate5) {
		this.dteRemainderDate5 = (String) setDefaultValue(dteRemainderDate5, "1900-01-01 00:00:00");
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
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

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	
	
}
