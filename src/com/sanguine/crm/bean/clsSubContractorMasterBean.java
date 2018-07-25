package com.sanguine.crm.bean;

import java.util.List;

import javax.persistence.Column;

import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;

public class clsSubContractorMasterBean {

	private String strPCode, strPName, strPhone, strMobile, strFax, strContact, strEmail, strPType, strBankName, strBankAdd1, strBankAdd2;
	private String strTaxNo1, strTaxNo2, strPartyType, strPmtTerms, strAcCrCode, strMAdd1, strMAdd2, strMCity, strMPin, strMState, strMCountry;
	private String strBAdd1, strBAdd2, strBCity, strBPin, strBState, strBCountry;
	private String strSAdd1, strSAdd2, strSCity, strSPin, strSState, strSCountry;
	private String strCST, strVAT, strExcise, strServiceTax, strSubType;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String dtExpiryDate, strManualCode, strRegistration, strRange, strDivision, strCommissionerate, strBankAccountNo, strBankABANo;
	private long intId, intCreditDays;
	private double dblCreditLimit, dblLatePercentage, dblRejectPercentage;
	private String strIBANNo, strSwiftCode, strCategory, strExcisable;
	private String strGSTNo, strLocCode, strPropCode;

	private List<clsPartyTaxIndicatorDtlModel> listclsPartyTaxIndicatorDtlModel;

	public String getStrPCode() {
		return strPCode;
	}

	public void setStrPCode(String strPCode) {
		this.strPCode = strPCode;
	}

	public String getStrPName() {
		return strPName;
	}

