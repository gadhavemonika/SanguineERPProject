package com.sanguine.model;

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

@Entity
@Table(name = "tblpartymaster")
@IdClass(clsSupplierMasterModel_ID.class)
public class clsSupplierMasterModel {

	private String strPCode, strPName, strPhone, strMobile, strFax, strContact, strEmail, strPType, strBankName, strBankAdd1, strBankAdd2;
	private String strTaxNo1, strTaxNo2, strPartyType, strPmtTerms, strAcCrCode, strMAdd1, strMAdd2, strMCity, strMPin, strMState, strMCountry;
	private String strBAdd1, strBAdd2, strBCity, strBPin, strBState, strBCountry;
	private String strSAdd1, strSAdd2, strSCity, strSPin, strSState, strSCountry;
	private String strCST, strVAT, strExcise, strServiceTax, strSubType;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String dtExpiryDate, strManualCode, strRegistration, strRange, strDivision, strCommissionerate, strBankAccountNo, strBankABANo;
	private long intPId, intCreditDays;
	private double dblCreditLimit, dblLatePercentage, dblRejectionPercentage;
	private String strIBANNo, strSwiftCode, strCategory, strExcisable, strClientCode, strPartyIndi, strDebtorCode;
	private String strOperational, strECCNo, dtInstallions, strAccManager, strLocName;
	private String strGSTNo, strLocCode, strPropCode, strExternalCode, strCurrency, strComesaRegion;

	private List<clsPartyTaxIndicatorDtlModel> arrListPartyTaxDtlModel = new ArrayList<clsPartyTaxIndicatorDtlModel>();

	public clsSupplierMasterModel() {
	}

	public clsSupplierMasterModel(clsSupplierMasterModel_ID clsSupplierMasterModel_ID) {
		strPCode = clsSupplierMasterModel_ID.getStrPCode();
		strClientCode = clsSupplierMasterModel_ID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblpartytaxdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strPCode") })
	public List<clsPartyTaxIndicatorDtlModel> getArrListPartyTaxDtlModel() {
		return arrListPartyTaxDtlModel;
	}

	public void setArrListPartyTaxDtlModel(List<clsPartyTaxIndicatorDtlModel> arrListPartyTaxDtlModel) {
		this.arrListPartyTaxDtlModel = arrListPartyTaxDtlModel;
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPCode", column = @Column(name = "strPCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strPCode")
	public String getStrPCode() {
		return strPCode;
	}

	public void setStrPCode(String strPCode) {
		this.strPCode = strPCode;
	}

	@Column(name = "strClientCode")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "strPName")
	public String getStrPName() {
		return strPName;
	}

	public void setStrPName(String strPName) {
		this.strPName = strPName;
	}

	@Column(name = "strPhone")
	public String getStrPhone() {
		return strPhone;
	}

	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	@Column(name = "strMobile")
	public String getStrMobile() {
		return strMobile;
	}

	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}

	@Column(name = "strFax")
	public String getStrFax() {
		return strFax;
	}

	public void setStrFax(String strFax) {
		this.strFax = strFax;
	}

	@Column(name = "strContact")
	public String getStrContact() {
		return strContact;
	}

	public void setStrContact(String strContact) {
		this.strContact = strContact;
	}

