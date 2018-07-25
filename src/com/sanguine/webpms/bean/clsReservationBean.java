package com.sanguine.webpms.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;


import com.sanguine.webpms.model.clsReservationRoomRateModelDtl;
import com.sanguine.webpms.model.clsRoomPackageDtl;

public class clsReservationBean {
	// Variable Declaration
	private String strReservationNo;

	private String strPropertyCode;

	private String strPropertyName;

	private String strGuestCode;

	private String strGuestPrefix;

	private String strFirstName;

	private String strMiddleName;

	private String strLastName;

	private String strCorporateCode;

	private String strBookingTypeCode;

	private String strBillingInstCode;

	private String strBookerCode;

	private String strBusinessSourceCode;

	private String strAgentCode;

	private String dteArrivalDate;

	private String dteDepartureDate;

	private int intNoOfNights;

	private String strContactPerson;

	private String strEmailId;

	private String strRemarks;

	private String dteCancelDate;

	private String dteConfirmDate;

	private String strCancelReservation;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String tmeArrivalTime;

	private String tmeDepartureTime;

	private String strRoomNo;

	private String strRoomDesc;

	private int intNoOfAdults;

	private int intNoOfChild;

	private String strExtraBedCode;

	private String strExtraBedDesc;

	private int intNoRoomsBooked;

	private String strPayeeGuestCode;

	private List<clsReservationDetailsBean> listReservationDetailsBean;
	
	private String strOTANo;
	
	private String strMarketSourceCode;
	
	private String strRoomLimit;
	
	private String strIncomeHeadCode;
	
	private String strNoRoomsBooked;
	
	private String tmePickUpTime;
	
	private String tmeDropTime;
	
	private String strPackageName;
	
	private String strPackageCode;
	
	private String strTotalPackageAmt;
	
	public String getStrTotalPackageAmt() {
		return strTotalPackageAmt;
	}