	public void setStrPName(String strPName) {
		this.strPName = strPName;
	}

	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	public String getStrMobile() {
		return strMobile;
	}

	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}

	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = strFax;
	}

	public String getStrContact() {
		return strContact;
	}

	public void setStrContact(String strContact) {
		this.strContact = strContact;
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}

	public String getStrPType() {
		return strPType;
	}

	public void setStrPType(String strPType) {
		this.strPType = strPType;
	}

	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = strBankName;
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

	public String getStrTaxNo1() {
		return strTaxNo1;
	}

	public void setStrTaxNo1(String strTaxNo1) {
		this.strTaxNo1 = strTaxNo1;
	}

	public String getStrTaxNo2() {
		return strTaxNo2;
	}

	public void setStrTaxNo2(String strTaxNo2) {
		this.strTaxNo2 = strTaxNo2;
	}

	public String getStrPartyType() {
		return strPartyType;
	}

	public void setStrPartyType(String strPartyType) {
		this.strPartyType = strPartyType;
	}

	public String getStrPmtTerms() {
		return strPmtTerms;
	}

	public void setStrPmtTerms(String strPmtTerms) {
		this.strPmtTerms = strPmtTerms;
	}

	public String getStrAcCrCode() {
		return strAcCrCode;
	}

	public void setStrAcCrCode(String strAcCrCode) {
		this.strAcCrCode = strAcCrCode;
	}

	public String getStrMAdd1() {
		return strMAdd1;
	}

	public void setStrMAdd1(String strMAdd1) {
		this.strMAdd1 = strMAdd1;
	}

	public String getStrMAdd2() {
		return strMAdd2;
	}

	public void setStrMAdd2(String strMAdd2) {
		this.strMAdd2 = strMAdd2;
	}

	public String getStrMCity() {
		return strMCity;
	}

	public void setStrMCity(String strMCity) {
		this.strMCity = strMCity;
	}

	public String getStrMPin() {
		return strMPin;
	}

	public void setStrMPin(String strMPin) {
		this.strMPin = strMPin;
	}

	public String getStrMState() {
		return strMState;
	}

	public void setStrMState(String strMState) {
		this.strMState = strMState;
	}

	public String getStrMCountry() {
		return strMCountry;
	}

	public void setStrMCountry(String strMCountry) {
		this.strMCountry = strMCountry;
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

	public String getStrBPin() {
		return strBPin;
	}

	public void setStrBPin(String strBPin) {
		this.strBPin = strBPin;
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

	public String getStrSPin() {
		return strSPin;
	}

	public void setStrSPin(String strSPin) {
		this.strSPin = strSPin;
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

	public String getStrExcise() {
		return strExcise;
	}

	public void setStrExcise(String strExcise) {
		this.strExcise = strExcise;
	}

	public String getStrServiceTax() {
		return strServiceTax;
	}

	public void setStrServiceTax(String strServiceTax) {
		this.strServiceTax = strServiceTax;
	}

	public String getStrSubType() {
		return strSubType;
	}

	public void setStrSubType(String strSubType) {
		this.strSubType = strSubType;
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

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getDtExpiryDate() {
		return dtExpiryDate;
	}

	public void setDtExpiryDate(String dtExpiryDate) {
		this.dtExpiryDate = dtExpiryDate;
	}

	public String getStrManualCode() {
		return strManualCode;
	}

	public void setStrManualCode(String strManualCode) {
		this.strManualCode = strManualCode;
	}

	public String getStrRegistration() {
		return strRegistration;
	}

	public void setStrRegistration(String strRegistration) {
		this.strRegistration = strRegistration;
	}

	public String getStrRange() {
		return strRange;
	}

	public void setStrRange(String strRange) {
		this.strRange = strRange;
	}

	public String getStrDivision() {
		return strDivision;
	}

	public void setStrDivision(String strDivision) {
		this.strDivision = strDivision;
	}

	public String getStrCommissionerate() {
		return strCommissionerate;
	}

	public void setStrCommissionerate(String strCommissionerate) {
		this.strCommissionerate = strCommissionerate;
	}

	public String getStrBankAccountNo() {
		return strBankAccountNo;
	}

	public void setStrBankAccountNo(String strBankAccountNo) {
		this.strBankAccountNo = strBankAccountNo;
	}

	public String getStrBankABANo() {
		return strBankABANo;
	}

	public void setStrBankABANo(String strBankABANo) {
		this.strBankABANo = strBankABANo;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public long getIntCreditDays() {
		return intCreditDays;
	}

	public void setIntCreditDays(long intCreditDays) {
		this.intCreditDays = intCreditDays;
	}

	public double getDblCreditLimit() {
		return dblCreditLimit;
	}

	public void setDblCreditLimit(double dblCreditLimit) {
		this.dblCreditLimit = dblCreditLimit;
	}

	public double getDblLatePercentage() {
		return dblLatePercentage;
	}

	public void setDblLatePercentage(double dblLatePercentage) {
		this.dblLatePercentage = dblLatePercentage;
	}

	public double getDblRejectPercentage() {
		return dblRejectPercentage;
	}

	public void setDblRejectPercentage(double dblRejectPercentage) {
		this.dblRejectPercentage = dblRejectPercentage;
	}

	public String getStrIBANNo() {
		return strIBANNo;
	}

	public void setStrIBANNo(String strIBANNo) {
		this.strIBANNo = strIBANNo;
	}

	public String getStrSwiftCode() {
		return strSwiftCode;
	}

	public void setStrSwiftCode(String strSwiftCode) {
		this.strSwiftCode = strSwiftCode;
	}

	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}

	public String getStrExcisable() {
		return strExcisable;
	}

	public void setStrExcisable(String strExcisable) {
		this.strExcisable = strExcisable;
	}

	public String getStrGSTNo() {
		return strGSTNo;
	}

	public void setStrGSTNo(String strGSTNo) {
		this.strGSTNo = strGSTNo;
	}

	public List<clsPartyTaxIndicatorDtlModel> getListclsPartyTaxIndicatorDtlModel() {
		return listclsPartyTaxIndicatorDtlModel;
	}

	public void setListclsPartyTaxIndicatorDtlModel(List<clsPartyTaxIndicatorDtlModel> listclsPartyTaxIndicatorDtlModel) {
		this.listclsPartyTaxIndicatorDtlModel = listclsPartyTaxIndicatorDtlModel;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrPropCode() {
		return strPropCode;
	}

	public void setStrPropCode(String strPropCode) {
		this.strPropCode = strPropCode;
	}

}