	@Column(name = "strEmail")
	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}

	@Column(name = "strPType")
	public String getStrPType() {
		return strPType;
	}

	public void setStrPType(String strPType) {
		this.strPType = strPType;
	}

	@Column(name = "strBankName")
	public String getStrBankName() {
		return strBankName;
	}

	public void setStrBankName(String strBankName) {
		this.strBankName = strBankName;
	}

	@Column(name = "strBankAdd1")
	public String getStrBankAdd1() {
		return strBankAdd1;
	}

	public void setStrBankAdd1(String strBankAdd1) {
		this.strBankAdd1 = strBankAdd1;
	}

	@Column(name = "strBankAdd2")
	public String getStrBankAdd2() {
		return strBankAdd2;
	}

	public void setStrBankAdd2(String strBankAdd2) {
		this.strBankAdd2 = strBankAdd2;
	}

	@Column(name = "strTaxNo1")
	public String getStrTaxNo1() {
		return strTaxNo1;
	}

	public void setStrTaxNo1(String strTaxNo1) {
		this.strTaxNo1 = strTaxNo1;
	}

	@Column(name = "strTaxNo2")
	public String getStrTaxNo2() {
		return strTaxNo2;
	}

	public void setStrTaxNo2(String strTaxNo2) {
		this.strTaxNo2 = strTaxNo2;
	}

	@Column(name = "strPartyType")
	public String getStrPartyType() {
		return strPartyType;
	}

	public void setStrPartyType(String strPartyType) {
		this.strPartyType = strPartyType;
	}

	@Column(name = "strPmtTerms")
	public String getStrPmtTerms() {
		return strPmtTerms;
	}

	public void setStrPmtTerms(String strPmtTerms) {
		this.strPmtTerms = strPmtTerms;
	}

	@Column(name = "strAcCrCode")
	public String getStrAcCrCode() {
		return strAcCrCode;
	}

	public void setStrAcCrCode(String strAcCrCode) {
		this.strAcCrCode = strAcCrCode;
	}

	@Column(name = "strMAdd1")
	public String getStrMAdd1() {
		return strMAdd1;
	}

	public void setStrMAdd1(String strMAdd1) {
		this.strMAdd1 = strMAdd1;
	}

	@Column(name = "strMAdd2")
	public String getStrMAdd2() {
		return strMAdd2;
	}

	public void setStrMAdd2(String strMAdd2) {
		this.strMAdd2 = strMAdd2;
	}

	@Column(name = "strMCity")
	public String getStrMCity() {
		return strMCity;
	}

	public void setStrMCity(String strMCity) {
		this.strMCity = strMCity;
	}

	@Column(name = "strMPin")
	public String getStrMPin() {
		return strMPin;
	}

	public void setStrMPin(String strMPin) {
		this.strMPin = strMPin;
	}

	@Column(name = "strMState")
	public String getStrMState() {
		return strMState;
	}

	public void setStrMState(String strMState) {
		this.strMState = strMState;
	}

	@Column(name = "strMCountry")
	public String getStrMCountry() {
		return strMCountry;
	}

	public void setStrMCountry(String strMCountry) {
		this.strMCountry = strMCountry;
	}

	@Column(name = "strBAdd1")
	public String getStrBAdd1() {
		return strBAdd1;
	}

	public void setStrBAdd1(String strBAdd1) {
		this.strBAdd1 = strBAdd1;
	}

	@Column(name = "strBAdd2")
	public String getStrBAdd2() {
		return strBAdd2;
	}

	public void setStrBAdd2(String strBAdd2) {
		this.strBAdd2 = strBAdd2;
	}

	@Column(name = "strBCity")
	public String getStrBCity() {
		return strBCity;
	}

	public void setStrBCity(String strBCity) {
		this.strBCity = strBCity;
	}

	@Column(name = "strBPin")
	public String getStrBPin() {
		return strBPin;
	}

	public void setStrBPin(String strBPin) {
		this.strBPin = strBPin;
	}

	@Column(name = "strBState")
	public String getStrBState() {
		return strBState;
	}

	public void setStrBState(String strBState) {
		this.strBState = strBState;
	}

	@Column(name = "strBCountry")
	public String getStrBCountry() {
		return strBCountry;
	}

	public void setStrBCountry(String strBCountry) {
		this.strBCountry = strBCountry;
	}

	@Column(name = "strSAdd1")
	public String getStrSAdd1() {
		return strSAdd1;
	}

	public void setStrSAdd1(String strSAdd1) {
		this.strSAdd1 = strSAdd1;
	}

	@Column(name = "strSAdd2")
	public String getStrSAdd2() {
		return strSAdd2;
	}

	public void setStrSAdd2(String strSAdd2) {
		this.strSAdd2 = strSAdd2;
	}

	@Column(name = "strSCity")
	public String getStrSCity() {
		return strSCity;
	}

	public void setStrSCity(String strSCity) {
		this.strSCity = strSCity;
	}

	@Column(name = "strSPin")
	public String getStrSPin() {
		return strSPin;
	}

	public void setStrSPin(String strSPin) {
		this.strSPin = strSPin;
	}

	@Column(name = "strSState")
	public String getStrSState() {
		return strSState;
	}

	public void setStrSState(String strSState) {
		this.strSState = strSState;
	}

	@Column(name = "strSCountry")
	public String getStrSCountry() {
		return strSCountry;
	}

	public void setStrSCountry(String strSCountry) {
		this.strSCountry = strSCountry;
	}

	@Column(name = "strCST")
	public String getStrCST() {
		return strCST;
	}

	public void setStrCST(String strCST) {
		this.strCST = strCST;
	}

	@Column(name = "strVAT")
	public String getStrVAT() {
		return strVAT;
	}

	public void setStrVAT(String strVAT) {
		this.strVAT = strVAT;
	}

	@Column(name = "strExcise")
	public String getStrExcise() {
		return strExcise;
	}

	public void setStrExcise(String strExcise) {
		this.strExcise = strExcise;
	}

	@Column(name = "strServiceTax")
	public String getStrServiceTax() {
		return strServiceTax;
	}

	public void setStrServiceTax(String strServiceTax) {
		this.strServiceTax = strServiceTax;
	}

	@Column(name = "strSubType")
	public String getStrSubType() {
		return strSubType;
	}

	public void setStrSubType(String strSubType) {
		this.strSubType = strSubType;
	}

	@Column(name = "strUserCreated")
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "strUserModified")
	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	@Column(name = "dtCreatedDate")
	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	@Column(name = "dtLastModified")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Column(name = "dtExpiryDate")
	public String getDtExpiryDate() {
		return dtExpiryDate;
	}

	public void setDtExpiryDate(String dtExpiryDate) {
		this.dtExpiryDate = dtExpiryDate;
	}

	@Column(name = "strManualCode")
	public String getStrManualCode() {
		return strManualCode;
	}

	public void setStrManualCode(String strManualCode) {
		this.strManualCode = strManualCode;
	}

	@Column(name = "strRegistration")
	public String getStrRegistration() {
		return strRegistration;
	}

	public void setStrRegistration(String strRegistration) {
		this.strRegistration = strRegistration;
	}

	@Column(name = "strRange")
	public String getStrRange() {
		return strRange;
	}

	public void setStrRange(String strRange) {
		this.strRange = strRange;
	}

	@Column(name = "strDivision")
	public String getStrDivision() {
		return strDivision;
	}

	public void setStrDivision(String strDivision) {
		this.strDivision = strDivision;
	}

	@Column(name = "strCommissionerate")
	public String getStrCommissionerate() {
		return strCommissionerate;
	}

	public void setStrCommissionerate(String strCommissionerate) {
		this.strCommissionerate = strCommissionerate;
	}

	@Column(name = "strBankAccountNo")
	public String getStrBankAccountNo() {
		return strBankAccountNo;
	}

	public void setStrBankAccountNo(String strBankAccountNo) {
		this.strBankAccountNo = strBankAccountNo;
	}

	@Column(name = "strBankABANo")
	public String getStrBankABANo() {
		return strBankABANo;
	}

	public void setStrBankABANo(String strBankABANo) {
		this.strBankABANo = strBankABANo;
	}

	@Column(name = "intPId", nullable = false, updatable = false)
	public long getIntPId() {
		return intPId;
	}

	public void setIntPId(long intPId) {
		this.intPId = intPId;
	}

	@Column(name = "intCreditDays")
	public long getIntCreditDays() {
		return intCreditDays;
	}

	public void setIntCreditDays(long intCreditDays) {
		this.intCreditDays = intCreditDays;
	}

	@Column(name = "dblCreditLimit")
	public double getDblCreditLimit() {
		return dblCreditLimit;
	}

	public void setDblCreditLimit(double dblCreditLimit) {
		this.dblCreditLimit = dblCreditLimit;
	}

	@Column(name = "dblLatePercentage")
	public double getDblLatePercentage() {
		return dblLatePercentage;
	}

	public void setDblLatePercentage(double dblLatePercentage) {
		this.dblLatePercentage = dblLatePercentage;
	}

	@Column(name = "dblRejectionPercentage")
	public double getDblRejectionPercentage() {
		return dblRejectionPercentage;
	}

	public void setDblRejectionPercentage(double dblRejectionPercentage) {
		this.dblRejectionPercentage = dblRejectionPercentage;
	}

	@Column(name = "strIBANNo")
	public String getStrIBANNo() {
		return strIBANNo;
	}

	public void setStrIBANNo(String strIBANNo) {
		this.strIBANNo = strIBANNo;
	}

	@Column(name = "strSwiftCode")
	public String getStrSwiftCode() {
		return strSwiftCode;
	}

	public void setStrSwiftCode(String strSwiftCode) {
		this.strSwiftCode = strSwiftCode;
	}

	@Column(name = "strCategory")
	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}

	@Column(name = "strExcisable")
	public String getStrExcisable() {
		return strExcisable;
	}

	public void setStrExcisable(String strExcisable) {
		this.strExcisable = strExcisable;
	}

	@Column(name = "strPartyIndi")
	public String getStrPartyIndi() {
		return strPartyIndi;
	}

	public void setStrPartyIndi(String strPartyIndi) {
		this.strPartyIndi = strPartyIndi;
	}

	@Column(name = "strOperational")
	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	@Column(name = "strECCNo")
	public String getStrECCNo() {
		return strECCNo;
	}

	public void setStrECCNo(String strECCNo) {
		this.strECCNo = strECCNo;
	}

	@Column(name = "dtInstallions")
	public String getDtInstallions() {
		return dtInstallions;
	}

	public void setDtInstallions(String dtInstallions) {
		this.dtInstallions = dtInstallions;
	}

	@Column(name = "strAccManager")
	public String getStrAccManager() {
		return strAccManager;
	}

	public void setStrAccManager(String strAccManager) {
		this.strAccManager = strAccManager;
	}

	@Column(name = "strGSTNo")
	public String getStrGSTNo() {
		return strGSTNo;
	}

	public void setStrGSTNo(String strGSTNo) {
		this.strGSTNo = strGSTNo;
	}

	@Column(name = "strDebtorCode")
	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = (String) setDefaultValue(strDebtorCode, "");// ;
	}

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

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	@Column(name = "strPropCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	public String getStrPropCode() {
		return strPropCode;
	}

	public void setStrPropCode(String strPropCode) {
		this.strPropCode = strPropCode;
	}

	@Transient
	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	@Column(name = "strExternalCode")
	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = strExternalCode;
	}

	@Column(name = "strCurrency", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	@Column(name = "strComesaRegion", columnDefinition = "VARCHAR(1) default 'N'")
	public String getStrComesaRegion() {
		return strComesaRegion;
	}

	public void setStrComesaRegion(String strComesaRegion) {
		this.strComesaRegion = (String) setDefaultValue(strComesaRegion, "N");
	}

}
