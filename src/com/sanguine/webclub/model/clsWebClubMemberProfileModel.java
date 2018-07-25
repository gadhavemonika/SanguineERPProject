package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblmembermaster")
@IdClass(clsWebClubMemberProfileModel_ID.class)
public class clsWebClubMemberProfileModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsWebClubMemberProfileModel() {
	}

	public clsWebClubMemberProfileModel(clsWebClubMemberProfileModel_ID clsWebClubMemberProfileModel_ID) {
		strCustomerCode = clsWebClubMemberProfileModel_ID.getStrCustomerCode();
		strClientCode = clsWebClubMemberProfileModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCustomerCode", column = @Column(name = "strCustomerCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strMemberCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strMemberCode;

	@Column(name = "strCustomerCode")
	private String strCustomerCode;

	@Column(name = "intGId", nullable = false, updatable = false)
	private long intGId;

	@Column(name = "strFirstName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strFirstName;

	@Column(name = "strMiddleName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strMiddleName;

	@Column(name = "strLastName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLastName;

	@Column(name = "strFullName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strFullName;

	@Column(name = "strPrefixCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strPrefixCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "strResidentAddressLine1", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strResidentAddressLine1;

	@Column(name = "strResidentAddressLine2", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strResidentAddressLine2;

	@Column(name = "strResidentAddressLine3", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strResidentAddressLine3;

	@Column(name = "strResidentAreaCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strResidentAreaCode;

	@Column(name = "strResidentCountryCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strResidentCountryCode;

	@Column(name = "strResidentCtCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strResidentCtCode;

	@Column(name = "strResidentEmailID", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strResidentEmailID;

	@Column(name = "strResidentFax1", columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strResidentFax1;

	@Column(name = "strResidentFax2", columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strResidentFax2;

	@Column(name = "strResidentLandMark", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strResidentLandMark;

	@Column(name = "strResidentMobileNo", columnDefinition = "VARCHAR(12) NOT NULL default ''")
	private String strResidentMobileNo;

	@Column(name = "strResidentPinCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strResidentPinCode;

	@Column(name = "strResidentRegionCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strResidentRegionCode;

	@Column(name = "strResidentStateCode", columnDefinition = "VARCHAR(10) NOT NULL default ''")
	private String strResidentStateCode;

	@Column(name = "strResidentTelephone1", columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strResidentTelephone1;

	@Column(name = "strResidentTelephone2", columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strResidentTelephone2;

	@Column(name = "strCompanyAddressLine1", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyAddressLine1;

	@Column(name = "strCompanyAddressLine2", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyAddressLine2;

	@Column(name = "strCompanyAddressLine3", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyAddressLine3;

	@Column(name = "strCompanyAreaCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyAreaCode;

	@Column(name = "strCompanyCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyCode;

	@Column(name = "strCompanyCountryCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyCountryCode;

	@Column(name = "strCompanyCtCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyCtCode;

	@Column(name = "strCompanyEmailID", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyEmailID;

	@Column(name = "strCompanyFax1", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyFax1;

	@Column(name = "strCompanyFax2", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyFax2;

	@Column(name = "strCompanyLandMark", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyLandMark;

	@Column(name = "strCompanyMobileNo", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyMobileNo;

	@Column(name = "strCompanyName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyName;

	@Column(name = "strCompanyStateCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyStateCode;

	@Column(name = "strCompanyRegionCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyRegionCode;

	@Column(name = "strCompanyPinCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyPinCode;

	@Column(name = "strCompanyTelePhone1", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyTelePhone1;

	@Column(name = "strCompanyTelePhone2", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCompanyTelePhone2;

	@Column(name = "strHoldingCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strHoldingCode;

	@Column(name = "strJobProfileCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strJobProfileCode;

	@Column(name = "strBillingAddressLine1", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strBillingAddressLine1;

	@Column(name = "strBillingAddressLine2", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strBillingAddressLine2;

	@Column(name = "strBillingAddressLine3", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strBillingAddressLine3;

	@Column(name = "strBillingAreaCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingAreaCode;

	@Column(name = "strBillingCountryCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingCountryCode;

	@Column(name = "strBillingCtCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingCtCode;

	@Column(name = "strBillingEmailID", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strBillingEmailID;

	@Column(name = "strBillingFax1", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingFax1;

	@Column(name = "strBillingFax2", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingFax2;

	@Column(name = "strBillingFlag", insertable = false, updatable = false)
	private String strBillingFlag;

	@Column(name = "strBillingLandMark", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strBillingLandMark;

	@Column(name = "strBillingMobileNo", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingMobileNo;

	@Column(name = "strBillingRegionCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingRegionCode;

	@Column(name = "strBillingPinCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingPinCode;

	@Column(name = "strBillingStateCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingStateCode;

	@Column(name = "strBillingTelePhone1", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingTelePhone1;

	@Column(name = "strBillingTelePhone2", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBillingTelePhone2;

	@Column(name = "strGender", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strGender;

	@Column(name = "strMaritalStatus", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strMaritalStatus;

	@Column(name = "strProfessionCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strProfessionCode;

	@Column(name = "dblEntranceFee", columnDefinition = "DECIMAL(18,4) NOT NULL default ''")
	private Double dblEntranceFee;

	@Column(name = "dblSubscriptionFee", columnDefinition = "DECIMAL(18,4) NOT NULL default ''")
	private Double dblSubscriptionFee;

	@Column(name = "strAttachment", insertable = false, updatable = false)
	private String strAttachment;

	@Column(name = "strBlocked", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBlocked;

	@Column(name = "strBlockedreasonCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strBlockedreasonCode;

	@Column(name = "strDesignationCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strDesignationCode;

	@Column(name = "strFatherMemberCode")
	private String strFatherMemberCode;

	@Column(name = "strCategoryCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strCategoryCode;

	@Column(name = "strGolfMemberShip", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strGolfMemberShip;

	@Column(name = "strInstation", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strInstation;

	@Column(name = "strLocker", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strLocker;

	@Column(name = "strLibrary", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strLibrary;

	@Column(name = "strPanNumber", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strPanNumber;

	@Column(name = "strProposerCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strProposerCode;

	@Column(name = "strQualification", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strQualification;

	@Column(name = "strResNonRes")
	private String strResNonRes;

	@Column(name = "strSeconderCode", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strSeconderCode;

	@Column(name = "strSeniorCitizen", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strSeniorCitizen;

	@Column(name = "strStopCredit", columnDefinition = "VARCHAR(15) NOT NULL default ''")
	private String strStopCredit;

	@Column(name = "dteCreatedDate", nullable = false, updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteProfileCreationDate")
	private String dteProfileCreationDate;

	@Column(name = "strSalesStaffCode")
	private String strSalesStaffCode;

	@Column(name = "strDependentYesNo")
	private String strDependentYesNo;

	@Column(name = "strRemark")
	private String strRemark;

	@Column(name = "strPhoto")
	private String strPhoto;

	@Column(name = "strNameOnCard")
	private String strNameOnCard;

	@Column(name = "dteDateofBirth", columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String dteDateofBirth;

	@Column(name = "dteModifiedDate", nullable = false, updatable = false)
	private String dteModifiedDate;

	@Column(name = "strDepedentRelation")
	private String strDepedentRelation;

	@Column(name = "dteMembershipExpiryDate")
	private String dteMembershipExpiryDate;

	@Column(name = "dblCMSBalance")
	private double dblCMSBalance;

	@Column(name = "dteMemberBlockDate")
	private String dteMemberBlockDate;

	@Column(name = "strDependentReasonCode")
	private String strDependentReasonCode;

	@Column(name = "dteInterviewDate")
	private String dteInterviewDate;

	@Column(name = "dteDependentDateofBirth")
	private String dteDependentDateofBirth;

	@Column(name = "strDependentMemberCode")
	private String strDependentMemberCode;

	@Column(name = "strCustomerID", nullable = false, updatable = false)
	private String strCustomerID;

	@Column(name = "strDependentFullName")
	private String strDependentFullName;

	@Column(name = "dteMembershipStartDate")
	private String dteMembershipStartDate;

	@Column(name = "dteMembershipEndDate")
	private String dteMembershipEndDate;

	@Column(name = "strSendCircularNoticeThrough")
	private String strSendCircularNoticeThrough;

	@Column(name = "strSendInvThrough")
	private String strSendInvThrough;

	@Column(name = "strDisLikes")
	private String strDisLikes;

	@Column(name = "strLikes")
	private String strLikes;

	@Column(name = "strMemberStatusCode")
	private String strMemberStatusCode;

	@Column(name = "strAuthorisedMember")
	private String strAuthorisedMember;

	@Column(name = "chrCircularemail")
	private String chrCircularemail;

	@Column(name = "strNSuffixCode")
	private String strNSuffixCode;

	@Column(name = "strSSuffixCode")
	private String strSSuffixCode;

	@Column(name = "chkmail")
	private long chkmail;

	@Column(name = "strVirtualAccountCode")
	private String strVirtualAccountCode;

	@Column(name = "strAlternateMemberCode")
	private String strAlternateMemberCode;

	@Column(name = "strGuestEntry")
	private String strGuestEntry;

	@Column(name = "intFormNo")
	private long intFormNo;

	@Column(name = "strLiquorPermitNo")
	private String strLiquorPermitNo;

	@Column(name = "dtePermitExpDate")
	private String dtePermitExpDate;

	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	@Column(name = "strPrimaryCustomerCode")
	private String strPrimaryCode;

	@Column(name = "dteAnniversaryDate")
	private String dteAnniversary;

	@Column(name = "strMemberYesNo")
	private String strMemberYesNo;

	// Setter-Getter Methods

	public String getStrMemberYesNo() {
		return strMemberYesNo;
	}

	public void setStrMemberYesNo(String strMemberYesNo) {
		this.strMemberYesNo = strMemberYesNo;
	}

	public String getStrLiquorPermitNo() {
		return strLiquorPermitNo;
	}

	public String getDteProfileCreationDate() {
		return dteProfileCreationDate;
	}

	public void setDteProfileCreationDate(String dteProfileCreationDate) {
		this.dteProfileCreationDate = dteProfileCreationDate;
	}

	public String getDteMembershipExpiryDate() {
		return dteMembershipExpiryDate;
	}

	public void setDteMembershipExpiryDate(String dteMembershipExpiryDate) {
		this.dteMembershipExpiryDate = dteMembershipExpiryDate;
	}

	public String getDteMemberBlockDate() {
		return dteMemberBlockDate;
	}

	public void setDteMemberBlockDate(String dteMemberBlockDate) {
		this.dteMemberBlockDate = dteMemberBlockDate;
	}

	public String getStrDependentReasonCode() {
		return strDependentReasonCode;
	}

	public void setStrDependentReasonCode(String strDependentReasonCode) {
		this.strDependentReasonCode = strDependentReasonCode;
	}

	public String getDteInterviewDate() {
		return dteInterviewDate;
	}

	public void setDteInterviewDate(String dteInterviewDate) {
		this.dteInterviewDate = dteInterviewDate;
	}

	public String getDteDependentDateofBirth() {
		return dteDependentDateofBirth;
	}

	public void setDteDependentDateofBirth(String dteDependentDateofBirth) {
		this.dteDependentDateofBirth = dteDependentDateofBirth;
	}

	public String getStrDependentFullName() {
		return strDependentFullName;
	}

	public void setStrDependentFullName(String strDependentFullName) {
		this.strDependentFullName = strDependentFullName;
	}

	public String getStrSendCircularNoticeThrough() {
		return strSendCircularNoticeThrough;
	}

	public void setStrSendCircularNoticeThrough(String strSendCircularNoticeThrough) {
		this.strSendCircularNoticeThrough = strSendCircularNoticeThrough;
	}

	public String getStrSendInvThrough() {
		return strSendInvThrough;
	}

	public void setStrSendInvThrough(String strSendInvThrough) {
		this.strSendInvThrough = strSendInvThrough;
	}

	public String getStrDisLikes() {
		return strDisLikes;
	}

	public void setStrDisLikes(String strDisLikes) {
		this.strDisLikes = strDisLikes;
	}

	public String getStrLikes() {
		return strLikes;
	}

	public void setStrLikes(String strLikes) {
		this.strLikes = strLikes;
	}

	public String getStrMemberStatusCode() {
		return strMemberStatusCode;
	}

	public void setStrMemberStatusCode(String strMemberStatusCode) {
		this.strMemberStatusCode = strMemberStatusCode;
	}

	public String getStrAuthorisedMember() {
		return strAuthorisedMember;
	}

	public void setStrAuthorisedMember(String strAuthorisedMember) {
		this.strAuthorisedMember = strAuthorisedMember;
	}

	public String getChrCircularemail() {
		return chrCircularemail;
	}

	public void setChrCircularemail(String chrCircularemail) {
		this.chrCircularemail = chrCircularemail;
	}

	public String getStrNSuffixCode() {
		return strNSuffixCode;
	}

	public void setStrNSuffixCode(String strNSuffixCode) {
		this.strNSuffixCode = strNSuffixCode;
	}

	public String getStrSSuffixCode() {
		return strSSuffixCode;
	}

	public void setStrSSuffixCode(String strSSuffixCode) {
		this.strSSuffixCode = strSSuffixCode;
	}

	public long getChkmail() {
		return chkmail;
	}

	public void setChkmail(long chkmail) {
		this.chkmail = chkmail;
	}

	public String getStrVirtualAccountCode() {
		return strVirtualAccountCode;
	}

	public void setStrVirtualAccountCode(String strVirtualAccountCode) {
		this.strVirtualAccountCode = strVirtualAccountCode;
	}

	public String getStrAlternateMemberCode() {
		return strAlternateMemberCode;
	}

	public void setStrAlternateMemberCode(String strAlternateMemberCode) {
		this.strAlternateMemberCode = strAlternateMemberCode;
	}

	public long getIntFormNo() {
		return intFormNo;
	}

	public void setIntFormNo(long intFormNo) {
		this.intFormNo = intFormNo;
	}

	public String getDtePermitExpDate() {
		return dtePermitExpDate;
	}

	public void setDtePermitExpDate(String dtePermitExpDate) {
		this.dtePermitExpDate = dtePermitExpDate;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public void setStrLiquorPermitNo(String strLiquorPermitNo) {
		this.strLiquorPermitNo = strLiquorPermitNo;
	}

	public String getStrResNonRes() {
		return strResNonRes;
	}

	public void setStrResNonRes(String strResNonRes) {
		this.strResNonRes = strResNonRes;
	}

	public String getStrSalesStaffCode() {
		return strSalesStaffCode;
	}

	public void setStrSalesStaffCode(String strSalesStaffCode) {
		this.strSalesStaffCode = strSalesStaffCode;
	}

	public String getStrDependentYesNo() {
		return strDependentYesNo;
	}

	public void setStrDependentYesNo(String strDependentYesNo) {
		this.strDependentYesNo = strDependentYesNo;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = strRemark;
	}

	public String getStrPhoto() {
		return strPhoto;
	}

	public void setStrPhoto(String strPhoto) {
		this.strPhoto = strPhoto;
	}

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = strDebtorCode;
	}

	public String getStrGuestEntry() {
		return strGuestEntry;
	}

	public void setStrGuestEntry(String strGuestEntry) {
		this.strGuestEntry = strGuestEntry;
	}

	public String getStrDependentMemberCode() {
		return strDependentMemberCode;
	}

	public void setStrDependentMemberCode(String strDependentMemberCode) {
		this.strDependentMemberCode = strDependentMemberCode;
	}

	public double getDblCMSBalance() {
		return dblCMSBalance;
	}

	public void setDblCMSBalance(double dblCMSBalance) {
		this.dblCMSBalance = dblCMSBalance;
	}

	public String getStrCustomerCode() {
		return strCustomerCode;
	}

	public void setStrCustomerCode(String strCustomerCode) {
		this.strCustomerCode = strCustomerCode;
	}

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = strMemberCode;
	}

	public String getStrPrefixCode() {
		return strPrefixCode;
	}

	public void setStrPrefixCode(String strPrefixCode) {
		this.strPrefixCode = strPrefixCode;
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

	public String getStrNameOnCard() {
		return strNameOnCard;
	}

	public void setStrNameOnCard(String strNameOnCard) {
		this.strNameOnCard = strNameOnCard;
	}

	public String getStrFullName() {
		return strFullName;
	}

	public void setStrFullName(String strFullName) {
		this.strFullName = strFullName;
	}

	public String getStrResidentAddressLine1() {
		return strResidentAddressLine1;
	}

	public void setStrResidentAddressLine1(String strResidentAddressLine1) {
		this.strResidentAddressLine1 = strResidentAddressLine1;
	}

	public String getStrResidentAddressLine2() {
		return strResidentAddressLine2;
	}

	public void setStrResidentAddressLine2(String strResidentAddressLine2) {
		this.strResidentAddressLine2 = strResidentAddressLine2;
	}

	public String getStrResidentAddressLine3() {
		return strResidentAddressLine3;
	}

	public void setStrResidentAddressLine3(String strResidentAddressLine3) {
		this.strResidentAddressLine3 = strResidentAddressLine3;
	}

	public String getStrResidentLandMark() {
		return strResidentLandMark;
	}

	public void setStrResidentLandMark(String strResidentLandMark) {
		this.strResidentLandMark = strResidentLandMark;
	}

	public String getStrResidentAreaCode() {
		return strResidentAreaCode;
	}

	public void setStrResidentAreaCode(String strResidentAreaCode) {
		this.strResidentAreaCode = strResidentAreaCode;
	}

	public String getStrResidentCtCode() {
		return strResidentCtCode;
	}

	public void setStrResidentCtCode(String strResidentCtCode) {
		this.strResidentCtCode = strResidentCtCode;
	}

	public String getStrResidentStateCode() {
		return strResidentStateCode;
	}

	public void setStrResidentStateCode(String strResidentStateCode) {
		this.strResidentStateCode = strResidentStateCode;
	}

	public String getStrResidentRegionCode() {
		return strResidentRegionCode;
	}

	public void setStrResidentRegionCode(String strResidentRegionCode) {
		this.strResidentRegionCode = strResidentRegionCode;
	}

	public String getStrResidentCountryCode() {
		return strResidentCountryCode;
	}

	public void setStrResidentCountryCode(String strResidentCountryCode) {
		this.strResidentCountryCode = strResidentCountryCode;
	}

	public String getStrResidentPinCode() {
		return strResidentPinCode;
	}

	public void setStrResidentPinCode(String strResidentPinCode) {
		this.strResidentPinCode = strResidentPinCode;
	}

	public String getStrResidentTelephone1() {
		return strResidentTelephone1;
	}

	public void setStrResidentTelephone1(String strResidentTelephone1) {
		this.strResidentTelephone1 = strResidentTelephone1;
	}

	public String getStrResidentTelephone2() {
		return strResidentTelephone2;
	}

	public void setStrResidentTelephone2(String strResidentTelephone2) {
		this.strResidentTelephone2 = strResidentTelephone2;
	}

	public String getStrResidentFax1() {
		return strResidentFax1;
	}

	public void setStrResidentFax1(String strResidentFax1) {
		this.strResidentFax1 = strResidentFax1;
	}

	public String getStrResidentFax2() {
		return strResidentFax2;
	}

	public void setStrResidentFax2(String strResidentFax2) {
		this.strResidentFax2 = strResidentFax2;
	}

	public String getStrResidentMobileNo() {
		return strResidentMobileNo;
	}

	public void setStrResidentMobileNo(String strResidentMobileNo) {
		this.strResidentMobileNo = strResidentMobileNo;
	}

	public String getStrResidentEmailID() {
		return strResidentEmailID;
	}

	public void setStrResidentEmailID(String strResidentEmailID) {
		this.strResidentEmailID = strResidentEmailID;
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

	public String getStrHoldingCode() {
		return strHoldingCode;
	}

	public void setStrHoldingCode(String strHoldingCode) {
		this.strHoldingCode = strHoldingCode;
	}

	public String getStrJobProfileCode() {
		return strJobProfileCode;
	}

	public void setStrJobProfileCode(String strJobProfileCode) {
		this.strJobProfileCode = strJobProfileCode;
	}

	public String getStrCompanyAddressLine1() {
		return strCompanyAddressLine1;
	}

	public void setStrCompanyAddressLine1(String strCompanyAddressLine1) {
		this.strCompanyAddressLine1 = strCompanyAddressLine1;
	}

	public String getStrCompanyAddressLine2() {
		return strCompanyAddressLine2;
	}

	public void setStrCompanyAddressLine2(String strCompanyAddressLine2) {
		this.strCompanyAddressLine2 = strCompanyAddressLine2;
	}

	public String getStrCompanyAddressLine3() {
		return strCompanyAddressLine3;
	}

	public void setStrCompanyAddressLine3(String strCompanyAddressLine3) {
		this.strCompanyAddressLine3 = strCompanyAddressLine3;
	}

	public String getStrCompanyLandMark() {
		return strCompanyLandMark;
	}

	public void setStrCompanyLandMark(String strCompanyLandMark) {
		this.strCompanyLandMark = strCompanyLandMark;
	}

	public String getStrCompanyAreaCode() {
		return strCompanyAreaCode;
	}

	public void setStrCompanyAreaCode(String strCompanyAreaCode) {
		this.strCompanyAreaCode = strCompanyAreaCode;
	}

	public String getStrCompanyCtCode() {
		return strCompanyCtCode;
	}

	public void setStrCompanyCtCode(String strCompanyCtCode) {
		this.strCompanyCtCode = strCompanyCtCode;
	}

	public String getStrCompanyStateCode() {
		return strCompanyStateCode;
	}

	public void setStrCompanyStateCode(String strCompanyStateCode) {
		this.strCompanyStateCode = strCompanyStateCode;
	}

	public String getStrCompanyRegionCode() {
		return strCompanyRegionCode;
	}

	public void setStrCompanyRegionCode(String strCompanyRegionCode) {
		this.strCompanyRegionCode = strCompanyRegionCode;
	}

	public String getStrCompanyCountryCode() {
		return strCompanyCountryCode;
	}

	public void setStrCompanyCountryCode(String strCompanyCountryCode) {
		this.strCompanyCountryCode = strCompanyCountryCode;
	}

	public String getStrCompanyPinCode() {
		return strCompanyPinCode;
	}

	public void setStrCompanyPinCode(String strCompanyPinCode) {
		this.strCompanyPinCode = strCompanyPinCode;
	}

	public String getStrCompanyTelePhone1() {
		return strCompanyTelePhone1;
	}

	public void setStrCompanyTelePhone1(String strCompanyTelePhone1) {
		this.strCompanyTelePhone1 = strCompanyTelePhone1;
	}

	public String getStrCompanyTelePhone2() {
		return strCompanyTelePhone2;
	}

	public void setStrCompanyTelePhone2(String strCompanyTelePhone2) {
		this.strCompanyTelePhone2 = strCompanyTelePhone2;
	}

	public String getStrCompanyFax1() {
		return strCompanyFax1;
	}

	public void setStrCompanyFax1(String strCompanyFax1) {
		this.strCompanyFax1 = strCompanyFax1;
	}

	public String getStrCompanyFax2() {
		return strCompanyFax2;
	}

	public void setStrCompanyFax2(String strCompanyFax2) {
		this.strCompanyFax2 = strCompanyFax2;
	}

	public String getStrCompanyMobileNo() {
		return strCompanyMobileNo;
	}

	public void setStrCompanyMobileNo(String strCompanyMobileNo) {
		this.strCompanyMobileNo = strCompanyMobileNo;
	}

	public String getStrCompanyEmailID() {
		return strCompanyEmailID;
	}

	public void setStrCompanyEmailID(String strCompanyEmailID) {
		this.strCompanyEmailID = strCompanyEmailID;
	}

	public String getStrBillingAddressLine1() {
		return strBillingAddressLine1;
	}

	public void setStrBillingAddressLine1(String strBillingAddressLine1) {
		this.strBillingAddressLine1 = strBillingAddressLine1;
	}

	public String getStrBillingAddressLine2() {
		return strBillingAddressLine2;
	}

	public void setStrBillingAddressLine2(String strBillingAddressLine2) {
		this.strBillingAddressLine2 = strBillingAddressLine2;
	}

	public String getStrBillingAddressLine3() {
		return strBillingAddressLine3;
	}

	public void setStrBillingAddressLine3(String strBillingAddressLine3) {
		this.strBillingAddressLine3 = strBillingAddressLine3;
	}

	public String getStrBillingLandMark() {
		return strBillingLandMark;
	}

	public void setStrBillingLandMark(String strBillingLandMark) {
		this.strBillingLandMark = strBillingLandMark;
	}

	public String getStrBillingAreaCode() {
		return strBillingAreaCode;
	}

	public void setStrBillingAreaCode(String strBillingAreaCode) {
		this.strBillingAreaCode = strBillingAreaCode;
	}

	public String getStrBillingCtCode() {
		return strBillingCtCode;
	}

	public void setStrBillingCtCode(String strBillingCtCode) {
		this.strBillingCtCode = strBillingCtCode;
	}

	public String getStrBillingStateCode() {
		return strBillingStateCode;
	}

	public void setStrBillingStateCode(String strBillingStateCode) {
		this.strBillingStateCode = strBillingStateCode;
	}

	public String getStrBillingRegionCode() {
		return strBillingRegionCode;
	}

	public void setStrBillingRegionCode(String strBillingRegionCode) {
		this.strBillingRegionCode = strBillingRegionCode;
	}

	public String getStrBillingCountryCode() {
		return strBillingCountryCode;
	}

	public void setStrBillingCountryCode(String strBillingCountryCode) {
		this.strBillingCountryCode = strBillingCountryCode;
	}

	public String getStrBillingPinCode() {
		return strBillingPinCode;
	}

	public void setStrBillingPinCode(String strBillingPinCode) {
		this.strBillingPinCode = strBillingPinCode;
	}

	public String getStrBillingTelePhone1() {
		return strBillingTelePhone1;
	}

	public void setStrBillingTelePhone1(String strBillingTelePhone1) {
		this.strBillingTelePhone1 = strBillingTelePhone1;
	}

	public String getStrBillingTelePhone2() {
		return strBillingTelePhone2;
	}

	public void setStrBillingTelePhone2(String strBillingTelePhone2) {
		this.strBillingTelePhone2 = strBillingTelePhone2;
	}

	public String getStrBillingFax1() {
		return strBillingFax1;
	}

	public void setStrBillingFax1(String strBillingFax1) {
		this.strBillingFax1 = strBillingFax1;
	}

	public String getStrBillingFax2() {
		return strBillingFax2;
	}

	public void setStrBillingFax2(String strBillingFax2) {
		this.strBillingFax2 = strBillingFax2;
	}

	public String getStrBillingMobileNo() {
		return strBillingMobileNo;
	}

	public void setStrBillingMobileNo(String strBillingMobileNo) {
		this.strBillingMobileNo = strBillingMobileNo;
	}

	public String getStrBillingEmailID() {
		return strBillingEmailID;
	}

	public void setStrBillingEmailID(String strBillingEmailID) {
		this.strBillingEmailID = strBillingEmailID;
	}

	public String getStrBillingFlag() {
		return strBillingFlag;
	}

	public void setStrBillingFlag(String strBillingFlag) {
		this.strBillingFlag = strBillingFlag;
	}

	public String getStrGender() {
		return strGender;
	}

	public void setStrGender(String strGender) {
		this.strGender = strGender;
	}

	public String getDteDateofBirth() {
		return dteDateofBirth;
	}

	public void setDteDateofBirth(String dteDateofBirth) {
		this.dteDateofBirth = dteDateofBirth;
	}

	public String getStrMaritalStatus() {
		return strMaritalStatus;
	}

	public void setStrMaritalStatus(String strMaritalStatus) {
		this.strMaritalStatus = strMaritalStatus;
	}

	public String getStrProfessionCode() {
		return strProfessionCode;
	}

	public void setStrProfessionCode(String strProfessionCode) {
		this.strProfessionCode = strProfessionCode;
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = strCategoryCode;
	}

	public String getStrPanNumber() {
		return strPanNumber;
	}

	public void setStrPanNumber(String strPanNumber) {
		this.strPanNumber = strPanNumber;
	}

	public String getStrProposerCode() {
		return strProposerCode;
	}

	public void setStrProposerCode(String strProposerCode) {
		this.strProposerCode = strProposerCode;
	}

	public String getStrSeconderCode() {
		return strSeconderCode;
	}

	public void setStrSeconderCode(String strSeconderCode) {
		this.strSeconderCode = strSeconderCode;
	}

	public String getStrBlocked() {
		return strBlocked;
	}

	public void setStrBlocked(String strBlocked) {
		this.strBlocked = strBlocked;
	}

	public String getStrBlockedreasonCode() {
		return strBlockedreasonCode;
	}

	public void setStrBlockedreasonCode(String strBlockedreasonCode) {
		this.strBlockedreasonCode = strBlockedreasonCode;
	}

	public String getStrQualification() {
		return strQualification;
	}

	public void setStrQualification(String strQualification) {
		this.strQualification = strQualification;
	}

	public String getStrDesignationCode() {
		return strDesignationCode;
	}

	public void setStrDesignationCode(String strDesignationCode) {
		this.strDesignationCode = strDesignationCode;
	}

	public String getStrLocker() {
		return strLocker;
	}

	public void setStrLocker(String strLocker) {
		this.strLocker = strLocker;
	}

	public String getStrLibrary() {
		return strLibrary;
	}

	public void setStrLibrary(String strLibrary) {
		this.strLibrary = strLibrary;
	}

	public String getStrInstation() {
		return strInstation;
	}

	public void setStrInstation(String strInstation) {
		this.strInstation = strInstation;
	}

	public String getStrSeniorCitizen() {
		return strSeniorCitizen;
	}

	public void setStrSeniorCitizen(String strSeniorCitizen) {
		this.strSeniorCitizen = strSeniorCitizen;
	}

	public Double getDblEntranceFee() {
		return dblEntranceFee;
	}

	public void setDblEntranceFee(Double dblEntranceFee) {
		this.dblEntranceFee = dblEntranceFee;
	}

	public Double getDblSubscriptionFee() {
		return dblSubscriptionFee;
	}

	public void setDblSubscriptionFee(Double dblSubscriptionFee) {
		this.dblSubscriptionFee = dblSubscriptionFee;
	}

	public String getStrGolfMemberShip() {
		return strGolfMemberShip;
	}

	public void setStrGolfMemberShip(String strGolfMemberShip) {
		this.strGolfMemberShip = strGolfMemberShip;
	}

	public String getStrStopCredit() {
		return strStopCredit;
	}

	public void setStrStopCredit(String strStopCredit) {
		this.strStopCredit = strStopCredit;
	}

	public String getStrAttachment() {
		return strAttachment;
	}

	public void setStrAttachment(String strAttachment) {
		this.strAttachment = strAttachment;
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

	public String getStrFatherMemberCode() {
		return strFatherMemberCode;
	}

	public void setStrFatherMemberCode(String strFatherMemberCode) {
		this.strFatherMemberCode = strFatherMemberCode;
	}

	public Long getIntGId() {
		return intGId;
	}

	public void setIntGId(Long intGId) {
		this.intGId = intGId;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getDteModifiedDate() {
		return dteModifiedDate;
	}

	public void setDteModifiedDate(String dteModifiedDate) {
		this.dteModifiedDate = dteModifiedDate;
	}

	public String getStrCustomerID() {
		return strCustomerID;
	}

	public void setStrCustomerID(String strCustomerID) {
		this.strCustomerID = strCustomerID;
	}

	public String getDteMembershipStartDate() {
		return dteMembershipStartDate;
	}

	public void setDteMembershipStartDate(String dteMembershipStartDate) {
		this.dteMembershipStartDate = dteMembershipStartDate;
	}

	public String getDteMembershipEndDate() {
		return dteMembershipEndDate;
	}

	public void setDteMembershipEndDate(String dteMembershipEndDate) {
		this.dteMembershipEndDate = dteMembershipEndDate;
	}

	public String getStrDepedentRelation() {
		return strDepedentRelation;
	}

	public void setStrDepedentRelation(String strDepedentRelation) {
		this.strDepedentRelation = strDepedentRelation;
	}

	public String getStrPrimaryCode() {
		return strPrimaryCode;
	}

	public void setStrPrimaryCode(String strPrimaryCode) {
		this.strPrimaryCode = strPrimaryCode;
	}

	public String getDteAnniversary() {
		return dteAnniversary;
	}

	public void setDteAnniversary(String dteAnniversary) {
		this.dteAnniversary = dteAnniversary;
	}

}
