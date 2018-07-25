package com.sanguine.webpms.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsWalkinRoomRateDtlModel;

public class clsWalkinBean {

	// Variable Declaration
	private String strWalkinNo;

	private String dteWalkinDate;

	private String dteCheckOutDate;

	private String tmeWalkinTime;

	private String tmeCheckOutTime;

	private String strCorporateCode;

	private String strBookerCode;

	private String strAgentCode;

	private long intNoOfNights;

	private String strRemarks;

	private String strClientCode;

	private String strGuestPrefix;

	private String strFirstName;

	private String strMiddleName;

	private String strLastName;

	private String strGender;

	private String dteDOB;

	private String strDesignation;

	private String strAddress;

	private String strCity;

	private String strState;

	private String strCountry;

	private String strNationality;

	private long intPinCode;

	private String strEmailId;

	private String strPANNo;

	private String strArrivalFrom;

	private String strProceedingTo;

	private String strStatus;

	private String strVisitingType;

	private String strPassportNo;

	private String dtePassportIssueDate;

	private String dtePassportExpiryDate;

	private String strGuestCorporate;

	private long intMobileNo;

	private long intFaxNo;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strRoomNo;

	private String strRoomDesc;

	private int intNoOfAdults;

	private int intNoOfChild;

	private String strExtraBedCode;

	private String strExtraBedDesc;
	
	private String strPackageName;
	
	private String strPackageCode;

	private String strTotalPackageAmt;
	
	private List<clsWalkinDtlBean> listWalkinDetailsBean;
	
	private List<clsWalkinRoomRateDtlModel> listWalkinRoomRateDtl = new ArrayList<clsWalkinRoomRateDtlModel>();
	
	private List<clsRoomPackageDtl> listRoomPackageDtl = new ArrayList<clsRoomPackageDtl>();
	
	private String strIncomeHeadCode;

	// Setter-Getter Methods
	public String getStrWalkinNo() {
		return strWalkinNo;
	}

	public void setStrWalkinNo(String strWalkinNo) {
		this.strWalkinNo = strWalkinNo;
	}

	public String getDteWalkinDate() {
		return dteWalkinDate;
	}

	public void setDteWalkinDate(String dteWalkinDate) {
		this.dteWalkinDate = dteWalkinDate;
	}

	public String getDteCheckOutDate() {
		return dteCheckOutDate;
	}

	public void setDteCheckOutDate(String dteCheckOutDate) {
		this.dteCheckOutDate = dteCheckOutDate;
	}

	public String getStrCorporateCode() {
		return strCorporateCode;
	}

	public void setStrCorporateCode(String strCorporateCode) {
		this.strCorporateCode = strCorporateCode;
	}

	public String getStrBookerCode() {
		return strBookerCode;
	}

	public void setStrBookerCode(String strBookerCode) {
		this.strBookerCode = strBookerCode;
	}

	public String getStrAgentCode() {
		return strAgentCode;
	}

	public void setStrAgentCode(String strAgentCode) {
		this.strAgentCode = strAgentCode;
	}

	public long getIntNoOfNights() {
		return intNoOfNights;
	}

