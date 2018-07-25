package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblpropertysetup")
@IdClass(clsParameterSetupModel_ID.class)
public class clsParameterSetupModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public clsParameterSetupModel() {

	}

	public clsParameterSetupModel(clsParameterSetupModel_ID objModelID) {
		strClientCode = objModelID.getStrClientCode();
		strPropertyCode = objModelID.getStrPropertyCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")) })
	@Column(name = "strControlCode")
	private String strControlCode;

	@Column(name = "strControlName")
	private String strControlName;

	@Column(name = "strBillableCode")
	private String strBillableCode;

	@Column(name = "strBillableName")
	private String strBillableName;

	@Column(name = "strSuspenceCode")
	private String strSuspenceCode;

	@Column(name = "strSuspenceName")
	private String strSuspenceName;

	@Column(name = "strDbtrSuspAcctCode")
	private String strDbtrSuspAcctCode;

	@Column(name = "strDbtrSuspAcctName")
	private String strDbtrSuspAcctName;

	@Column(name = "dteLastAR")
	private String dteLastAR;

	@Column(name = "dteLastRV")
	private String dteLastRV;

	@Column(name = "dteRVPosted")
	private String dteRVPosted;

	@Column(name = "strAIMS")
	private String strAIMS;

	@Column(name = "strCRM")
	private String strCRM;

	@Column(name = "strAdultAgeLimit")
	private String strAdultAgeLimit;

	@Column(name = "strAdultMember")
	private String strAdultMember;

	@Column(name = "strChildMember")
	private String strChildMember;

	@Column(name = "strAdultGuest")
	private String strAdultGuest;

	@Column(name = "strChildGuest")
	private String strChildGuest;

	@Column(name = "strYeaOutsta")
	private String strYeaOutsta;

	@Column(name = "strAdAgeLimit")
	private String strAdAgeLimit;

	@Column(name = "strECSBankcode")
	private String strECSBankcode;

	@Column(name = "strECSBankName")
	private String strECSBankName;

	@Column(name = "strSancCode")
	private String strSancCode;

	@Column(name = "strSancName")
	private String strSancName;

	@Column(name = "strExportType")
	private String strExportType;

	@Column(name = "strLastCreated")
	private String strLastCreated;

	@Column(name = "strNarrActivateJv")
	private String strNarrActivateJv;

	@Column(name = "strVouchNarrJv")
	private String strVouchNarrJv;

	@Column(name = "strAcctNarrJv")
	private String strAcctNarrJv;

	@Column(name = "strDebtorNarrJv")
	private String strDebtorNarrJv;

	@Column(name = "strNarrActivatePay")
	private String strNarrActivatePay;

	@Column(name = "strVouchNarrPay")
	private String strVouchNarrPay;

	@Column(name = "strAcctNarrPay")
	private String strAcctNarrPay;

	@Column(name = "strDebtorNarrPay")
	private String strDebtorNarrPay;

	@Column(name = "strNarrActivateRec")
	private String strNarrActivateRec;

	@Column(name = "strVouchNarrRec")
	private String strVouchNarrRec;

	@Column(name = "strAcctNarrRec")
	private String strAcctNarrRec;

	@Column(name = "strDebtorNarrRec")
	private String strDebtorNarrRec;

	@Column(name = "strRoundingCode")
	private String strRoundingCode;

	@Column(name = "strRoundingName")
	private String strRoundingName;

	@Column(name = "dteMemberDataTransfer")
	private String dteMemberDataTransfer;

	@Column(name = "strInvoiceBasedOn")
	private String strInvoiceBasedOn;

	@Column(name = "strEmailSmtpServer")
	private String strEmailSmtpServer;

	@Column(name = "strEmailFrom")
	private String strEmailFrom;

	@Column(name = "strEmailBcc")
	private String strEmailBcc;

	@Column(name = "strLetterCode")
	private String strLetterCode;

	@Column(name = "strLogo")
	private String strLogo;

	@Column(name = "strEcsLetterCode")
	private String strEcsLetterCode;

	@Column(name = "strDbServer")
	private String strDbServer;

	@Column(name = "strUserid")
	private String strUserid;

	@Column(name = "strPassword")
	private String strPassword;

	@Column(name = "strDatabase")
	private String strDatabase;

	@Column(name = "strGolfFac")
	private String strGolfFac;

	@Column(name = "strIntegrityChk")
	private String strIntegrityChk;

	@Column(name = "strLabelsetting")
	private String strLabelsetting;

	@Column(name = "strCreditLimit")
	private String strCreditLimit;

	@Column(name = "strjventry")
	private String strjventry;

	@Column(name = "strpayentry")
	private String strpayentry;

	@Column(name = "strrecpentry")
	private String strrecpentry;

	@Column(name = "strmembrecp")
	private String strmembrecp;

	@Column(name = "strReserveAccCode")
	private String strReserveAccCode;

	@Column(name = "strReserveAccName")
	private String strReserveAccName;

	@Column(name = "strLetterPrefix")
	private String strLetterPrefix;

	@Column(name = "strDbtRoomAdvCode")
	private String strDbtRoomAdvCode;

	@Column(name = "strDbtRoomAdvName")
	private String strDbtRoomAdvName;

	@Column(name = "strDbtRoomACCode")
	private String strDbtRoomACCode;

	@Column(name = "strDbtRoomACName")
	private String strDbtRoomACName;

	@Column(name = "strCustImgPath")
	private String strCustImgPath;

	@Column(name = "strAutoGenCode")
	private String strAutoGenCode;

	@Column(name = "strclientid")
	private String strclientid;

	@Column(name = "strpropertyid")
	private String strpropertyid;

	@Column(name = "strCurrencyCode")
	private String strCurrencyCode;

	@Column(name = "strCurrencyDesc")
	private String strCurrencyDesc;

	@Column(name = "strBillPrefix")
	private String strBillPrefix;

	@Column(name = "strTaxIndicator")
	private String strTaxIndicator;

	@Column(name = "strReceiptLetterCode")
	private String strReceiptLetterCode;

	@Column(name = "strDebtorAck")
	private String strDebtorAck;

	@Column(name = "strReceiptCc")
	private String strReceiptCc;

	@Column(name = "strReceiptBcc")
	private String strReceiptBcc;

	@Column(name = "strSmtpUserid")
	private String strSmtpUserid;

	@Column(name = "strSmtpPassword")
	private String strSmtpPassword;

	@Column(name = "strGroupCode")
	private String strGroupCode;

	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	@Column(name = "strCashFlowCode")
	private String strCashFlowCode;

	@Column(name = "strRoundOffCode")
	private String strRoundOffCode;

	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "strCreditorControlAccount")
	private String strCreditorControlAccount;

	@Column(name = "strAPJVEntry")
	private String strAPJVEntry;

	@Column(name = "strAPPaymentEntry")
	private String strAPPaymentEntry;

	@Column(name = "strAPReceiptEntry")
	private String strAPReceiptEntry;

	@Column(name = "strApDebtorReceiptEntry")
	private String strApDebtorReceiptEntry;

	@Column(name = "strAPECSBankCode")
	private String strAPECSBankCode;

	@Column(name = "strAmadeusInterface")
	private String strAmadeusInterface;

	@Column(name = "strDeborLedgerAccount")
	private String strDeborLedgerAccount;

	@Column(name = "strServiceTaxAccount")
	private String strServiceTaxAccount;

	@Column(name = "strCreditorLedgerAccount")
	private String strCreditorLedgerAccount;

	@Column(name = "strPettyCashAccountCode")
	private String strPettyCashAccountCode;

	@Column(name = "strMemberPreProfiling")
	private String strMemberPreProfiling;

	@Column(name = "strAdvanceAcct")
	private String strAdvanceAcct;

	@Column(name = "strPMS")
	private String strPMS;

	@Column(name = "strTypeOfPOsting")
	private String strTypeOfPOsting;

	@Column(name = "strDebtorPreProfiling")
	private String strDebtorPreProfiling;

	@Column(name = "strInvoiceNarrRec")
	private String strInvoiceNarrRec;

	@Column(name = "strNarrActivateInv")
	private String strNarrActivateInv;

	@Column(name = "strEmailSMTPPort")
	private String strEmailSMTPPort;

	@Column(name = "strEmailCc")
	private String strEmailCc;

	@Column(name = "strInvoicerAdvName")
	private String strInvoicerAdvName;

	@Column(name = "strInvoicerAdvCode")
	private String strInvoicerAdvCode;

	@Column(name = "strPOSCommonDB")
	private String strPOSCommonDB;

	@Column(name = "strPOSQfileDB")
	private String strPOSQfileDB;

	@Column(name = "strPOSMSDNdb")
	private String strPOSMSDNdb;

	@Column(name = "NEFTOnlineAccountCode")
	private String NEFTOnlineAccountCode;

	@Column(name = "NEFTOnlineAccountName")
	private String NEFTOnlineAccountName;

	@Column(name = "StrMasterDrivenNarration")
	private String StrMasterDrivenNarration;

	@Column(name = "IsMSOfficeInstalled")
	private String IsMSOfficeInstalled;

	@Column(name = "IncludeBanquetMember")
	private String IncludeBanquetMember;

	@Column(name = "IsMultipleDebtor")
	private String IsMultipleDebtor;

	@Column(name = "AllowSingleUserLogin")
	private String AllowSingleUserLogin;

	@Column(name = "EmailViaOutlook")
	private String EmailViaOutlook;

	@Column(name = "SML")
	private String SML;

	@Column(name = "PDCAccountCode")
	private String PDCAccountCode;

	@Column(name = "PDCAccountDesc")
	private String PDCAccountDesc;

	@Column(name = "strPostDatedChequeACCode")
	private String strPostDatedChequeACCode;

	@Column(name = "strPostDatedChequeACName")
	private String strPostDatedChequeACName;

	@Column(name = "strDebtorLedgerACCode")
	private String strDebtorLedgerACCode;

	@Column(name = "strDebtorLedgerACName")
	private String strDebtorLedgerACName;

	@Column(name = "strAdvanceACCode")
	private String strAdvanceACCode;

	@Column(name = "strTallyAlifTransLockYN")
	private String strTallyAlifTransLockYN;

	@Column(name = "strInvoiceHeader1")
	private String strInvoiceHeader1;

	@Column(name = "strInvoiceHeader2")
	private String strInvoiceHeader2;

	@Column(name = "strInvoiceHeader3")
	private String strInvoiceHeader3;

	@Column(name = "strInvoiceFooter1")
	private String strInvoiceFooter1;

	@Column(name = "strInvoiceFooter2")
	private String strInvoiceFooter2;

	@Column(name = "strInvoiceFooter3")
	private String strInvoiceFooter3;

	@Column(name = "strVouchNarrInvoice")
	private String strVouchNarrInvoice;

	@Column(name = "strSSLRequiredYN")
	private String strSSLRequiredYN;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods

	public String getEmailViaOutlook() {
		return EmailViaOutlook;
	}

	public void setEmailViaOutlook(String emailViaOutlook) {
		EmailViaOutlook = (String) setDefaultValue(emailViaOutlook, "NA");
	}

	public String getAllowSingleUserLogin() {
		return AllowSingleUserLogin;
	}

	public void setAllowSingleUserLogin(String allowSingleUserLogin) {
		AllowSingleUserLogin = (String) setDefaultValue(allowSingleUserLogin, "NA");
	}

	public String getStrControlCode() {
		return strControlCode;
	}

	public void setStrControlCode(String strControlCode) {
		this.strControlCode = (String) setDefaultValue(strControlCode, "NA");
	}

	public String getStrControlName() {
		return strControlName;
	}

	public void setStrControlName(String strControlName) {
		this.strControlName = (String) setDefaultValue(strControlName, "NA");
	}

	public String getStrBillableCode() {
		return strBillableCode;
	}

	public void setStrBillableCode(String strBillableCode) {
		this.strBillableCode = (String) setDefaultValue(strBillableCode, "NA");
	}

	public String getStrBillableName() {
		return strBillableName;
	}

	public void setStrBillableName(String strBillableName) {
		this.strBillableName = (String) setDefaultValue(strBillableName, "NA");
	}

	public String getStrSuspenceCode() {
		return strSuspenceCode;
	}

	public void setStrSuspenceCode(String strSuspenceCode) {
		this.strSuspenceCode = (String) setDefaultValue(strSuspenceCode, "NA");
	}

	public String getStrSuspenceName() {
		return strSuspenceName;
	}

	public void setStrSuspenceName(String strSuspenceName) {
		this.strSuspenceName = (String) setDefaultValue(strSuspenceName, "NA");
	}

	public String getStrDbtrSuspAcctCode() {
		return strDbtrSuspAcctCode;
	}

	public void setStrDbtrSuspAcctCode(String strDbtrSuspAcctCode) {
		this.strDbtrSuspAcctCode = (String) setDefaultValue(strDbtrSuspAcctCode, "NA");
	}

	public String getStrDbtrSuspAcctName() {
		return strDbtrSuspAcctName;
	}

	public void setStrDbtrSuspAcctName(String strDbtrSuspAcctName) {
		this.strDbtrSuspAcctName = (String) setDefaultValue(strDbtrSuspAcctName, "NA");
	}

	public String getDteLastAR() {
		return dteLastAR;
	}

	public void setDteLastAR(String dteLastAR) {
		this.dteLastAR = (String) setDefaultValue(dteLastAR, "1900-01-01 00:00:00");
	}

	public String getDteLastRV() {
		return dteLastRV;
	}

	public void setDteLastRV(String dteLastRV) {
		this.dteLastRV = (String) setDefaultValue(dteLastRV, "1900-01-01 00:00:00");
	}

	public String getDteRVPosted() {
		return dteRVPosted;
	}

	public void setDteRVPosted(String dteRVPosted) {
		this.dteRVPosted = (String) setDefaultValue(dteRVPosted, "1900-01-01 00:00:00");
	}

	public String getStrAIMS() {
		return strAIMS;
	}

	public void setStrAIMS(String strAIMS) {
		this.strAIMS = (String) setDefaultValue(strAIMS, "NA");
	}

	public String getStrCRM() {
		return strCRM;
	}

	public void setStrCRM(String strCRM) {
		this.strCRM = (String) setDefaultValue(strCRM, "NA");
	}

	public String getStrAdultAgeLimit() {
		return strAdultAgeLimit;
	}

	public void setStrAdultAgeLimit(String strAdultAgeLimit) {
		this.strAdultAgeLimit = (String) setDefaultValue(strAdultAgeLimit, "NA");
	}

	public String getStrAdultMember() {
		return strAdultMember;
	}

	public void setStrAdultMember(String strAdultMember) {
		this.strAdultMember = (String) setDefaultValue(strAdultMember, "NA");
	}

	public String getStrChildMember() {
		return strChildMember;
	}

	public void setStrChildMember(String strChildMember) {
		this.strChildMember = (String) setDefaultValue(strChildMember, "NA");
	}

	public String getStrAdultGuest() {
		return strAdultGuest;
	}

	public void setStrAdultGuest(String strAdultGuest) {
		this.strAdultGuest = (String) setDefaultValue(strAdultGuest, "NA");
	}

	public String getStrChildGuest() {
		return strChildGuest;
	}

	public void setStrChildGuest(String strChildGuest) {
		this.strChildGuest = (String) setDefaultValue(strChildGuest, "NA");
	}

	public String getStrYeaOutsta() {
		return strYeaOutsta;
	}

	public void setStrYeaOutsta(String strYeaOutsta) {
		this.strYeaOutsta = (String) setDefaultValue(strYeaOutsta, "NA");
	}

	public String getStrAdAgeLimit() {
		return strAdAgeLimit;
	}

	public void setStrAdAgeLimit(String strAdAgeLimit) {
		this.strAdAgeLimit = (String) setDefaultValue(strAdAgeLimit, "NA");
	}

	public String getStrECSBankcode() {
		return strECSBankcode;
	}

	public void setStrECSBankcode(String strECSBankcode) {
		this.strECSBankcode = (String) setDefaultValue(strECSBankcode, "NA");
	}

	public String getStrECSBankName() {
		return strECSBankName;
	}

	public void setStrECSBankName(String strECSBankName) {
		this.strECSBankName = (String) setDefaultValue(strECSBankName, "NA");
	}

	public String getStrSancCode() {
		return strSancCode;
	}

	public void setStrSancCode(String strSancCode) {
		this.strSancCode = (String) setDefaultValue(strSancCode, "NA");
	}

	public String getStrSancName() {
		return strSancName;
	}

	public void setStrSancName(String strSancName) {
		this.strSancName = (String) setDefaultValue(strSancName, "NA");
	}

	public String getStrExportType() {
		return strExportType;
	}

	public void setStrExportType(String strExportType) {
		this.strExportType = (String) setDefaultValue(strExportType, "NA");
	}

	public String getStrLastCreated() {
		return strLastCreated;
	}

	public void setStrLastCreated(String strLastCreated) {
		this.strLastCreated = (String) setDefaultValue(strLastCreated, "NA");
	}

	public String getStrNarrActivateJv() {
		return strNarrActivateJv;
	}

	public void setStrNarrActivateJv(String strNarrActivateJv) {
		this.strNarrActivateJv = (String) setDefaultValue(strNarrActivateJv, "NA");
	}

	public String getStrVouchNarrJv() {
		return strVouchNarrJv;
	}

	public void setStrVouchNarrJv(String strVouchNarrJv) {
		this.strVouchNarrJv = (String) setDefaultValue(strVouchNarrJv, "NA");
	}

	public String getStrAcctNarrJv() {
		return strAcctNarrJv;
	}

	public void setStrAcctNarrJv(String strAcctNarrJv) {
		this.strAcctNarrJv = (String) setDefaultValue(strAcctNarrJv, "NA");
	}

	public String getStrDebtorNarrJv() {
		return strDebtorNarrJv;
	}

	public void setStrDebtorNarrJv(String strDebtorNarrJv) {
		this.strDebtorNarrJv = (String) setDefaultValue(strDebtorNarrJv, "NA");
	}

	public String getStrNarrActivatePay() {
		return strNarrActivatePay;
	}

	public void setStrNarrActivatePay(String strNarrActivatePay) {
		this.strNarrActivatePay = (String) setDefaultValue(strNarrActivatePay, "NA");
	}

	public String getStrVouchNarrPay() {
		return strVouchNarrPay;
	}

	public void setStrVouchNarrPay(String strVouchNarrPay) {
		this.strVouchNarrPay = (String) setDefaultValue(strVouchNarrPay, "NA");
	}

	public String getStrAcctNarrPay() {
		return strAcctNarrPay;
	}

	public void setStrAcctNarrPay(String strAcctNarrPay) {
		this.strAcctNarrPay = (String) setDefaultValue(strAcctNarrPay, "NA");
	}

	public String getStrDebtorNarrPay() {
		return strDebtorNarrPay;
	}

	public void setStrDebtorNarrPay(String strDebtorNarrPay) {
		this.strDebtorNarrPay = (String) setDefaultValue(strDebtorNarrPay, "NA");
	}

	public String getStrNarrActivateRec() {
		return strNarrActivateRec;
	}

	public void setStrNarrActivateRec(String strNarrActivateRec) {
		this.strNarrActivateRec = (String) setDefaultValue(strNarrActivateRec, "NA");
	}

	public String getStrVouchNarrRec() {
		return strVouchNarrRec;
	}

	public void setStrVouchNarrRec(String strVouchNarrRec) {
		this.strVouchNarrRec = (String) setDefaultValue(strVouchNarrRec, "NA");
	}

	public String getStrAcctNarrRec() {
		return strAcctNarrRec;
	}

	public void setStrAcctNarrRec(String strAcctNarrRec) {
		this.strAcctNarrRec = (String) setDefaultValue(strAcctNarrRec, "NA");
	}

	public String getStrDebtorNarrRec() {
		return strDebtorNarrRec;
	}

	public void setStrDebtorNarrRec(String strDebtorNarrRec) {
		this.strDebtorNarrRec = (String) setDefaultValue(strDebtorNarrRec, "NA");
	}

	public String getStrRoundingCode() {
		return strRoundingCode;
	}

	public void setStrRoundingCode(String strRoundingCode) {
		this.strRoundingCode = (String) setDefaultValue(strRoundingCode, "NA");
	}

	public String getStrRoundingName() {
		return strRoundingName;
	}

	public void setStrRoundingName(String strRoundingName) {
		this.strRoundingName = (String) setDefaultValue(strRoundingName, "NA");
	}

	public String getDteMemberDataTransfer() {
		return dteMemberDataTransfer;
	}

	public void setDteMemberDataTransfer(String dteMemberDataTransfer) {
		this.dteMemberDataTransfer = (String) setDefaultValue(dteMemberDataTransfer, "NA");
	}

	public String getStrInvoiceBasedOn() {
		return strInvoiceBasedOn;
	}

	public void setStrInvoiceBasedOn(String strInvoiceBasedOn) {
		this.strInvoiceBasedOn = (String) setDefaultValue(strInvoiceBasedOn, "NA");
	}

	public String getStrEmailSmtpServer() {
		return strEmailSmtpServer;
	}

	public void setStrEmailSmtpServer(String strEmailSmtpServer) {
		this.strEmailSmtpServer = (String) setDefaultValue(strEmailSmtpServer, "NA");
	}

	public String getStrEmailFrom() {
		return strEmailFrom;
	}

	public void setStrEmailFrom(String strEmailFrom) {
		this.strEmailFrom = (String) setDefaultValue(strEmailFrom, "NA");
	}

	public String getStrEmailBcc() {
		return strEmailBcc;
	}

	public void setStrEmailBcc(String strEmailBcc) {
		this.strEmailBcc = (String) setDefaultValue(strEmailBcc, "NA");
	}

	public String getStrLetterCode() {
		return strLetterCode;
	}

	public void setStrLetterCode(String strLetterCode) {
		this.strLetterCode = (String) setDefaultValue(strLetterCode, "NA");
	}

	public String getStrLogo() {
		return strLogo;
	}

	public void setStrLogo(String strLogo) {
		this.strLogo = (String) setDefaultValue(strLogo, "NA");
	}

	public String getStrEcsLetterCode() {
		return strEcsLetterCode;
	}

	public void setStrEcsLetterCode(String strEcsLetterCode) {
		this.strEcsLetterCode = (String) setDefaultValue(strEcsLetterCode, "NA");
	}

	public String getStrDbServer() {
		return strDbServer;
	}

	public void setStrDbServer(String strDbServer) {
		this.strDbServer = (String) setDefaultValue(strDbServer, "NA");
	}

	public String getStrUserid() {
		return strUserid;
	}

	public void setStrUserid(String strUserid) {
		this.strUserid = (String) setDefaultValue(strUserid, "NA");
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = (String) setDefaultValue(strPassword, "NA");
	}

	public String getStrDatabase() {
		return strDatabase;
	}

	public void setStrDatabase(String strDatabase) {
		this.strDatabase = (String) setDefaultValue(strDatabase, "NA");
	}

	public String getStrGolfFac() {
		return strGolfFac;
	}

	public void setStrGolfFac(String strGolfFac) {
		this.strGolfFac = (String) setDefaultValue(strGolfFac, "NA");
	}

	public String getStrIntegrityChk() {
		return strIntegrityChk;
	}

	public void setStrIntegrityChk(String strIntegrityChk) {
		this.strIntegrityChk = (String) setDefaultValue(strIntegrityChk, "NA");
	}

	public String getStrLabelsetting() {
		return strLabelsetting;
	}

	public void setStrLabelsetting(String strLabelsetting) {
		this.strLabelsetting = (String) setDefaultValue(strLabelsetting, "NA");
	}

	public String getStrCreditLimit() {
		return strCreditLimit;
	}

	public void setStrCreditLimit(String strCreditLimit) {
		this.strCreditLimit = (String) setDefaultValue(strCreditLimit, "N");
	}

	public String getStrjventry() {
		return strjventry;
	}

	public void setStrjventry(String strjventry) {
		this.strjventry = (String) setDefaultValue(strjventry, "NA");
	}

	public String getStrpayentry() {
		return strpayentry;
	}

	public void setStrpayentry(String strpayentry) {
		this.strpayentry = (String) setDefaultValue(strpayentry, "NA");
	}

	public String getStrrecpentry() {
		return strrecpentry;
	}

	public void setStrrecpentry(String strrecpentry) {
		this.strrecpentry = (String) setDefaultValue(strrecpentry, "NA");
	}

	public String getStrmembrecp() {
		return strmembrecp;
	}

	public void setStrmembrecp(String strmembrecp) {
		this.strmembrecp = (String) setDefaultValue(strmembrecp, "NA");
	}

	public String getStrReserveAccCode() {
		return strReserveAccCode;
	}

	public void setStrReserveAccCode(String strReserveAccCode) {
		this.strReserveAccCode = (String) setDefaultValue(strReserveAccCode, "NA");
	}

	public String getStrReserveAccName() {
		return strReserveAccName;
	}

	public void setStrReserveAccName(String strReserveAccName) {
		this.strReserveAccName = (String) setDefaultValue(strReserveAccName, "NA");
	}

	public String getStrLetterPrefix() {
		return strLetterPrefix;
	}

	public void setStrLetterPrefix(String strLetterPrefix) {
		this.strLetterPrefix = (String) setDefaultValue(strLetterPrefix, "NA");
	}

	public String getStrDbtRoomAdvCode() {
		return strDbtRoomAdvCode;
	}

	public void setStrDbtRoomAdvCode(String strDbtRoomAdvCode) {
		this.strDbtRoomAdvCode = (String) setDefaultValue(strDbtRoomAdvCode, "NA");
	}

	public String getStrDbtRoomAdvName() {
		return strDbtRoomAdvName;
	}

	public void setStrDbtRoomAdvName(String strDbtRoomAdvName) {
		this.strDbtRoomAdvName = (String) setDefaultValue(strDbtRoomAdvName, "NA");
	}

	public String getStrDbtRoomACCode() {
		return strDbtRoomACCode;
	}

	public void setStrDbtRoomACCode(String strDbtRoomACCode) {
		this.strDbtRoomACCode = (String) setDefaultValue(strDbtRoomACCode, "NA");
	}

	public String getStrDbtRoomACName() {
		return strDbtRoomACName;
	}

	public void setStrDbtRoomACName(String strDbtRoomACName) {
		this.strDbtRoomACName = (String) setDefaultValue(strDbtRoomACName, "NA");
	}

	public String getStrCustImgPath() {
		return strCustImgPath;
	}

	public void setStrCustImgPath(String strCustImgPath) {
		this.strCustImgPath = (String) setDefaultValue(strCustImgPath, "NA");
	}

	public String getStrAutoGenCode() {
		return strAutoGenCode;
	}

	public void setStrAutoGenCode(String strAutoGenCode) {
		this.strAutoGenCode = (String) setDefaultValue(strAutoGenCode, "NA");
	}

	public String getStrclientid() {
		return strclientid;
	}

	public void setStrclientid(String strclientid) {
		this.strclientid = (String) setDefaultValue(strclientid, "NA");
	}

	public String getStrpropertyid() {
		return strpropertyid;
	}

	public void setStrpropertyid(String strpropertyid) {
		this.strpropertyid = (String) setDefaultValue(strpropertyid, "NA");
	}

	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = (String) setDefaultValue(strCurrencyCode, "NA");
	}

	public String getStrCurrencyDesc() {
		return strCurrencyDesc;
	}

	public void setStrCurrencyDesc(String strCurrencyDesc) {
		this.strCurrencyDesc = (String) setDefaultValue(strCurrencyDesc, "NA");
	}

	public String getStrBillPrefix() {
		return strBillPrefix;
	}

	public void setStrBillPrefix(String strBillPrefix) {
		this.strBillPrefix = (String) setDefaultValue(strBillPrefix, "NA");
	}

	public String getStrTaxIndicator() {
		return strTaxIndicator;
	}

	public void setStrTaxIndicator(String strTaxIndicator) {
		this.strTaxIndicator = (String) setDefaultValue(strTaxIndicator, "NA");
	}

	public String getStrReceiptLetterCode() {
		return strReceiptLetterCode;
	}

	public void setStrReceiptLetterCode(String strReceiptLetterCode) {
		this.strReceiptLetterCode = (String) setDefaultValue(strReceiptLetterCode, "NA");
	}

	public String getStrDebtorAck() {
		return strDebtorAck;
	}

	public void setStrDebtorAck(String strDebtorAck) {
		this.strDebtorAck = (String) setDefaultValue(strDebtorAck, "NA");
	}

	public String getStrReceiptCc() {
		return strReceiptCc;
	}

	public void setStrReceiptCc(String strReceiptCc) {
		this.strReceiptCc = (String) setDefaultValue(strReceiptCc, "NA");
	}

	public String getStrReceiptBcc() {
		return strReceiptBcc;
	}

	public void setStrReceiptBcc(String strReceiptBcc) {
		this.strReceiptBcc = (String) setDefaultValue(strReceiptBcc, "NA");
	}

	public String getStrSmtpUserid() {
		return strSmtpUserid;
	}

	public void setStrSmtpUserid(String strSmtpUserid) {
		this.strSmtpUserid = (String) setDefaultValue(strSmtpUserid, "NA");
	}

	public String getStrSmtpPassword() {
		return strSmtpPassword;
	}

	public void setStrSmtpPassword(String strSmtpPassword) {
		this.strSmtpPassword = (String) setDefaultValue(strSmtpPassword, "NA");
	}

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = (String) setDefaultValue(strGroupCode, "NA");
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = (String) setDefaultValue(strCategoryCode, "NA");
	}

	public String getStrCashFlowCode() {
		return strCashFlowCode;
	}

	public void setStrCashFlowCode(String strCashFlowCode) {
		this.strCashFlowCode = (String) setDefaultValue(strCashFlowCode, "NA");
	}

	public String getStrRoundOffCode() {
		return strRoundOffCode;
	}

	public void setStrRoundOffCode(String strRoundOffCode) {
		this.strRoundOffCode = (String) setDefaultValue(strRoundOffCode, "NA");
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = (String) setDefaultValue(strTaxCode, "NA");
	}

	public String getStrCreditorControlAccount() {
		return strCreditorControlAccount;
	}

	public void setStrCreditorControlAccount(String strCreditorControlAccount) {
		this.strCreditorControlAccount = (String) setDefaultValue(strCreditorControlAccount, "NA");
	}

	public String getStrAPJVEntry() {
		return strAPJVEntry;
	}

	public void setStrAPJVEntry(String strAPJVEntry) {
		this.strAPJVEntry = (String) setDefaultValue(strAPJVEntry, "NA");
	}

	public String getStrAPPaymentEntry() {
		return strAPPaymentEntry;
	}

	public void setStrAPPaymentEntry(String strAPPaymentEntry) {
		this.strAPPaymentEntry = (String) setDefaultValue(strAPPaymentEntry, "NA");
	}

	public String getStrAPReceiptEntry() {
		return strAPReceiptEntry;
	}

	public void setStrAPReceiptEntry(String strAPReceiptEntry) {
		this.strAPReceiptEntry = (String) setDefaultValue(strAPReceiptEntry, "NA");
	}

	public String getStrApDebtorReceiptEntry() {
		return strApDebtorReceiptEntry;
	}

	public void setStrApDebtorReceiptEntry(String strApDebtorReceiptEntry) {
		this.strApDebtorReceiptEntry = (String) setDefaultValue(strApDebtorReceiptEntry, "NA");
	}

	public String getStrAPECSBankCode() {
		return strAPECSBankCode;
	}

	public void setStrAPECSBankCode(String strAPECSBankCode) {
		this.strAPECSBankCode = (String) setDefaultValue(strAPECSBankCode, "NA");
	}

	public String getStrAmadeusInterface() {
		return strAmadeusInterface;
	}

	public void setStrAmadeusInterface(String strAmadeusInterface) {
		this.strAmadeusInterface = (String) setDefaultValue(strAmadeusInterface, "NA");
	}

	public String getStrDeborLedgerAccount() {
		return strDeborLedgerAccount;
	}

	public void setStrDeborLedgerAccount(String strDeborLedgerAccount) {
		this.strDeborLedgerAccount = (String) setDefaultValue(strDeborLedgerAccount, "NA");
	}

	public String getStrServiceTaxAccount() {
		return strServiceTaxAccount;
	}

	public void setStrServiceTaxAccount(String strServiceTaxAccount) {
		this.strServiceTaxAccount = (String) setDefaultValue(strServiceTaxAccount, "NA");
	}

	public String getStrCreditorLedgerAccount() {
		return strCreditorLedgerAccount;
	}

	public void setStrCreditorLedgerAccount(String strCreditorLedgerAccount) {
		this.strCreditorLedgerAccount = (String) setDefaultValue(strCreditorLedgerAccount, "NA");
	}

	public String getStrPettyCashAccountCode() {
		return strPettyCashAccountCode;
	}

	public void setStrPettyCashAccountCode(String strPettyCashAccountCode) {
		this.strPettyCashAccountCode = (String) setDefaultValue(strPettyCashAccountCode, "NA");
	}

	public String getStrMemberPreProfiling() {
		return strMemberPreProfiling;
	}

	public void setStrMemberPreProfiling(String strMemberPreProfiling) {
		this.strMemberPreProfiling = (String) setDefaultValue(strMemberPreProfiling, "NA");
	}

	public String getStrAdvanceAcct() {
		return strAdvanceAcct;
	}

	public void setStrAdvanceAcct(String strAdvanceAcct) {
		this.strAdvanceAcct = (String) setDefaultValue(strAdvanceAcct, "NA");
	}

	public String getStrPMS() {
		return strPMS;
	}

	public void setStrPMS(String strPMS) {
		this.strPMS = (String) setDefaultValue(strPMS, "NA");
	}

	public String getStrTypeOfPOsting() {
		return strTypeOfPOsting;
	}

	public void setStrTypeOfPOsting(String strTypeOfPOsting) {
		this.strTypeOfPOsting = (String) setDefaultValue(strTypeOfPOsting, "NA");
	}

	public String getStrDebtorPreProfiling() {
		return strDebtorPreProfiling;
	}

	public void setStrDebtorPreProfiling(String strDebtorPreProfiling) {
		this.strDebtorPreProfiling = (String) setDefaultValue(strDebtorPreProfiling, "NA");
	}

	public String getStrInvoiceNarrRec() {
		return strInvoiceNarrRec;
	}

	public void setStrInvoiceNarrRec(String strInvoiceNarrRec) {
		this.strInvoiceNarrRec = (String) setDefaultValue(strInvoiceNarrRec, "NA");
	}

	public String getStrNarrActivateInv() {
		return strNarrActivateInv;
	}

	public void setStrNarrActivateInv(String strNarrActivateInv) {
		this.strNarrActivateInv = (String) setDefaultValue(strNarrActivateInv, "NA");
	}

	public String getStrEmailSMTPPort() {
		return strEmailSMTPPort;
	}

	public void setStrEmailSMTPPort(String strEmailSMTPPort) {
		this.strEmailSMTPPort = (String) setDefaultValue(strEmailSMTPPort, "NA");
	}

	public String getStrEmailCc() {
		return strEmailCc;
	}

	public void setStrEmailCc(String strEmailCc) {
		this.strEmailCc = (String) setDefaultValue(strEmailCc, "NA");
	}

	public String getStrInvoicerAdvName() {
		return strInvoicerAdvName;
	}

	public void setStrInvoicerAdvName(String strInvoicerAdvName) {
		this.strInvoicerAdvName = (String) setDefaultValue(strInvoicerAdvName, "NA");
	}

	public String getStrInvoicerAdvCode() {
		return strInvoicerAdvCode;
	}

	public void setStrInvoicerAdvCode(String strInvoicerAdvCode) {
		this.strInvoicerAdvCode = (String) setDefaultValue(strInvoicerAdvCode, "NA");
	}

	public String getStrPOSCommonDB() {
		return strPOSCommonDB;
	}

	public void setStrPOSCommonDB(String strPOSCommonDB) {
		this.strPOSCommonDB = (String) setDefaultValue(strPOSCommonDB, "NA");
	}

	public String getStrPOSQfileDB() {
		return strPOSQfileDB;
	}

	public void setStrPOSQfileDB(String strPOSQfileDB) {
		this.strPOSQfileDB = (String) setDefaultValue(strPOSQfileDB, "NA");
	}

	public String getStrPOSMSDNdb() {
		return strPOSMSDNdb;
	}

	public void setStrPOSMSDNdb(String strPOSMSDNdb) {
		this.strPOSMSDNdb = (String) setDefaultValue(strPOSMSDNdb, "NA");
	}

	public String getNEFTOnlineAccountCode() {
		return NEFTOnlineAccountCode;
	}

	public void setNEFTOnlineAccountCode(String nEFTOnlineAccountCode) {
		NEFTOnlineAccountCode = (String) setDefaultValue(nEFTOnlineAccountCode, "NA");
	}

	public String getNEFTOnlineAccountName() {
		return NEFTOnlineAccountName;
	}

	public void setNEFTOnlineAccountName(String nEFTOnlineAccountName) {
		NEFTOnlineAccountName = (String) setDefaultValue(nEFTOnlineAccountName, "NA");
	}

	public String getStrMasterDrivenNarration() {
		return StrMasterDrivenNarration;
	}

	public void setStrMasterDrivenNarration(String strMasterDrivenNarration) {
		StrMasterDrivenNarration = (String) setDefaultValue(strMasterDrivenNarration, "NA");
	}

	public String getIsMSOfficeInstalled() {
		return IsMSOfficeInstalled;
	}

	public void setIsMSOfficeInstalled(String isMSOfficeInstalled) {
		IsMSOfficeInstalled = (String) setDefaultValue(isMSOfficeInstalled, "NA");
	}

	public String getIsMultipleDebtor() {
		return IsMultipleDebtor;
	}

	public void setIsMultipleDebtor(String isMultipleDebtor) {
		IsMultipleDebtor = (String) setDefaultValue(isMultipleDebtor, "NA");
	}

	public String getIncludeBanquetMember() {
		return IncludeBanquetMember;
	}

	public void setIncludeBanquetMember(String includeBanquetMember) {
		IncludeBanquetMember = (String) setDefaultValue(includeBanquetMember, "NA");
	}

	public String getSML() {
		return SML;
	}

	public void setSML(String sML) {
		SML = (String) setDefaultValue(sML, "NA");
	}

	public String getPDCAccountCode() {
		return PDCAccountCode;
	}

	public void setPDCAccountCode(String pDCAccountCode) {
		PDCAccountCode = (String) setDefaultValue(pDCAccountCode, "NA");
	}

	public String getPDCAccountDesc() {
		return PDCAccountDesc;
	}

	public void setPDCAccountDesc(String pDCAccountDesc) {
		PDCAccountDesc = (String) setDefaultValue(pDCAccountDesc, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getStrPostDatedChequeACCode() {
		return strPostDatedChequeACCode;
	}

	public void setStrPostDatedChequeACCode(String strPostDatedChequeACCode) {
		this.strPostDatedChequeACCode = (String) setDefaultValue(strPostDatedChequeACCode, "NA");
	}

	public String getStrPostDatedChequeACName() {
		return strPostDatedChequeACName;
	}

	public void setStrPostDatedChequeACName(String strPostDatedChequeACName) {
		this.strPostDatedChequeACName = (String) setDefaultValue(strPostDatedChequeACName, "NA");
	}

	public String getStrDebtorLedgerACCode() {
		return strDebtorLedgerACCode;
	}

	public void setStrDebtorLedgerACCode(String strDebtorLedgerACCode) {
		this.strDebtorLedgerACCode = (String) setDefaultValue(strDebtorLedgerACCode, "NA");
	}

	public String getStrDebtorLedgerACName() {
		return strDebtorLedgerACName;
	}

	public void setStrDebtorLedgerACName(String strDebtorLedgerACName) {
		this.strDebtorLedgerACName = (String) setDefaultValue(strDebtorLedgerACName, "NA");
	}

	public String getStrAdvanceACCode() {
		return strAdvanceACCode;
	}

	public void setStrAdvanceACCode(String strAdvanceACCode) {
		this.strAdvanceACCode = (String) setDefaultValue(strAdvanceACCode, "NA");
	}

	public String getStrTallyAlifTransLockYN() {
		return strTallyAlifTransLockYN;
	}

	public void setStrTallyAlifTransLockYN(String strTallyAlifTransLockYN) {
		this.strTallyAlifTransLockYN = (String) setDefaultValue(strTallyAlifTransLockYN, "NA");
	}

	public String getStrInvoiceHeader1() {
		return strInvoiceHeader1;
	}

	public void setStrInvoiceHeader1(String strInvoiceHeader1) {
		this.strInvoiceHeader1 = (String) setDefaultValue(strInvoiceHeader1, "NA");
	}

	public String getStrInvoiceHeader2() {
		return strInvoiceHeader2;
	}

	public void setStrInvoiceHeader2(String strInvoiceHeader2) {
		this.strInvoiceHeader2 = (String) setDefaultValue(strInvoiceHeader2, "NA");
	}

	public String getStrInvoiceHeader3() {
		return strInvoiceHeader3;
	}

	public void setStrInvoiceHeader3(String strInvoiceHeader3) {
		this.strInvoiceHeader3 = (String) setDefaultValue(strInvoiceHeader3, "NA");
	}

	public String getStrInvoiceFooter1() {
		return strInvoiceFooter1;
	}

	public void setStrInvoiceFooter1(String strInvoiceFooter1) {
		this.strInvoiceFooter1 = (String) setDefaultValue(strInvoiceFooter1, "NA");
	}

	public String getStrInvoiceFooter2() {
		return strInvoiceFooter2;
	}

	public void setStrInvoiceFooter2(String strInvoiceFooter2) {
		this.strInvoiceFooter2 = (String) setDefaultValue(strInvoiceFooter2, "NA");
	}

	public String getStrInvoiceFooter3() {
		return strInvoiceFooter3;
	}

	public void setStrInvoiceFooter3(String strInvoiceFooter3) {
		this.strInvoiceFooter3 = (String) setDefaultValue(strInvoiceFooter3, "NA");
	}

	public String getStrVouchNarrInvoice() {
		return strVouchNarrInvoice;
	}

	public void setStrVouchNarrInvoice(String strVouchNarrInvoice) {
		this.strVouchNarrInvoice = (String) setDefaultValue(strVouchNarrInvoice, "NA");
	}

	public String getStrSSLRequiredYN() {
		return strSSLRequiredYN;
	}

	public void setStrSSLRequiredYN(String strSSLRequiredYN) {
		this.strSSLRequiredYN = (String) setDefaultValue(strSSLRequiredYN, "NA");
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
		} else if (value != null && value instanceof Boolean) {
			return value;
		} else {
			return defaultValue;
		}
	}

}