	public void setStrTotalPackageAmt(String strTotalPackageAmt) {
		this.strTotalPackageAmt = strTotalPackageAmt;
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

	private List<clsReservationRoomRateModelDtl> listReservationRoomRateDtl = new ArrayList<clsReservationRoomRateModelDtl>();
	
	private List<clsRoomPackageDtl> listRoomPackageDtl = new ArrayList<clsRoomPackageDtl>();

	public List<clsRoomPackageDtl> getListRoomPackageDtl() {
		return listRoomPackageDtl;
	}

	public void setListRoomPackageDtl(List<clsRoomPackageDtl> listRoomPackageDtl) {
		this.listRoomPackageDtl = listRoomPackageDtl;
	}

	// Setter-Getter Methods
	public String getStrReservationNo() {
		return strReservationNo;
	}

	public void setStrReservationNo(String strReservationNo) {
		this.strReservationNo = strReservationNo;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrGuestCode() {
		return strGuestCode;
	}

	public void setStrGuestCode(String strGuestCode) {
		this.strGuestCode = strGuestCode;
	}

	public String getStrCorporateCode() {
		return strCorporateCode;
	}

	public void setStrCorporateCode(String strCorporateCode) {
		this.strCorporateCode = strCorporateCode;
	}

	public String getStrBookingTypeCode() {
		return strBookingTypeCode;
	}

	public void setStrBookingTypeCode(String strBookingTypeCode) {
		this.strBookingTypeCode = strBookingTypeCode;
	}

	public String getDteArrivalDate() {
		return dteArrivalDate;
	}

	public void setDteArrivalDate(String dteArrivalDate) {
		this.dteArrivalDate = dteArrivalDate;
	}

	public String getDteDepartureDate() {
		return dteDepartureDate;
	}

	public void setDteDepartureDate(String dteDepartureDate) {
		this.dteDepartureDate = dteDepartureDate;
	}

	public int getIntNoOfNights() {
		return intNoOfNights;
	}

	public void setIntNoOfNights(int intNoOfNights) {
		this.intNoOfNights = intNoOfNights;
	}

	public String getStrContactPerson() {
		return strContactPerson;
	}

	public void setStrContactPerson(String strContactPerson) {
		this.strContactPerson = strContactPerson;
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = strEmailId;
	}

	public String getStrBillingInstCode() {
		return strBillingInstCode;
	}

	public void setStrBillingInstCode(String strBillingInstCode) {
		this.strBillingInstCode = strBillingInstCode;
	}

	public String getStrBookerCode() {
		return strBookerCode;
	}

	public void setStrBookerCode(String strBookerCode) {
		this.strBookerCode = strBookerCode;
	}

	public String getStrBusinessSourceCode() {
		return strBusinessSourceCode;
	}

	public void setStrBusinessSourceCode(String strBusinessSourceCode) {
		this.strBusinessSourceCode = strBusinessSourceCode;
	}

	public String getStrAgentCode() {
		return strAgentCode;
	}

	public void setStrAgentCode(String strAgentCode) {
		this.strAgentCode = strAgentCode;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getDteCancelDate() {
		return dteCancelDate;
	}

	public void setDteCancelDate(String dteCancelDate) {
		this.dteCancelDate = dteCancelDate;
	}

	public String getDteConfirmDate() {
		return dteConfirmDate;
	}

	public void setDteConfirmDate(String dteConfirmDate) {
		this.dteConfirmDate = dteConfirmDate;
	}

	public String getStrCancelReservation() {
		return strCancelReservation;
	}

	public void setStrCancelReservation(String strCancelReservation) {
		this.strCancelReservation = strCancelReservation;
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

	public List<clsReservationDetailsBean> getListReservationDetailsBean() {
		return listReservationDetailsBean;
	}

	public void setListReservationDetailsBean(List<clsReservationDetailsBean> listReservationDetailsBean) {
		this.listReservationDetailsBean = listReservationDetailsBean;
	}

	public String getTmeArrivalTime() {
		return tmeArrivalTime;
	}

	public void setTmeArrivalTime(String tmeArrivalTime) {
		this.tmeArrivalTime = tmeArrivalTime;
	}

	public String getTmeDepartureTime() {
		return tmeDepartureTime;
	}

	public void setTmeDepartureTime(String tmeDepartureTime) {
		this.tmeDepartureTime = tmeDepartureTime;
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

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = (String) setDefaultValue(strRoomNo, "");
		;
	}

	public void setStrLastName(String strLastName) {
		this.strLastName = strLastName;
	}

	public String getStrPropertyName() {
		return strPropertyName;
	}

	public void setStrPropertyName(String strPropertyName) {
		this.strPropertyName = strPropertyName;
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
		this.strExtraBedCode = (String) setDefaultValue(strExtraBedCode, "");
	}

	public String getStrRoomDesc() {
		return strRoomDesc;
	}

	public void setStrRoomDesc(String strRoomDesc) {
		this.strRoomDesc = (String) setDefaultValue(strRoomDesc, "");
	}

	public String getStrExtraBedDesc() {
		return strExtraBedDesc;
	}

	public void setStrExtraBedDesc(String strExtraBedDesc) {
		this.strExtraBedDesc = (String) setDefaultValue(strExtraBedDesc, "");
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

	public int getIntNoRoomsBooked() {
		return intNoRoomsBooked;
	}

	public void setIntNoRoomsBooked(int intNoRoomsBooked) {
		this.intNoRoomsBooked = intNoRoomsBooked;
	}

	public String getStrPayeeGuestCode() {
		return strPayeeGuestCode;
	}

	public void setStrPayeeGuestCode(String strPayeeGuestCode) {
		this.strPayeeGuestCode = strPayeeGuestCode;
	}

	public String getStrOTANo() {
		return strOTANo;
	}

	public void setStrOTANo(String strOTANo) {
		this.strOTANo = strOTANo;
	}

	public String getStrMarketSourceCode() {
		return strMarketSourceCode;
	}

	public void setStrMarketSourceCode(String strMarketSourceCode) {
		this.strMarketSourceCode = strMarketSourceCode;
	}

	public String getStrRoomLimit() {
		return strRoomLimit;
	}

	public void setStrRoomLimit(String strRoomLimit) {
		this.strRoomLimit = strRoomLimit;
	}

	public String getStrIncomeHeadCode() {
		return strIncomeHeadCode;
	}

	public void setStrIncomeHeadCode(String strIncomeHeadCode) {
		this.strIncomeHeadCode = strIncomeHeadCode;
	}

	public List<clsReservationRoomRateModelDtl> getListReservationRoomRateDtl() {
		return listReservationRoomRateDtl;
	}

	public void setListReservationRoomRateDtl(List<clsReservationRoomRateModelDtl> listReservationRoomRateDtl) {
		this.listReservationRoomRateDtl = listReservationRoomRateDtl;
	}

	public String getStrNoRoomsBooked() {
		return strNoRoomsBooked;
	}

	public void setStrNoRoomsBooked(String strNoRoomsBooked) {
		this.strNoRoomsBooked = strNoRoomsBooked;
	}

	public String getTmePickUpTime() {
		return tmePickUpTime;
	}

	public void setTmePickUpTime(String tmePickUpTime) {
		this.tmePickUpTime = tmePickUpTime;
	}

	public String getTmeDropTime() {
		return tmeDropTime;
	}

	public void setTmeDropTime(String tmeDropTime) {
		this.tmeDropTime = tmeDropTime;
	}

	


}