	public void setIntNoOfNights(long intNoOfNights) {
		this.intNoOfNights = intNoOfNights;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsWalkinDtlBean> getListWalkinDetailsBean() {
		return listWalkinDetailsBean;
	}

	public void setListWalkinDetailsBean(List<clsWalkinDtlBean> listWalkinDetailsBean) {
		this.listWalkinDetailsBean = listWalkinDetailsBean;
	}

	public String getTmeWalkinTime() {
		return tmeWalkinTime;
	}

	public void setTmeWalkinTime(String tmeWalkinTime) {
		this.tmeWalkinTime = tmeWalkinTime;
	}

	public String getTmeCheckOutTime() {
		return tmeCheckOutTime;
	}

	public void setTmeCheckOutTime(String tmeCheckOutTime) {
		this.tmeCheckOutTime = tmeCheckOutTime;
	}

	public String getStrGuestPrefix() {
		return strGuestPrefix;
	}

	public void setStrGuestPrefix(String strGuestPrefix) {
		this.strGuestPrefix = strGuestPrefix;
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

	public String getStrGender() {
		return strGender;
	}

	public void setStrGender(String strGender) {
		this.strGender = strGender;
	}

	public String getDteDOB() {
		return dteDOB;
	}

	public void setDteDOB(String dteDOB) {
		this.dteDOB = dteDOB;
	}

	public String getStrDesignation() {
		return strDesignation;
	}

	public void setStrDesignation(String strDesignation) {
		this.strDesignation = strDesignation;
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = strAddress;
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

	public String getStrNationality() {
		return strNationality;
	}

	public void setStrNationality(String strNationality) {
		this.strNationality = strNationality;
	}

	public long getIntPinCode() {
		return intPinCode;
	}

	public void setIntPinCode(long intPinCode) {
		this.intPinCode = intPinCode;
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = strEmailId;
	}

	public String getStrPANNo() {
		return strPANNo;
	}

	public void setStrPANNo(String strPANNo) {
		this.strPANNo = strPANNo;
	}

	public String getStrArrivalFrom() {
		return strArrivalFrom;
	}

	public void setStrArrivalFrom(String strArrivalFrom) {
		this.strArrivalFrom = strArrivalFrom;
	}

	public String getStrProceedingTo() {
		return strProceedingTo;
	}

	public void setStrProceedingTo(String strProceedingTo) {
		this.strProceedingTo = strProceedingTo;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrVisitingType() {
		return strVisitingType;
	}

	public void setStrVisitingType(String strVisitingType) {
		this.strVisitingType = strVisitingType;
	}

	public String getStrPassportNo() {
		return strPassportNo;
	}

	public void setStrPassportNo(String strPassportNo) {
		this.strPassportNo = strPassportNo;
	}

	public String getDtePassportIssueDate() {
		return dtePassportIssueDate;
	}

	public void setDtePassportIssueDate(String dtePassportIssueDate) {
		this.dtePassportIssueDate = dtePassportIssueDate;
	}

	public String getDtePassportExpiryDate() {
		return dtePassportExpiryDate;
	}

	public void setDtePassportExpiryDate(String dtePassportExpiryDate) {
		this.dtePassportExpiryDate = dtePassportExpiryDate;
	}

	public String getStrGuestCorporate() {
		return strGuestCorporate;
	}

	public void setStrGuestCorporate(String strGuestCorporate) {
		this.strGuestCorporate = strGuestCorporate;
	}

	public long getIntMobileNo() {
		return intMobileNo;
	}

	public void setIntMobileNo(long intMobileNo) {
		this.intMobileNo = intMobileNo;
	}

	public long getIntFaxNo() {
		return intFaxNo;
	}

	public void setIntFaxNo(long intFaxNo) {
		this.intFaxNo = intFaxNo;
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

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public String getStrRoomDesc() {
		return strRoomDesc;
	}

	public void setStrRoomDesc(String strRoomDesc) {
		this.strRoomDesc = strRoomDesc;
	}

	public int getIntNoOfAdults() {
		return intNoOfAdults;
	}

	public void setIntNoOfAdults(int intNoOfAdults) {
		this.intNoOfAdults = intNoOfAdults;
	}

	public int getIntNoOfChild() {
		return intNoOfChild;
	}

	public void setIntNoOfChild(int intNoOfChild) {
		this.intNoOfChild = intNoOfChild;
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public String getStrExtraBedDesc() {
		return strExtraBedDesc;
	}

	public void setStrExtraBedDesc(String strExtraBedDesc) {
		this.strExtraBedDesc = strExtraBedDesc;
	}

	public String getStrIncomeHeadCode() {
		return strIncomeHeadCode;
	}

	
	
	public String getStrPackageName() {
		return strPackageName;
	}

	public void setStrPackageName(String strPackageName) {
		this.strPackageName = strPackageName;
	}

	public String getStrPackageCode() {
		return strPackageCode;
	}

	public void setStrPackageCode(String strPackageCode) {
		this.strPackageCode = strPackageCode;
	}

	public void setStrIncomeHeadCode(String strIncomeHeadCode) {
		this.strIncomeHeadCode = strIncomeHeadCode;
	}

	public List<clsWalkinRoomRateDtlModel> getListWalkinRoomRateDtl() {
		return listWalkinRoomRateDtl;
	}

	public void setListWalkinRoomRateDtl(List<clsWalkinRoomRateDtlModel> listWalkinRoomRateDtl) {
		this.listWalkinRoomRateDtl = listWalkinRoomRateDtl;
	}

	public List<clsRoomPackageDtl> getListRoomPackageDtl() {
		return listRoomPackageDtl;
	}

	public void setListRoomPackageDtl(List<clsRoomPackageDtl> listRoomPackageDtl) {
		this.listRoomPackageDtl = listRoomPackageDtl;
	}

	public String getStrTotalPackageAmt() {
		return strTotalPackageAmt;
	}

	public void setStrTotalPackageAmt(String strTotalPackageAmt) {
		this.strTotalPackageAmt = strTotalPackageAmt;
	}
	
	
	

}
